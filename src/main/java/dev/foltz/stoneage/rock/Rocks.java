package dev.foltz.stoneage.rock;

import dev.foltz.stoneage.block.SABlockRock;
import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.item.SAItemRock;
import dev.foltz.stoneage.item.SAItems;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public class Rocks {
    public static final Set<Rock> ROCKS = new HashSet<Rock>();

    public static final Rock FLINT_SMALL = register(new Rock(Rock.Type.FLINT, Rock.Size.SMALL));
    public static final Rock FLINT_MEDIUM = register(new Rock(Rock.Type.FLINT, Rock.Size.MEDIUM));
    public static final Rock GRANITE_SMALL = register(new Rock(Rock.Type.GRANITE, Rock.Size.SMALL));
    public static final Rock GRANITE_MEDIUM = register(new Rock(Rock.Type.GRANITE, Rock.Size.MEDIUM));

    public static Rock register(Rock rock) {
        ROCKS.add(rock);
        return rock;
    }
}
