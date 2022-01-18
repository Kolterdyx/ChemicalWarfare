package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;

public enum Tier {
    ONE(1, ChemicalWarfare.getCustomConfig().getInt("cloud-particles.tier-one"), ChemicalWarfare.getCustomConfig().getInt("gas-mask-durability.tier-one")),
    TWO(2, ChemicalWarfare.getCustomConfig().getInt("cloud-particles.tier-two"), ChemicalWarfare.getCustomConfig().getInt("gas-mask-durability.tier-two")),
    THREE(3, ChemicalWarfare.getCustomConfig().getInt("cloud-particles.tier-three"), ChemicalWarfare.getCustomConfig().getInt("gas-mask-durability.tier-three")),
    FOUR(4, ChemicalWarfare.getCustomConfig().getInt("cloud-particles.tier-four"), ChemicalWarfare.getCustomConfig().getInt("gas-mask-durability.tier-four"));

    private final int value;
    private int amount;
    public static final int duration = ChemicalWarfare.getCustomConfig().getInt("cloud-duration");
    private int durability;

    Tier(int v, int a, int d){
        this.value = v;
        this.amount = a;
        this.durability = d;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setDurability(int durability){
        this.durability = durability;
    }

    public int getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }

    public int getDurability() {
        return durability*20;
    }
}
