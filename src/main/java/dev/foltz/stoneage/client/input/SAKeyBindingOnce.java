package dev.foltz.stoneage.client.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SAKeyBindingOnce extends KeyBinding {
    private static ArrayList<SAKeyBindingOnce> bindings = new ArrayList<>();
    private final boolean queueActiveOnPress;
    private boolean isQueued;

    public SAKeyBindingOnce(String name, int key, String category, boolean queueActiveOnPress) {
        super(name, key, category);
        this.queueActiveOnPress = queueActiveOnPress;
        isQueued = false;
        bindings.add(this);
    }

    @SubscribeEvent
    public static void onInput(InputEvent.KeyInputEvent event) {
        for (SAKeyBindingOnce binding : bindings) {
            if (binding.isActiveAndMatches(InputMappings.getInputByCode(event.getKey(), event.getScanCode()))) {
                if (event.getAction() == GLFW.GLFW_PRESS) {
                    if (binding.queueActiveOnPress) {
                        binding.isQueued = true;
                    }
                }
                else if (event.getAction() == GLFW.GLFW_RELEASE) {
                    if (!binding.queueActiveOnPress) {
                        binding.isQueued = true;
                    }
                }
            }
        }
    }

    public boolean query() {
        if (queueActiveOnPress) {
            if (isKeyDown()) {
                if (isQueued) {
                    isQueued = false;
                    return true;
                }
            }
            return false;
        }
        else {
            if (!isKeyDown()) {
                if (isQueued) {
                    isQueued = false;
                    return true;
                }
            }
            return false;
        }
    }
}
