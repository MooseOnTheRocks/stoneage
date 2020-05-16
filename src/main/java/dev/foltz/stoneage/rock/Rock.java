package dev.foltz.stoneage.rock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public class Rock {
    public enum Type { FLINT, GRANITE }
    public final Type type;

    public enum Size { SMALL, MEDIUM }
    public final Size size;

    protected RegistryObject<Block> block;
    protected RegistryObject<Item> item;

    protected Rock(Type type, Size size) {
        this.type = type;
        this.size = size;
    }

    public Block asBlock() {
        return block.get();
    }

    public BlockState asBlockState() {
        return asBlock().getDefaultState();
    }

    public Item asItem() {
        return item.get();
    }

    public ItemStack asItemStack() {
        return new ItemStack(asItem());
    }

    public String getRegistryName() {
        return "rock_" + type.name().toLowerCase() + "_" + size.name().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rock rock = (Rock) o;
        return type == rock.type && size == rock.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size);
    }
}
