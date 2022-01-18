package me.kolterdyx.chemicalwarfare;

import me.kolterdyx.chemicalwarfare.utils.*;
import me.kolterdyx.chemicalwarfare.weapons.GasCloud;
import me.kolterdyx.chemicalwarfare.web.ResourcePackHost;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public final class ChemicalWarfare extends JavaPlugin {

    public static long particlePeriod=1;
    private ArrayList<BukkitRunnable> gasClouds = new ArrayList<>();
    private static ArrayList<NamespacedKey> recipes = new ArrayList<>();
    private File customConfigFile;
    private static FileConfiguration customConfig;

    public static String getString() {
        return ChatColor.RED + "[" +ChatColor.DARK_GREEN+"Chemical Warfare"+  ChatColor.RED +"]"+ChatColor.YELLOW;
    }

    public static void addRecipe(NamespacedKey key) {
        recipes.add(key);
    }

    public static Collection<NamespacedKey> getRecipes() {
        return recipes;
    }

    @Override
    public void onEnable() {

        createCustomConfig();

        ItemManager.init(this);

        getServer().getPluginManager().registerEvents(new CWListener(this), this);

        this.getCommand("gas").setExecutor(new Commands(this));
        this.getCommand("cwpack").setExecutor(new Commands(this));
        this.getCommand("gas").setTabCompleter(new CWTabCompleter(this));

        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            player.discoverRecipes(getRecipes());
        }

        try {
            new ResourcePackHost().start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().info("Chemical Warfare plugin loaded");
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()){
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public static FileConfiguration getCustomConfig(){
        return customConfig;
    }
    
    public void addGasCloud(GasCloud cloud){
        gasClouds.add(cloud);
    }

    public void stopAllGas(){
        for (BukkitRunnable cloud : gasClouds){
            cloud.cancel();
        }
    }

    @Override
    public void onDisable() {
        stopAllGas();
    }

    public void reload() {
        createCustomConfig();

        Tier.ONE.setAmount(customConfig.getInt("cloud-particles.tier-one"));
        Tier.TWO.setAmount(customConfig.getInt("cloud-particles.tier-two"));
        Tier.THREE.setAmount(customConfig.getInt("cloud-particles.tier-three"));
        Tier.FOUR.setAmount(customConfig.getInt("cloud-particles.tier-four"));

        Tier.ONE.setDurability(customConfig.getInt("gas-mask-durability.tier-one"));
        Tier.TWO.setDurability(customConfig.getInt("gas-mask-durability.tier-two"));
        Tier.THREE.setDurability(customConfig.getInt("gas-mask-durability.tier-three"));
        Tier.FOUR.setDurability(customConfig.getInt("gas-mask-durability.tier-four"));

    }
}
