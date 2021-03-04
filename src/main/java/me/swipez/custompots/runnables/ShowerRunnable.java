package me.swipez.custompots.runnables;

import me.swipez.custompots.CustomPots;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShowerRunnable extends BukkitRunnable {

    CustomPots plugin;

    public ShowerRunnable(CustomPots plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.hasShowers.get(player.getUniqueId())){
                Location loc = player.getLocation();
                int randomx = (int) (Math.random() * (10 - 1 + 1) + 1);
                int randomz = (int) (Math.random() * (10 - 1 + 1) + 1);
                int coinflipx = (int) (Math.random()*100);
                int coinflipz = (int) (Math.random()*100);
                if (coinflipx < 50){
                    randomx = randomx * -1;
                }
                if (coinflipz < 50){
                    randomz = randomz * -1;
                }
                loc.setX(loc.getX()+randomx);
                loc.setZ(loc.getZ()+randomz);
                loc.setY(loc.getY()+20);

                Random random = new Random();
                List<Material> materials = Arrays.asList(Material.values());
                Material randommaterial = materials.get(random.nextInt(materials.size()));
                if (randommaterial != Material.AIR){
                    try {
                        player.getWorld().dropItemNaturally(loc, new ItemStack(randommaterial, 1));
                    }
                    catch (IllegalArgumentException exception){
                        player.getWorld().dropItemNaturally(loc, new ItemStack(Material.COBBLESTONE, 1));
                    }

                }
            }
        }
    }
}
