package me.swipez.custompots.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SendTitleBarMessage {
    public static void sendMessage(Player player, String potionname, int seconds) {
        int minutes = 0;
        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        String time;
        if (String.valueOf(seconds).length() == 1)
            time = ChatColor.GRAY + " [" + minutes + ":0" + seconds + "]";
        else
            time = ChatColor.GRAY + " [" + minutes + ":" + seconds + "]";

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(potionname + time));
    }
}
