package dev.foltz.stoneage.network.packets;

import net.minecraft.network.PacketBuffer;

public class SAHandCraftAttemptPacket {
    public SAHandCraftAttemptPacket() {
    }

    public static void encode(SAHandCraftAttemptPacket packet, PacketBuffer buffer) {
    }

    public static SAHandCraftAttemptPacket decode(PacketBuffer buffer) {
        return new SAHandCraftAttemptPacket();
    }
}
