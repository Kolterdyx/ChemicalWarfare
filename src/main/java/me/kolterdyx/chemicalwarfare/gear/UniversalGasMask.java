package me.kolterdyx.chemicalwarfare.gear;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class UniversalGasMask extends GasMask {
    public UniversalGasMask(Plugin plugin, Tier tier, int amount, int gas_filter) {
        super(plugin, tier, amount, gas_filter);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(4+tier.getValue());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "oxygen"), PersistentDataType.INTEGER, ChemicalWarfare.getCustomConfig().getInt("max-oxygen"));
        List<String> lore = meta.getLore();
        lore.add(ChatColor.GOLD+"Oxygen left: "+ChatColor.GREEN+"100%");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @Override
    public void craftingRecipe() {

    }
}
