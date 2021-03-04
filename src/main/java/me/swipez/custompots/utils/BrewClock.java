package me.swipez.custompots.utils;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BrewClock extends BukkitRunnable {
    private Plugin plugin;
    BrewingController brewingController;

    private BrewingStand stand;

    private int stopTime;
    private int brewTime;
    private int fuelUse;


    public BrewClock(Plugin plugin, BrewingController brewingController, BrewingStand stand, int stopTime, int fuelUse) {
        super();
        this.plugin = plugin;
        this.brewingController = brewingController;

        this.stand = stand;

        this.stopTime = stopTime;
        this.brewTime = 0;
        this.fuelUse = fuelUse;

        useUpFuel(fuelUse);
        runTaskTimer(this.plugin, 0, 1);
    }

    @Override
    public void run() {
        updateTime();

        if(brewTime < stopTime){
            brewTime++;
        }
        else{
            BrewerInventory bInv = (BrewerInventory)stand.getInventory();
        
            ItemStack ing = bInv.getIngredient();
            int maxIng = 0;

            for(int i = 0; i < 3; i++){
                ItemStack base = bInv.getItem(i);
                BrewingRecipe recipe = brewingController.getRecipe(ing, base);
                if(recipe != null){
                    maxIng = Math.max(maxIng, recipe.getInputIngredient().getAmount());
                    recipe.getAction().brew(stand, recipe, i);
                }
            }

            ing.setAmount( ing.getAmount() - maxIng );
            if(ing.getAmount() == 0) ing = new ItemStack(Material.AIR);
            bInv.setIngredient(ing);

            this.cancel();
        }
    }

    private void useUpFuel(int fuel){
        int processedFuel = 0;

        while(processedFuel < fuel){
            int inside = this.stand.getFuelLevel();
            if(inside == 0) return;
            if(this.stand.getFuelLevel() >= fuel){
                this.stand.setFuelLevel(inside - fuel);
                processedFuel = fuel;
            }
            else{
                processedFuel += inside;
                this.stand.setFuelLevel(0);
            }
            this.stand.update();
        }
    }

    private void updateTime(){
        this.stand.setBrewingTime( (int)(400 * (1 - (double)brewTime / (double)stopTime)) );
        this.stand.update();
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    public int getFuelUse() {
        return fuelUse;
    }

    public void setFuelUse(int fuelUse) {
        if(fuelUse - this.fuelUse > 0){
            this.useUpFuel(fuelUse - this.fuelUse);
        }
        this.fuelUse = fuelUse;
    }
}
 