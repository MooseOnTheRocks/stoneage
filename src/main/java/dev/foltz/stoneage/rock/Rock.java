package dev.foltz.stoneage.rock;

import dev.foltz.stoneage.block.SABlockRock;
import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.item.SAItemRock;
import dev.foltz.stoneage.item.SAItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

public class Rock {
    public enum Type {
        FLINT,
        GRANITE;
    }

    public enum Size {
        SMALL,
        MEDIUM;
    }

    public final Type type;
    public final Size size;

    public final RegistryObject<SABlockRock> BLOCK;
    public final RegistryObject<SAItemRock> ITEM;

    public Rock(Type type, Size size) {
        this.type = type;
        this.size = size;

        BLOCK = SABlocks.registerBlock(getRegistryName(), () -> new SABlockRock(this));
        ITEM = SAItems.registerItem(getRegistryName(), () -> new SAItemRock(this));
    }

    public String getRegistryName() {
        return "rock_" + type.name().toLowerCase() + "_" + size.name().toLowerCase();
    }

    public SABlockRock asBlock() {
        return BLOCK.get();
    }

    public BlockState asBlockState() {
        return asBlock().getDefaultState();
    }

    public SAItemRock asItem() {
        return ITEM.get();
    }

    public ItemStack asItemStack() {
        return new ItemStack(asItem());
    }
}
