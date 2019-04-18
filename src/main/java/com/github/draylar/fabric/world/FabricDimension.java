package com.github.draylar.fabric.world;

import com.github.draylar.fabric.FabricDimensions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.chunk.ChunkPos;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class FabricDimension extends Dimension
{
    public FabricDimension(World world_1, DimensionType dimensionType_1)
    {
        super(world_1, dimensionType_1);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator()
    {
        return FabricDimensions.FABRIC_CHUNK_GENERATOR.create(world, BiomeSourceType.FIXED.applyConfig(BiomeSourceType.FIXED.getConfig().setBiome(FabricDimensions.MINING_BIOME)), new ChunkGeneratorConfig());
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos var1, boolean var2)
    {
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int var1, int var2, boolean var3)
    {
        return null;
    }

    @Override
    public float getSkyAngle(long var1, float var3)
    {
        return 0;
    }

    @Override
    public boolean hasVisibleSky()
    {
        return false;
    }

    @Override
    public Vec3d getFogColor(float var1, float var2)
    {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public boolean canPlayersSleep()
    {
        return false;
    }

    @Override
    public boolean shouldRenderFog(int var1, int var2)
    {
        return false;
    }

    @Override
    public DimensionType getType()
    {
        return FabricDimensions.FABRIC_WORLD;
    }
}
