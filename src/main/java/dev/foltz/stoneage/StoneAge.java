package dev.foltz.stoneage;

import dev.foltz.stoneage.block.SABlocks;
import dev.foltz.stoneage.client.input.SAKeyBindings;
import dev.foltz.stoneage.interactions.gathering.Gathering;
import dev.foltz.stoneage.interactions.handcrafting.HandCrafting;
import dev.foltz.stoneage.interactions.placing.Placing;
import dev.foltz.stoneage.interactions.tool.ToolActions;
import dev.foltz.stoneage.item.SAItems;
import dev.foltz.stoneage.network.SAPacketHandler;
import dev.foltz.stoneage.rock.Rocks;
import dev.foltz.stoneage.world.biome.SABiomes;
import dev.foltz.stoneage.world.SAWorldType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
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
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::complete);
        eventBus.addListener(this::dedicatedServerSetup);

        SABlocks.BLOCKS.register(eventBus);
        SAItems.ITEMS.register(eventBus);
        SABiomes.BIOMES.register(eventBus);

        // Just here to cause Rocks to register
        // TODO: Make this not be here...
        Object o = Rocks.GRANITE_SMALL;
    }

    public void onServerAboutToStart(final FMLServerAboutToStartEvent event) {
    }

    public void dedicatedServerSetup(final FMLDedicatedServerSetupEvent event) {
    }

    public void setup(final FMLCommonSetupEvent event) {
        SAPacketHandler.register();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            SAKeyBindings.register();
        });
    }

    public void complete(final FMLLoadCompleteEvent event) {
        GameSettings settings = Minecraft.getInstance().gameSettings;
        settings.keyBindAttack.bind(InputMappings.INPUT_INVALID);
        settings.keyBindUseItem.bind(InputMappings.INPUT_INVALID);

        ToolActions.registerToolAction(SAItems.HANDAX_FLINT.get(), ((world, player, tool, pos) -> {
            System.out.println("Attempting to use tool");
            BlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() == SABlocks.LOG.get()) {
                tool.damageItem(1, player, (p) -> {});
                System.out.println("OONGA");
                player.dropItem(new ItemStack(SAItems.BARK.get()), true, false);
                return true;
            }
            return false;
        }));

        // TODO: Find a better place to put these
        Gathering.addGatherable(Rocks.FLINT_SMALL.asBlockState(), Rocks.FLINT_SMALL.asItemStack());
        Gathering.addGatherable(Rocks.FLINT_MEDIUM.asBlockState(), Rocks.FLINT_MEDIUM.asItemStack());
        Gathering.addGatherable(Rocks.GRANITE_SMALL.asBlockState(), Rocks.GRANITE_SMALL.asItemStack());
        Gathering.addGatherable(Rocks.GRANITE_MEDIUM.asBlockState(), Rocks.GRANITE_MEDIUM.asItemStack());
        Gathering.addGatherable(Blocks.GRASS.getDefaultState(), new ItemStack(SAItems.PLANT_FIBER.get(), 1));

        Placing.addPlaceable(Rocks.FLINT_SMALL.asItem(), Rocks.FLINT_SMALL.asBlockState());
        Placing.addPlaceable(Rocks.FLINT_MEDIUM.asItem(), Rocks.FLINT_MEDIUM.asBlockState());
        Placing.addPlaceable(Rocks.GRANITE_SMALL.asItem(), Rocks.GRANITE_SMALL.asBlockState());
        Placing.addPlaceable(Rocks.GRANITE_MEDIUM.asItem(), Rocks.GRANITE_MEDIUM.asBlockState());

        HandCrafting.addRecipe(Rocks.GRANITE_SMALL.asItemStack(), Rocks.FLINT_SMALL.asItemStack(), new ItemStack(SAItems.STONE_EDGE_FLINT.get(), 1));
        HandCrafting.addRecipe(Rocks.GRANITE_SMALL.asItemStack(), Rocks.FLINT_MEDIUM.asItemStack(), new ItemStack(SAItems.HANDAX_FLINT.get(), 1));
        HandCrafting.addRecipe(new ItemStack(SAItems.STONE_EDGE_FLINT.get(), 1), new ItemStack(SAItems.STICK.get(), 1), new ItemStack(SAItems.SPEAR.get(), 1));
        HandCrafting.addRecipe(new ItemStack(SAItems.PLANT_FIBER.get(), 1), new ItemStack(SAItems.PLANT_FIBER.get(), 1), new ItemStack(SAItems.PLANT_FIBER_WOVEN.get(), 1));
        HandCrafting.addRecipe(new ItemStack(SAItems.PLANT_FIBER_WOVEN.get(), 4), new ItemStack(SAItems.BARK.get(), 4), new ItemStack(SAItems.MAT_BARK.get(), 1));
    }
}
