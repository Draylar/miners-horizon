package com.github.draylar.miners_horizon.common.world;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.common.Blocks;
import com.github.draylar.miners_horizon.config.ConfigHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

import java.util.Random;

public class MinersHorizonChunkGenerator extends SurfaceChunkGenerator<ChunkGeneratorConfig>
{
    // use a map to determine where peaks are, and a map to determine how tall they are
    private final NoiseSampler surfaceNoise;
    private final int worldMidHeight = ConfigHolder.configInstance.worldMidHeight;
    private final int zone_1 = ConfigHolder.configInstance.zone1Y;
    private final int zone_2 = ConfigHolder.configInstance.zone2Y;
    private final int zone_3 = ConfigHolder.configInstance.zone3Y;

    public MinersHorizonChunkGenerator(IWorld world, BiomeSource biomeSource_1, ChunkGeneratorConfig config) {
        super(world, biomeSource_1, 4, 8, 256, config, true);
        this.random.consume(2620);
        surfaceNoise = new OctaveSimplexNoiseSampler(this.random, 4);

    }

    @Override
    public void buildSurface(Chunk chunk_1)
    {
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                // spawn in regular terrain, flattens out at around 220
                double multiplier = 0.0625D;
                int posX = x + chunk_1.getPos().getStartX();
                int posZ = z + chunk_1.getPos().getStartZ();

                double height = worldMidHeight + surfaceNoise.sample(posX * multiplier, posZ * multiplier, multiplier, multiplier * x);
                for (int y = 0; y < height; y++)
                {
                    if (y < zone_3)
                        chunk_1.setBlockState(new BlockPos(x, y, z), Blocks.COMPRESSED_STONE.getDefaultState(), false);
                    else if (y < zone_2)
                        chunk_1.setBlockState(new BlockPos(x, y, z), Blocks.REINFORCED_STONE.getDefaultState(), false);
                    else if (y < zone_1)
                        chunk_1.setBlockState(new BlockPos(x, y, z), Blocks.HARDENED_STONE.getDefaultState(), false);
                    else if (y < height - 1)
                    {
                        int rand = world.getRandom().nextInt(4);
                        if (rand == 0 || rand == 1)
                            chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.STONE.getDefaultState(), false);
                        if (rand == 2)
                            chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.COBBLESTONE.getDefaultState(), false);
                        if (rand == 3)
                            chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.ANDESITE.getDefaultState(), false);
                    }
                }

                // spawn in mountains
                double multiplier2 = 0.06;
                double height2 = worldMidHeight - 3 + Math.pow(surfaceNoise.sample(posX * multiplier2, posZ * multiplier2, multiplier2, multiplier2 * x), ConfigHolder.configInstance.mountainHeight);

                if (height2 > 0)
                {
                    for (double y = height - 1; y < height2; y++)
                    {
                        if (y < height2 - 1)
                        {
                            chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.STONE.getDefaultState(), false);
                        }

                        else chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState(), false);
                    }
                }

                MinersHorizon.MINING_BIOME.buildSurface(new Random(234612362L * posX + -8264616432452L * posZ), chunk_1, posX, posZ, 255, surfaceNoise.sample(posX, posZ, 1, 1), Blocks.COMPRESSED_STONE.getDefaultState(), net.minecraft.block.Blocks.WATER.getDefaultState(), getSeaLevel(), world.getSeed());
            }
        }

        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                chunk_1.setBlockState(new BlockPos(x, 0, z), net.minecraft.block.Blocks.BEDROCK.getDefaultState(), false);
            }
        }
    }

    public int getSpawnHeight() {
        return this.world.getSeaLevel() + 1;
    }

    public int getSeaLevel() {
        return 0;
    }

    @Override
    protected double[] computeNoiseRange(int i, int i1)
    {
        return new double[0];
    }

    @Override
    protected double computeNoiseFalloff(double v, double v1, int i)
    {
        return 0;
    }

    @Override
    protected void sampleNoiseColumn(double[] doubles, int i, int i1)
    {

    }
}

