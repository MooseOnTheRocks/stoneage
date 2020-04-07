package dev.foltz.stoneage.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class SAToolActionAttemptPacket {
    public final Hand hand;
    public final BlockPos pos;

    public SAToolActionAttemptPacket(Hand hand, BlockPos pos) {
        this.hand = hand;
        this.pos = pos;
    };

    public static void encode(SAToolActionAttemptPacket packet, PacketBuffer buffer) {
        buffer.writeEnumValue(packet.hand);
        buffer.writeBlockPos(packet.pos);
    }

    public static SAToolActionAttemptPacket decode(PacketBuffer buffer) {
        return new SAToolActionAttemptPacket(buffer.readEnumValue(Hand.class), buffer.readBlockPos());
    }
}
