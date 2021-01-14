package com.github.draylar.miners_horizon;

import com.github.draylar.miners_horizon.common.Blocks;
import com.github.draylar.miners_horizon.common.world.biome.MiningCaveCarver;
import com.github.draylar.miners_horizon.common.world.biome.MiningDimensionBiome;
import com.github.draylar.miners_horizon.common.world.biome.MiningDimensionSurfaceBuilder;
import com.github.draylar.miners_horizon.common.world.dims.ChunkGeneratorTypeWorkaround;
import com.github.draylar.miners_horizon.common.world.dims.FabricDimensionType;
import com.github.draylar.miners_horizon.common.world.dims.MinersHorizonDimension;
import com.github.draylar.miners_horizon.common.world.feature.CustomOreFeature;
import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import com.github.draylar.miners_horizon.config.OreConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class MinersHorizon implements ModInitializer
{
	public static final DimensionType MINERS_HORIZON = new FabricDimensionType(getModIdentifier("miners_horizon"), 5, (world_1, dimensionType_1) -> new MinersHorizonDimension(world_1, dimensionType_1));
	public static final ChunkGeneratorType FABRIC_CHUNK_GENERATOR = new ChunkGeneratorTypeWorkaround().getChunkGeneratorType(ChunkGeneratorConfig::new);
	public static final SurfaceBuilder<TernarySurfaceConfig> MINING_BIOME_SURFACE = Registry.register(Registry.SURFACE_BUILDER, getModIdentifier("mining_surface"), new MiningDimensionSurfaceBuilder());
	public static final Carver<ProbabilityConfig> CAVE = Registry.register(Registry.CARVER, getModIdentifier("mining_carver"), new MiningCaveCarver(256));
	public static final Feature<OreFeatureConfig> CUSTOM_ORE_FEATURE = Registry.register(Registry.FEATURE, getModIdentifier("ore"), new CustomOreFeature(OreFeatureConfig::deserialize));
	public static Biome MINING_BIOME;

	@Override
	public void onInitialize()
	{
		Blocks.register();
		AutoConfig.register(MinersHorizonConfig.class, GsonConfigSerializer::new);
		MINING_BIOME = Registry.register(Registry.BIOME, getModIdentifier("mining_biome"), new MiningDimensionBiome());
	}

	public static Identifier getModIdentifier(String path)
	{
		return new Identifier("miners_horizon", path);
	}

	public static void addOres()
	{
		MinersHorizonConfig config = AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig();

		for (
				OreConfig oreConfig : config.oreConfigList)
		{
			MINING_BIOME.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Biome.configureFeature(
					MinersHorizon.CUSTOM_ORE_FEATURE,
					new OreFeatureConfig(
							OreFeatureConfig.Target.NATURAL_STONE,
							Registry.BLOCK.get(new Identifier(oreConfig.block)).getDefaultState(),
							oreConfig.size),
					Decorator.COUNT_RANGE,
					new RangeDecoratorConfig(
							oreConfig.count,
							oreConfig.bottomOffset,
							oreConfig.topOffset,
							oreConfig.maxY
					)
			));
		}
	}
}
