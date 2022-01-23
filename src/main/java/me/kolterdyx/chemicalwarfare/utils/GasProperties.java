package me.kolterdyx.chemicalwarfare.utils;


import org.bukkit.ChatColor;

public enum GasProperties {
    EMPTY(255, 255, 255, ChatColor.WHITE+"Empty", "empty"),
    UNIVERSAL(0, 0, 0, ChatColor.WHITE+"Universal", "universal"),
    MUSTARD(241, 255, 38, ChatColor.YELLOW+"Mustard gas", "mustard"),
    CHLORINE(203, 255, 82, ChatColor.GREEN+"Chlorine gas", "chlorine"),
    TEAR(250, 213, 255, ChatColor.AQUA+"Tear gas", "tear");


    private int[] color;
    private int index;
    public static int counter=0;
    private String formalName;
    private String name;

    GasProperties(int r, int g, int b, String formalName, String name){
        color = new int[]{r, g, b};
        this.formalName = formalName;
        this.name = name;
        index = GasProperties.getNewIndex();
    }

    public static int getIndexByName(String arg) {
        for (GasProperties gas :
                GasProperties.values()) {
            if (gas.getName().equals(arg)) return gas.getIndex();
        }
        return -1;
    }

    public int[] getColor(){
        return color;
    }

    public static GasProperties getByIndex(int index){
        for (GasProperties gas :
                GasProperties.values()) {
            if (gas.getIndex() == index) return gas;
        }
        return null;
    }

    public int getIndex(){
        return index;
    }

    public static int getNewIndex(){
        return counter++;
    }

    public String getFormalName() {
        return formalName;
    }

    public String getName() {
        return name;
    }
}
