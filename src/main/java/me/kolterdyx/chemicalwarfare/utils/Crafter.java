package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.items.Canister;
import me.kolterdyx.chemicalwarfare.items.Rocket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.type.Fire;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class Crafter implements Listener {

    private final Plugin plugin;

    public Crafter(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        ItemStack[] grid = e.getInventory().getMatrix();
        craftCanister(grid, e);
        craftRPG(grid, e);
    }

    private void craftRPG(ItemStack[] grid, PrepareItemCraftEvent e) {
        int rocketPower = 0;
        int canisterCount = 0;
        int rocketCount = 0;
        int tier = 0;
        int gasItem = 0;


        for (ItemStack item : grid) {
            if (item != null) {
                if (item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        NamespacedKey tierKey = new NamespacedKey(plugin, "tier");
                        NamespacedKey gasKey = new NamespacedKey(plugin, "gas");
                        NamespacedKey itemTypeKey = new NamespacedKey(plugin, "item_type");
                        Integer datatier = data.get(tierKey, PersistentDataType.INTEGER);
                        Integer gastype = data.get(gasKey, PersistentDataType.INTEGER);
                        String item_type = data.get(itemTypeKey, PersistentDataType.STRING);
                        if (item_type != null) {
                            if (item_type.equalsIgnoreCase("canister")) {
                                tier = datatier;
                                gasItem = gastype;
                                canisterCount++;
                            }
                        }
                    }
                }

                if (item.getType() == Material.FIREWORK_ROCKET) {
                    rocketCount++;
                    rocketPower = ((FireworkMeta) item.getItemMeta()).getPower();

                }
            }
        }
        if (canisterCount == 1 && rocketCount == 1) {
            e.getInventory().setResult(new Rocket(plugin, Tier.getByValue(tier), gasItem, rocketPower).getItem());
        } else if(canisterCount > 1 || rocketCount > 1){
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }


    private void craftCanister(ItemStack[]grid, PrepareItemCraftEvent e) {
        int tier = -1;
        int gasCount = 0;
        int canisterCount = 0;
        String gasItem = "null";

        String[] gasses = new String[]{"sulfur_mustard", "chlorine_concentrate", "seaweed_extract"};

        for (ItemStack item : grid) {
            if (item != null) {
                if (item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        NamespacedKey tierKey = new NamespacedKey(plugin, "tier");
                        NamespacedKey gasKey = new NamespacedKey(plugin, "gas");
                        NamespacedKey itemTypeKey = new NamespacedKey(plugin, "item_type");
                        Integer datatier = data.get(tierKey, PersistentDataType.INTEGER);
                        Integer datagas = data.get(gasKey, PersistentDataType.INTEGER);
                        String item_type = data.get(itemTypeKey, PersistentDataType.STRING);
                        if (item_type != null) {
                            if ("canister".equalsIgnoreCase(item_type) && datagas == 0) {
                                tier = datatier;
                                canisterCount++;
                            } else if ("canister".equalsIgnoreCase(item_type) && datagas>0) {
                                canisterCount = 0;
                                break;
                            } else {
                                for (String s : gasses) {
                                    if (item_type.equalsIgnoreCase(s)) {
                                        if (item_type.equalsIgnoreCase(gasItem) || gasItem.equalsIgnoreCase("null")) {
                                            gasItem = item_type;
                                            gasCount++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (canisterCount == 1 && gasCount == tier) {
            switch (gasItem) {
                case "sulfur_mustard":
                    e.getInventory().setResult(new Canister(plugin, Tier.getByValue(tier), 1, 1).getItemStack());
                    break;
                case "chlorine_concentrate":
                    e.getInventory().setResult(new Canister(plugin, Tier.getByValue(tier), 1, 2).getItemStack());
                    break;
                case "seaweed_extract":
                    e.getInventory().setResult(new Canister(plugin, Tier.getByValue(tier), 1, 3).getItemStack());
                    break;
            }
        } else if (gasCount != tier && gasCount > 0){
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
