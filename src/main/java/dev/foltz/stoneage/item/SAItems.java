package dev.foltz.stoneage.item;

import dev.foltz.stoneage.StoneAge;
import dev.foltz.stoneage.block.SABlockRock;
import dev.foltz.stoneage.block.SABlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SAItems {
    public static final ItemGroup itemGroup = new ItemGroup("stoneage") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Item.BLOCK_TO_ITEM.get(SABlocks.ROCK_FLINT_SMALL.get()));
        }
    };

    // NOTE: public for development reasons, use the public functions instead.
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, StoneAge.MODID);

    // == ALL ITEMS ==
    // -- Rocks
    public static final RegistryObject<Item> ROCK_FLINT_SMALL = registerItem("rock_flint_small", () -> new SAItemRock((SABlockRock) SABlocks.ROCK_FLINT_SMALL.get()));
    public static final RegistryObject<Item> ROCK_FLINT_MEDIUM = registerItem("rock_flint_medium", () -> new SAItemRock((SABlockRock) SABlocks.ROCK_FLINT_MEDIUM.get()));
    public static final RegistryObject<Item> ROCK_GRANITE_SMALL = registerItem("rock_granite_small", () -> new SAItemRock((SABlockRock) SABlocks.ROCK_GRANITE_SMALL.get()));
    public static final RegistryObject<Item> ROCK_GRANITE_MEDIUM = registerItem("rock_granite_medium", () -> new SAItemRock((SABlockRock) SABlocks.ROCK_GRANITE_MEDIUM.get()));
    // -- Tools
    public static final RegistryObject<Item> STONE_EDGE_FLINT = registerItem("stone_edge_flint", () -> new Item(new Item.Properties().group(SAItems.itemGroup).defaultMaxDamage(20)));
    public static final RegistryObject<Item> HANDAX_FLINT = registerItem("handax_flint", () -> new SAItemHandax());
    public static final RegistryObject<Item> SPEAR = registerItem("spear", () -> new Item(new Item.Properties().group(itemGroup)));
    // -- Sticks
    public static final RegistryObject<Item> STICK = registerItem("stick", () -> new Item(new Item.Properties().group(itemGroup).defaultMaxDamage(10)));
    // -- Materials
    public static final RegistryObject<Item> BARK = registerItem("bark", () -> new Item(new Item.Properties().group(itemGroup)));

    public static RegistryObject<Item> registerItem(String name, Supplier<Item> sup) {
        return ITEMS.register(name, sup);
    }
}
