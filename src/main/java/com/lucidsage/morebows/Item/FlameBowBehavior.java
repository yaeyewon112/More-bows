package com.lucidsage.morebows.item;

import net.minecraft.entity.projectile.EntityArrow;


public class FlameBowBehavior extends BasicBowBehavior
{
    @Override
    protected void ArrowModifier(EntityArrow entityarrow)
    {
        entityarrow.setFire(70);
    }
}
