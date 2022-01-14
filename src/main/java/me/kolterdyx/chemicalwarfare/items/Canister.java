package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Random;

public class Canister{

    private ItemStack item;
    private Plugin plugin;
    private Tier tier;
    private int gas;

    public Canister(Plugin plugin, Tier tier, int amount, int gas){
        item = new ItemStack(Material.SPLASH_POTION, amount);
        this.plugin = plugin;
        this.tier = tier;
        this.gas = gas;
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gas"), PersistentDataType.INTEGER, gas);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER, tier.getValue());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "canister");
        meta.setCustomModelData(tier.getValue());
        meta.setDisplayName(ChatColor.WHITE+"Canister");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Tier: "+ChatColor.GREEN+tier.getValue());
        lore.add(ChatColor.GOLD+"Contents: "+ GasProperties.getByIndex(gas).getName());
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
    }


    public void craftingRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "canister"+tier.getValue()+""+gas);
        ChemicalWarfare.addRecipe(key);
        ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        recipe.addIngredient(new RecipeChoice.ExactChoice(new Canister(plugin, tier, 1, 0).getItemStack()));
        switch (gas){
            case 1:
                for (int i = 0; i < tier.getValue(); i++) {
                    recipe.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SULFUR_MUSTARD));
                }
                break;
            case 2:
                for (int i = 0; i < tier.getValue(); i++) {
                    recipe.addIngredient(new RecipeChoice.ExactChoice(ItemManager.CHLORINE_CONCENTRATE));
                }
                break;
            case 3:
                for (int i = 0; i < tier.getValue(); i++) {
                    recipe.addIngredient(new RecipeChoice.ExactChoice(ItemManager.SEAWEED_EXTRACT));
                }
                break;
        }
        Bukkit.getServer().addRecipe(recipe);
    }


    public ItemStack getItemStack() {
        return item;
    }
}
