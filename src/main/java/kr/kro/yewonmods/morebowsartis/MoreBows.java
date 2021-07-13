package kr.kro.yewonmods.morebowsartis;

import kr.kro.yewonmods.morebowsartis.behavior.EnderBowBehavior;
import kr.kro.yewonmods.morebowsartis.behavior.FlameBowBehavior;
import kr.kro.yewonmods.morebowsartis.behavior.FrostBowBehavior;
import kr.kro.yewonmods.morebowsartis.behavior.MultiBowBehavior;
import kr.kro.yewonmods.morebowsartis.Item.*;
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
    public static final String VERSION = "2.1.0";

    @SidedProxy(clientSide = "kr.kro.yewonmods.morebowsartis.ClientProxy", serverSide= "kr.kro.yewonmods.morebowsartis.CommonProxy")
    public static CommonProxy proxy;

    public static List<IItemToRegister> bows = new LinkedList<IItemToRegister>();

    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        bows.add(new Bow("stone_bow", 484, 1.0,
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

        Bow ironBow = new Bow("iron_bow", 550, 0.8,
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

        bows.add(new Bow("gold_bow", 68, 0.5,
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

        bows.add(new Bow("diamond_bow", 1016, 0.5,
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

        bows.add(new Bow("multi_bow", 550, 0.6,
                Item.ToolMaterial.IRON.getEnchantability(),
                EnumRarity.RARE,
                new MultiBowBehavior(),
                new Object[]{
                        " (*",
                        "# *",
                        " (*",
                        '#', Items.iron_ingot,
                        '*', Items.string,
                        '(', ironBow
                }));

        bows.add(new Bow("flame_bow", 576, 0.6,
                Item.ToolMaterial.GOLD.getEnchantability(),
                EnumRarity.RARE,
                new FlameBowBehavior(),
                new Object[]{
                        " N|",
                        " #(",
                        " N|",
                        'N', Blocks.netherrack,
                        '|', Items.blaze_rod,
                        '#', Items.gold_ingot,
                        '(', ironBow
                }));

        bows.add(new Bow("ender_bow", 215, 1.1,
                Item.ToolMaterial.GOLD.getEnchantability(),
                EnumRarity.EPIC,
                new EnderBowBehavior(),
                new Object[]{
                        " #P",
                        " E(",
                        " #P",
                        'P', Items.ender_pearl,
                        'E', Items.ender_eye,
                        '#', Items.gold_ingot,
                        '(', ironBow
                }));

        bows.add(new Bow("frost_bow", 550, 1.3,
                Item.ToolMaterial.IRON.getEnchantability(),
                EnumRarity.RARE,
                new FrostBowBehavior(),
                new Object[]{
                        " $*",
                        "#(*",
                        " $*",
                        '#', Items.snowball,
                        '*', Items.string,
                        '$', Blocks.ice,
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
