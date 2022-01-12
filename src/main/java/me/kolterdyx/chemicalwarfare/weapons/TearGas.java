package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TearGas extends GasCloud {


    public TearGas(Plugin plugin, int tier, Location pos, World world, int seconds) {
        super(plugin, tier, pos, world, seconds);
    }

    public TearGas(Plugin plugin, int tier, Location pos, World world) {
        super(plugin, tier, pos, world);
    }

    @Override
    public void run() {
        createToxicCloud(GasProperties.TEAR.getColor(), level.getAmount());
        for (Player player : world.getPlayers()) {
            if (pos.distanceSquared(player.getLocation()) < effectDistance){
                ItemStack helmet = player.getInventory().getHelmet();
                if (helmet != null && helmet.hasItemMeta()) {
                    PersistentDataContainer data = helmet.getItemMeta().getPersistentDataContainer();
                    NamespacedKey key1 = new NamespacedKey(this.plugin, "gas_filter");
                    NamespacedKey key2 = new NamespacedKey(this.plugin, "tier");
                    if (data.get(key1, PersistentDataType.INTEGER) != GasProperties.TEAR.getIndex() || level.higherThan(data.get(key2, PersistentDataType.INTEGER))) {
                        applyGas(player);
                    }
                } else {
                    applyGas(player);
                }
            }
        }
        live();
    }

    private void applyGas(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60*20, level.getValue()-1, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10*20, 0, false, false, false));
    }
}
