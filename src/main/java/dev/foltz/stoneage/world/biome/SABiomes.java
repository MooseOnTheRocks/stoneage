package dev.foltz.stoneage.world.biome;

import dev.foltz.stoneage.StoneAge;
import dev.foltz.stoneage.world.biome.SABiomePlains;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SABiomes {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, StoneAge.MODID);

    public static final RegistryObject<Biome> SAPLAINS = BIOMES.register("saplains", () -> new SABiomePlains());
}
