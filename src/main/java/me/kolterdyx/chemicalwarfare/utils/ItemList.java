package me.kolterdyx.chemicalwarfare.utils;

public enum ItemList {
    SULFUR,
    ETHYLENE,
    SALT,
    CHLORINE,
    SULFUR_DICHLORIDE,
    SEAWEED_EXTRACT,
    SULFUR_MUSTARD,
    CHLORINE_CONCENTRATE,
    SALT_WATER;

    private int id;
    private static int counter=0;

    ItemList(){
        id = getNewId();
    }

    public int getId(){
        return id;
    }

    private static int getNewId(){
        return counter++;
    }

}
