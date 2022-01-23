package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.gear.GasMask;
import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import me.kolterdyx.chemicalwarfare.utils.ItemManager;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GasCloud extends BukkitRunnable {

    protected Tier tier;
    protected Location pos;
    protected World world;
    private double radius;
    protected int effectDistance;
    protected int ticksLived=0;
    protected int duration;
    protected Plugin plugin;
    protected GasProperties properties;

    public GasCloud(Plugin plugin, int tier, Location pos, World world, int seconds){
        this.tier = new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[tier-1];
        radius = tier*2;
        effectDistance = (int) (radius*2.5f);
        this.pos = pos;
        this.world = world;
        this.duration = seconds*20;
        this.plugin = plugin;
        ((ChemicalWarfare)plugin).addGasCloud(this);
    }

    public GasCloud(Plugin plugin, int tier, Location pos, World world){
        this(plugin, tier, pos, world, Tier.duration);
    }

    public void createToxicCloud(int[] color, int amount){
        // spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data, boolean force)
        Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(color[0], color[1], color[2]), Color.fromRGB(color[0], color[1], color[2]), 1f);
        world.spawnParticle(Particle.DUST_COLOR_TRANSITION, pos, (int) (amount*radius*radius*radius), radius, radius, radius, 1, dustTransition, true);

    }

    public void createSmokeCloud(int seconds, int amount){

    }

    public void live(){
        if(ticksLived>=duration){
            switch (tier.getValue()) {
                case 4 -> {
                    tier = Tier.THREE;
                    ticksLived = 0;
                }
                case 3 -> {
                    tier = Tier.TWO;
                    ticksLived = 0;
                }
                case 2 -> {
                    tier = Tier.ONE;
                    ticksLived = 0;
                }
                case 1 -> this.cancel();
            }
            radius = tier.getValue()*2;
            effectDistance = (int) (radius*2.5f);
        }
        createToxicCloud(properties.getColor(), tier.getAmount());
        ticksLived++;
    }

    @Override
    public void run(){
        live();
        for (Entity entity : world.getNearbyEntities(pos, effectDistance, effectDistance, effectDistance)){
            if (!(entity instanceof LivingEntity livingEntity)) continue;
            boolean isPlayer = livingEntity instanceof Player;
            if (!(isPlayer) && !ChemicalWarfare.getCustomConfig().getBoolean("gas-affects-living-entities")) continue;
            if (livingEntity instanceof Player player && player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;


            if (pos.distanceSquared(livingEntity.getLocation()) < Math.pow(effectDistance, 2)){
                if (isPlayer || livingEntity instanceof Villager){
                    ItemStack helmet = livingEntity.getEquipment().getHelmet();
                    if (helmet == null) {
                        applyGas(livingEntity);
                        continue;
                    }
                    ItemMeta meta = helmet.getItemMeta();
                    String itemType = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING);
                    Bukkit.getLogger().info(itemType);
                    if (!"gasmask".equals(itemType)){
                        applyGas(livingEntity);
                        continue;
                    }
                    GasProperties maskProperties = GasProperties.getByIndex(meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "gas_filter"), PersistentDataType.INTEGER));
                    if (properties.equals(maskProperties) || maskProperties.equals(GasProperties.UNIVERSAL)) {
                        GasMask.use(helmet, plugin);
                        continue;
                    }
                    if (!GasMask.use(helmet, plugin)){
                        applyGas(livingEntity);
                    }
                }
                applyGas(livingEntity);
            }
        }
    }

    protected abstract void applyGas(LivingEntity entity);
}
