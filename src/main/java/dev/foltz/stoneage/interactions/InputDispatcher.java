package dev.foltz.stoneage.interactions;

import com.google.common.eventbus.Subscribe;
import dev.foltz.stoneage.interactions.gathering.Gathering;
import dev.foltz.stoneage.interactions.handcrafting.HandCrafting;
import dev.foltz.stoneage.interactions.placing.Placing;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sun.security.ssl.HandshakeMessage;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InputDispatcher {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickItem(final PlayerInteractEvent.RightClickItem event) {
        event.setCanceled(true);

        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        Hand hand = event.getHand();

        // Hand-craft?
        boolean didHandCraft = false;
        if (!world.isRemote && hand == Hand.MAIN_HAND) {
            didHandCraft = HandCrafting.attempt(world, player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        event.setCancellationResult(ActionResultType.SUCCESS);
        event.setUseBlock(Event.Result.DENY);
        event.setUseItem(Event.Result.DENY);
        // This makes things work?
        // Even though I check isRemote in onRightClickItem also...
        // Without this handcrafting gets activated when it shouldn't.
        if (event.getWorld().isRemote) {
            event.setCanceled(true);
        }

        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        BlockState blockState = world.getBlockState(pos);
        Direction face = event.getFace();
        Hand hand = event.getHand();
        // Gather?
        boolean didGather = false;
        if (!world.isRemote && hand == Hand.MAIN_HAND) {
            didGather = Gathering.attempt(world, player, pos) || Gathering.isGatherable(blockState);
        }
        // Place?
        boolean didPlace = false;
        if (!world.isRemote && hand == Hand.MAIN_HAND && !didGather) {
            didPlace = Placing.attempt(world, player, Hand.MAIN_HAND, pos, face) || Placing.attempt(world, player, Hand.OFF_HAND, pos, face);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBreakSpeed(final PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(0);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        //event.setUseBlock(Event.Result.DENY);
        //event.setUseItem(Event.Result.DENY);

        // Break?
    }
}
