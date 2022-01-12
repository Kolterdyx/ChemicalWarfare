package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.items.Canister;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class Crafter implements Listener {

    private final Plugin plugin;

    public Crafter(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e){
//        Bukkit.getLogger().info(""+e.getRecipe());
        ItemStack[] grid = e.getInventory().getMatrix();
        boolean foundCanister = false;
        boolean foundGas = false;
        int tier = -1;
        int gasCount = 0;
        int gasId=-1;

        for (ItemStack item : grid){
            if (item != null){
                if (item.hasItemMeta()){
                    ItemMeta meta = item.getItemMeta();
                    if (meta!=null) {
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        NamespacedKey tierKey = new NamespacedKey(plugin, "tier");
                        NamespacedKey gasKey = new NamespacedKey(plugin, "gas");
                        NamespacedKey itemIdKey = new NamespacedKey(plugin, "item_id");
                        Integer datatier = data.get(tierKey, PersistentDataType.INTEGER);
                        Integer datagas = data.get(gasKey, PersistentDataType.INTEGER);
                        Integer dataitem_id = data.get(itemIdKey, PersistentDataType.INTEGER);
                        Bukkit.getLogger().info(""+datatier+" "+datagas);
                        if (datatier!=null && datagas != null && datagas == 0) {
                            Bukkit.getLogger().info("Found canister");
                            foundCanister = true;
                            tier = datatier;
                        } else if (dataitem_id != null){
                            Bukkit.getLogger().info("Found gas");
                            foundGas = true;
                            if (dataitem_id == gasId || gasId == -1){
                                Bukkit.getLogger().info("Saved gas id: "+dataitem_id);
                                gasId = dataitem_id;
                                gasCount++;
                            } else {
                                Bukkit.getLogger().info("Found two different gasses");
                                foundGas = false;
                            }

                        }
                    }
                }
            }
        }
        
        if (foundCanister && foundGas && gasCount == tier){
            switch (gasId){
                case 6:
                    e.getInventory().setResult(new Canister(plugin, Tier.getByValue(tier), 1, 1).getItemStack());
                    break;
                case 7:
                    e.getInventory().setResult(new Canister(plugin, Tier.getByValue(tier), 1, 2).getItemStack());
                    break;
                case 5:
                    e.getInventory().setResult(new Canister(plugin, Tier.getByValue(tier), 1, 3).getItemStack());
                    break;
            }

        }
    }

}
