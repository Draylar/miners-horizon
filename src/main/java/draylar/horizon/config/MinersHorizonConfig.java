package draylar.horizon.config;


import draylar.omegaconfig.api.Config;
import draylar.omegaconfig.api.Syncing;

public class MinersHorizonConfig implements Config {

    @Syncing public int zone1Start = 100;
    @Syncing public int zone2Start = 60;
    @Syncing public int zone3Start = 30;

    @Syncing public int zone1HardnessModifier = 3;
    @Syncing public int zone2HardnessModifier = 6;
    @Syncing public int zone3HardnessModifier = 10;

    @Syncing public int zone1StoneDarkness = 40;
    @Syncing public int zone2StoneDarkness = 60;
    @Syncing public int zone3StoneDarkness = 80;

    public int worldMidHeight = 200;
    public boolean enableMineshafts = true;
    public boolean enableLakes = true;
    public boolean enableCaves = true;
    public boolean enableCanyons = true;

    public OreConfig[] oreConfigList = new OreConfig[]
            {
                    // default layer
                    new OreConfig("minecraft:coal_ore", 17, 30, 100, 255),
                    new OreConfig("minecraft:iron_ore", 9, 30, 100, 255),
                    new OreConfig("minecraft:gold_ore", 9, 2, 100, 140),
                    new OreConfig("minecraft:redstone_ore", 8, 8, 100, 120),
                    new OreConfig("minecraft:lapis_ore", 8, 2, 100, 120),
                    new OreConfig("minecraft:diamond_ore", 8, 1, 100, 120),
                    new OreConfig("minecraft:emerald_ore", 8, 1, 100, 120),

                    // zone 1
                    new OreConfig("minecraft:coal_ore", 17, 5, 60, 100),
                    new OreConfig("minecraft:iron_ore", 9, 5, 60, 100),
                    new OreConfig("minecraft:gold_ore", 9, 2, 60, 100),
                    new OreConfig("minecraft:redstone_ore", 8, 4, 60, 100),
                    new OreConfig("minecraft:lapis_ore", 8, 2, 60, 100),
                    new OreConfig("minecraft:diamond_ore", 8, 2, 60, 100),
                    new OreConfig("minecraft:emerald_ore", 8, 2, 60, 100),

                    // zone 2
                    new OreConfig("minecraft:gold_ore", 9, 4, 30, 60),
                    new OreConfig("minecraft:redstone_ore", 8, 6, 30, 60),
                    new OreConfig("minecraft:lapis_ore", 8, 4, 30, 60),
                    new OreConfig("minecraft:diamond_ore", 8, 2, 30, 60),
                    new OreConfig("minecraft:emerald_ore", 8, 2, 30, 60),

                    // zone 3
                    new OreConfig("minecraft:gold_ore", 9, 4, 0, 30),
                    new OreConfig("minecraft:redstone_ore", 8, 6, 0, 30),
                    new OreConfig("minecraft:lapis_ore", 8, 4, 0, 30),
                    new OreConfig("minecraft:diamond_ore", 8, 4, 0, 30),
                    new OreConfig("minecraft:emerald_ore", 8, 4, 0, 30),
            };

    @Override
    public String getName() {
        return "minershorizon";
    }
}
