package dev.foltz.stoneage.world.biome;

import dev.foltz.stoneage.block.SABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.awt.color.ColorSpace;

public class SABiomePlains extends Biome {
    public SABiomePlains() {
        super((new Biome.Builder())
                .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_SAND_CONFIG)
                .precipitation(Biome.RainType.RAIN)
                .scale(0.2f)
                .depth(0.5f)
                .category(Biome.Category.PLAINS)
                .temperature(0.7F)
                .downfall(0.8F)
                .waterColor(0x03A1FC)
                .waterFogColor(0x0437CF)
                .parent((String)null));

        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 500, 3, 10));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 100, 2, 5));
    }

    @OnlyIn(Dist.CLIENT)
    public int getGrassColor(BlockPos pos) {
        return Color.HSBtoRGB(90 / 360.0f, 0.35f, 0.6f);
    }

    @OnlyIn(Dist.CLIENT)
    public int getFoliageColor(BlockPos pos) {
        return Color.HSBtoRGB(90 / 360.0f, 0.35f, 0.6f);
    }
}
