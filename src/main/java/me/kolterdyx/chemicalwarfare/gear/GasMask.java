package me.kolterdyx.chemicalwarfare.gear;

import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import net.minecraft.world.item.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class GasMask {

    private ItemStack item;

    public GasMask(Plugin plugin, Tier tier, int amount, int gas_filter) {
        item = new ItemStack(Material.CARVED_PUMPKIN, amount);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gas_filter"), PersistentDataType.INTEGER, gas_filter);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER, tier.getValue());
        meta.setCustomModelData(tier.getValue());
        meta.setDisplayName(ChatColor.WHITE+"Gas mask");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Tier: "+ChatColor.GREEN+tier.getValue());
        lore.add(ChatColor.GOLD+"Protects from: "+ GasProperties.getByIndex(gas_filter).getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public ItemStack getItemStack(){
        return item;
    }
}
