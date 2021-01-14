package com.github.draylar.miners_horizon.common.world.biome;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

import java.util.List;

public class MiningDimensionBiome extends Biome
{
    public MiningDimensionBiome()
    {
        super(new Settings().configureSurfaceBuilder(MinersHorizon.MINING_BIOME_SURFACE, SurfaceBuilder.GRASS_CONFIG).precipitation(Precipitation.NONE).category(Category.NONE).depth(1).scale(1).temperature(0.7F).downfall(0.0f).waterColor(4159204).waterFogColor(329011).parent(""));

        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addExtraDefaultFlowers(this);
        DefaultBiomeFeatures.addForestFlowers(this);

        MinersHorizonConfig config = AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig();

        if(config.enableCaves)
        {
            this.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(MinersHorizon.CAVE, new ProbabilityConfig(config.caveRarity)));
        }

        if(config.enableCanyons)
        {
            this.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(Carver.CANYON, new ProbabilityConfig(config.canyonRarity)));
        }

        if(config.enableMineshafts)
        {
            this.addStructureFeature(Feature.MINESHAFT, new MineshaftFeatureConfig(config.mineshaftRarity, MineshaftFeature.Type.MESA));
        }

        DefaultBiomeFeatures.addDefaultStructures(this);

        this.addSpawn(EntityCategory.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }

    @Override
    public List<SpawnEntry> getEntitySpawnList(EntityCategory entityCategory_1)
    {
        return super.getEntitySpawnList(entityCategory_1);
    }

    @Override
    public int getSkyColor(float float_1)
    {
        return 0x99bfff;
    }

    @Override
    public int getGrassColorAt(BlockPos blockPos_1)
    {
        return 0x42b25a;
    }
}
