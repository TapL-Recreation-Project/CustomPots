package me.swipez.custompots.runnables;

import me.swipez.custompots.CustomPots;
import me.swipez.custompots.potions.PotionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class TimesUpRunnable extends BukkitRunnable {

    CustomPots plugin;

    public TimesUpRunnable(CustomPots plugin) {
        this.plugin = plugin;
    }

    public static String doPotionHeadsUp(String potionname, ChatColor color){
        String string = ChatColor.WHITE+"Your "+color+potionname+ChatColor.WHITE+" Potion has expired!";
        String bolded = ChatColor.BOLD+string;
        return bolded;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            if (plugin.hasCollections.get(uuid)){
                if (plugin.timeCollections.get(uuid) <= 0){
                    plugin.hasCollections.put(uuid, false);
                }
            }
            if (plugin.hasCloning.get(uuid)){
                if (plugin.timeCloning.get(uuid) <= 0){
                    plugin.hasCloning.put(uuid, false);
                }
            }
            if (plugin.hasRandomizer.get(uuid)){
                if (plugin.timeRandomizer.get(uuid) <= 0){
                    plugin.hasRandomizer.put(uuid, false);
                }
            }
            if (plugin.hasCreative.get(uuid)){
                if (plugin.timeCreative.get(uuid) <= 0){
                    plugin.hasCreative.put(uuid, false);
                    changeGamemode(player);
                    if (plugin.hasWings.get(uuid)){
                        player.setAllowFlight(true);
                    }
                }
            }
            if (plugin.hasShowers.get(uuid)){
                if (plugin.timeShowers.get(uuid) <= 0){
                    plugin.hasShowers.put(uuid, false);
                }
            }
            if (plugin.hasExplosives.get(uuid)){
                if (plugin.timeExplosives.get(uuid) <= 0){
                    plugin.hasExplosives.put(uuid,false);
                }
            }
            if (plugin.hasWings.get(uuid)){
                if (plugin.timeWings.get(uuid) <= 0){
                    plugin.hasWings.put(uuid, false);
                    player.setAllowFlight(false);
                }
            }
        }
    }
    public static void changeGamemode(Player player){
        player.setGameMode(PotionManager.uuidToGameMode.get(player.getUniqueId()));
    }
}
