package me.swipez.custompots;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.swipez.custompots.potions.PotionManager;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GiveAllPotsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0 || !(sender instanceof Player) || !sender.hasPermission("giveallpots.command")) {
            return false;
        }

        Player player = (Player) sender;
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_COLLECTIONS.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_CLONING.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_WINGS.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_ANNIHILATION.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_CREATIVE.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_ENCHANTMENTS.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_RANDOMIZER.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_TELEPORTATION.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_SHOWERS.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());
        for (Map.Entry<Integer, ItemStack> entry :  player.getInventory().addItem(PotionManager.POT_EXPLOSIONS.clone()).entrySet()) player.getWorld().dropItem(player.getLocation(), entry.getValue());

        return true;
    }
}
