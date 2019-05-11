package com.github.draylar.miners_horizon.config;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;

@Config(name = "minershorizon")
public class MinersHorizonConfig implements ConfigData
{
    public int worldMidHeight = 150;
    public double mountainHeight = 2.5;

    public boolean enableMineshafts = true;
    public double mineshaftRarity = 0.008d;

    public OreConfig[] oreConfigList  = new OreConfig[]
            {
                    new OreConfig(
                            "minecraft:coal_ore",
                            15,
                            20,
                            0,
                            10,
                            256
                    ),
                    new OreConfig(
                            "minecraft:iron_ore",
                            15,
                            20,
                            0,
                            0,
                            256
                    ),
                    new OreConfig(
                            "minecraft:lapis_ore",
                            8,
                            10,
                            0,
                            0,
                            75
                    ),
                    new OreConfig(
                            "minecrat:gold_ore",
                            8,
                            10,
                            0,
                            0,
                            50
                    ),
                    new OreConfig(
                            "minecraft:redstone_ore",
                            8,
                            10,
                            0,
                            0,
                            75
                    ),
                    new OreConfig(
                            "minecraft:diamond_ore",
                            8,
                            2,
                            0,
                            0,
                            35),
                    new OreConfig(
                            "minecraft:emerald_ore",
                            8,
                            2,
                            0,
                            0,
                            35
                    )
            };
}
