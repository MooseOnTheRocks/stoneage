package dev.foltz.stoneage;

import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.interactions.gathering.Gathering;
import dev.foltz.stoneage.interactions.handcrafting.HandCrafting;
import dev.foltz.stoneage.interactions.placing.Placing;
import dev.foltz.stoneage.item.SAItems;
import dev.foltz.stoneage.world.biome.SABiomes;
import dev.foltz.stoneage.world.SAWorldType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(StoneAge.MODID)
public class StoneAge {
    public static final String MODID = "stoneage";
    //public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    private static final Logger LOGGER = LogManager.getLogger();

    public StoneAge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::complete);
        SABlocks.BLOCKS.register(eventBus);
        SAItems.ITEMS.register(eventBus);
        SABiomes.BIOMES.register(eventBus);
    }

    public void setup(final FMLCommonSetupEvent event) {
        new SAWorldType();
    }

    public void complete(final FMLLoadCompleteEvent event) {
        // TODO: Find a better place to put these
        Gathering.addGatherable(SABlocks.ROCK_FLINT_SMALL.get().getDefaultState(), new ItemStack(SAItems.ROCK_FLINT_SMALL.get(), 1));
        Gathering.addGatherable(SABlocks.ROCK_FLINT_MEDIUM.get().getDefaultState(), new ItemStack(SAItems.ROCK_FLINT_MEDIUM.get(), 1));
        Gathering.addGatherable(SABlocks.ROCK_GRANITE_SMALL.get().getDefaultState(), new ItemStack(SAItems.ROCK_GRANITE_SMALL.get(), 1));
        Gathering.addGatherable(SABlocks.ROCK_GRANITE_MEDIUM.get().getDefaultState(), new ItemStack(SAItems.ROCK_GRANITE_MEDIUM.get(), 1));

        Placing.addPlaceable(SAItems.ROCK_FLINT_SMALL.get(), SABlocks.ROCK_FLINT_SMALL.get().getDefaultState());
        Placing.addPlaceable(SAItems.ROCK_FLINT_MEDIUM.get(), SABlocks.ROCK_FLINT_MEDIUM.get().getDefaultState());
        Placing.addPlaceable(SAItems.ROCK_GRANITE_SMALL.get(), SABlocks.ROCK_GRANITE_SMALL.get().getDefaultState());
        Placing.addPlaceable(SAItems.ROCK_GRANITE_MEDIUM.get(), SABlocks.ROCK_GRANITE_MEDIUM.get().getDefaultState());

        HandCrafting.addRecipe(new ItemStack(SAItems.ROCK_GRANITE_SMALL.get(), 1), new ItemStack(SAItems.ROCK_FLINT_SMALL.get(), 1), new ItemStack(SAItems.STONE_EDGE_FLINT.get(), 1));
        HandCrafting.addRecipe(new ItemStack(SAItems.ROCK_GRANITE_MEDIUM.get(), 1), new ItemStack(SAItems.ROCK_FLINT_MEDIUM.get(), 1), new ItemStack(SAItems.HANDAX_FLINT.get(), 1));
        HandCrafting.addRecipe(new ItemStack(SAItems.STONE_EDGE_FLINT.get(), 1), new ItemStack(SAItems.STICK.get(), 1), new ItemStack(SAItems.SPEAR.get(), 1));
    }
}
