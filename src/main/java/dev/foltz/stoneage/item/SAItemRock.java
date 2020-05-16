package dev.foltz.stoneage.item;

import dev.foltz.stoneage.rock.Rock;
import net.minecraft.item.Item;

public class SAItemRock extends SAItem {
    public final Rock rock;

    public SAItemRock(Rock rock) {
        super(new Item.Properties()
            .maxStackSize(1)
            .defaultMaxDamage(rock.size == Rock.Size.SMALL ? 8 : 16));
        this.rock = rock;
    }
}
