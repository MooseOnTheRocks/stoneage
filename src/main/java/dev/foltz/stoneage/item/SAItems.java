package dev.foltz.stoneage.item;

import dev.foltz.stoneage.StoneAge;
import dev.foltz.stoneage.block.SABlockRock;
import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.rock.Rocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
            return Rocks.FLINT_SMALL.asItemStack();
        }
    };

    // NOTE: public for development reasons, use the public functions instead.
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, StoneAge.MODID);

    // == ALL ITEMS ==
    public static final RegistryObject<Item> LOG = registerItem("log", () -> new BlockItem(SABlocks.LOG.get(), new Item.Properties().group(SAItems.itemGroup)));
    // -- Tools
    public static final RegistryObject<Item> STONE_EDGE_FLINT = registerItem("stone_edge_flint", () -> new Item(new Item.Properties().group(SAItems.itemGroup).defaultMaxDamage(20)));
    public static final RegistryObject<Item> HANDAX_FLINT = registerItem("handax_flint", () -> new SAItemHandax());
    public static final RegistryObject<Item> SPEAR = registerItem("spear", () -> new Item(new Item.Properties().group(itemGroup)));
    // -- Sticks
    public static final RegistryObject<Item> STICK = registerItem("stick", () -> new Item(new Item.Properties().group(itemGroup).defaultMaxDamage(10)));
    // -- Materials
    public static final RegistryObject<Item> BARK = registerItem("bark", () -> new Item(new Item.Properties().group(itemGroup)));
    public static final RegistryObject<Item> PLANT_FIBER = registerItem("plant_fiber", () -> new Item(new Item.Properties().group(itemGroup).defaultMaxDamage(2)));
    public static final RegistryObject<Item> PLANT_FIBER_WOVEN = registerItem("plant_fiber_woven", () -> new Item(new Item.Properties().group(itemGroup).defaultMaxDamage(4)));
    public static final RegistryObject<Item> MAT_BARK = registerItem("mat_bark", () -> new Item(new Item.Properties().group(itemGroup).defaultMaxDamage(4)));
    public static final RegistryObject<Item> MAT_CRAFTING = registerItem("mat_crafting", () -> new Item(new Item.Properties().group(itemGroup)));

    public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> sup) {
        return ITEMS.register(name, sup);
    }
}
