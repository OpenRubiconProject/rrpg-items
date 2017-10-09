package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class Striking extends Socket {
    public float damage = 2f;

    public Striking() {
        super();
        this.name = "Striking";
        this.key = "striking";
        this.description = "Strikes the entity that killed you with lightning.";
        this.materials.addAll(MaterialGroups.HELMETS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        double min = 1;
        double max = i.getPowerScore();

        damage = (float) ((Math.random() * (max - min)) + min) * 2;

        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",damage:" + this.damage;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("damage"))
            this.damage = Float.parseFloat(settingsMap.get("damage"));

        return true;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent e, FullItem item, Inventory.SlotType slot)
    {
        if(e.getEntity().getKiller() == null)
            return;

        Location location = e.getEntity().getKiller().getLocation();

        LightningStrike lightning = e.getEntity().getWorld().strikeLightning(location);
        lightning.setMetadata("DamageMod", new FixedMetadataValue(Economics.plugin, this.damage));
    }
}
