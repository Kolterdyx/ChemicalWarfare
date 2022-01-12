package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.utils.ItemList;
import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class SeaweedExtract {
    private ItemStack item;

    public SeaweedExtract(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Seaweed extract");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SEAWEED_EXTRACT.getId());
        meta.setColor(Color.fromRGB(250, 213, 255));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("seaweed_extract"), item);
        sr.addIngredient(2, Material.SEAGRASS);
        sr.addIngredient(1, Material.BEETROOT);
        sr.addIngredient(2, Material.DRIED_KELP_BLOCK);
        sr.addIngredient(2, Material.COAL_BLOCK);
        sr.addIngredient(1, Material.GLASS_BOTTLE);
        sr.addIngredient(1, Material.WATER_BUCKET);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
