package draylar.horizon.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import draylar.horizon.MinersHorizon;
import draylar.horizon.registry.HorizonBiomes;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MinersHorizonChunkGenerator extends NoiseChunkGenerator {

    public static final Codec<MinersHorizonChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.populationSource;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.seed;
        }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.settings;
        })).apply(instance, instance.stable(MinersHorizonChunkGenerator::new));
    });

    // use a map to determine where peaks are, and a map to determine how tall they are
    private final NoiseSampler surfaceNoise;
    private final int worldMidHeight = MinersHorizon.CONFIG.worldMidHeight;
    private final long seed;
    private final Supplier<ChunkGeneratorSettings> settings;
    private final ChunkRandom random;

    private MinersHorizonChunkGenerator(BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
        super(biomeSource, seed, settings);
        this.seed = seed;
        this.random = new ChunkRandom();
        random.setSeed(seed);
        this.surfaceNoise = new OctaveSimplexNoiseSampler(random, IntStream.rangeClosed(0, 4));
        this.settings = settings;
        this.horizontalNoiseResolution = 4;
        this.verticalNoiseResolution = 8;
        this.noiseSizeY = 256 / this.verticalNoiseResolution;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new MinersHorizonChunkGenerator(populationSource.withSeed(seed), seed, settings);
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // spawn in regular terrain, flattens out at around 220
                double multiplier = 0.0625D;
                int posX = x + chunk.getPos().getStartX();
                int posZ = z + chunk.getPos().getStartZ();

                double height = worldMidHeight + surfaceNoise.sample((posX * multiplier) / 5f, (posZ * multiplier) / 5f, multiplier, multiplier * x) * 7;
                for (int y = 0; y < height; y++) {
                    chunk.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.STONE.getDefaultState(), false);
                }

                // spawn in mountains
                double multiplier2 = 0.06;
                double height2 = worldMidHeight - 3 +
                        Math.pow(surfaceNoise.sample((posX * multiplier2) / 10f, (posZ * multiplier2) / 10f, multiplier2, multiplier2 * x) * 10, 3);

                if (height2 > 0) {
                    for (double y = height - 1; y < height2; y++) {
                        if (y < height2 - 1) {
                            chunk.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.STONE.getDefaultState(), false);
                        } else
                            chunk.setBlockState(new BlockPos(x, y, z), net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState(), false);
                    }
                }

                BuiltinRegistries.BIOME.get(HorizonBiomes.ROCKY_PLAINS_KEYS).buildSurface(new Random(234612362L * posX + -8264616432452L * posZ), chunk, posX, posZ, 255, surfaceNoise.sample(posX, posZ, 1, 1), Blocks.STONE.getDefaultState(), net.minecraft.block.Blocks.WATER.getDefaultState(), getSeaLevel(), seed);
            }
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(new BlockPos(x, 0, z), net.minecraft.block.Blocks.BEDROCK.getDefaultState(), false);
            }
        }
    }

    public int getSpawnHeight() {
        return getSeaLevel() + 1;
    }

    public int getSeaLevel() {
        return 70;
    }
}

