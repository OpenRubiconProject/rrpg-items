package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerMovedLocationEvent;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class MarathonRunner extends Socket {
    public double rate = 0.01;

    @Override
    public String getKey() {
        return "marathon_runner";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.ARMOR;
    }

    @Override
    public String getName() {
        return "Marathon Runner";
    }

    @Override
    public String getDescription() {
        return "Gain XP as you move and run around";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = this.getItemSpecs().getPower() / 2.;
        if(min < 1)
            min = 1;
        double max = this.getItemSpecs().getPower();
        if(max < 2)
            max = 2;
        rate = Helpers.randomDouble(min, max);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("rate", this.rate);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.rate = this.getSocketProperties().getDouble("rate");

        return true;
    }

    @Override
    public void onPlayerMovedLocation(PlayerMovedLocationEvent e, UniqueItem item, EntityInventorySlotType slot) {
                Player p = e.getPlayer();

        double amount = Helpers.getDistance(e.getPreviousLocation(), e.getNewLocation());

        if(amount < 1)
            amount = 1;

        int chance = Helpers.rng.nextInt(1 + (int)(Math.pow(p.getLevel(), 1.65)));

        if(chance <= this.rate)
            p.giveExp((int)amount);
    }
}
