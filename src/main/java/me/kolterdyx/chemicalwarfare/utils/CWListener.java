package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.weapons.ChlorineGas;
import me.kolterdyx.chemicalwarfare.weapons.MustardGas;
import me.kolterdyx.chemicalwarfare.weapons.TearGas;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class CWListener implements Listener {

    private Plugin plugin;

    public CWListener(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event){
        ThrownPotion entity = event.getEntity();
        PersistentDataContainer data = entity.getItem().getItemMeta().getPersistentDataContainer();

        Integer tier = data.get(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER);
        GasProperties gas = GasProperties.getByIndex(data.get(new NamespacedKey(plugin, "gas"), PersistentDataType.INTEGER));

        if (tier != null && gas != null) {
            switch (gas){
                case MUSTARD:
                    new MustardGas(plugin, tier, entity.getLocation(), entity.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                    event.setCancelled(true);
                    break;
                case TEAR:
                    new TearGas(plugin, tier, entity.getLocation(), entity.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                    event.setCancelled(true);
                    break;
                case CHLORINE:
                    new ChlorineGas(plugin, tier, entity.getLocation(), entity.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                    event.setCancelled(true);
                    break;
            }
        }

    }

    @EventHandler
    public void onPotionThrow(PlayerInteractEvent event){
        // TODO: Avoid throwing empty canisters
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        if (e.getItem().hasItemMeta()){
            ItemMeta meta = e.getItem().getItemMeta();
            if (meta != null){
                PersistentDataContainer data = meta.getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(plugin, "item_id");
                Integer id = data.get(key, PersistentDataType.INTEGER);
                if (id != null){
                    Bukkit.getLogger().info("Player tried to consume chemical: "+id);
                    e.setCancelled(true);
                }
            }
        }
    }

}
