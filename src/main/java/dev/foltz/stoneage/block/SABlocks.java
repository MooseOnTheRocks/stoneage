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
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, StoneAge.MODID);

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> sup) {
        return BLOCKS.register(name, sup);
    }

    // -- Nature
    public static final RegistryObject<Block> LOG = registerBlock("log", () -> new LogBlock(Material.WOOD.getColor(), Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
}
