package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.utils.ItemList;
import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class Ethylene {
    private ItemStack item;

    public Ethylene(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Ethylene");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.ETHYLENE.getId());
        meta.setColor(Color.fromRGB(235, 79, 45));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe();
    }

    public void craftingRecipe(){
        ItemStack result = item.clone();
        result.setAmount(2);
        ShapelessRecipe sr = new ShapelessRecipe(NamespacedKey.minecraft("ethylene"), result);
        sr.addIngredient(1, Material.BLAZE_POWDER);
        sr.addIngredient(1, Material.POISONOUS_POTATO);
        sr.addIngredient(2, Material.POTION);
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }


}

