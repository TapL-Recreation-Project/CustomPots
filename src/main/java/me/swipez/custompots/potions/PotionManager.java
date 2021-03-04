package me.swipez.custompots.potions;

import me.swipez.custompots.utils.PotionEvent;
import me.swipez.custompots.utils.PotionItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PotionManager extends ItemStack {

    static String prefix = ChatColor.WHITE+"Potion of ";
    public static Map<UUID, GameMode> uuidToGameMode = new HashMap<>();
    public static final ItemStack POT_COLLECTIONS = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.DARK_BLUE+"Collections"+ChatColor.GRAY+" [2:00]")
            .lore(ChatColor.GRAY+"Placed chests will have random items.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.BLUE)
            .build();
    public static final ItemStack POT_CLONING = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.GREEN+"Cloning"+ChatColor.GRAY+" [3:00]")
            .lore(ChatColor.GRAY+"Mobs killed will duplicate themselves and drop a stack of their spawn eggs.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.GREEN)
            .build();
    public static final ItemStack POT_ANNIHILATION = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.BLUE+"Annihilation"+ChatColor.GRAY+" [Instant]")
            .lore(ChatColor.GRAY+"Instantly kills all nearby entities.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.BLUE)
            .build();
    public static final ItemStack POT_TELEPORTATION = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.DARK_GREEN+"Teleportation"+ChatColor.GRAY+" [Instant]")
            .lore(ChatColor.GRAY+"You will be teleported 1,000,000 blocks away!")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.GREEN)
            .build();
    public static final ItemStack POT_ENCHANTMENTS = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.DARK_BLUE+"Enchantments"+ChatColor.GRAY+" [Instant]")
            .lore(ChatColor.GRAY+"You will receive 100 random enchantments on your tools, armor, weapons, etc.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.FUCHSIA)
            .build();
    public static final ItemStack POT_RANDOMIZER = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.LIGHT_PURPLE+"Randomizer"+ChatColor.GRAY+" [4:00]")
            .lore(ChatColor.GRAY+"When mined, blocks drop random blocks and items.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.PURPLE)
            .build();
    public static final ItemStack POT_SHOWERS = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.AQUA+"Showers"+ChatColor.GRAY+" [1:00]")
            .lore(ChatColor.GRAY+"Every half a second, random loot drops from the sky.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.AQUA)
            .build();
    public static final ItemStack POT_EXPLOSIONS = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.RED+"Explosions"+ChatColor.GRAY+" [3:00]")
            .lore(ChatColor.GRAY+"Every block you mine or every mob you hit, you explode the block/mob.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.RED)
            .build();
    public static final ItemStack POT_WINGS = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.YELLOW+"Wings"+ChatColor.GRAY+" [1:30]")
            .lore(ChatColor.GRAY+"You can fly like creative mode.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.YELLOW)
            .build();
    public static final ItemStack POT_CREATIVE = PotionItemBuilder.of(Material.POTION)
            .name(prefix+ChatColor.GOLD+"Creative"+ChatColor.GRAY+" [0:03]")
            .lore(ChatColor.GRAY+"Your gamemode is changed to creative.")
            .enchantment(Enchantment.CHANNELING, 1)
            .flags(ItemFlag.HIDE_ENCHANTS)
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .color(Color.ORANGE)
            .build();
    public static void run(ItemStack stack) {

    }
}
