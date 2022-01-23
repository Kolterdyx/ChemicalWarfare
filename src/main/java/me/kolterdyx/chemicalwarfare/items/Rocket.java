package me.kolterdyx.chemicalwarfare.items;

import me.kolterdyx.chemicalwarfare.ChemicalWarfare;
import me.kolterdyx.chemicalwarfare.utils.GasProperties;
import me.kolterdyx.chemicalwarfare.utils.Tier;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Rocket {

    private ItemStack item;
    private Tier tier;
    private int gas;
    private int power;
    private Plugin plugin;

    public Rocket(Plugin plugin, Tier tier, int gas, int power){
        item = new ItemStack(Material.FIREWORK_ROCKET, 1);
        this.tier = tier;
        this.gas = gas;
        this.power = power;
        this.plugin = plugin;
        FireworkMeta meta =  (FireworkMeta) item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gas"), PersistentDataType.INTEGER, gas);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "tier"), PersistentDataType.INTEGER, tier.getValue());
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "item_type"), PersistentDataType.STRING, "rocket");
        meta.setDisplayName(ChatColor.WHITE+"Gas RPG");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Tier: "+ChatColor.GREEN+tier.getValue());
        lore.add(ChatColor.GOLD+"Contents: "+ GasProperties.getByIndex(gas).getFormalName());
        meta.setLore(lore);
        meta.setPower(power);
        item.setItemMeta(meta);
    }

    public void craftingRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "rocket"+tier.getValue()+""+gas+""+power);
        ChemicalWarfare.addRecipe(key);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        ItemStack rocket = new ItemStack(Material.FIREWORK_ROCKET);
        FireworkMeta meta = (FireworkMeta) rocket.getItemMeta();
        meta.setPower(power);
        rocket.setItemMeta(meta);
        recipe.shape("C","R");
        recipe.setIngredient('R', new RecipeChoice.ExactChoice(rocket));
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(new Canister(plugin, tier, 1, gas).getItemStack()));
        Bukkit.getServer().addRecipe(recipe);
    }

    public ItemStack getItem(){
        return item;
    }

}
