package com.github.draylar.fabric;

import com.github.draylar.fabric.dims.FabricDimensionType;
import com.github.draylar.fabric.world.ChunkGeneratorTypeWorkaround;
import com.github.draylar.fabric.world.FabricChunkGenerator;
import com.github.draylar.fabric.world.FabricDimension;
import com.github.draylar.fabric.world.biome.MiningDimensionBiome;
import com.github.draylar.fabric.world.feature.CobbleSpikeFeature;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.feature.Feature;

public class FabricDimensions implements ModInitializer
{
	public static DimensionType FABRIC_WORLD;

	public static ChunkGeneratorType<ChunkGeneratorConfig, FabricChunkGenerator> FABRIC_CHUNK_GENERATOR;
	public static Biome MINING_BIOME = Registry.register(Registry.BIOME, new Identifier("fabric", "mining_biome"), new MiningDimensionBiome());
	public static Feature COBBLE_SPIKE = Registry.register(Registry.FEATURE, new Identifier("fabric", "cobble_spike"), new CobbleSpikeFeature());


	@Override
	public void onInitialize()
	{
		initWorlds();
		Blocks.registerBlocks();
		FABRIC_CHUNK_GENERATOR = new ChunkGeneratorTypeWorkaround().getChunkGeneratorType(ChunkGeneratorConfig::new);

		Registry.register(
				Registry.ITEM,
				new Identifier("fabric", "debug"),
				new Item(new Item.Settings().itemGroup(ItemGroup.MISC))
				{
					@Override
					public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1)
					{
						if(hand_1 == Hand.MAIN && !world_1.isClient)
						{
							if(playerEntity_1.dimension == FabricDimensions.FABRIC_WORLD) playerEntity_1.changeDimension(DimensionType.OVERWORLD);
							else playerEntity_1.changeDimension(FabricDimensions.FABRIC_WORLD);
						}

						return super.use(world_1, playerEntity_1, hand_1);
					}
				}
		);
	}

	public static void initWorlds()
	{
		FABRIC_WORLD = new FabricDimensionType(new Identifier("fabric", "fabric"), 5, FabricDimension::new);
	}
}
