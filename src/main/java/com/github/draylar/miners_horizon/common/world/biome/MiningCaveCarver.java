package com.github.draylar.miners_horizon.common.world.biome;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;

public class MiningCaveCarver extends CaveCarver
{
    public MiningCaveCarver(int int_1)
    {
        super(ProbabilityConfig::deserialize, int_1);
        this.alwaysCarvableBlocks = ImmutableSet.of(
                Blocks.STONE,
                Blocks.COBBLESTONE,
                com.github.draylar.miners_horizon.common.Blocks.COMPRESSED_STONE,
                com.github.draylar.miners_horizon.common.Blocks.HARDENED_STONE,
                com.github.draylar.miners_horizon.common.Blocks.REINFORCED_STONE,
                Blocks.GRANITE,
                Blocks.DIORITE,
                Blocks.ANDESITE,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.PODZOL,
                Blocks.GRASS_BLOCK,
                Blocks.TERRACOTTA,
                Blocks.WHITE_TERRACOTTA,
                Blocks.ORANGE_TERRACOTTA,
                Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA,
                Blocks.YELLOW_TERRACOTTA,
                Blocks.LIME_TERRACOTTA,
                Blocks.PINK_TERRACOTTA,
                Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA,
                Blocks.CYAN_TERRACOTTA,
                Blocks.PURPLE_TERRACOTTA,
                Blocks.BLUE_TERRACOTTA,
                Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA,
                Blocks.RED_TERRACOTTA,
                Blocks.BLACK_TERRACOTTA,
                Blocks.SANDSTONE,
                Blocks.RED_SANDSTONE,
                Blocks.MYCELIUM,
                Blocks.SNOW,
                Blocks.PACKED_ICE);
    }
}
