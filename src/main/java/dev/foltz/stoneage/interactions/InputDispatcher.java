package dev.foltz.stoneage.interactions;

import dev.foltz.stoneage.client.input.SAKeyBindings;
import dev.foltz.stoneage.network.packets.SAGatherAttemptPacket;
import dev.foltz.stoneage.network.packets.SAHandCraftAttemptPacket;
import dev.foltz.stoneage.network.SAPacketHandler;
import dev.foltz.stoneage.network.packets.SAPlaceAttemptPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InputDispatcher {

    // Prevents default MC left and right click.
    // Also sends information to server.
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInput(InputEvent.RawMouseEvent event) {
        event.setCanceled(true);

        int button = event.getButton();
        int action = event.getAction();
        int mods = event.getMods();
        final boolean left = button == GLFW.GLFW_MOUSE_BUTTON_LEFT;
        final boolean right = button == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
        final boolean release = action == GLFW.GLFW_RELEASE;
        final boolean press = action == GLFW.GLFW_PRESS;
        final boolean ctrl = (mods & GLFW.GLFW_MOD_CONTROL) == GLFW.GLFW_MOD_CONTROL;
        final boolean shift = (mods & GLFW.GLFW_MOD_SHIFT) == GLFW.GLFW_MOD_SHIFT;
        final boolean alt = (mods & GLFW.GLFW_MOD_ALT) == GLFW.GLFW_MOD_ALT;
        final boolean sup = (mods & GLFW.GLFW_MOD_SUPER) == GLFW.GLFW_MOD_SUPER;

        Minecraft mc = Minecraft.getInstance();
        World world = mc.world;
        PlayerEntity player = mc.player;
        double playerReach = player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue();
        float partialTicks = mc.getRenderPartialTicks();
        ItemStack leftItem = player.getHeldItemOffhand();
        ItemStack rightItem = player.getHeldItemMainhand();

        if (release) return;

        if (left) {
            if (ctrl) {
                SAPacketHandler.sendHandCraftAttempt(new SAHandCraftAttemptPacket());
            }
            else {
                if (leftItem.isEmpty() && rightItem.isEmpty()) {
                    player.swingArm(Hand.MAIN_HAND);
                }
                else if (!leftItem.isEmpty()) {
                    player.swingArm(Hand.MAIN_HAND);
                }
                else if (!rightItem.isEmpty()) {
                    Vec3d vec3d = player.getEyePosition(partialTicks);
                    Vec3d vec3d1 = player.getLook(partialTicks);
                    Vec3d vec3d2 = vec3d.add(vec3d1.x * playerReach, vec3d1.y * playerReach, vec3d1.z * playerReach);
                    RayTraceResult trace = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, false ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, player));
                    switch (trace.getType()) {
                        case BLOCK:
                            BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
                            System.out.println("Sending place packet");
                            SAPacketHandler.sendToolActionAttempt(Hand.MAIN_HAND, blockTrace.getPos());
                            break;
                        case ENTITY:
                        default:
                            break;
                    }
                }
            }
        }
        else if (right) {
            if (ctrl) {
                Vec3d vec3d = player.getEyePosition(partialTicks);
                Vec3d vec3d1 = player.getLook(partialTicks);
                Vec3d vec3d2 = vec3d.add(vec3d1.x * playerReach, vec3d1.y * playerReach, vec3d1.z * playerReach);
                RayTraceResult trace = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, false ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, player));
                switch (trace.getType()) {
                    case BLOCK:
                        BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
                        System.out.println("Sending place packet");
                        SAPacketHandler.sendPlaceAttempt(new SAPlaceAttemptPacket(blockTrace, Hand.MAIN_HAND));
                        break;
                    case ENTITY:
                    default:
                        break;
                }
            }
            else {
                Vec3d vec3d = player.getEyePosition(partialTicks);
                Vec3d vec3d1 = player.getLook(partialTicks);
                Vec3d vec3d2 = vec3d.add(vec3d1.x * playerReach, vec3d1.y * playerReach, vec3d1.z * playerReach);
                RayTraceResult trace = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, false ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, player));
                System.out.println(trace.getType());
                switch (trace.getType()) {
                    case BLOCK:
                        BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
                        System.out.println("Sending gather packet");
                        SAPacketHandler.sendGatherAttempt(new SAGatherAttemptPacket(blockTrace.getPos()));
                        break;
                    case ENTITY:
                    default:
                        break;
                }
            }
        }
    }

    // Handle keyboard input here
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTick(final TickEvent.ClientTickEvent event) {
        if (SAKeyBindings.SWAP_ITEMS.query()) {
            SAPacketHandler.sendSwapItemsAttempt();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBreakSpeed(final PlayerEvent.BreakSpeed event) {
        //event.setNewSpeed(0);
    }
}
