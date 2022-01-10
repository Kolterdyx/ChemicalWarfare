package me.kolterdyx.chemicalwarfare;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChemicalWarfare extends JavaPlugin {

    @Override
    public void onEnable() {


        Bukkit.getLogger().info("Chemical Warfare plugin loaded");
    }

    @Override
    public void onDisable() {
    }
}
