package dev.foltz.stoneage.interactions.tool;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ToolActions {
    public static final Map<Item, ToolAction> ACTION_MAP = new HashMap<>();

    public static void registerToolAction(Item item, ToolAction action) {
        if (ACTION_MAP.putIfAbsent(item, action) != null) {
            System.out.println("Double registering tool");
        }
    }

    // TODO: Make this more expressive (i.e. add block face information)
    public static boolean attempt(World world, PlayerEntity player, ItemStack tool, BlockPos pos) {
        Item item = tool.getItem();
        return ACTION_MAP.getOrDefault(item, (a, b, c, d) -> false).attempt(world, player, tool, pos);
    }
}
