package dev.foltz.stoneage.interactions.inventory;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

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
        Random rand = new Random();
        Hand hand = player.getHeldItemOffhand().isEmpty() ? Hand.MAIN_HAND : Hand.OFF_HAND;
        if (hand == Hand.MAIN_HAND && player.getHeldItemMainhand().isEmpty()) return false;
        System.out.println("Dropping hand: " + hand);
        double y = player.posY - (double)0.3F + (double)player.getEyeHeight();
        ItemStack stackDrop = player.getHeldItem(hand).copy();
        stackDrop.setCount(1);
        ItemEntity itementity = new ItemEntity(player.world, player.posX, y, player.posZ, stackDrop);
        itementity.setPickupDelay(40);
        float f7 = 0.3F;
        float f8 = MathHelper.sin(player.rotationPitch * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(player.rotationPitch * ((float)Math.PI / 180F));
        float f3 = MathHelper.sin(player.rotationYaw * ((float)Math.PI / 180F));
        float f4 = MathHelper.cos(player.rotationYaw * ((float)Math.PI / 180F));
        float f5 = rand.nextFloat() * ((float)Math.PI * 2F);
        float f6 = 0.02F * rand.nextFloat();
        itementity.setMotion((double)(-f3 * f2 * 0.3F), 0, (double)(f4 * f2 * 0.3F));


        if (!player.world.isRemote) {
            player.getEntityWorld().addEntity(itementity);
            player.getHeldItem(hand).shrink(1);
        }

        return false;
    }
}
