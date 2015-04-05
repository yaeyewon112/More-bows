package com.lucidsage.morebows.item;

import com.lucidsage.morebows.MoreBows;
import com.lucidsage.morebows.behavior.BasicBowBehavior;
import com.lucidsage.morebows.behavior.IBowBehavior;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bow extends ItemBow implements IItemToRegister {

    String name;
    Object[] recipe;
    int enchantability;
    double drawSpeedRelativeToVanilla;
    EnumRarity rarity;
    IBowBehavior behavior;
    String[] pullingModelNames = new String[3];

    public Bow(String inName, int damage, double inDrawSpeedRelativeToVanilla, int inEnchantability, EnumRarity inRarity, Object[] inRecipe)
    {
        this(inName, damage, inDrawSpeedRelativeToVanilla, inEnchantability, inRarity, new BasicBowBehavior(), inRecipe);
    }

    public Bow(String inName, int damage, double inDrawSpeedRelativeToVanilla, int inEnchantability, EnumRarity inRarity, IBowBehavior bowBehavior, Object[] inRecipe)
    {
        name = inName;
        recipe = inRecipe;
        drawSpeedRelativeToVanilla = inDrawSpeedRelativeToVanilla;
        enchantability = inEnchantability;
        rarity = inRarity;
        behavior = bowBehavior;
        this.setMaxDamage(damage);
        this.setUnlocalizedName(name);
    }

    public void init()
    {
        GameRegistry.registerItem(this, name);

        GameRegistry.addRecipe(new ItemStack(this, 1), recipe);
    }

    @SideOnly(Side.CLIENT)
    public void clientInit(ItemModelMesher itemModelMesher)
    {
        String baseModelName = MoreBows.MODID + ":" + name;

        ModelBakery.addVariantName(this, baseModelName);
        itemModelMesher.register(this, 0, new ModelResourceLocation(baseModelName, "inventory"));

        for (int i = 0; i < 3; i++) {
            String variantName = baseModelName + "_pulling_" + Integer.toString(i);
            pullingModelNames[i] = variantName;
            ModelResourceLocation pullingModel = new ModelResourceLocation(variantName, "inventory");

            ModelBakery.addVariantName(this, variantName);
            itemModelMesher.register(this, i + 1, pullingModel);
        }
    }

    @Override
    public int getItemEnchantability() { return enchantability; }

    @Override
    public EnumRarity getRarity(ItemStack stack) { return rarity; }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
    {
        if(useRemaining < 1) return null;

        double vanillaTimeToFullPull = 18;
        double timeToFullPull = vanillaTimeToFullPull * drawSpeedRelativeToVanilla;
        double usedSoFar = this.getMaxItemUseDuration(stack) - useRemaining;
        String variantName = pullingModelNames[ Math.min((int) Math.floor((usedSoFar / timeToFullPull) * 2.0), 2) ];

        return new ModelResourceLocation(variantName, "inventory");
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        int timeUsed = this.getMaxItemUseDuration(stack) - timeLeft;
        net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(playerIn, stack, timeUsed);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
        timeUsed = event.charge;

        behavior.Firing(stack, worldIn, playerIn, timeUsed, this, drawSpeedRelativeToVanilla);
    }
}
