package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
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
        for (Entity e : world.getNearbyEntities(pos, this.effectDistance, this.effectDistance, this.effectDistance)) {
            if (e instanceof LivingEntity entity){
                if (!(entity instanceof Player) && !ChemicalWarfare.getCustomConfig().getBoolean("gas-affects-living-entities")) continue;
                if (pos.distanceSquared(entity.getLocation()) < effectDistanceSquared){
                    ItemStack helmet = entity.getEquipment().getHelmet();
                    if (helmet != null && helmet.hasItemMeta()) {
                        ItemMeta meta = helmet.getItemMeta();
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        NamespacedKey key1 = new NamespacedKey(this.plugin, "gas_filter");
                        Integer gas_filter = data.get(key1, PersistentDataType.INTEGER);
                        if (gas_filter == null || gas_filter != GasProperties.TEAR.getIndex() || gas_filter != GasProperties.UNIVERSAL.getIndex()){
                            applyGas(entity, 0);
                        } else {
                            if (!(entity instanceof Villager)){
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
                                    applyGas(entity, 0);
                                }
                            }
                        }
                    } else {
                        applyGas(entity, 0);
                    }
                }
            }
        }
        live();
    }


    @Override
    protected void applyGas(LivingEntity entity, int power){
        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60*20, level.getValue()-1, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10*20, 0, false, false, false));
    }
}
