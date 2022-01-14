package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.ItemList;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Ethylene {
    private ItemStack item;

    public Ethylene(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Ethylene");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.ETHYLENE.getId());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "ethylene");
        meta.setColor(Color.fromRGB(235, 79, 45));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        craftingRecipe(plugin);
    }

    public void craftingRecipe(Plugin plugin){
        ItemStack result = item.clone();
        result.setAmount(2);
        ItemStack waterBottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) waterBottle.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.WATER));
        waterBottle.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(plugin, "ethylene");
        ChemicalWarfare.addRecipe(key);
        ShapedRecipe sr = new ShapedRecipe(key, result);
        sr.shape("WB","WP");
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('P', Material.POISONOUS_POTATO);
        sr.setIngredient('W', new RecipeChoice.ExactChoice(waterBottle));
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }


}

