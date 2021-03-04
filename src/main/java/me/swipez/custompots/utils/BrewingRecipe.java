package me.swipez.custompots.utils;

import org.bukkit.*;
import org.bukkit.inventory.*;

public class BrewingRecipe {
    private final NamespacedKey key;

    private final ItemStack result;
    private final ItemStack inputIngredient;
    private final ItemStack inputBase;

    private int fuelUse;
    private int cookingTime;

    private BrewAction action;

    public BrewingRecipe(NamespacedKey key, ItemStack result, ItemStack inputIngredient, ItemStack inputBase) {
        this(key, result, inputIngredient, inputBase, 1, 400, new BrewAction());
    }

    public BrewingRecipe(NamespacedKey key, ItemStack result, ItemStack inputIngredient, ItemStack inputBase, int fuelUse, int cookingTime) {
        this(key, result, inputIngredient, inputBase, fuelUse, cookingTime, new BrewAction());
    }
    
    public BrewingRecipe(NamespacedKey key, ItemStack result, ItemStack inputIngredient, ItemStack inputBase,
            int fuelUse, int cookingTime, BrewAction action) {
        this.key = key;
        this.result = result;
        this.inputIngredient = (inputIngredient == null) ? new ItemStack(Material.AIR) : inputIngredient;
        this.inputBase = (inputBase == null) ? new ItemStack(Material.AIR) : inputBase;
        this.fuelUse = fuelUse;
        this.cookingTime = cookingTime;
        this.action = action;
    }


    public NamespacedKey getKey() {
        return key;
    }

    public ItemStack getResult() {
        return result;
    }

    public ItemStack getInputIngredient() {
        return inputIngredient;
    }

    public ItemStack getInputBase() {
        return inputBase;
    }

    public int getFuelUse() {
        return fuelUse;
    }

    public void setFuelUse(int fuelUse) {
        this.fuelUse = fuelUse;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public BrewAction getAction() {
        return action;
    }

    public void setAction(BrewAction action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "BrewingRecipe [cookingTime=" + cookingTime + ", fuelUse=" + fuelUse + ", inputBase=" + inputBase.toString()
                + ", inputIngredient=" + inputIngredient.toString() + ", key=" + key.toString() + ", result=" + result.toString() + "]";
    }

}