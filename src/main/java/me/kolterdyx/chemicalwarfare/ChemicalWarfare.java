package me.kolterdyx.chemicalwarfare;

import me.kolterdyx.chemicalwarfare.utils.CWListener;
import me.kolterdyx.chemicalwarfare.utils.Commands;
import me.kolterdyx.chemicalwarfare.utils.Crafter;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import me.kolterdyx.chemicalwarfare.weapons.GasCloud;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public final class ChemicalWarfare extends JavaPlugin {

    public static long particlePeriod=2;
    private ArrayList<BukkitRunnable> gasClouds = new ArrayList<>();
    private static Wind wind = new Wind();

    public static Vector getWindVelocity() {
        return wind.getVelocity();
    }

    public static Vector getRawWindVelocity() {
        return wind.getRawVelocity();
    }

    @Override
    public void onEnable() {

        wind.runTaskTimer(this, 0L, 1L);

        ItemManager.init(this);

        getServer().getPluginManager().registerEvents(new CWListener(this), this);
        getServer().getPluginManager().registerEvents(new Crafter(this), this);

        this.getCommand("chlorine").setExecutor(new Commands(this));
        this.getCommand("mustard").setExecutor(new Commands(this));
        this.getCommand("tear").setExecutor(new Commands(this));
        this.getCommand("canister").setExecutor(new Commands(this));
        this.getCommand("gasmask").setExecutor(new Commands(this));
        this.getCommand("cleargas").setExecutor(new Commands(this));
        this.getCommand("wind").setExecutor(new Commands(this));

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
        wind.cancel();
    }
}
