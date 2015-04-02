package com.lucidsage.morebows.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

import java.util.Random;

public class BasicBowBehavior implements IBowBehavior {
    protected static Random itemRand = new Random();

    public void Firing(ItemStack stack, World worldIn, EntityPlayer playerIn, float timeUsed, Item item, double drawSpeedRelativeToVanilla)
    {
        Firing(stack,worldIn,playerIn,timeUsed,item,drawSpeedRelativeToVanilla, 2.0F, true);
    }

    public void Firing(
            ItemStack stack,
            World worldIn,
            EntityPlayer playerIn,
            float timeUsed,
            Item item,
            double drawSpeedRelativeToVanilla,
            float velocity,
            boolean consumesArrow)
    {
        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || playerIn.inventory.hasItem(Items.arrow))
        {
            float perMaxUsed = timeUsed / (float)(20.0 * drawSpeedRelativeToVanilla);
            perMaxUsed = (perMaxUsed * perMaxUsed + perMaxUsed * 2.0F) / 3.0F;

            if ((double)perMaxUsed < 0.1D)
            {
                return;
            }

            if (perMaxUsed > 1.0F)
            {
                perMaxUsed = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(worldIn, playerIn, perMaxUsed * velocity);

            if (perMaxUsed == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            entityarrow.setDamage(entityarrow.getDamage() + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack) * 0.5D + 0.5D);

            entityarrow.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack));

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, playerIn);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + perMaxUsed * 0.5F);

            if (flag || !consumesArrow)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                playerIn.inventory.consumeInventoryItem(Items.arrow);
            }

            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(item)]);

            if (!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(entityarrow);
            }
        }
    }
}
