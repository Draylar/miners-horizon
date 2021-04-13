package draylar.horizon.config;


import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "minershorizon")
public class MinersHorizonConfig implements ConfigData {

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
    public boolean enableMineshafts = true;
    public boolean enableLakes = true;
    public boolean enableCaves = true;
    public boolean enableCanyons = true;

    public OreConfig[] oreConfigList = new OreConfig[]
            {
                    // default layer
                    new OreConfig("minecraft:coal_ore", 17, 30, 255),
                    new OreConfig("minecraft:iron_ore", 9, 30, 255),
                    new OreConfig("minecraft:gold_ore", 9, 2, 140),
                    new OreConfig("minecraft:redstone_ore", 8, 8, 120),
                    new OreConfig("minecraft:lapis_ore", 8, 2, 120),
                    new OreConfig("minecraft:diamond_ore", 8, 1, 120),
                    new OreConfig("minecraft:emerald_ore", 8, 1, 120),

                    // zone 1
                    new OreConfig("minecraft:coal_ore", 17, 5, 100),
                    new OreConfig("minecraft:iron_ore", 9, 5, 100),
                    new OreConfig("minecraft:gold_ore", 9, 2, 100),
                    new OreConfig("minecraft:redstone_ore", 8, 4, 100),
                    new OreConfig("minecraft:lapis_ore", 8, 2, 100),
                    new OreConfig("minecraft:diamond_ore", 8, 2, 100),
                    new OreConfig("minecraft:emerald_ore", 8, 2, 100),

                    // zone 2
                    new OreConfig("minecraft:gold_ore", 9, 4, 60),
                    new OreConfig("minecraft:redstone_ore", 8, 6, 60),
                    new OreConfig("minecraft:lapis_ore", 8, 4, 60),
                    new OreConfig("minecraft:diamond_ore", 8, 2, 60),
                    new OreConfig("minecraft:emerald_ore", 8, 2, 60),

                    // zone 3
                    new OreConfig("minecraft:gold_ore", 9, 4, 30),
                    new OreConfig("minecraft:redstone_ore", 8, 6, 30),
                    new OreConfig("minecraft:lapis_ore", 8, 4, 30),
                    new OreConfig("minecraft:diamond_ore", 8, 4, 30),
                    new OreConfig("minecraft:emerald_ore", 8, 4, 30),
            };
}
