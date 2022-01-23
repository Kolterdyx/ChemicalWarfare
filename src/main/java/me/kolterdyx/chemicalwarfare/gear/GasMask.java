package me.kolterdyx.chemicalwarfare.gear;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GasMask {

    protected ItemStack item;
    protected Tier tier;
    protected int gas_filter;
    protected Plugin plugin;

    public GasMask(Plugin plugin, Tier tier, int amount, int gas_filter) {
        this.tier = tier;
        this.gas_filter = gas_filter;
        this.plugin = plugin;
        item = new ItemStack(Material.CARVED_PUMPKIN, amount);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gas_filter"), PersistentDataType.INTEGER, gas_filter);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER, tier.getValue());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "durability"), PersistentDataType.INTEGER, tier.getDurability());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "gasmask");
        meta.setCustomModelData(tier.getValue());
        meta.setDisplayName(ChatColor.WHITE+"Gas mask");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Tier: "+ChatColor.GREEN+tier.getValue());
        lore.add(ChatColor.GOLD+"Protects from: "+ GasProperties.getByIndex(gas_filter).getFormalName());
        lore.add(ChatColor.GOLD+"Durability left: "+ ChatColor.GREEN + (int)(tier.getDurability()/20));
        meta.setLore(lore);
        item.setItemMeta(meta);

    }

    public void craftingRecipe(){
        if (gas_filter == GasProperties.UNIVERSAL.getIndex()) return;
        NamespacedKey key = new NamespacedKey(plugin, "gasmask"+tier.getValue()+""+gas_filter);
        ChemicalWarfare.addRecipe(key);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("LLL",
                     "GHG",
                     "BFB");

        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('G', Material.TINTED_GLASS);
        recipe.setIngredient('H', Material.HOPPER);

        switch (tier){
            case ONE:
                recipe.setIngredient('B', Material.IRON_BLOCK);
                break;
            case TWO:
                recipe.setIngredient('B', Material.REDSTONE_BLOCK);
                break;
            case THREE:
                recipe.setIngredient('B', Material.DIAMOND);
                break;
            case FOUR:
                recipe.setIngredient('B', Material.NETHERITE_SCRAP);
                break;
        }

        switch (GasProperties.getByIndex(gas_filter)){
            case UNIVERSAL:
                recipe.setIngredient('B', Material.NETHERITE_INGOT);
                recipe.setIngredient('F', Material.COBWEB);
                break;
            case MUSTARD:
                recipe.setIngredient('F', Material.PHANTOM_MEMBRANE);
                break;
            case CHLORINE:
                recipe.setIngredient('F', Material.COAL_BLOCK);
                break;
            case TEAR:
                recipe.setIngredient('F', Material.GHAST_TEAR);
                break;
        }

        Bukkit.getServer().addRecipe(recipe);
    }

    public static boolean use(ItemStack mask, Plugin plugin){

        ItemMeta meta = mask.getItemMeta();
        List<String> lore = meta.getLore();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey durabilityKey = new NamespacedKey(plugin, "durability");
        int durability = data.get(durabilityKey, PersistentDataType.INTEGER);
        if (durability>0){
            data.set(durabilityKey, PersistentDataType.INTEGER, --durability);
            lore.set(2, ChatColor.GOLD+"Durability left: " + ChatColor.GREEN + durability/20);
            meta.setLore(lore);
            mask.setItemMeta(meta);
            return true;
        }
        lore.set(2, ChatColor.GOLD+"Durability left: " + ChatColor.RED + 0);

        meta.setLore(lore);
        mask.setItemMeta(meta);

        return false;
    }

    public static boolean useUniversal(ItemStack mask, Plugin plugin){
        ItemMeta meta = mask.getItemMeta();
        List<String> lore = meta.getLore();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey oxygenKey = new NamespacedKey(plugin, "oxygen");
        NamespacedKey gasKey = new NamespacedKey(plugin, "gas_filter");
        boolean universal = data.get(gasKey, PersistentDataType.INTEGER) == GasProperties.UNIVERSAL.getIndex();
        if (universal){
            int oxygen = data.get(oxygenKey, PersistentDataType.INTEGER);
            if (oxygen > 0){
                double oxygenPercent = ((float)oxygen / (float)ChemicalWarfare.getCustomConfig().getInt("max-oxygen"))*100;
                if (oxygenPercent > 25){
                    lore.set(3, ChatColor.GOLD+"Oxygen left: " + ChatColor.GREEN + String.format("%.2f", oxygenPercent) + "%");
                } else {
                    lore.set(3, ChatColor.GOLD+"Oxygen left: " + ChatColor.YELLOW + String.format("%.2f", oxygenPercent) + "%");
                }
                data.set(oxygenKey, PersistentDataType.INTEGER, --oxygen);
                meta.setLore(lore);
                mask.setItemMeta(meta);
                return true;
            }
            lore.set(3, ChatColor.GOLD+"Oxygen left: " + ChatColor.DARK_RED + ChatColor.BOLD + "0%");
            meta.setLore(lore);
            mask.setItemMeta(meta);
            return false;

        }
        return true;
    }

    public ItemStack getItemStack(){
        return item.clone();
    }
}
