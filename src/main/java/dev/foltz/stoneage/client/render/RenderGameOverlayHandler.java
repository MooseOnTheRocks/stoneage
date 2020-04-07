package dev.foltz.stoneage.client.render;

import dev.foltz.stoneage.client.input.SAKeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderGameOverlayHandler {

    public static boolean shouldShowOverlay = false;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onDrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
        // TODO: Context sensitive block highlights
        // NOTE: Event name will change in 1.15
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Pre event) {
        switch (event.getType()) {
        case ARMOR:
        case HEALTH:
        case FOOD:
        case AIR:
        case HOTBAR:
        case EXPERIENCE:
            if (!SAKeyBindings.SHOW_ITEM_INFO.isKeyDown()) {
                event.setCanceled(true);
            }
            break;

        case ALL:
        case HELMET:
        case PORTAL:
        case CROSSHAIRS:
        case BOSSHEALTH: // All boss bars
        case BOSSINFO:   // Individual boss bar
        case TEXT:
        case HEALTHMOUNT:
        case JUMPBAR:
        case CHAT:
        case PLAYER_LIST:
        case DEBUG:
        case POTION_ICONS:
        case SUBTITLES:
        case FPS_GRAPH:
        case VIGNETTE:
        default:
            break;
        }
    }
}
