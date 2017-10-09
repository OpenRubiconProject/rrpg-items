package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class Martyrdom extends Socket {
    public float fuse = 40f;

    public Martyrdom() {
        super();
        this.name = "Martyrdom";
        this.key = "martyrdom";
        this.description = "Drops primed TNT on your body when you die.";
        this.materials.addAll(MaterialGroups.ARMOR);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        double min = 1;
        double max = i.getPowerScore();
        fuse = (float) ((Math.random() * (max - min)) + min);
        fuse = 20 - fuse;
        fuse *= 3;
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",fuse:" + this.fuse;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("fuse"))
            this.fuse = Float.parseFloat(settingsMap.get("fuse"));

        return true;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent e, FullItem item, Inventory.SlotType slot)
    {
        Location location = e.getEntity().getLocation();

        TNTPrimed tnt = e.getEntity().getWorld().spawn(location, TNTPrimed.class);
        tnt.setFuseTicks((int)this.fuse);
        tnt.setMetadata("BlockDamage", new FixedMetadataValue(Economics.plugin, false));
    }
}
