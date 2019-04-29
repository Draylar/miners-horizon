package com.github.draylar.miners_horizon;

import com.github.draylar.miners_horizon.common.Blocks;
import com.github.draylar.miners_horizon.common.Items;
import com.github.draylar.miners_horizon.common.world.ChunkGeneratorTypeWorkaround;
import com.github.draylar.miners_horizon.common.world.MinersHorizonDimension;
import com.github.draylar.miners_horizon.common.world.biome.MiningDimensionBiome;
import com.github.draylar.miners_horizon.common.world.biome.MiningDimensionSurfaceBuilder;
import com.github.draylar.miners_horizon.common.world.dims.FabricDimensionType;
import com.github.draylar.miners_horizon.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class MinersHorizon implements ModInitializer
{
	static
	{
		new ModConfig().checkConfigFolder();
	}

	public static DimensionType FABRIC_WORLD = new FabricDimensionType(getModIdentifier("miners_horizon"), 5, MinersHorizonDimension::new);
	public static ChunkGeneratorType FABRIC_CHUNK_GENERATOR = new ChunkGeneratorTypeWorkaround().getChunkGeneratorType(ChunkGeneratorConfig::new);
	public static SurfaceBuilder<TernarySurfaceConfig> MINING_BIOME_SURFACE = Registry.register(Registry.SURFACE_BUILDER, getModIdentifier("mining_surface"), new MiningDimensionSurfaceBuilder());
	public static Biome MINING_BIOME = Registry.register(Registry.BIOME, getModIdentifier("mining_biome"), new MiningDimensionBiome());

	@Override
	public void onInitialize()
	{
		Blocks.register();
		Items.register();
	}

	public static Identifier getModIdentifier(String path)
	{
		return new Identifier("miners_horizon", path);
	}
}
