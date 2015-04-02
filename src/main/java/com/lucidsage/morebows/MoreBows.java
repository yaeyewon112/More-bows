package com.lucidsage.morebows;

import com.lucidsage.morebows.item.IItemToRegister;
import com.lucidsage.morebows.item.Bow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.LinkedList;
import java.util.List;

@Mod(modid = MoreBows.MODID, version = MoreBows.VERSION)
public class MoreBows
{
    public static final String MODID = "morebows";
    public static final String VERSION = "2.0.0";

    @SidedProxy(clientSide = "com.lucidsage.morebows.ClientProxy", serverSide= "com.lucidsage.morebows.CommonProxy")
    public static CommonProxy proxy;

    public static List<IItemToRegister> bows = new LinkedList<IItemToRegister>();

    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        bows.add(new Bow("stoneBow", 484, 1.0,
                Item.ToolMaterial.STONE.getEnchantability(),
                EnumRarity.COMMON,
                new Object[]{
                " $*",
                "#(*",
                " $*",
                '#', Items.stick,
                '*', Items.string,
                '$', Blocks.stone,
                '(', Items.bow
        }));

        Bow ironBow = new Bow("ironBow", 550, 0.8,
                Item.ToolMaterial.IRON.getEnchantability(),
                EnumRarity.COMMON,
                new Object[]{
                        " $*",
                        "$(*",
                        " $*",
                        '*', Items.string,
                        '$', Items.iron_ingot,
                        '(', Items.bow
                });
        bows.add(ironBow);

        bows.add(new Bow("goldBow", 68, 0.5,
                Item.ToolMaterial.GOLD.getEnchantability(),
                EnumRarity.UNCOMMON,
                new Object[]{
                " $*",
                "$(*",
                " $*",
                '*', Items.string,
                '$', Items.gold_ingot,
                '(', Items.bow
        }));

        bows.add(new Bow("diamondBow", 1016, 0.5,
                Item.ToolMaterial.EMERALD.getEnchantability(),
                EnumRarity.RARE,
                new Object[]{
                " $*",
                "#(*",
                " $*",
                '#', Items.iron_ingot,
                '*', Items.string,
                '$', Items.diamond,
                '(', Items.bow
        }));

        bows.add(new Bow("multiBow", 550, 0.6,
                Item.ToolMaterial.IRON.getEnchantability(),
                EnumRarity.RARE,
                new Object[]{
                        " (*",
                        "# *",
                        " (*",
                        '#', Items.iron_ingot,
                        '*', Items.string,
                        '(', ironBow
                }));
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        for (IItemToRegister b : bows) { b.init(); }

        proxy.init();
    }
}
