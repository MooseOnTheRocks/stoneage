package dev.foltz.stoneage.block;

import dev.foltz.stoneage.StoneAge;
import dev.foltz.stoneage.item.SAItems;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SABlocks {
    // NOTE: public for development reasons, use the public functions instead.
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, StoneAge.MODID);

    // == ALL BLOCKS ==
    // -- Rocks
    public static final RegistryObject<Block> ROCK_FLINT_SMALL = registerBlock("rock_flint_small", () -> new SABlockRock(SABlockRock.RockType.FLINT, SABlockRock.RockSize.SMALL));
    public static final RegistryObject<Block> ROCK_FLINT_MEDIUM = registerBlock("rock_flint_medium", () -> new SABlockRock(SABlockRock.RockType.FLINT, SABlockRock.RockSize.MEDIUM));
    public static final RegistryObject<Block> ROCK_GRANITE_SMALL = registerBlock("rock_granite_small", () -> new SABlockRock(SABlockRock.RockType.GRANITE, SABlockRock.RockSize.SMALL));
    public static final RegistryObject<Block> ROCK_GRANITE_MEDIUM = registerBlock("rock_granite_medium", () -> new SABlockRock(SABlockRock.RockType.GRANITE, SABlockRock.RockSize.MEDIUM));
    // -- Nature
    public static final RegistryObject<Block> LOG = registerBlockAndItem("log", () -> new LogBlock(Material.WOOD.getColor(), Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));

    public static RegistryObject<Block> registerBlock(String name, Supplier<Block> sup) {
        return BLOCKS.register(name, sup);
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> sup) {
        RegistryObject<Block> regblock = registerBlock(name, sup);
        SAItems.registerItem(name, () -> new BlockItem(regblock.get(), new Item.Properties().group(SAItems.itemGroup)));
        return regblock;
    }
}
