package com.lucidsage.morebows;

import com.lucidsage.morebows.item.IBow;
import com.lucidsage.morebows.item.Bow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

    public static List<IBow> bows = new LinkedList<IBow>();

    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        bows.add(new Bow("stoneBow", 484, new Object[]{
                " $*",
                "#(*",
                " $*",
                '#', Items.stick,
                '*', Items.string,
                '$', Blocks.stone,
                '(', Items.bow
        }));

        bows.add(new Bow("ironBow", 550, new Object[]{
                " $*",
                "$(*",
                " $*",
                '*', Items.string,
                '$', Items.iron_ingot,
                '(', Items.bow
        }));
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        for (IBow b : bows) { b.init(); }

        proxy.init();
    }
}
