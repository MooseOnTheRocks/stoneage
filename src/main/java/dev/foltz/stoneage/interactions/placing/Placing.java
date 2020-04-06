package dev.foltz.stoneage.interactions.placing;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Placing {
    private static final Map<Item, BlockState> placeable = new HashMap<>();

    public static void addPlaceable(Item item, BlockState blockState) {
        placeable.put(item, blockState);
    }

    public static boolean isPlaceable(Item item) {
        return placeable.containsKey(item);
    }

    public static boolean attempt(World world, PlayerEntity player, Hand hand, BlockPos pos, Direction face) {
        ItemStack heldItemStack = player.getHeldItem(hand);
        Item placeItem = heldItemStack.getItem();

        if (!isPlaceable(placeItem)) {
            return false;
        }

        BlockPos placePos = pos.offset(face);
        BlockState blockAt = world.getBlockState(placePos);
        if (blockAt.getBlock() == Blocks.AIR) {
            BlockState blockToPlace = placeable.get(placeItem);
            world.setBlockState(placePos, blockToPlace);
            heldItemStack.shrink(1);
            player.swingArm(hand);
            return true;
        }
        else {
            return false;
        }
    }
}
