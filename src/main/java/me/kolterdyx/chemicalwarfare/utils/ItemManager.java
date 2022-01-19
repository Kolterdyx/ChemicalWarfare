package me.kolterdyx.chemicalwarfare.utils;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.gear.GasMask;
import me.kolterdyx.chemicalwarfare.items.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.plugin.Plugin;

public class ItemManager {

    public static ItemStack canister1;
    public static ItemStack canister2;
    public static ItemStack canister3;
    public static ItemStack canister4;
    public static ItemStack SALT_WATER;
    public static ItemStack SULFUR;
    public static ItemStack ETHYLENE;
    public static ItemStack SALT;
    public static ItemStack CHLORINE;
    public static ItemStack CHLORINE_CONCENTRATE;
    public static ItemStack SULFUR_MUSTARD;
    public static ItemStack SEAWEED_EXTRACT;
    public static ItemStack SULFUR_DICHLORIDE;
    private static Plugin plugin;

    public static void init(Plugin plugin){

        setPlugin(plugin);

        ETHYLENE = new Ethylene(plugin).getItem();
        SULFUR = new Sulfur(plugin).getItem();
        SALT_WATER = new SaltWater(plugin).getItem();
        SALT = new Salt(plugin).getItem();
        CHLORINE = new Chlorine(plugin).getItem();
        SULFUR_DICHLORIDE = new SulfurDichloride(plugin).getItem();
        SULFUR_MUSTARD = new SulfurMustard(plugin).getItem();
        SEAWEED_EXTRACT = new SeaweedExtract(plugin).getItem();
        CHLORINE_CONCENTRATE = new ChlorineConcentrate(plugin).getItem();

        for (Tier tier : Tier.values()){

            new GasMask(plugin, tier, 1, GasProperties.UNIVERSAL.getIndex()).craftingRecipe();
            new GasMask(plugin, tier, 1, GasProperties.MUSTARD.getIndex()).craftingRecipe();
            new GasMask(plugin, tier, 1, GasProperties.CHLORINE.getIndex()).craftingRecipe();
            new GasMask(plugin, tier, 1, GasProperties.TEAR.getIndex()).craftingRecipe();

            for (int i = 0; i < 3; i++) {
                new Rocket(plugin, tier, 1, i+1).craftingRecipe();
                new Rocket(plugin, tier, 2, i+1).craftingRecipe();
                new Rocket(plugin, tier, 3, i+1).craftingRecipe();
            }

            new Canister(plugin, tier, 1, 1).craftingRecipe();
            new Canister(plugin, tier, 1, 2).craftingRecipe();
            new Canister(plugin, tier, 1, 3).craftingRecipe();

            ItemStack item =  new Canister(plugin, tier, 1, 0).getItemStack();
            item.setAmount(4);
            NamespacedKey key = new NamespacedKey(plugin, "canister"+tier.getValue());
            ChemicalWarfare.addRecipe(key);
            ShapedRecipe sr = new ShapedRecipe(key, item);

            switch (tier){
                case ONE:
                    canister1 = item.clone();
                    sr.shape("TTT", "CBC", "IRI");
                    sr.setIngredient('C', Material.IRON_BLOCK);
                    break;
                case TWO:
                    canister2 = item.clone();
                    sr.shape("TTT", "ABA", "IRI");
                    sr.setIngredient('A', Material.GOLD_BLOCK);
                    break;
                case THREE:
                    canister3 = item.clone();
                    sr.shape("TTT", "DBD", "IRI");
                    sr.setIngredient('D', Material.DIAMOND);
                    break;
                case FOUR:
                    canister4 = item.clone();
                    sr.shape("TTT", "NBN", "IRI");
                    sr.setIngredient('N', Material.NETHERITE_SCRAP);
                    break;
            }
            sr.setIngredient('T', Material.CLAY);
            sr.setIngredient('B', Material.GLASS_BOTTLE);
            sr.setIngredient('I', Material.IRON_INGOT);
            sr.setIngredient('R', Material.REDSTONE);


            Bukkit.getServer().addRecipe(sr);
        }

    }

    private static void setPlugin(Plugin p) {
        plugin = p;
    }

}
