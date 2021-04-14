package draylar.horizon.registry;

import dev.latvian.kubejs.script.ScriptType;
import draylar.horizon.MinersHorizon;
import draylar.horizon.config.OreConfig;
import draylar.horizon.kubejs.MinersHorizonConfigEventJS;
import draylar.horizon.kubejs.MinersHorizonOreEventJS;
import draylar.horizon.world.MinersHorizonChunkGenerator;
import draylar.horizon.world.MiningCaveCarver;
import draylar.horizon.world.RockySurfaceBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HorizonWorld {

    public static final RegistryKey<World> MINERS_HORIZON = RegistryKey.of(Registry.DIMENSION, MinersHorizon.id("miners_horizon"));
    public static final List<ConfiguredFeature<?, ?>> ORES = new ArrayList<>();

    // SURFACE BUILDERS
    public static final SurfaceBuilder<TernarySurfaceConfig> ROCKY_SURFACE = Registry.register(Registry.SURFACE_BUILDER, MinersHorizon.id("rocky_surface"), new RockySurfaceBuilder());
    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CONFIGURED_ROCKY_SURFACE = register("rocky_surface", ROCKY_SURFACE.withConfig(new TernarySurfaceConfig(
            Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.STONE.getDefaultState()
    )));

    // FEATURES
    public static final Carver<ProbabilityConfig> MINING_CAVE = Registry.register(Registry.CARVER, MinersHorizon.id("mining_cave"), new MiningCaveCarver(ProbabilityConfig.CODEC, 256));
    public static final ConfiguredCarver<ProbabilityConfig> CONFIGURED_MINING_CAVE = register("mining_cave", MINING_CAVE.configure(new ProbabilityConfig(0.3f)));
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

        // Filter config ores if KubeJS is loaded
        if(FabricLoader.getInstance().isModLoaded("kubejs")) {
            ores = ores.stream().filter(config -> {
                MinersHorizonConfigEventJS event = new MinersHorizonConfigEventJS(config);
                event.post(ScriptType.STARTUP, "horizon.config");
                return !event.isCancelled();
            }).collect(Collectors.toList());
        }

        // Load/build all ore configs if KubeJS is loaded
        if(FabricLoader.getInstance().isModLoaded("kubejs")) {
            ores.addAll(MinersHorizonOreEventJS.build());
        }

        // Register each ore
        for (OreConfig ore : ores) {
            ConfiguredFeature<?, ?> feature = register(
                    String.format("%s_%d_%d_%d", new Identifier(ore.block).getPath(), ore.size, ore.count, ore.maxY),
                    Feature.ORE.configure(new OreFeatureConfig(
                            OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                            Registry.BLOCK.get(new Identifier(ore.block)).getDefaultState(),
                            ore.size)
                    )
                            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(ore.minY, ore.minY, ore.maxY)))
                            .spreadHorizontally()
                            .repeat(ore.count));

            ORES.add(feature);
        }
    }
}
