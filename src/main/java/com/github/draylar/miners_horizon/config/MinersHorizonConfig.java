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
                        "minecraft:iron_ore",
                        8,
                        4,
                        0,
                        10,
                        50
                )
            };
}
