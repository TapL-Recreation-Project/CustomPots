package me.swipez.custompots.listeners;

import me.swipez.custompots.CustomPots;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EventListeners implements Listener {

    CustomPots plugin;

    public EventListeners(CustomPots plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e){
        if (e.getEntity().getKiller() instanceof Player){
            if (plugin.hasCloning.get(e.getEntity().getKiller().getUniqueId())){
                Entity entity = e.getEntity();
                for (int i=0;i<25;i++){
                    entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
                }
                String suffix = "_SPAWN_EGG";
                ItemStack spawneggs = new ItemStack(Material.valueOf(entity.getType().toString()+suffix), 64);
                entity.getWorld().dropItemNaturally(entity.getLocation(), spawneggs);
            }
        }
    }
    @EventHandler
    public void onPlayerPlacesChest(BlockPlaceEvent e){
        if (plugin.hasCollections.get(e.getPlayer().getUniqueId())){
            if (e.getBlock().getType().equals(Material.CHEST)){
                Block block = e.getBlock();
                Chest chestblock = (Chest) block.getState();
                Inventory inventory = chestblock.getBlockInventory();
                for (int i=0;i<inventory.getSize();i++){
                    int percentage = (int) (Math.random()*100);
                    if (percentage < 40){
                        int stack = (int) (Math.random() * 10);
                        Random random = new Random();
                        List<Material> materials = Arrays.asList(Material.values());
                        Material randommaterial = materials.get(random.nextInt(materials.size()));
                        ItemStack itemStack = new ItemStack(randommaterial, stack);
                        inventory.setItem(i, itemStack);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerBreaksBlock(BlockBreakEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (plugin.hasRandomizer.get(uuid)){
            Block block = e.getBlock();
            Location blockloc = block.getLocation();
            Random random = new Random();
            List<Material> materials = Arrays.asList(Material.values());
            Material randommaterial = materials.get(random.nextInt(materials.size()));
            if ((randommaterial == Material.AIR || randommaterial == Material.WATER || randommaterial == Material.LAVA || randommaterial == Material.FIRE)){
                randommaterial = Material.COBBLESTONE;
            }
            try {
                block.getWorld().dropItemNaturally(blockloc, new ItemStack(randommaterial, 1));
                block.setType(Material.AIR);
            }
            catch (IllegalArgumentException exception) {
                block.getWorld().dropItemNaturally(blockloc, new ItemStack(Material.COBBLESTONE, 1));
                block.setType(Material.AIR);
            }
        }
        if (plugin.hasExplosives.get(uuid)){
            Block block = e.getBlock();
            Location blockloc = block.getLocation();

            block.getWorld().createExplosion(blockloc, 1);
        }
    }
    @EventHandler
    public void onPlayerHitsMob(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if (plugin.hasExplosives.get(player.getUniqueId())){
                e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 1);
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        // Boolean values
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
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        // Boolean values
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
