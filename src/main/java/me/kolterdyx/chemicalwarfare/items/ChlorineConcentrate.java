package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.utils.ItemList;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class ChlorineConcentrate {
    private ItemStack item;

    public ChlorineConcentrate(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Chlorine concentrate");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.CHLORINE_CONCENTRATE.getId());
        meta.setColor(Color.fromRGB(203, 255, 82));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ItemStack result = item.clone();
        result.setAmount(2);
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("chlorine_concentrate"), result);
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.addIngredient(2, Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}