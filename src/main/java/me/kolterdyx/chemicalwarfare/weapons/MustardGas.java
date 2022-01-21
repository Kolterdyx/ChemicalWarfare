package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
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
        for (Entity e : world.getNearbyEntities(pos, this.effectDistance, this.effectDistance, this.effectDistance)) {
            if (e instanceof LivingEntity entity){
                if (pos.distanceSquared(entity.getLocation()) < effectDistance){
                    ItemStack helmet = entity.getEquipment().getHelmet();
                    if (helmet != null && helmet.hasItemMeta()) {
                        ItemMeta meta = helmet.getItemMeta();
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        NamespacedKey key1 = new NamespacedKey(this.plugin, "gas_filter");
                        Integer gas_filter = data.get(key1, PersistentDataType.INTEGER);
                        if (gas_filter == null || gas_filter != GasProperties.MUSTARD.getIndex()) {
                            applyGas(entity, power);
                        } else {
                            if (!(entity instanceof Villager)) {
                                NamespacedKey durabilityKey = new NamespacedKey(this.plugin, "durability");
                                int durability = data.get(durabilityKey, PersistentDataType.INTEGER);
                                if (durability > 0) {
                                    data.set(durabilityKey, PersistentDataType.INTEGER, --durability);
                                    List<String> lore = meta.getLore();
                                    lore.set(2, ChatColor.GOLD + "Durability left: " + ChatColor.GREEN + durability / 20);
                                    meta.setLore(lore);
                                    helmet.setItemMeta(meta);
                                } else {
                                    List<String> lore = meta.getLore();
                                    lore.set(2, ChatColor.GOLD + "Durability left: " + ChatColor.RED + 0);
                                    meta.setLore(lore);
                                    helmet.setItemMeta(meta);
                                    applyGas(entity, power);
                                }
                            }
                        }
                    } else {
                        applyGas(entity, power);
                    }
                }
            }
        }
        live();
    }

    private void applyGas(LivingEntity entity, int power){
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30*20, (int)Math.floor(power*0.75f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 45*20, power, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30*20, power, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60*20, (int)Math.floor(power*0.75f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30*20, (int)Math.floor(power*0.75f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 15*20, power+1, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40*20, power, false, false, false));
    }
}
