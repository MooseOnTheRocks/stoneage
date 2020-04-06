package dev.foltz.stoneage.interactions.gathering;

import dev.foltz.stoneage.block.SABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Gathering {
    private static final Map<BlockState, ItemStack> gatherable = new HashMap<>();

    public static void addGatherable(BlockState blockState, ItemStack itemStack) {
        gatherable.put(blockState, itemStack.copy());
    }

    public static boolean isGatherable(BlockState blockState) {
        return gatherable.containsKey(blockState);
    }

    public static ItemStack gatherResult(BlockState blockState) {
        return gatherable.getOrDefault(blockState, ItemStack.EMPTY).copy();
    }

    public static boolean attempt(World world, PlayerEntity player, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (isGatherable(blockState)) {
            ItemStack gatherStack = gatherResult(blockState);
            ItemStack stackMainhand = player.getHeldItemMainhand();
            ItemStack stackOffhand = player.getHeldItemOffhand();
            boolean didGather = false;
            if (stackMainhand.isItemEqual(gatherStack)
            && stackMainhand.getMaxStackSize() < stackMainhand.getCount() + gatherStack.getCount()) {
                stackMainhand.grow(gatherStack.getCount());
                player.setActiveHand(Hand.MAIN_HAND);
                didGather = true;
            }
            else if (stackOffhand.isItemEqual(gatherStack)
            && stackOffhand.getMaxStackSize() < stackOffhand.getCount() + gatherStack.getCount()) {
                stackOffhand.grow(gatherStack.getCount());
                player.setActiveHand(Hand.OFF_HAND);
                didGather = true;
            }
            else if (stackMainhand.isEmpty()) {
                player.setHeldItem(Hand.MAIN_HAND, gatherStack);
                player.setActiveHand(Hand.MAIN_HAND);
                didGather = true;
            }
            else if (stackOffhand.isEmpty()) {
                player.setHeldItem(Hand.OFF_HAND, gatherStack);
                player.setActiveHand(Hand.OFF_HAND);
                didGather = true;
            }

            if (didGather) {
                world.destroyBlock(pos, false);
                player.swingArm(player.getActiveHand());
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
