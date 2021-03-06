package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.ItemList;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import org.bukkit.*;
import org.bukkit.inventory.*;
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
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "chlorine_concentrate");
        meta.setColor(Color.fromRGB(203, 255, 82));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe(plugin);
    }

    public void craftingRecipe(Plugin plugin){
        ItemStack result = item.clone();
        result.setAmount(2);
        NamespacedKey key = new NamespacedKey(plugin, "chlorine_concentrate");
        ChemicalWarfare.addRecipe(key);
        ShapedRecipe sr = new ShapedRecipe(key, result);
        sr.shape("CC","CC","BB");
        sr.setIngredient('C', new RecipeChoice.ExactChoice(ItemManager.CHLORINE));
        sr.setIngredient('B', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
