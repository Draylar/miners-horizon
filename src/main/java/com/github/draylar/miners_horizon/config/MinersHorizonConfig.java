package com.github.draylar.miners_horizon.config;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;

@Config(name = "minershorizon")
public class MinersHorizonConfig implements ConfigData
{
    public String portalBlockId = "minecraft:chiseled_stone_bricks";

    public int zone1Start = 100;
    public int zone2Start = 60;
    public int zone3Start = 30;

    public int zone1HardnessModifier = 3;
    public int zone2HardnessModifier = 6;
    public int zone3HardnessModifier = 10;

    public int zone1StoneDarkness = 40;
    public int zone2StoneDarkness = 60;
    public int zone3StoneDarkness = 80;

    public int worldMidHeight = 200;
    public double mountainHeight = 2.4;

    public boolean enableMineshafts = true;
    public double mineshaftRarity = 0.008d;

    public boolean enableCaves = true;
    public float caveRarity = 10;

    public boolean enableCanyons = true;
    public float canyonRarity = 0.02F;

    public OreConfig[] oreConfigList  = new OreConfig[]
            {
                    // default layer
                    new OreConfig(
                            "minecraft:coal_ore",
                            17,
                            30,
                            100,
                            100,
                            255
                    ),
                    new OreConfig(
                            "minecraft:iron_ore",
                            9,
                            30,
                            100,
                            100,
                            255
                    ),
                    new OreConfig(
                            "minecraft:gold_ore",
                            9,
                            2,
                            100,
                            100,
                            140
                    ),
                    new OreConfig(
                            "minecraft:redstone_ore",
                            8,
                            8,
                            100,
                            100,
                            120
                    ),
                    new OreConfig(
                            "minecraft:lapis_ore",
                            8,
                            2,
                            100,
                            100,
                            120
                    ),
                    new OreConfig(
                            "minecraft:diamond_ore",
                            8,
                            1,
                            100,
                            100,
                            120
                    ),
                    new OreConfig(
                            "minecraft:emerald_ore",
                            8,
                            1,
                            100,
                            100,
                            120
                    ),


                    // zone 1
                    new OreConfig(
                            "minecraft:coal_ore",
                            17,
                            5,
                            60,
                            60,
                            100
                    ),
                    new OreConfig(
                            "minecraft:iron_ore",
                            9,
                            5,
                            60,
                            60,
                            100
                    ),
                    new OreConfig(
                            "minecraft:gold_ore",
                            9,
                            2,
                            60,
                            60,
                            100
                    ),
                    new OreConfig(
                            "minecraft:redstone_ore",
                            8,
                            4,
                            60,
                            60,
                            100
                    ),
                    new OreConfig(
                            "minecraft:lapis_ore",
                            8,
                            2,
                            60,
                            60,
                            100
                    ),
                    new OreConfig(
                            "minecraft:diamond_ore",
                            8,
                            2,
                            60,
                            60,
                            100
                    ),
                    new OreConfig(
                            "minecraft:emerald_ore",
                            8,
                            2,
                            60,
                            60,
                            100
                    ),

                    // zone 2
                    new OreConfig(
                            "minecraft:gold_ore",
                            9,
                            4,
                            30,
                            30,
                            60
                    ),
                    new OreConfig(
                            "minecraft:redstone_ore",
                            8,
                            6,
                            30,
                            30,
                            60
                    ),
                    new OreConfig(
                            "minecraft:lapis_ore",
                            8,
                            4,
                            30,
                            30,
                            60
                    ),
                    new OreConfig(
                            "minecraft:diamond_ore",
                            8,
                            2,
                            30,
                            30,
                            60
                    ),
                    new OreConfig(
                            "minecraft:emerald_ore",
                            8,
                            2,
                            30,
                            30,
                            60
                    ),

                    // zone 3
                    new OreConfig(
                            "minecraft:gold_ore",
                            9,
                            4,
                            0,
                            0,
                            30
                    ),
                    new OreConfig(
                            "minecraft:redstone_ore",
                            8,
                            6,
                            0,
                            0,
                            30
                    ),
                    new OreConfig(
                            "minecraft:lapis_ore",
                            8,
                            4,
                            0,
                            0,
                            30
                    ),
                    new OreConfig(
                            "minecraft:diamond_ore",
                            8,
                            4,
                            0,
                            0,
                            30
                    ),
                    new OreConfig(
                            "minecraft:emerald_ore",
                            8,
                            4,
                            0,
                            0,
                            30
                    ),
            };
}
