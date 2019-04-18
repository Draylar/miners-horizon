package com.github.draylar.fabric.world;

import com.github.draylar.fabric.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class FabricChunkGenerator extends SurfaceChunkGenerator<ChunkGeneratorConfig>
{
    private final NoiseSampler surfaceDepthNoise;
    private final OpenSimplexNoise towerNoise;

    public FabricChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, ChunkGeneratorConfig config) {
        super(iWorld_1, biomeSource_1, 4, 8, 256, config, true);
        surfaceDepthNoise = new OctaveSimplexNoiseSampler(iWorld_1.getRandom(), 4);
        towerNoise = new OpenSimplexNoise(iWorld_1.getRandom().nextLong());
        this.random.consume(2620);
    }

    @Override
    public void buildSurface(Chunk chunk_1)
    {
        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                for (int y = 0; y < 200 + surfaceDepthNoise.sample((x + chunk_1.getPos().getStartX()) * 0.0625D, (z + chunk_1.getPos().getStartZ()) * 0.0625D, 0.0625D, 0.0625D * x); y++)
                {
                    if(y < 50) chunk_1.setBlockState(new BlockPos(x, y, z), Blocks.COMPRESSED_STONE.getDefaultState(), false);
                    else if (y < 100) chunk_1.setBlockState(new BlockPos(x, y, z), Blocks.REINFORCED_STONE.getDefaultState(), false);
                    else if (y < 150) chunk_1.setBlockState(new BlockPos(x, y, z), Blocks.HARDENED_STONE.getDefaultState(), false);
                    else
                    {
                        int rand = world.getRandom().nextInt(5);
                        if(rand == 0 || rand == 1) chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.STONE.getDefaultState(), false);
                        if (rand == 2) chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.COBBLESTONE.getDefaultState(), false);
                        if (rand == 3) chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.GRAVEL.getDefaultState(), false);
                        if (rand == 4) chunk_1.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.ANDESITE.getDefaultState(), false);
                    }
                }
            }
        }

        if(towerNoise.eval(chunk_1.getPos().getStartX(), chunk_1.getPos().getStartZ()) > .3) System.out.println("adding tower");
        // todo: place bedrock
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

