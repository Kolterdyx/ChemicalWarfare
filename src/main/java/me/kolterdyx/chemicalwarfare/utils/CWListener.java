package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.gear.GasMask;
import me.kolterdyx.chemicalwarfare.weapons.ChlorineGas;
import me.kolterdyx.chemicalwarfare.weapons.MustardGas;
import me.kolterdyx.chemicalwarfare.weapons.TearGas;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

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
    public void onFireworkExplode(FireworkExplodeEvent event){
        Firework entity = event.getEntity();
        PersistentDataContainer data = entity.getFireworkMeta().getPersistentDataContainer();
        String item_type = data.get(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING);
        Integer tier = data.get(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER);
        GasProperties gas = GasProperties.getByIndex(data.get(new NamespacedKey(plugin, "gas"), PersistentDataType.INTEGER));

        if (tier != null && gas != null && "rocket".equals(item_type)) {
            switch (gas){
                case MUSTARD:
                    new MustardGas(plugin, tier, entity.getLocation(), entity.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                    break;
                case TEAR:
                    new TearGas(plugin, tier, entity.getLocation(), entity.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                    break;
                case CHLORINE:
                    new ChlorineGas(plugin, tier, entity.getLocation(), entity.getWorld()).runTaskTimer(plugin, 0L, ChemicalWarfare.particlePeriod);
                    break;
            }
        }
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event){
        if (!ChemicalWarfare.getCustomConfig().getBoolean("allow-repair-gas-masks"))return;
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);
        if (first==null) return;
        if (second==null) return;
        if (first.hasItemMeta()){
            ItemMeta meta = first.getItemMeta();
            if (meta==null) return;
            PersistentDataContainer data = meta.getPersistentDataContainer();
            NamespacedKey itemKey = new NamespacedKey(plugin, "item_type");
            if (!data.has(itemKey, PersistentDataType.STRING)) return;
            String itemType = data.get(itemKey, PersistentDataType.STRING);
            if ("gasmask".equalsIgnoreCase(itemType)){
                NamespacedKey tierKey = new NamespacedKey(plugin, "tier");
                NamespacedKey gasKey = new NamespacedKey(plugin, "gas_filter");
                NamespacedKey duraKey = new NamespacedKey(plugin, "durability");

                int durability = data.get(duraKey, PersistentDataType.INTEGER);
                int gas = data.get(gasKey, PersistentDataType.INTEGER);
                int tier = data.get(tierKey, PersistentDataType.INTEGER);

                if (ChemicalWarfare.getCustomConfig().getString("mask-repair-item").equalsIgnoreCase("none")) return;

                if (second.getAmount() == 1 && second.isSimilar(new ItemStack(Material.getMaterial(ChemicalWarfare.getCustomConfig().getString("mask-repair-item"))))){
                    switch (tier){
                        case 1:
                            event.getInventory().setRepairCost(ChemicalWarfare.getCustomConfig().getInt("mask-repair-cost.tier-one"));
                            break;
                        case 2:
                            event.getInventory().setRepairCost(ChemicalWarfare.getCustomConfig().getInt("mask-repair-cost.tier-two"));
                            break;
                        case 3:
                            event.getInventory().setRepairCost(ChemicalWarfare.getCustomConfig().getInt("mask-repair-cost.tier-three"));
                            break;
                        case 4:
                            event.getInventory().setRepairCost(ChemicalWarfare.getCustomConfig().getInt("mask-repair-cost.tier-four"));
                            break;
                    }

                    Tier mTier = new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[tier-1];

                    if (mTier.getDurability() <= durability) return;

                    durability += ChemicalWarfare.getCustomConfig().getInt("mask-repair-amount")*20;
                    ItemStack repairedMask = new GasMask(plugin, mTier, 1, gas).getItemStack();
                    ItemMeta repairedMeta = repairedMask.getItemMeta();
                    repairedMeta.getPersistentDataContainer().set(duraKey, PersistentDataType.INTEGER, Math.min(durability, mTier.getDurability()));
                    repairedMeta.setDisplayName(meta.getDisplayName());
                    List<String> lore = repairedMeta.getLore();
                    lore.set(2, ChatColor.GOLD + "Durability left: " + ChatColor.GREEN + Math.min(durability, mTier.getDurability())/20);
                    repairedMeta.setLore(lore);
                    repairedMask.setItemMeta(repairedMeta);

                    event.setResult(repairedMask);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        boolean rightClick = event.getAction() == Action.RIGHT_CLICK_AIR;
        boolean rightClickBlock = event.getAction() == Action.RIGHT_CLICK_BLOCK;

        if (rightClickBlock){
            ItemStack item = event.getItem();
            if (item != null) {
                NamespacedKey itemKey = new NamespacedKey(plugin, "item_type");
                NamespacedKey gasKey = new NamespacedKey(plugin, "gas");
                PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
                String itemType = data.get(itemKey, PersistentDataType.STRING);
                if (itemType != null) {
                    if (data.get(gasKey, PersistentDataType.INTEGER) == 0) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        if (rightClick){
            ItemStack item = event.getItem();
            if (item != null){

                NamespacedKey itemKey = new NamespacedKey(plugin, "item_type");
                NamespacedKey gasKey = new NamespacedKey(plugin, "gas");
                PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
                String itemType = data.get(itemKey, PersistentDataType.STRING);
                if (itemType != null){
                    switch (itemType){
                        case "canister":
                            if (data.get(gasKey, PersistentDataType.INTEGER) == 0){
                                event.setCancelled(true);
                            }
                            break;
                        case "ethylene":
                        case "salt_water":
                        case "chlorine_concentrate":
                        case "sulfur_mustard":
                        case "seaweed_extract":
                            event.setCancelled(true);
                            break;
                    }
                }
                if (!ChemicalWarfare.getCustomConfig().getBoolean("allow-multishot-rpgs")){
                    if (item.getType() == Material.CROSSBOW){
                        CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
                        var projectiles = meta.getChargedProjectiles();
                        if (projectiles.size() == 0) {
                            ItemStack p = event.getPlayer().getInventory().getItemInOffHand();
                            if (p.getType() == Material.FIREWORK_ROCKET){
                                NamespacedKey rItemKey = new NamespacedKey(plugin, "item_type");
                                PersistentDataContainer rData = p.getItemMeta().getPersistentDataContainer();
                                String rItemType = rData.get(rItemKey, PersistentDataType.STRING);
                                if ("rocket".equals(rItemType)){
                                    if (meta.getEnchants().containsKey(Enchantment.MULTISHOT)){
                                        event.getPlayer().sendMessage(ChemicalWarfare.getString()+" You can not fire gas RPGs with multishot crossbows");
                                        event.setCancelled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event){
        ItemStack item = event.getItemInHand();
        NamespacedKey itemKey = new NamespacedKey(plugin, "item_type");
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        String itemType = data.get(itemKey, PersistentDataType.STRING);
        if ("gasmask".equals(itemType)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.discoverRecipes(ChemicalWarfare.getRecipes());
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setResourcePack(ChemicalWarfare.getCustomConfig().getString("resource-pack-url"));
            }
        }.runTaskLater(plugin, 20L);
    }

    @EventHandler
    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event){
        Status status = event.getStatus();
        switch (status){
            case FAILED_DOWNLOAD:
                Bukkit.getLogger().info("[ChemicalWarfare] The resource pack download failed for: "+event.getPlayer().getDisplayName());
                event.getPlayer().sendMessage(ChemicalWarfare.getString() + " The resource pack download has failed. You can try again using: "+ChatColor.GREEN+"/cwpack");
            case DECLINED:
                event.getPlayer().sendMessage(ChemicalWarfare.getString() +" You have disabled the resourcepack. You can enable it and if the download doesn't start automatically use: "+ ChatColor.GREEN+"/cwpack");
        }
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
                    e.setCancelled(true);
                }
            }
        }
    }

}
