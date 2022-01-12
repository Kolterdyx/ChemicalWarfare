package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GasCloud extends BukkitRunnable {

    protected Tier level;
    protected Location pos;
    protected World world;
    private double radius;
    protected int effectDistance;
    protected int ticksLived=0;
    protected int duration;
    protected Plugin plugin;

    public GasCloud(Plugin plugin, int tier, Location pos, World world, int seconds){
        this.level = new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[tier-1];
        radius = tier*2;
        effectDistance = (int) Math.pow(radius*2.5f, 2);
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
            switch (level.getValue()){
                case 4:
                    level = Tier.THREE;
                    ticksLived = 0;
                    break;
                case 3:
                    level = Tier.TWO;
                    ticksLived = 0;
                    break;
                case 2:
                    level = Tier.ONE;
                    ticksLived = 0;
                    break;
                case 1:
                    this.cancel();
            }
            radius = level.getValue()*2;
        }
        pos.add(ChemicalWarfare.getWindVelocity());
        ticksLived++;
    }

    @Override
    public abstract void run();
}
