package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;

public class FlightSuit extends Socket {
    public double ratio = 1.1;

    @Override
    public String getKey() {
        return "flight_suit";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.CHESTPLATES;
    }

    @Override
    public String getName() {
        return "Flight Suit";
    }

    @Override
    public String getDescription() {
        return "Reduces damage taken from flying into walls";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1.1;
        double max = this.getItemSpecs().getPower();

        if(max < min)
            max = 1.5;

        this.ratio = (float) ((Math.random() * (max - min)) + min);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("ratio", this.ratio);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.ratio = this.getSocketProperties().getDouble("ratio");

        return true;
    }


    @Override
    public void onEntityDamage(EntityDamageEvent e, SpecialItem item, EntityInventorySlotType slot)
    {
        if(e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL)
        {
            double damage = e.getDamage() / this.ratio;
            e.setDamage(damage);
        }

    }
}
