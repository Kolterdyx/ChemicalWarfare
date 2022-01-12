package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.utils.ItemList;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class Salt {
    private ItemStack item;
    private Plugin plugin;

    public Salt(Plugin plugin){
        this.plugin = plugin;
        item = new ItemStack(Material.SUGAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Salt");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SALT.getId());
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        FurnaceRecipe sr = new FurnaceRecipe(new NamespacedKey(plugin, "salt"), item, new RecipeChoice.ExactChoice(ItemManager.SALT_WATER), 1, 200);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
