package com.lucidsage.morebows.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class MultiBowBehavior implements IBowBehavior
{
    private Random rand = new Random();

    public void Firing(ItemStack stack, World worldIn, EntityPlayer playerIn, float timeUsed, Item item, double drawSpeedRelativeToVanilla)
    {
        BasicBowBehavior basic = new BasicBowBehavior();

        basic.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla);
        basic.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla, 1.8F, true);

        // Random chance of a bonus arrow!
        if(rand.nextInt(4) == 0)
            basic.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla, 1.6F, false);
    }
}
