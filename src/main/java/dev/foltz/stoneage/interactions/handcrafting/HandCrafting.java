package dev.foltz.stoneage.interactions.handcrafting;

import dev.foltz.stoneage.item.SAItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class HandCrafting {

    public static final ArrayList<Recipe> recipes = new ArrayList<>();

    private static class Recipe {
        public final ItemStack tool, mat, result;
        public Recipe(ItemStack tool, ItemStack mat, ItemStack result) {
            this.tool = tool;
            this.mat = mat;
            this.result = result;
            System.out.println("New Recipe: (" + tool + ", " + mat + ") => " + result);
        }

        public boolean validFor(ItemStack a, ItemStack b) {
            if ((tool.isItemEqualIgnoreDurability(a) && mat.isItemEqualIgnoreDurability(b))
                || (tool.isItemEqualIgnoreDurability(b) && mat.isItemEqualIgnoreDurability(a))) {
                return true;
            }
            else{
                return false;
            }
        }
    }

    public static Recipe getRecipe(ItemStack a, ItemStack b) {
        for (Recipe recipe : recipes) {
            if (recipe.validFor(a, b)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isRecipe(ItemStack a, ItemStack b) {
        for (Recipe recipe : recipes) {
            System.out.println("Recipe: (" + recipe.tool + ", " + recipe.mat + ") => " + recipe.result);
            System.out.println("Hands:  (" + a + ", " + b + ")");
            if (recipe.validFor(a, b)) return true;
        }
        return false;
    }

    public static void addRecipe(ItemStack tool, ItemStack mat, ItemStack result) {
        if (isRecipe(tool, mat)) {
            Recipe recipe = getRecipe(tool, mat);
            System.out.println("Ambiguous recipe! New: (" + tool + ", " + mat + ") => " + result + " | Old: (" + recipe.tool + ", " + recipe.mat + ") => " + result);
        }
        recipes.add(new Recipe(tool.copy(), mat.copy(), result.copy()));
    }

    public static boolean attempt(World world, PlayerEntity player) {
        ItemStack stackRight = player.getHeldItem(Hand.MAIN_HAND);
        ItemStack stackLeft = player.getHeldItem(Hand.OFF_HAND);
        if (!isRecipe(stackRight, stackLeft)) {
            return false;
        }
        Recipe recipe = getRecipe(stackRight, stackLeft);
        ItemStack stackTool;
        ItemStack stackMat;
        Hand handMat;
        if (recipe.tool.isItemEqualIgnoreDurability(stackRight)) {
            stackTool = stackRight;
            stackMat = stackLeft;
            handMat = Hand.OFF_HAND;
        }
        else {
            stackTool = stackLeft;
            stackMat = stackRight;
            handMat = Hand.MAIN_HAND;
        }

        System.out.println("Recipe:");
        System.out.println("  Tool: " + recipe.tool);
        System.out.println("  Mat: " + recipe.mat);
        System.out.println("Selected:" );
        System.out.println("  Tool: " + stackTool);
        System.out.println("  Mat: " + stackMat);

        stackTool.damageItem(1, player, (p) -> {});
        stackMat.damageItem(1, player, (p) -> {
            System.out.println("Hand Mat: " + handMat);
            if (handMat == Hand.MAIN_HAND) {
                player.setItemStackToSlot(EquipmentSlotType.MAINHAND, recipe.result.copy());
            }
            else {
                player.setItemStackToSlot(EquipmentSlotType.OFFHAND, recipe.result.copy());
            }
        });

        return true;
    }
}
