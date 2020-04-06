package dev.foltz.stoneage.item;

import dev.foltz.stoneage.block.SABlockRock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class SAItemRock extends BlockItem {
    public final SABlockRock rock;
    public SAItemRock(SABlockRock rock) {
        super(rock, new Item.Properties()
                .group(SAItems.itemGroup)
                .maxStackSize(rock.size == SABlockRock.RockSize.SMALL ? 4 : 2)
                .defaultMaxDamage(rock.size == SABlockRock.RockSize.SMALL ? 8 : 16));
        this.rock = rock;
    }
}
