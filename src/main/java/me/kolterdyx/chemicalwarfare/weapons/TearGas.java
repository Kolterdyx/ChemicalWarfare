package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TearGas extends GasCloud {


    public TearGas(Plugin plugin, int tier, Location pos, World world, int seconds) {
        super(plugin, tier, pos, world, seconds);
        this.properties = GasProperties.TEAR;
    }

    public TearGas(Plugin plugin, int tier, Location pos, World world) {
        super(plugin, tier, pos, world);
        this.properties = GasProperties.TEAR;
    }

    @Override
    protected void applyGas(LivingEntity entity){
        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, tier.getValue()-1, false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0, false, false, false));
    }
}
