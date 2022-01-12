package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.utils.ItemList;
import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class SaltWater {
    private ItemStack item;

    public SaltWater(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Salt water");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SALT_WATER.getId());
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ItemStack result = item.clone();
        result.setAmount(6);
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("salt_water"), result);
        sr.addIngredient(3, Material.PRISMARINE_CRYSTALS);
        sr.addIngredient(1, Material.POTION);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
