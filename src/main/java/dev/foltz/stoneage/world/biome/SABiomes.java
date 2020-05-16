package dev.foltz.stoneage.world.biome;

import dev.foltz.stoneage.StoneAge;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SABiomes {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, StoneAge.MODID);

    public static final RegistryObject<Biome> SAPLAINS = BIOMES.register("saplains", () -> new SABiomePlains());

    public static void register(IEventBus bus) {
        BIOMES.register(bus);
    }
}
