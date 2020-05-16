package dev.foltz.stoneage;

import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.client.input.SAKeyBindings;
import dev.foltz.stoneage.interactions.gathering.Gathering;
import dev.foltz.stoneage.interactions.knapping.Knapping;
import dev.foltz.stoneage.interactions.placing.Placing;
import dev.foltz.stoneage.item.SAItems;
import dev.foltz.stoneage.network.SAPacketHandler;
import dev.foltz.stoneage.rock.Rock;
import dev.foltz.stoneage.rock.Rocks;
import dev.foltz.stoneage.world.SAWorldType;
import dev.foltz.stoneage.world.biome.SABiomes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(StoneAge.MODID)
public class StoneAge {
    public static final String MODID = "stoneage";
    public static SAWorldType SAWORLD = new SAWorldType();
    //public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    private static final Logger LOGGER = LogManager.getLogger();

    public StoneAge() {
        IEventBus modEvents = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEvents = MinecraftForge.EVENT_BUS;

        modEvents.addListener(this::setup);
        modEvents.addListener(this::complete);

        SABlocks.register(modEvents);
        SAItems.register(modEvents);
        SABiomes.register(modEvents);

        Rock r = Rocks.FLINT_MEDIUM;
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(final FMLClientSetupEvent event) {

    }

    public void setup(final FMLCommonSetupEvent event) {
        SAPacketHandler.register();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            SAKeyBindings.register();
        });
    }

    public void complete(final FMLLoadCompleteEvent event) {
        // TODO: Find a proper place for all this
        Gathering.addGatherable(Rocks.FLINT_SMALL.asBlockState(), Rocks.FLINT_SMALL.asItemStack());
        Gathering.addGatherable(Rocks.FLINT_MEDIUM.asBlockState(), Rocks.FLINT_MEDIUM.asItemStack());
        Gathering.addGatherable(Rocks.GRANITE_SMALL.asBlockState(), Rocks.GRANITE_SMALL.asItemStack());
        Gathering.addGatherable(Rocks.GRANITE_MEDIUM.asBlockState(), Rocks.GRANITE_MEDIUM.asItemStack());
        Placing.addPlaceable(Rocks.FLINT_SMALL.asItem(), Rocks.FLINT_SMALL.asBlockState());
        Placing.addPlaceable(Rocks.FLINT_MEDIUM.asItem(), Rocks.FLINT_MEDIUM.asBlockState());
        Placing.addPlaceable(Rocks.GRANITE_SMALL.asItem(), Rocks.GRANITE_SMALL.asBlockState());
        Placing.addPlaceable(Rocks.GRANITE_MEDIUM.asItem(), Rocks.GRANITE_MEDIUM.asBlockState());

        Knapping.addRecipe(Rocks.GRANITE_SMALL.asItemStack(), Rocks.FLINT_SMALL.asItemStack(), new ItemStack(SAItems.STONE_EDGE_FLINT.get()));
        Knapping.addRecipe(Rocks.GRANITE_MEDIUM.asItemStack(), Rocks.FLINT_MEDIUM.asItemStack(), new ItemStack(SAItems.HANDAX_FLINT.get()));
    }
}
