package dev.foltz.stoneage.interactions.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class InventoryActions {
    public static boolean swapOrCombineHands(PlayerEntity player) {
        ItemStack left = player.getHeldItemOffhand();
        ItemStack right = player.getHeldItemMainhand();
        if (left.getItem() == right.getItem() && left.getMaxStackSize() <= left.getCount() + right.getCount()) {
            left.grow(right.getCount());
            right.setCount(0);
            return false;
        }
        else {
            player.setHeldItem(Hand.MAIN_HAND, left);
            player.setHeldItem(Hand.OFF_HAND, right);
            return true;
        }
    }

    public static boolean splitHands(PlayerEntity player) {

        return false;
    }

    public static boolean dropItem(PlayerEntity player) {

        return false;
    }
}
