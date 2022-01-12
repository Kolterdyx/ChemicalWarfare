package me.kolterdyx.chemicalwarfare;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wind extends BukkitRunnable {
    private Vector velocity=new Vector(0, 0, 0);
    private Vector angle = new Vector(1, 0, 0);
    private double angleIncrement = 15;
    private Random random = new Random();
    private double maxSpeed = 3;
    private double minSpeed = 0;
    private double speed = 1.5f;
    private int counter=0;
    private List<Pair<Double, Double>> weightedIncrements;
    private EnumeratedDistribution increments;

    public Wind(){
        double[] possibleIncrements = new double[]{-0.4, -0.3, -0.2, -0.1,    0,  0.1,  0.2,  0.3,  0.4};
        double[] weights =            new double[]{   1,    2,    4,    6,    7,    6,    4,    2,    1};

        weightedIncrements = new ArrayList<>();

        for (int i = 0; i < weights.length; i++) {
            weightedIncrements.add(new Pair<>(Double.valueOf(possibleIncrements[i]), Double.valueOf(weights[i])));
        }

        increments = new EnumeratedDistribution<>(weightedIncrements);
        change();
    }

    @Override
    public void run() {
        if (counter==120*20){
            change();
            counter = 0;
        }
        counter++;
    }

    private void change(){
        double increment = (double) increments.sample();
        speed += increment;
        if (speed > maxSpeed) speed = maxSpeed;
        if (speed < minSpeed) speed = minSpeed;
        angle.rotateAroundY(randomBetween(-angleIncrement, angleIncrement)*Math.PI/180);
        angle.normalize();
        velocity = angle.clone().multiply(speed);
    }

    private double randomBetween(double low, double high){
        return random.nextDouble(high-low)+low;
    }

    public Vector getVelocity() {
        return velocity.clone().multiply(.05f);
    }

    public Vector getRawVelocity() {
        return velocity.clone();
    }
}
