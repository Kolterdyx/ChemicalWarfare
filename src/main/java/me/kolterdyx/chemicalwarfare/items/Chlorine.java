package me.kolterdyx.chemicalwarfare.items;

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

public class Chlorine {
    private ItemStack item;

    public Chlorine(Plugin plugin){
        item = new ItemStack(Material.GLOWSTONE_DUST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Chlorine");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.CHLORINE.getId());
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("chlorine"), item);
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SALT));
        sr.addIngredient(1, Material.REDSTONE);
        sr.addIngredient(2, Material.LIGHTNING_ROD);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
