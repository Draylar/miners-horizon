package draylar.horizon.registry;

import draylar.horizon.MinersHorizon;
import draylar.horizon.config.OreConfig;
import draylar.horizon.world.MinersHorizonChunkGenerator;
import draylar.horizon.world.MiningCaveCarver;
import draylar.horizon.world.RockySurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorizonWorld {

    public static final RegistryKey<World> MINERS_HORIZON = RegistryKey.of(Registry.WORLD_KEY, MinersHorizon.id("miners_horizon"));
    public static final List<ConfiguredFeature<?, ?>> ORES = new ArrayList<>();

    // SURFACE BUILDERS
    public static final SurfaceBuilder<TernarySurfaceConfig> ROCKY_SURFACE = Registry.register(Registry.SURFACE_BUILDER, MinersHorizon.id("rocky_surface"), new RockySurfaceBuilder());
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_ROCKY_SURFACE = register("rocky_surface", ROCKY_SURFACE.withConfig(new TernarySurfaceConfig(
            Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState()
    )));

    // FEATURES
    public static final Carver<CaveCarverConfig> MINING_CAVE = Registry.register(Registry.CARVER, MinersHorizon.id("mining_cave"), new MiningCaveCarver(CaveCarverConfig.CAVE_CODEC));
    public static final ConfiguredCarver<CaveCarverConfig> CONFIGURED_MINING_CAVE = register("mining_cave", MINING_CAVE.configure(
            new CaveCarverConfig(
                    0.3F,
                    BiasedToBottomHeightProvider.create(YOffset.fixed(0), YOffset.fixed(256), 8),
                    ConstantFloatProvider.create(0.5F),
                    YOffset.aboveBottom(10),
                    false,
                    CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()),
                    ConstantFloatProvider.create(1.0F),
                    ConstantFloatProvider.create(1.0F),
                    ConstantFloatProvider.create(-0.7F)
            )));

    public static final ConfiguredStructureFeature<MineshaftFeatureConfig, ? extends StructureFeature<MineshaftFeatureConfig>> MINESHAFT
            = register("mineshaft", StructureFeature.MINESHAFT.configure(new MineshaftFeatureConfig(0.002F, MineshaftFeature.Type.NORMAL)));

    private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> register(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, MinersHorizon.id(id), configuredSurfaceBuilder);
    }

    private static <WC extends CarverConfig> ConfiguredCarver<WC> register(String id, ConfiguredCarver<WC> configuredCarver) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, MinersHorizon.id(id), configuredCarver);
    }

    private static <FC extends FeatureConfig, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String id, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, MinersHorizon.id(id), configuredStructureFeature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, MinersHorizon.id(id), configuredFeature);
    }

    public static void init() {
        Registry.register(Registry.CHUNK_GENERATOR, MinersHorizon.id("horizon"), MinersHorizonChunkGenerator.CODEC);
    }

    public static void loadOres() {
        List<OreConfig> ores = new ArrayList<>(Arrays.asList(MinersHorizon.CONFIG.oreConfigList));

        // Register each ore
        for (OreConfig ore : ores) {
            ConfiguredFeature<?, ?> feature = register(
                    String.format("%s_%d_%d_%d", new Identifier(ore.block).getPath(), ore.size, ore.count, ore.maxY),
                    Feature.ORE.configure(new OreFeatureConfig(
                            OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                            Registry.BLOCK.get(new Identifier(ore.block)).getDefaultState(),
                            ore.size)
                    )
                            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.aboveBottom(ore.minY), YOffset.belowTop(ore.maxY)))))
                            .spreadHorizontally()
                            .repeat(ore.count));

            ORES.add(feature);
        }
    }
}
