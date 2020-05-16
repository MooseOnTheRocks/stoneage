package dev.foltz.stoneage.item;

import dev.foltz.stoneage.StoneAge;
import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.rock.Rocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SAItems {
    public static final ItemGroup itemGroup = new ItemGroup("stoneage") {
        @Override
        public ItemStack createIcon() {
            return Rocks.FLINT_SMALL.asItemStack();
        }
    };

    private static final DeferredRegister<Item> ALL_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, StoneAge.MODID);

    // == ALL ITEMS ==
    public static final RegistryObject<Item> LOG                    = registerItem("log", () -> new BlockItem(SABlocks.LOG.get(), new Item.Properties().group(SAItems.itemGroup)));
    // -- Tools
    public static final RegistryObject<SAItem> STONE_EDGE_FLINT     = registerItem("stone_edge_flint");
    public static final RegistryObject<SAItem> HANDAX_FLINT         = registerItem("handax_flint");
    public static final RegistryObject<SAItem> SPEAR                = registerItem("spear");
    // -- Sticks
    public static final RegistryObject<SAItem> STICK                = registerItem("stick");
    // -- Materials
    public static final RegistryObject<SAItem> BARK                 = registerItem("bark");
    public static final RegistryObject<SAItem> PLANT_FIBER          = registerItem("plant_fiber");
    public static final RegistryObject<SAItem> PLANT_FIBER_WOVEN    = registerItem("plant_fiber_woven");
    public static final RegistryObject<SAItem> MAT_BARK             = registerItem("mat_bark");
    public static final RegistryObject<SAItem> MAT_CRAFTING         = registerItem("mat_crafting");

    public static final RegistryObject<Item> GRASS_BUSH             = registerItem("grass_bush", () -> new BlockItem(SABlocks.GRASS_BUSH.get(), new Item.Properties().group(SAItems.itemGroup)));
    public static final RegistryObject<Item> GRASS                  = registerItem("grass", () -> new BlockItem(SABlocks.GRASS.get(), new Item.Properties().group(SAItems.itemGroup)));
    public static final RegistryObject<Item> DIRT                   = registerItem("dirt", () -> new BlockItem(SABlocks.DIRT.get(), new Item.Properties().group(SAItems.itemGroup)));
    public static final RegistryObject<Item> SAND                   = registerItem("sand", () -> new BlockItem(SABlocks.SAND.get(), new Item.Properties().group(SAItems.itemGroup)));

    public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> sup) {
        return ALL_ITEMS.register(name, sup);
    }

    public static RegistryObject<SAItem> registerItem(String name) {
        return ALL_ITEMS.register(name, () -> new SAItem());
    }

    public static void register(IEventBus bus) {
        ALL_ITEMS.register(bus);
    }
}
