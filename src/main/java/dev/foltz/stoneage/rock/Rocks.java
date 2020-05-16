package dev.foltz.stoneage.rock;

import dev.foltz.stoneage.block.SABlockRock;
import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.item.SAItemRock;
import dev.foltz.stoneage.item.SAItems;

public class Rocks {
    public static final Rock FLINT_SMALL    = register(Rock.Type.FLINT, Rock.Size.SMALL);
    public static final Rock FLINT_MEDIUM   = register(Rock.Type.FLINT, Rock.Size.MEDIUM);
    public static final Rock GRANITE_SMALL  = register(Rock.Type.GRANITE, Rock.Size.SMALL);
    public static final Rock GRANITE_MEDIUM = register(Rock.Type.GRANITE, Rock.Size.MEDIUM);

    private static Rock register(Rock.Type type, Rock.Size size) {
        Rock rock = new Rock(type, size);
        String name = rock.getRegistryName();
        rock.block = SABlocks.registerBlock(name, () -> new SABlockRock(rock));
        rock.item = SAItems.registerItem(name, () -> new SAItemRock(rock));
        return rock;
    }
}
