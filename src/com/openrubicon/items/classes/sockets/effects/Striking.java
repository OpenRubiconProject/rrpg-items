package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.enums.InventorySlotType;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.RRPGItems;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashSet;

public class Striking extends Socket {
    public double damage = 2;

    @Override
    public String getKey() {
        return "striking";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HELMETS;
    }

    @Override
    public String getName() {
        return "Striking";
    }

    @Override
    public String getDescription() {
        return "Strikes the entity that killed you with lightning";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1;
        double max = this.getItemSpecs().getPower();

        damage = ((Math.random() * (max - min)) + min) * 2;

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("damage", this.damage);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.damage = this.getSocketProperties().getDouble("damage");

        return true;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent e, SpecialItem item, InventorySlotType slot)
    {
        if(e.getEntity().getKiller() == null)
            return;

        Location location = e.getEntity().getKiller().getLocation();

        LightningStrike lightning = e.getEntity().getWorld().strikeLightning(location);
        lightning.setMetadata("DamageMod", new FixedMetadataValue(RRPGItems.plugin, this.damage));
    }
}
