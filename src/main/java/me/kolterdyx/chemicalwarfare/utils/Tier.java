package me.kolterdyx.chemicalwarfare.utils;

public enum Tier {
    ONE(1, 1),
    TWO(2, 5),
    THREE(3, 10),
    FOUR(4, 10);

    private int value;
    private int amount;
    public static final int duration = 60;

    Tier(int v, int a){
        this.value = v;
        this.amount = a;
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
}
