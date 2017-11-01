package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;

public class SteelFeet extends Socket {
    public double ratio = 1.1f;

    @Override
    public String getKey() {
        return "steel_feet";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.BOOTS;
    }

    @Override
    public String getName() {
        return "Steel Feet";
    }

    @Override
    public String getDescription() {
        return "Landing on the ground will bounce you back up in the air";
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
    public void onEntityDamage(EntityDamageEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            double damage = e.getDamage() / this.ratio;
            e.setDamage(damage);
        }

    }
}
