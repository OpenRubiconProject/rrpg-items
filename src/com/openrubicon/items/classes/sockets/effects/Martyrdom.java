package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.RRPGItems;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashSet;

public class Martyrdom extends Socket {
    public int fuse = 40;

    @Override
    public String getKey() {
        return "martydom";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.ARMOR;
    }

    @Override
    public String getName() {
        return "Martydom";
    }

    @Override
    public String getDescription() {
        return "Landing on the ground will bounce you back up in the air";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1;
        double max = this.getItemSpecs().getPower();
        fuse = (int) ((Math.random() * (max - min)) + min);
        fuse = 20 - fuse;
        fuse *= 3;

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addInteger("fuse", this.fuse);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.fuse = this.getSocketProperties().getInteger("fuse");

        return true;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent e, SpecialItem item, EntityInventorySlotType slot)
    {
        Location location = e.getEntity().getLocation();

        TNTPrimed tnt = e.getEntity().getWorld().spawn(location, TNTPrimed.class);
        tnt.setFuseTicks(this.fuse);
        tnt.setMetadata("BlockDamage", new FixedMetadataValue(RRPGItems.plugin, false));
    }
}
