package dev.foltz.stoneage.world;

import dev.foltz.stoneage.world.biome.SABiomes;
import dev.foltz.stoneage.world.gen.SABiomeProvider;
import dev.foltz.stoneage.world.gen.SAChunkGenerator;
import dev.foltz.stoneage.world.gen.SAGenerationSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SAWorldType extends WorldType {
    public SAWorldType() {
        super("stoneage");
    }

    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event) {
        System.out.println("Fixing spawn point.");
        IWorld world = event.getWorld();
        Dimension dim = world.getDimension();
        if (dim.getType() == DimensionType.OVERWORLD) {
            int height = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, 0, 0);
            dim.setSpawnPoint(new BlockPos(0, height, 0));
            event.setCanceled(true);
        }
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator(World world) {
        if (world.getDimension().getType() == DimensionType.OVERWORLD) {
            System.out.println("Overworld time baby!");
            return new SAChunkGenerator(world, new SABiomeProvider(), new SAGenerationSettings());
        }
        else {
            return super.createChunkGenerator(world);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onCustomizeButton(Minecraft mc, CreateWorldScreen gui) {
        super.onCustomizeButton(mc, gui);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean hasInfoNotice() {
        return true;
    }
}
