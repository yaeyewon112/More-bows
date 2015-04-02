package com.lucidsage.morebows.item;

import com.lucidsage.morebows.MoreBows;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.stats.StatList;
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
    ModelResourceLocation[] pullingModels = new ModelResourceLocation[3];

    public Bow(String inName, int damage, double inDrawSpeedRelativeToVanilla, int inEnchantability, EnumRarity inRarity, Object[] inRecipe)
    {
        name = inName;
        recipe = inRecipe;
        drawSpeedRelativeToVanilla = inDrawSpeedRelativeToVanilla;
        enchantability = inEnchantability;
        rarity = inRarity;
        this.setMaxDamage(damage);
        this.setUnlocalizedName(name);
    }

    public void init()
    {
        GameRegistry.registerItem(this, name);

        GameRegistry.addRecipe(new ItemStack(this, 1), recipe);
    }

    public void clientInit(ItemModelMesher itemModelMesher)
    {
        String baseModelName = MoreBows.MODID + ":" + name;

        ModelBakery.addVariantName(this, baseModelName);
        itemModelMesher.register(this, 0, new ModelResourceLocation(baseModelName, "inventory"));

        for (int i = 0; i < 3; i++) {
            String variantName = baseModelName + "_pulling_" + Integer.toString(i);
            pullingModels[i] = new ModelResourceLocation(variantName, "inventory");

            ModelBakery.addVariantName(this, variantName);
            itemModelMesher.register(this, i + 1, pullingModels[i]);
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
        return pullingModels[ Math.min((int)Math.floor((usedSoFar / timeToFullPull) * 2.0), 2) ];
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
        int j = this.getMaxItemUseDuration(stack) - timeLeft;
        net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(playerIn, stack, j);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
        j = event.charge;

        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || playerIn.inventory.hasItem(Items.arrow))
        {
            float f = (float)j / (float)(20.0 * drawSpeedRelativeToVanilla);
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(worldIn, playerIn, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, playerIn);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                playerIn.inventory.consumeInventoryItem(Items.arrow);
            }

            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

            if (!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(entityarrow);
            }
        }
    }
}
