package dev.foltz.stoneage.network;

import dev.foltz.stoneage.StoneAge;
import dev.foltz.stoneage.interactions.gathering.Gathering;
import dev.foltz.stoneage.interactions.handcrafting.HandCrafting;
import dev.foltz.stoneage.interactions.inventory.InventoryActions;
import dev.foltz.stoneage.interactions.placing.Placing;
import dev.foltz.stoneage.interactions.tool.ToolAction;
import dev.foltz.stoneage.interactions.tool.ToolActions;
import dev.foltz.stoneage.network.packets.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SAPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(StoneAge.MODID, "samainnetwork"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, SAGatherAttemptPacket.class, SAGatherAttemptPacket::encode, SAGatherAttemptPacket::decode, SAPacketHandler::receiveGatherAttempt);
        INSTANCE.registerMessage(id++, SAHandCraftAttemptPacket.class, SAHandCraftAttemptPacket::encode, SAHandCraftAttemptPacket::decode, SAPacketHandler::receiveHandCraftAttempt);
        INSTANCE.registerMessage(id++, SAPlaceAttemptPacket.class, SAPlaceAttemptPacket::encode, SAPlaceAttemptPacket::decode, SAPacketHandler::receivePlaceAttempt);
        INSTANCE.registerMessage(id++, SASwapItemsAttemptPacket.class, SASwapItemsAttemptPacket::encode, SASwapItemsAttemptPacket::decode, SAPacketHandler::receiveSwapItemsAttempt);
        INSTANCE.registerMessage(id++, SADropItemAttemptPacket.class, SADropItemAttemptPacket::encode, SADropItemAttemptPacket::decode, SAPacketHandler::receiveDropItemAttempt);
        INSTANCE.registerMessage(id++, SAToolActionAttemptPacket.class, SAToolActionAttemptPacket::encode, SAToolActionAttemptPacket::decode, SAPacketHandler::receiveToolActionAttempt);
    }

    public static void sendToolActionAttempt(Hand hand, BlockPos pos) {
        INSTANCE.sendToServer(new SAToolActionAttemptPacket(hand, pos));
    }

    public static void receiveToolActionAttempt(SAToolActionAttemptPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("tool action packet");
            ServerPlayerEntity player = context.get().getSender();
            ToolActions.attempt(player.world, player, player.getHeldItem(packet.hand), packet.pos);
        });
        context.get().setPacketHandled(true);
    }

    public static void sendDropItemAttempt() {
        INSTANCE.sendToServer(new SADropItemAttemptPacket());
    }

    public static void receiveDropItemAttempt(SADropItemAttemptPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("item drop packet");
            ServerPlayerEntity player = context.get().getSender();
            InventoryActions.dropItem(player);
        });
        context.get().setPacketHandled(true);
    }

    public static void sendSwapItemsAttempt() {
        INSTANCE.sendToServer(new SASwapItemsAttemptPacket());
    }

    public static void receiveSwapItemsAttempt(SASwapItemsAttemptPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("item swap packet");
            ServerPlayerEntity player = context.get().getSender();
            InventoryActions.swapOrCombineHands(player);
        });
        context.get().setPacketHandled(true);
    }

    public static void sendGatherAttempt(SAGatherAttemptPacket packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void receiveGatherAttempt(SAGatherAttemptPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("gather packet: " + packet.gatherPos);
            ServerPlayerEntity player = context.get().getSender();
            Gathering.attempt(player.world, player, packet.gatherPos);
        });
        context.get().setPacketHandled(true);
    }

    public static void sendHandCraftAttempt(SAHandCraftAttemptPacket packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void receiveHandCraftAttempt(SAHandCraftAttemptPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("handcraft packet: " + packet);
            ServerPlayerEntity player = context.get().getSender();
            HandCrafting.attempt(player.world, player);
        });
        context.get().setPacketHandled(true);
    }

    public static void sendPlaceAttempt(SAPlaceAttemptPacket packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void receivePlaceAttempt(SAPlaceAttemptPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("place packet: " + packet.blockRayTrace.getPos());
            ServerPlayerEntity player = context.get().getSender();
            BlockPos pos = packet.blockRayTrace.getPos();
            Direction face = packet.blockRayTrace.getFace();
            Placing.attempt(player.world, player, packet.hand, pos, face);
        });
        context.get().setPacketHandled(true);
    }
}
