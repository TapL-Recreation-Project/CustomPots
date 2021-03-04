package me.swipez.custompots;

import me.swipez.custompots.potions.PotionManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PotionListener implements Listener {

    CustomPots plugin;

    public PotionListener(CustomPots plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (e.getItem().isSimilar(PotionManager.POT_CREATIVE)){
            plugin.hasCreative.put(uuid, true);
            plugin.timeCreative.put(uuid, 3);
            PotionManager.uuidToGameMode.put(e.getPlayer().getUniqueId(), e.getPlayer().getGameMode());
            player.setGameMode(GameMode.CREATIVE);
            ItemStack glassbottle = new ItemStack(Material.GLASS_BOTTLE, 1);
            int heldslot = player.getInventory().getHeldItemSlot();
            player.getInventory().setItem(heldslot, glassbottle);

        }
        if (e.getItem().isSimilar(PotionManager.POT_COLLECTIONS)){
            plugin.hasCollections.put(uuid, true);
            plugin.timeCollections.put(uuid, 120);
        }
        if (e.getItem().isSimilar(PotionManager.POT_CLONING)){
            plugin.hasCloning.put(uuid, true);
            plugin.timeCloning.put(uuid, 180);
        }
        if (e.getItem().isSimilar(PotionManager.POT_ANNIHILATION)){
            List<Entity> entities = player.getNearbyEntities(100,100,100);
            for (int i=0;i<entities.size();i++){
                Entity entity = entities.get(i);
                if (entity instanceof Damageable){
                    if (entity instanceof Player){
                        Player playerentity = (Player) entity;
                        if (playerentity.getGameMode().equals(GameMode.CREATIVE)){
                            return;
                        }
                    }
                    Damageable damageable = (Damageable) entity;
                    damageable.setHealth(0);
                }
            }
        }
        if (e.getItem().isSimilar(PotionManager.POT_TELEPORTATION)){
            Location teleportloc = player.getLocation();
            teleportloc.setX(teleportloc.getX()+500000);
            teleportloc.setZ(teleportloc.getZ()+500000);
            int y = player.getWorld().getHighestBlockYAt(teleportloc);
            teleportloc.setY(y);
            player.teleport(teleportloc);
        }
        if (e.getItem().isSimilar(PotionManager.POT_ENCHANTMENTS)){
            Inventory inventory = player.getInventory();
            for (int i=0;i<inventory.getSize();i++){
                for (int j=0;j<100;j++){
                    if (inventory.getItem(i) != null){
                        ItemStack itemStack = inventory.getItem(i);
                        Map<Enchantment, Integer> list = itemStack.getEnchantments();
                        Enchantment randEnchant = Enchantment.values()[(int) (Math.random()*Enchantment.values().length)];
                        if (randEnchant.canEnchantItem(itemStack)){
                            if (itemStack.containsEnchantment(randEnchant)){
                                int value = list.get(randEnchant)+1;
                                itemStack.addEnchantment(randEnchant, value);
                            }
                            else {
                                int value = 1;
                                itemStack.addEnchantment(randEnchant, value);
                            }
                        }
                    }
                }
            }
        }
        if (e.getItem().isSimilar(PotionManager.POT_RANDOMIZER)){
            plugin.hasRandomizer.put(uuid, true);
            plugin.timeRandomizer.put(uuid, 240);
        }
        if (e.getItem().isSimilar(PotionManager.POT_SHOWERS)){
            plugin.hasShowers.put(uuid, true);
            plugin.timeShowers.put(uuid, 60);
        }
        if (e.getItem().isSimilar(PotionManager.POT_EXPLOSIONS)){
            plugin.hasExplosives.put(uuid, true);
            plugin.timeExplosives.put(uuid, 180);
        }
        if (e.getItem().isSimilar(PotionManager.POT_WINGS)){
            plugin.hasWings.put(uuid, true);
            plugin.timeWings.put(uuid, 90);
            player.setAllowFlight(true);
        }
        if (e.getItem().getType().equals(Material.MILK_BUCKET)){
            plugin.hasCreative.put(uuid, false);
            plugin.hasShowers.put(uuid, false);
            plugin.hasWings.put(uuid, false);
            plugin.hasExplosives.put(uuid, false);
            plugin.hasRandomizer.put(uuid, false);
            plugin.hasCollections.put(uuid, false);
            plugin.hasCloning.put(uuid, false);

            // Integers
            plugin.timeCreative.put(uuid, 0);
            plugin.timeShowers.put(uuid, 0);
            plugin.timeWings.put(uuid, 0);
            plugin.timeExplosives.put(uuid, 0);
            plugin.timeRandomizer.put(uuid, 0);
            plugin.timeCollections.put(uuid, 0);
            plugin.timeCloning.put(uuid, 0);
        }

    }
}
