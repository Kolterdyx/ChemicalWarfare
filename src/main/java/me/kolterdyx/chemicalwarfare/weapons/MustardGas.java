package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class MustardGas extends GasCloud {


    public MustardGas(Plugin plugin, int tier, Location pos, World world, int seconds) {
        super(plugin, tier, pos, world, seconds);
    }

    public MustardGas(Plugin plugin, int tier, Location pos, World world) {
        super(plugin, tier, pos, world);
    }

    @Override
    public void run() {
        int power = level.getValue()-1;
        createToxicCloud(GasProperties.MUSTARD.getColor(), level.getAmount());
        for (Player player : world.getPlayers()) {
            if (pos.distanceSquared(player.getLocation()) < effectDistance){
                ItemStack helmet = player.getInventory().getHelmet();
                if (helmet != null && helmet.hasItemMeta()) {
                    ItemMeta meta = helmet.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    NamespacedKey key1 = new NamespacedKey(this.plugin, "gas_filter");
                    NamespacedKey key2 = new NamespacedKey(this.plugin, "tier");
                    if (data.get(key1, PersistentDataType.INTEGER) != GasProperties.MUSTARD.getIndex()) {
                        applyGas(player, power);
                    } else {
                        NamespacedKey durabilityKey = new NamespacedKey(this.plugin, "durability");
                        int durability = data.get(durabilityKey, PersistentDataType.INTEGER);
                        if (durability>0){
                            data.set(durabilityKey, PersistentDataType.INTEGER, --durability);
                            List<String> lore = meta.getLore();
                            lore.set(2, ChatColor.GOLD+"Durability left: " + ChatColor.GREEN + durability/20);
                            meta.setLore(lore);
                            helmet.setItemMeta(meta);
                        } else {
                            List<String> lore = meta.getLore();
                            lore.set(2, ChatColor.GOLD+"Durability left: " + ChatColor.RED + 0);
                            meta.setLore(lore);
                            helmet.setItemMeta(meta);
                            applyGas(player, power);
                        }
                    }
                } else {
                    applyGas(player, power);
                }
            }
        }
        live();
    }

    private void applyGas(Player player, int power){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30*20, (int)Math.floor(power*0.75f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 45*20, power, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30*20, power, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60*20, (int)Math.floor(power*0.75f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30*20, (int)Math.floor(power*0.75f), false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 15*20, power, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40*20, power, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 2*20, (int)Math.floor(power*0.5f), false, false, false));
    }
}
