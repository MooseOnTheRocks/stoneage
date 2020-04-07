package dev.foltz.stoneage.world.gen;

import dev.foltz.stoneage.world.gen.desc.WorldDesc;
import dev.foltz.stoneage.world.gen.desc.WorldDescComposite;
import dev.foltz.stoneage.world.gen.desc.WorldDescIsland;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

import java.util.ArrayList;

public class SAChunkGenerator extends ChunkGenerator<SAGenerationSettings> {
    protected final SharedSeedRandom randomSeed;
    private static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    private final WorldDesc worldDesc;

    public SAChunkGenerator(IWorld world, BiomeProvider provider, SAGenerationSettings settings) {
        super(world, provider, settings);
        this.randomSeed = new SharedSeedRandom(this.seed);

        long seed = world.getSeed();
        ArrayList<WorldDesc> composite = new ArrayList<>();
        composite.add(new WorldDescIsland(0, 0, 64, 128, getSeaLevel(), 15, seed));
        worldDesc = new WorldDescComposite(composite);
    }

    // makeBase generates the shape of the terrain.
    // generateSurface generates specific biome things.
    @Override
    public void makeBase(IWorld worldIn, IChunk chunkIn) {
        BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();
        Heightmap heightmap = chunkIn.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = chunkIn.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);

        ChunkPos chunkPos = chunkIn.getPos();
        int startX = chunkPos.getXStart();
        int startZ = chunkPos.getZStart();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                // Bottom layer
                chunkIn.setBlockState(blockpos.setPos(i, 0, j), BEDROCK, false);
                heightmap.update(i, 0, j, BEDROCK);
                heightmap1.update(i, 0, j, BEDROCK);
                // Everything above
                for (int k = 1; k < getMaxHeight(); k++) {
                    int blockX = startX + i;
                    int blockY = k;
                    int blockZ = startZ + j;

                    BlockState blockState = worldDesc.blockAt(blockX, blockY, blockZ);
                    if (blockState == AIR && blockY <= getSeaLevel()) {
                        blockState = WATER;
                    }

                    if (blockState != AIR) {
                        chunkIn.setBlockState(blockpos.setPos(i, k, j), blockState, false);
                        heightmap.update(i, k, j, blockState);
                        heightmap1.update(i, k, j, blockState);
                    }
                }
            }
        }
    }

    @Override
    public void generateSurface(IChunk chunk) {
        /*
        Biome[] biomes = chunk.getBiomes();
        ChunkPos chunkPos = chunk.getPos();
        int startX = chunkPos.getXStart();
        int startZ = chunkPos.getZStart();
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setBaseChunkSeed(chunkPos.x, chunkPos.z);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int x = startX + i;
                int z = startZ + j;
                Biome biome = biomes[i + j * 16];
                int topY = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, i, j) + 1;
                double surfaceDepth = this.surfaceDepthNoise.noiseAt((double)x * 0.0625D, (double)z * 0.0625D, 0.0625D, (double)i * 0.0625D);
                // Random random, IChunk chunkIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed
                // System.out.println("Surface depth [" + topY + "] (" + x + ", " + z + "}) = " + surfaceDepth);
                // biome.buildSurface cares about defaultBlock and defaultFluid:
                // The biome will override these two with its own blocks, so
                // if the block is not one of those two it will not let the biome generate.
                biome.buildSurface(sharedseedrandom, chunk, x, z, topY, surfaceDepth, this.defaultBlock, this.defaultFluid,40, this.world.getSeed());
            }
        }
        */
    }

    @Override
    public int getGroundHeight() {
        return 64;
    }

    @Override
    public int func_222529_a(int x, int z, Heightmap.Type heightmapType) {
        for (int y = this.getMaxHeight(); y > 0; y--) {
            BlockState blockstate = worldDesc.blockAt(x, y, z);
            if (blockstate != AIR && heightmapType.getHeightLimitPredicate().test(blockstate)) {
                return y + 1;
            }
        }

        return 0;
    }
}
