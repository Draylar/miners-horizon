package com.github.draylar.miners_horizon.common.world.biome;

import com.github.draylar.miners_horizon.config.ConfigHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class MiningDimensionSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig>
{
    private static final Random rand = new Random();
    private static final int zone1 = ConfigHolder.configInstance.zone1Y;
    private static final int zone2 = ConfigHolder.configInstance.zone2Y;
    private static final int zone3 = ConfigHolder.configInstance.zone3Y;

    public MiningDimensionSurfaceBuilder()
    {
        super(TernarySurfaceConfig::deserialize);
    }

    @Override
    public void generate(Random var1, Chunk chunk, Biome var3, int vxr4, int z, int height, double noise, BlockState var9, BlockState var10, int var11, long var12, TernarySurfaceConfig var14)
    {
        generate(chunk, vxr4, z, height, var11, var14);
    }


    private static void generate(Chunk chunk, int x, int z, int worldHeight, int seaLevel, TernarySurfaceConfig surfaceBlocks)
    {
        // copied from tropical, thanks valo
        int x1 = x & 15;
        int z1 = z & 15;

        for (int j1 = worldHeight; j1 >= 0; --j1)
        {
            Block b = chunk.getBlockState(new BlockPos(x1, j1, z1)).getBlock();

            if (b == Blocks.STONE)
            {
                Block aboveState;
                BlockState material = Blocks.STONE.getDefaultState();

                if (j1 < 255)
                {
                    material = getUndergroundStone();

                    aboveState = chunk.getBlockState(new BlockPos(x1, j1 + 1, z1)).getBlock();
                    if (aboveState == Blocks.AIR)
                        material = getRandomStoneVariant();
                }

                chunk.setBlockState(new BlockPos(x1, j1, z1), material, false);
            }

            else if (b == Blocks.WATER)
                chunk.setBlockState(new BlockPos(x1, j1, z1), Blocks.WATER.getDefaultState(), false);

            else if (b == Blocks.GRASS_BLOCK)
                chunk.setBlockState(new BlockPos(x1, j1, z1), Blocks.GRASS_BLOCK.getDefaultState(), false);

            else
                chunk.setBlockState(new BlockPos(x1, j1, z1), Blocks.AIR.getDefaultState(), false);
        }
    }

    private static BlockState getRandomStoneVariant()
    {
        int d = rand.nextInt(3);
        switch(d)
        {
            case 0:
                return Blocks.STONE.getDefaultState();
            case 1:
                return Blocks.ANDESITE.getDefaultState();
            case 2:
                return Blocks.COBBLESTONE.getDefaultState();
        }

        return Blocks.STONE.getDefaultState();
    }

    private static BlockState getUndergroundStone()
    {
        return Blocks.STONE.getDefaultState();
    }
}
