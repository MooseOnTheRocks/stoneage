package dev.foltz.stoneage.world.gen.desc;

import dev.foltz.stoneage.block.SABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public abstract class WorldDesc {

    public static final BlockState AIR = Blocks.AIR.getDefaultState();
    // Shore
    public static final BlockState SAND = Blocks.SAND.getDefaultState();
    // Mainland
    public static final BlockState GRASS = Blocks.GRASS_BLOCK.getDefaultState();
    public static final BlockState DIRT = Blocks.DIRT.getDefaultState();
    public static final BlockState STONE = Blocks.STONE.getDefaultState();
    // Mainland -> Top decoration
    public static final BlockState SHRUB = Blocks.GRASS.getDefaultState();
    public static final BlockState ROCK_FLINT_SMALL = SABlocks.ROCK_FLINT_SMALL.get().getDefaultState();
    public static final BlockState ROCK_FLINT_MEDIUM = SABlocks.ROCK_FLINT_MEDIUM.get().getDefaultState();
    public static final BlockState ROCK_GRANITE_SMALL = SABlocks.ROCK_GRANITE_SMALL.get().getDefaultState();
    public static final BlockState ROCK_GRANITE_MEDIUM = SABlocks.ROCK_GRANITE_MEDIUM.get().getDefaultState();

    public abstract BlockState blockAt(int x, int y, int z);
}
