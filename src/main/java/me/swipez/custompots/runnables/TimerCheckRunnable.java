package me.swipez.custompots.runnables;

import me.swipez.custompots.CustomPots;
import me.swipez.custompots.utils.SendTitleBarMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;


import java.util.UUID;

public class TimerCheckRunnable extends BukkitRunnable {

    CustomPots plugin;

    public TimerCheckRunnable(CustomPots plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            if (plugin.hasCloning.get(uuid)){
                int cloningtimer = plugin.timeCloning.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.GREEN + " Cloning", cloningtimer);
                plugin.timeCloning.put(uuid, cloningtimer);
            }
            if (plugin.hasCollections.get(uuid)){
                int collectionstimer = plugin.timeCollections.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.DARK_BLUE + " Collections", collectionstimer);
                plugin.timeCollections.put(uuid, collectionstimer);
            }
            if (plugin.hasCreative.get(uuid)){
                int creativetimer = plugin.timeCreative.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.GOLD + " Creative", creativetimer);

                plugin.timeCreative.put(uuid, creativetimer);
            }
            if (plugin.hasRandomizer.get(uuid)){
                int randomizertimer = plugin.timeRandomizer.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.LIGHT_PURPLE + " Randomizer", randomizertimer);

                plugin.timeRandomizer.put(uuid, randomizertimer);
            }
            if (plugin.hasShowers.get(uuid)){
                int showerstimer = plugin.timeShowers.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.AQUA + " Showers", showerstimer);

                plugin.timeShowers.put(uuid, showerstimer);
            }
            if (plugin.hasExplosives.get(uuid)){
                int explosiontimer = plugin.timeExplosives.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.RED + " Explosions", explosiontimer);
                plugin.timeExplosives.put(uuid, explosiontimer);
            }
            if (plugin.hasWings.get(uuid)){
                int wingstimer = plugin.timeWings.get(uuid)-1;
                SendTitleBarMessage.sendMessage(player, ChatColor.GRAY + "Potion of"  + ChatColor.YELLOW + " Wings", wingstimer);

                plugin.timeWings.put(uuid, wingstimer);
            }
        }
    }
}
