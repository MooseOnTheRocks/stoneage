package dev.foltz.stoneage.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class SAGatherAttemptPacket {
    public final BlockPos gatherPos;

    public SAGatherAttemptPacket(BlockPos blockPos) {
        gatherPos = blockPos;
    }

    public static void encode(SAGatherAttemptPacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.gatherPos);
    }

    public static SAGatherAttemptPacket decode(PacketBuffer buffer) {
        return new SAGatherAttemptPacket(buffer.readBlockPos());
    }
}
