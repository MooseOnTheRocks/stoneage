package dev.foltz.stoneage.interactions.tool;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ToolAction {
    boolean attempt(World world, PlayerEntity player, ItemStack tool, BlockPos pos);
}
