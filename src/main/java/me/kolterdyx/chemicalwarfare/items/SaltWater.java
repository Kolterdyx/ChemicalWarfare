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

public class SaltWater {
    private ItemStack item;

    public SaltWater(Plugin plugin){
        item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE+"Salt water");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_id"), PersistentDataType.INTEGER, ItemList.SALT_WATER.getId());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "salt_water");
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setColor(Color.WHITE);
        item.setItemMeta(meta);
        craftingRecipe(plugin);
    }

    public void craftingRecipe(Plugin plugin){
        ItemStack result = item.clone();
        NamespacedKey key = new NamespacedKey(plugin, "salt_water");
        ChemicalWarfare.addRecipe(key);
        ShapedRecipe sr = new ShapedRecipe(key, result);
        sr.shape("PPP"," B ");
        sr.setIngredient('P', Material.PRISMARINE_CRYSTALS);
        ItemStack waterBottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) waterBottle.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.WATER));
        waterBottle.setItemMeta(meta);
        sr.setIngredient('B', new RecipeChoice.ExactChoice(waterBottle));
        Bukkit.getServer().addRecipe(sr);
    }

    public ItemStack getItem(){
        return item;
    }
}
