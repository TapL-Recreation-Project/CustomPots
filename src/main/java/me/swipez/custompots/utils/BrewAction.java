package me.swipez.custompots.utils;

import me.swipez.custompots.potions.PotionManager;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class BrewAction {
    public void brew(BrewingStand stand, BrewingRecipe recipe, int slot){
        BrewerInventory inv = stand.getInventory();
        inv.setItem(slot, recipe.getResult());
    }
}