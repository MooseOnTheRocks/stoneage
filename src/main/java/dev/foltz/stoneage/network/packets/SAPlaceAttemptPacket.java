package dev.foltz.stoneage.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;

public class SAPlaceAttemptPacket {
    public final BlockRayTraceResult blockRayTrace;
    public final Hand hand;

    public SAPlaceAttemptPacket(BlockRayTraceResult blockRayTrace, Hand hand) {
        this.blockRayTrace = blockRayTrace;
        this.hand = hand;
    }

    public static void encode(SAPlaceAttemptPacket packet, PacketBuffer buffer) {
        buffer.writeBlockRay(packet.blockRayTrace);
        buffer.writeEnumValue(packet.hand);
    }

    public static SAPlaceAttemptPacket decode(PacketBuffer buffer) {
        return new SAPlaceAttemptPacket(buffer.readBlockRay(), buffer.readEnumValue(Hand.class));
    }
}
