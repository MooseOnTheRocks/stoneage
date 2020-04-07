package dev.foltz.stoneage.item;

import dev.foltz.stoneage.block.SABlockRock;
import dev.foltz.stoneage.rock.Rock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class SAItemRock extends Item {
    public final Rock rock;
    public SAItemRock(Rock rock) {
        super(new Item.Properties()
                .group(SAItems.itemGroup)
                .maxStackSize(rock.size == Rock.Size.SMALL ? 4 : 2)
                .defaultMaxDamage(rock.size == Rock.Size.SMALL ? 8 : 16));
        this.rock = rock;
    }
}
