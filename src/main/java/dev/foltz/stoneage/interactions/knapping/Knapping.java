package dev.foltz.stoneage.interactions.knapping;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;

public class Knapping {
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
            System.out.println("Not a recipe :c");
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

        stackTool.damageItem(1, player, (p) -> {});
        stackMat.damageItem(1, player, (p) -> {
            if (handMat == Hand.MAIN_HAND) {
                player.setItemStackToSlot(EquipmentSlotType.MAINHAND, recipe.result.copy());
            }
            else {
                player.setItemStackToSlot(EquipmentSlotType.OFFHAND, recipe.result.copy());
            }
        });
        System.out.println("Did it!");
        System.out.println("isRemote: " + world.isRemote);
        return true;
    }
}
