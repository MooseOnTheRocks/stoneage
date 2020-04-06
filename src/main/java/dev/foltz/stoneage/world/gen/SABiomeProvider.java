package dev.foltz.stoneage.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import dev.foltz.stoneage.world.biome.SABiomes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SABiomeProvider extends BiomeProvider {
    private final List<Biome> SPAWN_BIOMES = Lists.newArrayList(SABiomes.SAPLAINS.get());
    private final Biome biome = SABiomes.SAPLAINS.get();

    public SABiomeProvider() {
        System.out.println("Spawn biomes: ");
        for (Biome b : SPAWN_BIOMES) {
            System.out.println(" - " + b.getRegistryName());
        }
    }

    public List<Biome> getBiomesToSpawnIn() {
        return BIOMES_TO_SPAWN_IN;
    }

    public Biome getBiome(int x, int y) {
        return this.biome;
    }

    public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
        Biome[] abiome = new Biome[width * length];
        Arrays.fill(abiome, 0, width * length, this.biome);
        return abiome;
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
        return biomes.contains(this.biome) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) : null;
    }

    public boolean hasStructure(Structure<?> structureIn) {
        return this.hasStructureCache.computeIfAbsent(structureIn, this.biome::hasStructure);
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            this.topBlocksCache.add(this.biome.getSurfaceBuilderConfig().getTop());
        }

        return this.topBlocksCache;
    }

    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
        return Sets.newHashSet(this.biome);
    }
}
