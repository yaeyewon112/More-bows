package kr.kro.yewonmods.morebowsartis.behavior;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class MultiBowBehavior extends BasicBowBehavior
{
    public void Firing(ItemStack stack, World worldIn, EntityPlayer playerIn, float timeUsed, Item item, double drawSpeedRelativeToVanilla)
    {
        super.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla);
        super.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla, 1.8F, true);

        // Random chance of a bonus arrow!
        if(itemRand.nextInt(4) == 0)
            super.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla, 1.6F, false);
    }
}
