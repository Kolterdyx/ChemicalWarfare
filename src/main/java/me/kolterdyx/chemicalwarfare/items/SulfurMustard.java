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

public class SulfurMustard {
    private ItemStack item;

    public SulfurMustard(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Sulfur mustard");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SULFUR_MUSTARD.getId());
        meta.setColor(Color.fromRGB(241, 255, 38));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("sulfur_mustard"), item);
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.ETHYLENE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.ETHYLENE));
        sr.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SULFUR_DICHLORIDE));
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
