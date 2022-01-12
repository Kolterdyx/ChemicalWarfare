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

public class ChlorineGas extends GasCloud{

    public ChlorineGas(Plugin plugin, int tier, Location pos, World world, int seconds) {
        super(plugin, tier, pos, world, seconds);
    }

    public ChlorineGas(Plugin plugin, int tier, Location pos, World world){
        super(plugin, tier, pos, world);
    }

    @Override
    public void run() {
        int power = level.getValue()-1;
        createToxicCloud(GasProperties.CHLORINE.getColor(), level.getAmount());
        for (Player player : world.getPlayers()) {
            if (pos.distanceSquared(player.getLocation()) < effectDistance){
                ItemStack helmet = player.getInventory().getHelmet();
                if (helmet != null && helmet.hasItemMeta()) {
                    PersistentDataContainer data = helmet.getItemMeta().getPersistentDataContainer();
                    NamespacedKey key1 = new NamespacedKey(this.plugin, "gas_filter");
                    NamespacedKey key2 = new NamespacedKey(this.plugin, "tier");
                    if (data.get(key1, PersistentDataType.INTEGER) != GasProperties.CHLORINE.getIndex() || level.higherThan(data.get(key2, PersistentDataType.INTEGER))) {
                        applyGas(player, power);
                    }
                } else {
                    applyGas(player, power);
                }
            }
        }
        live();
    }

    private void applyGas(Player player, int power){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 22*20, (int)Math.floor(power*0.75f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 22*20, (int)Math.floor(power*0.75f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30*20, (int)Math.floor(power*0.75f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 22*20, (int)Math.floor(power*0.5f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 15*20, power, false, false, false));
    }
}
