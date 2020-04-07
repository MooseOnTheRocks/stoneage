package dev.foltz.stoneage.client.input;

import dev.foltz.stoneage.StoneAge;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class SAKeyBindings {
    public static SAKeyBindingOnce SWAP_ITEMS;
    public static KeyBinding SHOW_ITEM_INFO;
    public static void register() {
        SWAP_ITEMS = new SAKeyBindingOnce(StoneAge.MODID + ".key.swapHands", GLFW.GLFW_KEY_E, "key.categories.stoneage", true);
        SHOW_ITEM_INFO = new KeyBinding(StoneAge.MODID + ".key.showItemInfo", GLFW.GLFW_KEY_LEFT_CONTROL, "key.categories.stoneage");
        ClientRegistry.registerKeyBinding(SWAP_ITEMS);
        ClientRegistry.registerKeyBinding(SHOW_ITEM_INFO);
    }
}
