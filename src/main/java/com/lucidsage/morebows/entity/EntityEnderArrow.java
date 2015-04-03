package com.lucidsage.morebows.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityEnderArrow extends EntityArrow {
    long timeStart;
    int numberOfWaves;

    public EntityEnderArrow(World worldIn, EntityLivingBase shooter, float velocity)
    {
        super(worldIn, shooter, velocity);

        timeStart = worldIn.getTotalWorldTime();
        numberOfWaves = 3;
    }
    public EntityEnderArrow(World worldIn, int inNumberOfWaves)
    {
        super(worldIn);

        timeStart = worldIn.getTotalWorldTime();
        numberOfWaves = inNumberOfWaves;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!WaitToFireBarrage()) return;

        FireBarrage();

        timeStart = 0;
    }

    protected void FireBarrage()
    {
        if(numberOfWaves == 0)
            return;

        for (int i = 0; i < 5; i++) {
            FireArrow();
        }

        timeStart = 0;
    }

    @Override
    public void setDead()
    {
        super.setDead();

        if(timeStart < 1) return;

        FireBarrage();
    }

    boolean WaitToFireBarrage()
    {
        return timeStart > 0 && worldObj.getTotalWorldTime() - timeStart > 30;
    }

    protected void FireArrow()
    {
        EntityArrow entityarrow = new EntityEnderArrow(worldObj, numberOfWaves - 1);

        entityarrow.setPositionAndRotation(this.posX, this.posY + 20, this.posZ, 0.0F, 0.0F);
        entityarrow.setThrowableHeading(0, -.1, 0, 1.6F, 10F);
        entityarrow.canBePickedUp = 2;

        worldObj.playSoundAtEntity(entityarrow, "random.bow", 1.0F, 1.0F);
        worldObj.spawnEntityInWorld(entityarrow);
    }
}
