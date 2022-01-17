package me.kolterdyx.chemicalwarfare.utils;

public enum Tier {
    ONE(1, 1, 120),
    TWO(2, 5, 180),
    THREE(3, 10, 300),
    FOUR(4, 10, 600);

    private int value;
    private int amount;
    public static final int duration = 300;
    private int durability;

    Tier(int v, int a, int d){
        this.value = v;
        this.amount = a;
        this.durability = d;
    }

    public static Tier getByValue(int v) {
        for (Tier tier : Tier.values()){
            if (tier.getValue() == v){
                return tier;
            }
        }
        return null;
    }

    public boolean higherThan(Tier tier){
        return this.value > tier.getValue();
    }
    public boolean higherThan(int number){
        return this.value > number;
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
