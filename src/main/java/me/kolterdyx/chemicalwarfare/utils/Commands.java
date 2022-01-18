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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            switch (label){
                case "gas":
                    switch (args.length){
                        case 1:
                            switch (args[0]){
                                case "clear":
                                    ((ChemicalWarfare)plugin).stopAllGas();
                                    break;
                                case "reload":
                                    ((ChemicalWarfare)plugin).reload();
                            }
                            break;
                        case 2:
                            switch (args[0]) {
                                case "mustard":
                                    new MustardGas(plugin, Integer.parseInt(args[1]), player.getLocation(), player.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                                    break;
                                case "tear":
                                    new TearGas(plugin, Integer.parseInt(args[1]), player.getLocation(), player.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                                    break;
                                case "chlorine":
                                    new ChlorineGas(plugin, Integer.parseInt(args[1]), player.getLocation(), player.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                                    break;
                                case "canister":
                                    ItemStack canister = new Canister(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[1])-1], 1, 0).getItemStack();
                                    player.getInventory().addItem(canister);
                                    break;
                                case "mask":
                                    ItemStack gasmask = new GasMask(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[1])-1], 1, 1).getItemStack();
                                    player.getInventory().addItem(gasmask);
                                    break;
                            }
                            break;
                        case 3:
                            switch (args[0]) {
                                case "mustard":
                                    new MustardGas(plugin, Integer.parseInt(args[1]), player.getLocation(), player.getWorld(), Integer.parseInt(args[2])).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                                    break;
                                case "tear":
                                    new TearGas(plugin, Integer.parseInt(args[1]), player.getLocation(), player.getWorld(), Integer.parseInt(args[2])).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                                    break;
                                case "chlorine":
                                    new ChlorineGas(plugin, Integer.parseInt(args[1]), player.getLocation(), player.getWorld(), Integer.parseInt(args[2])).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                                    break;
                                case "mask":
                                    ItemStack gasmask = new GasMask(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[1])-1], 1,  Integer.parseInt(args[2])).getItemStack();
                                    player.getInventory().addItem(gasmask);
                                    break;
                                case "canister":
                                    ItemStack canister = new Canister(plugin, new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[Integer.parseInt(args[1])-1], 1, Integer.parseInt(args[2])).getItemStack();
                                    player.getInventory().addItem(canister);
                                    break;
                            }
                    }
                    break;
                case "cwpack":
                    player.sendMessage(ChemicalWarfare.getString() + " Sending resource pack...");
                    player.setResourcePack(ChemicalWarfare.getResourcePack());
            }
        } else {
            if (label.equals("gas") && "clear".equals(args[0])){
                ((ChemicalWarfare)plugin).stopAllGas();
            }
        }
        return true;
    }

}
