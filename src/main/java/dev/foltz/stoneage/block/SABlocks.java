package dev.foltz.stoneage.block;

import dev.foltz.stoneage.StoneAge;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SABlocks {
    private static final DeferredRegister<Block> ALL_BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, StoneAge.MODID);

    public static final RegistryObject<Block> LOG = registerBlock("log", () -> new LogBlock(Material.WOOD.getColor(), Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GRASS_TALL = registerBlock("grass_tall", () -> new SABlockTallGrass());
    public static final RegistryObject<Block> GRASS_BUSH = registerBlock("grass_bush", () -> new SABlockBushGrass());
    public static final RegistryObject<Block> GRASS = registerBlock("grass", () -> new SABlockGrass());
    public static final RegistryObject<Block> DIRT = registerBlock("dirt", () -> new Block(Block.Properties.create(Material.EARTH)));
    public static final RegistryObject<Block> SAND = registerBlock("sand", () -> new Block(Block.Properties.create(Material.SAND)));

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> sup) {
        return ALL_BLOCKS.register(name, sup);
    }

    public static void register(IEventBus bus) {
        ALL_BLOCKS.register(bus);
    }
}
