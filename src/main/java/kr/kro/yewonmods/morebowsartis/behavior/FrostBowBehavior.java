package kr.kro.yewonmods.morebowsartis.behavior;

import kr.kro.yewonmods.morebowsartis.entity.EntityFrostArrow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;


public class FrostBowBehavior extends BasicBowBehavior
{
//    public void Firing(ItemStack stack, World worldIn, EntityPlayer playerIn, float timeUsed, Item item, double drawSpeedRelativeToVanilla)
//    {
//        super.Firing(stack, worldIn, playerIn, timeUsed, item, drawSpeedRelativeToVanilla);
//    }

    @Override
    protected EntityArrow getEntityArrow(World worldIn, EntityPlayer playerIn, float velocity) {
        return new EntityFrostArrow(worldIn, playerIn, velocity);
    }
}
