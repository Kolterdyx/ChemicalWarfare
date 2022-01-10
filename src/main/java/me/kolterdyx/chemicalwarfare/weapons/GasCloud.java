package me.kolterdyx.chemicalwarfare.weapons;

import me.kolterdyx.chemicalwarfare.component.Tier;
import org.bukkit.Location;

public abstract class GasCloud {

    private Tier tier;
    private Location pos;

    public GasCloud(Tier tier, Location pos){
        this.tier = tier;
        this.pos = pos;
    }

}
