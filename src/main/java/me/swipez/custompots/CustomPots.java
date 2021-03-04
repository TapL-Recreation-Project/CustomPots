package me.swipez.custompots;

import me.swipez.custompots.bstats.Metrics;
import me.swipez.custompots.listeners.EventListeners;
import me.swipez.custompots.potions.PotionManager;
import me.swipez.custompots.runnables.ShowerRunnable;
import me.swipez.custompots.runnables.TimerCheckRunnable;
import me.swipez.custompots.runnables.TimesUpRunnable;
import me.swipez.custompots.utils.BrewingController;
import me.swipez.custompots.utils.BrewingRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class CustomPots extends JavaPlugin {

    // Storing potion boolean values
    public HashMap<UUID, Boolean> hasCollections = new HashMap<>();
    public HashMap<UUID, Boolean> hasCloning = new HashMap<>();
    public HashMap<UUID, Boolean> hasCreative = new HashMap<>();
    public HashMap<UUID, Boolean> hasRandomizer = new HashMap<>();
    public HashMap<UUID, Boolean> hasShowers = new HashMap<>();
    public HashMap<UUID, Boolean> hasExplosives = new HashMap<>();
    public HashMap<UUID, Boolean> hasWings = new HashMap<>();

    // Storing potion values in seconds
    public HashMap<UUID, Integer> timeCollections = new HashMap<>();
    public HashMap<UUID, Integer> timeCloning = new HashMap<>();
    public HashMap<UUID, Integer> timeCreative = new HashMap<>();
    public HashMap<UUID, Integer> timeRandomizer = new HashMap<>();
    public HashMap<UUID, Integer> timeShowers = new HashMap<>();
    public HashMap<UUID, Integer> timeExplosives = new HashMap<>();
    public HashMap<UUID, Integer> timeWings = new HashMap<>();

    private BrewingController bc;

    @Override
    public void onEnable() {
        getCommand("giveallpots").setExecutor(new GiveAllPotsCommand());
        // Get players currently ingame
        for (Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            this.hasCreative.put(uuid, false);
            this.hasShowers.put(uuid, false);
            this.hasWings.put(uuid, false);
            this.hasExplosives.put(uuid, false);
            this.hasRandomizer.put(uuid, false);
            this.hasCollections.put(uuid, false);
            this.hasCloning.put(uuid, false);

            this.timeCreative.put(uuid, 0);
            this.timeShowers.put(uuid, 0);
            this.timeWings.put(uuid, 0);
            this.timeExplosives.put(uuid, 0);
            this.timeRandomizer.put(uuid, 0);
            this.timeCollections.put(uuid, 0);
            this.timeCloning.put(uuid, 0);

        }



        bc = new BrewingController(this);

        // Recipe Init
        recipeInit("pot_collections", PotionManager.POT_COLLECTIONS.clone(), Material.CHEST);
        recipeInit("pot_cloning", PotionManager.POT_CLONING.clone(), Material.WHEAT_SEEDS);
        recipeInit("pot_annihilation", PotionManager.POT_ANNIHILATION.clone(), Material.DIAMOND_SWORD);
        recipeInit("pot_creative", PotionManager.POT_CREATIVE.clone(), Material.REDSTONE_BLOCK);
        recipeInit("pot_teleportation", PotionManager.POT_TELEPORTATION.clone(), Material.ENDER_PEARL);
        recipeInit("pot_enchantments", PotionManager.POT_ENCHANTMENTS.clone(), Material.LAPIS_LAZULI);
        recipeInit("pot_randomizer", PotionManager.POT_RANDOMIZER.clone(), Material.REDSTONE_TORCH);
        recipeInit("pot_showers", PotionManager.POT_SHOWERS.clone(), Material.NETHERITE_INGOT);
        recipeInit("pot_explosives", PotionManager.POT_EXPLOSIONS.clone(), Material.FIRE_CHARGE);
        recipeInit("pot_wings", PotionManager.POT_WINGS.clone(), Material.FIREWORK_ROCKET);

        // Runnables
        BukkitTask TimeCheck = new TimerCheckRunnable(this).runTaskTimer(this, 20, 20);
        BukkitTask TimesUp = new TimesUpRunnable(this).runTaskTimer(this, 20, 20);
        BukkitTask Shower = new ShowerRunnable(this).runTaskTimer(this, 5, 5);

        // Listeners
        getServer().getPluginManager().registerEvents(new PotionListener(this), this);
        getServer().getPluginManager().registerEvents(new EventListeners(this), this);
        new Metrics(this, 10549);
    }

    @Override
    public void onDisable() {
        bc.stop();
    }


    public void recipeInit(String key, ItemStack resultstack, Material input){
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.AWKWARD));
        potion.setItemMeta(pm);

        BrewingRecipe recipe = new BrewingRecipe(
                new NamespacedKey(this, key),
                resultstack,
                new ItemStack(input),
                potion
        );
        bc.addRecipe(recipe);
    }
}
