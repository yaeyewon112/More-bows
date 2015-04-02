package com.lucidsage.morebows.item;

import com.lucidsage.morebows.MoreBows;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Bow extends ItemBow implements IBow {

    String name;
    Object[] recipe;

    public Bow(String inName, int damage, Object[] inRecipe)
    {
        name = inName;
        recipe = inRecipe;
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
        itemModelMesher.register(this, 0, new ModelResourceLocation(MoreBows.MODID + ":" + name, "inventory"));
    }

}
