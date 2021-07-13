package kr.kro.yewonmods.morebowsartis.behavior;

import net.minecraft.entity.projectile.EntityArrow;


public class FlameBowBehavior extends BasicBowBehavior
{
    @Override
    protected void ArrowModifier(EntityArrow entityarrow)
    {
        entityarrow.setFire(70);
    }
}
