package me.kolterdyx.chemicalwarfare;

import me.kolterdyx.chemicalwarfare.utils.*;
import me.kolterdyx.chemicalwarfare.weapons.GasCloud;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;

public final class ChemicalWarfare extends JavaPlugin {

    public static long particlePeriod=1;
    private ArrayList<BukkitRunnable> gasClouds = new ArrayList<>();
    private static ArrayList<NamespacedKey> recipes = new ArrayList<>();

    public static String getString() {
        return ChatColor.RED + "[" +ChatColor.DARK_GREEN+"Chemical Warfare"+  ChatColor.RED +"]"+ChatColor.YELLOW;
    }

    public static String getResourcePack() {
        return "http://nube.videlicet.es/s/zJz5LQo4FbapcXW/download/ChemicalWarfare.zip";
    }

    public static void addRecipe(NamespacedKey key) {
        recipes.add(key);
    }

    public static Collection<NamespacedKey> getRecipes() {
        return recipes;
    }

    @Override
    public void onEnable() {

        ItemManager.init(this);

        getServer().getPluginManager().registerEvents(new CWListener(this), this);

        this.getCommand("chlorine").setExecutor(new Commands(this));
        this.getCommand("mustard").setExecutor(new Commands(this));
        this.getCommand("tear").setExecutor(new Commands(this));
        this.getCommand("canister").setExecutor(new Commands(this));
        this.getCommand("gasmask").setExecutor(new Commands(this));
        this.getCommand("cleargas").setExecutor(new Commands(this));
        this.getCommand("cwpack").setExecutor(new Commands(this));

        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            player.discoverRecipes(getRecipes());
        }

        Bukkit.getLogger().info("Chemical Warfare plugin loaded");
    }

    public void addGasCloud(GasCloud cloud){
        gasClouds.add(cloud);
    }

    public static void setParticlePeriod(long period){
        particlePeriod = period;
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
}
