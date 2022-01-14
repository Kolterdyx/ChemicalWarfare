package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.gear.GasMask;
import me.kolterdyx.chemicalwarfare.items.Canister;
import me.kolterdyx.chemicalwarfare.weapons.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Commands implements CommandExecutor {

    private Plugin plugin;

    public Commands(Plugin plugin) {
        this.plugin = plugin;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 2) {
                switch (label) {
                    case "mustard":
                        new MustardGas(plugin, Integer.parseInt(args[0]), player.getLocation(), player.getWorld(), Integer.parseInt(args[1])).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                        break;
                    case "tear":
                        new TearGas(plugin, Integer.parseInt(args[0]), player.getLocation(), player.getWorld(), Integer.parseInt(args[1])).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                        break;
                    case "chlorine":
                        new ChlorineGas(plugin, Integer.parseInt(args[0]), player.getLocation(), player.getWorld(), Integer.parseInt(args[1])).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                        break;
                    case "gasmask":
                        ItemStack gasmask = new GasMask(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[0])-1], 1,  Integer.parseInt(args[1])).getItemStack();
                        player.getInventory().addItem(gasmask);
                        break;
                    case "canister":
                        ItemStack canister = new Canister(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[0])-1], 1, Integer.parseInt(args[1])).getItemStack();
                        player.getInventory().addItem(canister);
                        break;
                }
                return true;
            } else if (args.length == 1) {
                switch (label) {
                    case "mustard":
                        new MustardGas(plugin, Integer.parseInt(args[0]), player.getLocation(), player.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                        break;
                    case "tear":
                        new TearGas(plugin, Integer.parseInt(args[0]), player.getLocation(), player.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                        break;
                    case "chlorine":
                        new ChlorineGas(plugin, Integer.parseInt(args[0]), player.getLocation(), player.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                        break;
                    case "canister":
                        ItemStack canister = new Canister(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[0])-1], 1, 0).getItemStack();
                        player.getInventory().addItem(canister);
                        break;
                    case "gasmask":
                        ItemStack gasmask = new GasMask(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[0])-1], 1, 1).getItemStack();
                        player.getInventory().addItem(gasmask);
                        break;
                    case "particleperiod":
                        ChemicalWarfare.setParticlePeriod(Integer.parseInt(args[0]));
                        break;
                }
            }
            switch (label){
                case "cleargas":
                    ((ChemicalWarfare)plugin).stopAllGas();
                    break;
                case "cwpack":
                    player.sendMessage(ChemicalWarfare.getString() + " Sending resource pack...");
                    player.setResourcePack(ChemicalWarfare.getResourcePack());
            }
        }
        return true;
    }

    private void sendWindData(Player player, Vector velocity){

        double angleSouth = velocity.angle(new Vector(0, 0, 1)) * 180/Math.PI;

        player.sendMessage(ChatColor.GREEN+"Speed: "+ChatColor.YELLOW+String.format("%.2f", velocity.length())+"m/s");
        player.sendMessage(ChatColor.GREEN+"Angle: "+ChatColor.YELLOW+String.format("%.2f", angleSouth)+"°");
    }
}
