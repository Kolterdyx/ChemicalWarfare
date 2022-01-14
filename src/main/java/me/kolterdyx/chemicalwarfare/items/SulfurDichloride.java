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
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class SulfurDichloride {
    private ItemStack item;

    public SulfurDichloride(Plugin plugin){
        item = new ItemStack(Material.REDSTONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Sulfur dichloride");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SULFUR_DICHLORIDE.getId());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "sulfur_dichloride");
        item.setItemMeta(meta);
        craftingRecipe(plugin);
    }

    public void craftingRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "sulfur_dichloride");
        ChemicalWarfare.addRecipe(key);
        ShapelessRecipe sr = new ShapelessRecipe(key, item);
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SULFUR));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SULFUR));
        sr.addIngredient(1, Material.LAVA_BUCKET);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
