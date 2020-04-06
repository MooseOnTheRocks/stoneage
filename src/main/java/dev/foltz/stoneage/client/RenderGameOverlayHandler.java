package dev.foltz.stoneage.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
@OnlyIn(Dist.CLIENT)
public class RenderGameOverlayHandler {
    @SubscribeEvent
    public static void onDrawBlockHighlightEvent(final DrawBlockHighlightEvent event) {
        // TODO: Context sensitive block highlights
        // NOTE: Event name will change in 1.15
    }

    @SubscribeEvent
    public static void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Pre event) {
        switch (event.getType()) {
        case ARMOR:
        case HEALTH:
        case FOOD:
        case AIR:
        case HOTBAR:
        case EXPERIENCE:
            event.setCanceled(true);
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
