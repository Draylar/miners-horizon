package draylar.horizon.world;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.CaveCarverConfig;

import java.util.Random;

public class MiningCaveCarver extends CaveCarver {

    public MiningCaveCarver(Codec<CaveCarverConfig> codec) {
        super(codec);

        this.alwaysCarvableBlocks = ImmutableSet.of(
                Blocks.STONE,
                Blocks.COBBLESTONE,
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

    @Override
    public float getTunnelSystemWidth(Random random) {
        return super.getTunnelSystemWidth(random) * 1.5f;
    }
}
