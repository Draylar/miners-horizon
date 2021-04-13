package draylar.horizon.registry;

import draylar.horizon.MinersHorizon;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class HorizonBiomes {

    private static final Biome ROCKY_PLAINS = createRockyPlains();
    public static final RegistryKey<Biome> ROCKY_PLAINS_KEYS = RegistryKey.of(Registry.BIOME_KEY, MinersHorizon.id("rocky_plains"));

    private HorizonBiomes() {
        // NO-OP
    }

    public static void init() {
        Registry.register(BuiltinRegistries.BIOME, ROCKY_PLAINS_KEYS.getValue(), ROCKY_PLAINS);
    }

    public static Biome createRockyPlains() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();

        DefaultBiomeFeatures.addDungeons(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addExtraDefaultFlowers(settings);
        DefaultBiomeFeatures.addForestFlowers(settings);
        settings.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_LAVA);

        HorizonWorld.ORES.forEach(ore -> {
           settings.feature(GenerationStep.Feature.UNDERGROUND_ORES, ore);
        });

        // Use the Rocky Surface Builder, which has a mix of gravel, stone, and cobblestone on the surface.
        settings.surfaceBuilder(HorizonWorld.CONFIGURED_ROCKY_SURFACE);

        // Features that can be tweaked on and off in the config file
        {
            if (MinersHorizon.CONFIG.enableCaves) {
                // Add Canyons & our custom Mining Cave.
                settings.carver(GenerationStep.Carver.AIR, HorizonWorld.CONFIGURED_MINING_CAVE);
            }

            if (MinersHorizon.CONFIG.enableLakes) {
                settings.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_WATER);
            }

            if (MinersHorizon.CONFIG.enableCanyons) {
                settings.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
            }

            if (MinersHorizon.CONFIG.enableMineshafts) {
                settings.structureFeature(HorizonWorld.MINESHAFT);
            }
        }

        // Entity spawn settings
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        spawnSettings.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.BAT, 10, 8, 8));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITCH, 5, 1, 1));

        return new Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.PLAINS)
                .depth(0.125f)
                .scale(0.5f)
                .temperature(1.0f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x050533)
                        .fogColor(0xc0d8ff)
                        .skyColor(0x99bfff)
                        .grassColor(0x42b25a)
                        // TODO: special sound?
//                        .loopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .build();
    }
}
