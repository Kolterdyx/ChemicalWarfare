package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GasCloud extends BukkitRunnable {

    protected Tier level;
    protected Location pos;
    protected World world;
    private double radius;
    protected int gas_type=0;
    protected int effectDistanceSquared;
    protected int effectDistance;
    protected int ticksLived=0;
    protected int duration;
    protected Plugin plugin;

    public GasCloud(Plugin plugin, int tier, Location pos, World world, int seconds){
        this.level = new Tier[]{Tier.ONE, Tier.TWO, Tier.THREE, Tier.FOUR}[tier-1];
        radius = tier*2;
        effectDistanceSquared = (int) Math.pow(radius*2.5f, 2);
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

    public void createSmokeCloud(int seconds, int amount){}


    public void createDebugLine(){
        Particle.DustTransition red = new Particle.DustTransition(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 0, 0), 1f);
        Particle.DustTransition green = new Particle.DustTransition(Color.fromRGB(0, 255, 0), Color.fromRGB(0, 255, 0), 1f);
        Particle.DustTransition blue = new Particle.DustTransition(Color.fromRGB(0, 0, 255), Color.fromRGB(0, 0, 255), 1f);
//        world.spawnParticle(Particle.DUST_COLOR_TRANSITION, pos, 1, 0, 0, 0, 1, dustTransition, true);

        for (float t=0;t<360;t+=4){
            double x = pos.getX()+Math.cos(Math.toRadians(t))*effectDistance;
            double y = pos.getY()+Math.sin(Math.toRadians(t))*effectDistance;
            double z1 = pos.getZ()+Math.sin(Math.toRadians(t))*effectDistance;
            double z2 = pos.getZ()+Math.cos(Math.toRadians(t))*effectDistance;
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(world, x, y, pos.getZ()), 1, 0, 0, 0, 1, red, true);
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(world, x, pos.getY(), z1), 1, 0, 0, 0, 1, green, true);
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(world, pos.getX(), y, z2), 1, 0, 0, 0, 1, blue, true);
        }

        int ring_number = 20;
        int point_number = 20;
        double deltaTheta = Math.PI / (float)ring_number;
        double deltaPhi = 2f * Math.PI / (float)point_number;
        double theta = 0;
        double phi = 0;
        for (int r=0; r<ring_number; r++){
            theta += deltaTheta;
            for (int p=0; p<point_number; p++){
                phi += deltaPhi;
                double x1 = pos.getX() + Math.sin(theta) * Math.cos(phi) * effectDistance;
                double y1 = pos.getY() + Math.cos(theta) * effectDistance;
                double z1 = pos.getZ() + Math.sin(theta) * Math.sin(phi) * effectDistance;
                world.spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(world, x1, y1, z1), 1, 0, 0, 0, 1, green, true);

                double x2 = pos.getX() + Math.cos(theta) * effectDistance;
                double y2 = pos.getY() + Math.sin(theta) * Math.cos(phi) * effectDistance;
                double z2 = pos.getZ() + Math.sin(theta) * Math.sin(phi) * effectDistance;
                world.spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(world, x2, y2, z2), 1, 0, 0, 0, 1, red, true);

                double x3 = pos.getX() + Math.sin(theta) * Math.cos(phi) * effectDistance;
                double y3 = pos.getY() + Math.sin(theta) * Math.sin(phi) * effectDistance;
                double z3 = pos.getZ() + Math.cos(theta) * effectDistance;
                world.spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(world, x3, y3, z3), 1, 0, 0, 0, 1, blue, true);
            }
        }

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
            effectDistanceSquared = (int) Math.pow(radius*2.5f, 2);
            effectDistance = (int) (radius*2.5f);
        }

        if (ChemicalWarfare.getCustomConfig().getBoolean("debug-circle")){
            createDebugLine();
        }

        ticksLived++;
    }

    @Override
    public abstract void run();

    protected abstract void applyGas(LivingEntity entity, int power);
}
