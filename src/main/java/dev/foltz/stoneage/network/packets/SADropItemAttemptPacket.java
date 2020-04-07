package dev.foltz.stoneage.network.packets;

import net.minecraft.network.PacketBuffer;

public class SADropItemAttemptPacket {
    public SADropItemAttemptPacket() {
    };

    public static void encode(SADropItemAttemptPacket packet, PacketBuffer buffer) {
    }

    public static SADropItemAttemptPacket decode(PacketBuffer buffer) {
        return new SADropItemAttemptPacket();
    }
}
