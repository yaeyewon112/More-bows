package com.lucidsage.morebows.behavior;

import com.lucidsage.morebows.entity.EntityEnderArrow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class EnderBowBehavior extends BasicBowBehavior
{
    long timeStart = 0;

    public void Firing(ItemStack stack, World worldIn, EntityPlayer playerIn, float timeUsed, Item item, double drawSpeedRelativeToVanilla)
    {
        super.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla);
        timeStart = worldIn.getTotalWorldTime();
    }

    @Override
    protected EntityArrow getEntityArrow(World worldIn, EntityPlayer playerIn, float velocity) {
        return new EntityEnderArrow(worldIn, playerIn, velocity);
    }
}
