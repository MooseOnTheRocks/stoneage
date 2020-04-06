package dev.foltz.stoneage.world.gen.desc;

import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorldDescComposite extends WorldDesc {
    private List<WorldDesc> parts;

    public WorldDescComposite(Collection<WorldDesc> parts) {
        this.parts = new ArrayList<WorldDesc>();
        for (WorldDesc desc : parts) {
            this.parts.add(desc);
        }
    }

    @Override
    public BlockState blockAt(int x, int y, int z) {
        for (WorldDesc desc : parts) {
            BlockState blockState = desc.blockAt(x, y, z);
            if (blockState != AIR) return blockState;
        }
        return AIR;
    }
}
