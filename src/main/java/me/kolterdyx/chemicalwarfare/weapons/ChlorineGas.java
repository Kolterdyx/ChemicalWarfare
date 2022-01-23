package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChlorineGas extends GasCloud{

    public ChlorineGas(Plugin plugin, int tier, Location pos, World world, int seconds) {
        super(plugin, tier, pos, world, seconds);
        this.properties = GasProperties.CHLORINE;
    }

    public ChlorineGas(Plugin plugin, int tier, Location pos, World world){
        super(plugin, tier, pos, world);
        this.properties = GasProperties.CHLORINE;
    }

    @Override
    protected void applyGas(LivingEntity entity){

        int power = tier.getValue()-1;
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, (int)Math.floor(power*0.75f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, (int)Math.floor(power*0.75f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, (int)Math.floor(power*0.75f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, (int)Math.floor(power*0.5f), false, false, false));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, power, false, false, false));
    }
}
