package kr.kro.yewonmods.morebowsartis.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class EntityFrostArrow extends EntityArrow {

    public EntityFrostArrow(World worldIn, EntityLivingBase shooter, float velocity)
    {
        super(worldIn, shooter, velocity);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void setDead()
    {
        super.setDead();

        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        // Only listen to events that involve this entity
        if(     event.source == null ||
                event.source.getEntity() == null ||
                !event.source.getSourceOfDamage().isEntityEqual(this))
            return;

        // Apply slow effect to target
        event.entityLiving.setAIMoveSpeed(event.entityLiving.getAIMoveSpeed() / 2);
        System.out.println("Frost arrow attack hit!");
    }

    void onBlockHit(BlockPos target)
    {
        // Add snow to the ground where the arrow hit
        BlockPos blockOnTopOfGround = worldObj.getTopSolidOrLiquidBlock(target);

        // Limit how far up the snow effect can go
        if(blockOnTopOfGround.getY() - target.getY() < 2)
            worldObj.setBlockState(blockOnTopOfGround, Blocks.snow_layer.getDefaultState());
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        // Check to see if we have hit the ground
        if(arrowShake != 6)
            return;

        Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
        Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);

        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit == null)
            {
                BlockPos blockpos = movingobjectposition.getBlockPos();
                IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
                Block target = iblockstate.getBlock();

                if (target.getMaterial() != Material.air)
                {
                    onBlockHit(blockpos);
                }
            }
        }
    }

}
