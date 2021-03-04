package me.swipez.custompots.utils;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;

public class PotionEvent implements Listener {
    private Plugin plugin;
    private BrewingController brewingController;

    private static HashMap<BrewingStand, BrewClock> activeBrews = new HashMap<BrewingStand, BrewClock>();

    public PotionEvent(Plugin plugin, BrewingController brewingController) {
        super();
        this.plugin = plugin;
        this.brewingController = brewingController;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBrewingInventoryClickEvent(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();

        if (inv == null || inv.getType() != InventoryType.BREWING) return; // If not brewing stand

        manageBrewerInventory(event);

        BrewerInventory bInv = (BrewerInventory)inv;
        
        ItemStack ing = bInv.getIngredient();
        
        boolean canBrew = false;
        int maxTime = 0;
        int maxFuel = 0;
        
        BrewingStand bStand = bInv.getHolder();
        BrewClock brewClock = activeBrews.get(bStand);

        java.util.logging.Logger l = plugin.getLogger();
        int fuel = BrewingController.totalFuelInBrewingStand(bStand);

        if(brewClock != null && !brewClock.isCancelled()) fuel += brewClock.getFuelUse();


        for(int i = 0; i < 3; i++){
            ItemStack base = bInv.getItem(i);
            BrewingRecipe r = brewingController.getRecipe(ing, base, fuel);
            if(r != null){
                canBrew = true;
                maxTime = Math.max(maxTime, r.getCookingTime());
                maxFuel = Math.max(maxFuel, r.getFuelUse());
            }
        }



        if(brewClock != null && !brewClock.isCancelled()){
            if(!canBrew){
                brewClock.cancel();
                brewClock = null;
            }
            else{
                if(brewClock.getFuelUse() != maxFuel)
                    brewClock.setFuelUse(maxFuel);
                if(brewClock.getStopTime() != maxTime)
                    brewClock.setStopTime(maxTime);
            }
        }
        else{
            if(canBrew){
                brewClock = new BrewClock(plugin, brewingController,  bStand, maxTime, maxFuel);
                activeBrews.put(bStand, brewClock);
            }
        }
    }
    
    private void manageBrewerInventory(InventoryClickEvent event){
        event.setCancelled(true);

        ClickType cl = event.getClick();
        Player p = (Player)event.getWhoClicked();
        ItemStack slot = event.getCurrentItem();
        ItemStack held = event.getCursor();

        int slotC = slot.getAmount();
        int heldC = held.getAmount();

        ItemStack empty = new ItemStack(Material.AIR);
        boolean dropAll = true;
        
        switch (cl) {
            case LEFT: {
                if(slot.isSimilar(held)){ // Combine slot and held stacks (limited to max stack size)
                    if(slotC + heldC > slot.getMaxStackSize()){
                        slot.setAmount(slot.getMaxStackSize());
                        event.setCurrentItem(slot);
                        held.setAmount(heldC - (slot.getMaxStackSize() - slotC));
                        p.setItemOnCursor(held);
                    }
                    else{
                        slot.setAmount(slotC + heldC);
                        event.setCurrentItem(slot);
                        p.setItemOnCursor(empty);
                    }
                }
                else{ // Or swap them
                    event.setCurrentItem(held);
                    p.setItemOnCursor(slot);
                }
                break;
            }
            case RIGHT: {
                if(heldC > 0 && (slot.isSimilar(held) || slotC == 0) && slotC + 1 <= held.getMaxStackSize()){ // Add one item to slot if empty or similar
                    held.setAmount(slotC + 1);
                    event.setCurrentItem(held);
                    if(heldC - 1 > 0) 
                        held.setAmount(heldC - 1);
                    else
                        held = empty;
                    p.setItemOnCursor(held);
                }
                else if(heldC == 0){ // Or take a half
                    slot.setAmount(slotC / 2);
                    event.setCurrentItem(slot);
                    slot.setAmount(slotC - (slotC / 2));
                    p.setItemOnCursor(slot);
                }
                else{ // Or swap them
                    event.setCurrentItem(held);
                    p.setItemOnCursor(slot);
                }
                break;
            }
            case SHIFT_LEFT:
            case SHIFT_RIGHT: { // Shift click item out of an inventory
                HashMap<Integer, ItemStack> overflow =  p.getInventory().addItem(slot);
                if(overflow.size() > 0){
                    for(Integer i : overflow.keySet()){
                        event.setCurrentItem(overflow.get(i));
                    }
                }
                else{
                    event.setCurrentItem(empty);
                }
                break;
            }
            case DROP:{
                dropAll = false;
            }
            case CONTROL_DROP:{ // Drop one or all items out of inventory
                if(slotC == 0 || heldC > 0) break;

                ItemStack hand = p.getInventory().getItemInMainHand();
                p.getInventory().setItemInMainHand(slot);
                p.dropItem(dropAll);
                p.getInventory().setItemInMainHand(hand);

                int drop = (dropAll) ? slotC : 1;
                if(slotC - drop > 0) 
                    slot.setAmount(slotC - drop);
                else
                    slot = empty;

                event.setCurrentItem(slot);
                break;
            }
            case SWAP_OFFHAND:{ // Swap item with offhand
                event.setCurrentItem(p.getInventory().getItemInOffHand());
                p.getInventory().setItemInOffHand(slot);
                break;
            }
            case NUMBER_KEY:{ // Swap item with hotbar
                int hotbar = event.getHotbarButton();
                event.setCurrentItem(p.getInventory().getItem(hotbar));
                p.getInventory().setItem(hotbar, slot);
                break;
            }
            case DOUBLE_CLICK:{ // Double click pickup
                int i = 0;
                for(ItemStack stack : event.getInventory().getContents()){ // Search brewing inventory
                    if(stack != null && stack.isSimilar(held)){
                        int stackC = stack.getAmount();
                        if(stackC + held.getAmount() > held.getMaxStackSize()){
                            stack.setAmount(held.getAmount() - (held.getMaxStackSize() - stackC));
                            held.setAmount(held.getMaxStackSize());
                            event.getInventory().setItem(i, stack);
                            p.setItemOnCursor(held);
                        }
                        else{
                            held.setAmount(stackC + held.getAmount());
                            event.getInventory().setItem(i, empty);
                        }
                    }
                    p.setItemOnCursor(held);
                    
                    i++;
                }
                if(held.getAmount() < held.getMaxStackSize()){ // If still empty search player inventory
                    i = 0;
                    for(ItemStack stack : p.getInventory().getContents()){ 
                        if(stack != null && stack.isSimilar(held)){
                            int stackC = stack.getAmount();
                            if(stackC + held.getAmount() > held.getMaxStackSize()){
                                stack.setAmount(held.getAmount() - (held.getMaxStackSize() - stackC));
                                held.setAmount(held.getMaxStackSize());
                                p.getInventory().setItem(i, stack);
                                p.setItemOnCursor(held);
                            }
                            else{
                                held.setAmount(stackC + held.getAmount());
                                p.getInventory().setItem(i, empty);
                            }
                        }
                        p.setItemOnCursor(held);
                        
                        i++;
                    }
                }
                break;
            }
            default: break;
        }
        ((BrewingStand)((BrewerInventory)event.getInventory()).getHolder()).update(true);
    }
}