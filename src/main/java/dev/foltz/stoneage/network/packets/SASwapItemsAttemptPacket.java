package dev.foltz.stoneage.network.packets;

import net.minecraft.network.PacketBuffer;

public class SASwapItemsAttemptPacket {
    public SASwapItemsAttemptPacket() {
    };

    public static void encode(SASwapItemsAttemptPacket packet, PacketBuffer buffer) {
    }

    public static SASwapItemsAttemptPacket decode(PacketBuffer buffer) {
        return new SASwapItemsAttemptPacket();
    }
}
