package dev.foltz.stoneage.block;

import dev.foltz.stoneage.rock.Rock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SABlockRock extends Block {

    private static final VoxelShape SMALL_AABB = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D);
    private static final VoxelShape MEDIUM_AABB = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);

    public final Rock rock;

    public SABlockRock(Rock rock) {
        super(Properties
                .create(Material.ROCK)
                .doesNotBlockMovement()
                .hardnessAndResistance(0.5F));
        this.rock = rock;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos below = pos.down();
        BlockState blockState = worldIn.getBlockState(below);
        return Block.doesSideFillSquare(blockState.getShape(worldIn, below), Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (rock.size) {
            case SMALL:
                return SMALL_AABB;
            case MEDIUM:
            default:
                return MEDIUM_AABB;
        }
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
