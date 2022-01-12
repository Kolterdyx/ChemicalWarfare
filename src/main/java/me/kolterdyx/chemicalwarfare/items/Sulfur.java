package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.utils.ItemList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class Sulfur {
    private ItemStack item;

    public Sulfur(Plugin plugin){
        item = new ItemStack(Material.GUNPOWDER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Sulfur");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SULFUR.getId());
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("sulfur"), item);
        sr.addIngredient(4, Material.GUNPOWDER);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
