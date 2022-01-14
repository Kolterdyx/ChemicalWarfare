package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.ItemList;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class Chlorine {
    private ItemStack item;

    public Chlorine(Plugin plugin){
        item = new ItemStack(Material.GLOWSTONE_DUST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Chlorine");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.CHLORINE.getId());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "chlorine");
        item.setItemMeta(meta);
        craftingRecipe(plugin);
    }

    public void craftingRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "chlorine");
        ChemicalWarfare.addRecipe(key);
        ShapedRecipe sr = new ShapedRecipe(key, item);
        sr.shape("SSS","CRC","SSS");
        sr.setIngredient('S', new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.setIngredient('R', Material.REDSTONE);
        sr.setIngredient('C', Material.COPPER_INGOT);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
