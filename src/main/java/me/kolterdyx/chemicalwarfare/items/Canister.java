package me.kolterdyx.chemicalwarfare.items;

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

    private final Plugin plugin;
    private ItemStack item;
    private Tier tier;
    private int gas;

    public Canister(Plugin plugin, Tier tier, int amount, int gas){
        item = new ItemStack(Material.SPLASH_POTION, amount);
        this.tier = tier;
        this.gas = gas;
        this.plugin = plugin;
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gas"), PersistentDataType.INTEGER, gas);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER, tier.getValue());
        meta.setCustomModelData(tier.getValue());
        meta.setDisplayName(ChatColor.WHITE+"Canister");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Tier: "+ChatColor.GREEN+tier.getValue());
        lore.add(ChatColor.GOLD+"Contents: "+ GasProperties.getByIndex(gas).getName());
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
    }



    public ItemStack getItemStack() {
        return item;
    }
}
