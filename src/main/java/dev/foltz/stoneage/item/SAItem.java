package dev.foltz.stoneage.item;

import net.minecraft.item.Item;

public class SAItem extends Item {
    public SAItem(Item.Properties properties) {
        super(properties.group(SAItems.itemGroup));
    }

    public SAItem() {
        this(new Item.Properties().maxStackSize(1));
    }
}
