package dev.foltz.stoneage.item;

import dev.foltz.stoneage.block.SABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SAItemHandax extends Item {
    public SAItemHandax() {
        super(new Item.Properties()
            .group(SAItems.itemGroup)
            .defaultMaxDamage(60));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (blockstate.getBlock() == SABlocks.LOG.get()) {
            PlayerEntity playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
                playerentity.addItemStackToInventory(new ItemStack(SAItems.BARK.get(), 1 + playerentity.getRNG().nextInt(1)));
                if (playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (p) -> {
                        p.sendBreakAnimation(context.getHand());
                    });
                }
            }
            return ActionResultType.SUCCESS;
        }
        else {
            return ActionResultType.PASS;
        }
    }
}
