package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class SteelFeet extends Socket {
    public float ratio = 1.1f;

    public SteelFeet() {
        super();
        this.name = "Steel Feet";
        this.key = "steel_feet";
        this.description = "Reduces damage taken from falling";
        this.materials.addAll(MaterialGroups.BOOTS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        double min = 1.1;
        double max = i.getPowerScore();

        if(max < min)
            max = 1.5;

        this.ratio = (float) ((Math.random() * (max - min)) + min);

        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",ratio:" + this.ratio;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("ratio"))
            this.ratio = Float.parseFloat(settingsMap.get("ratio"));

        return true;
    }

    @Override
    public void onEntityDamage(EntityDamageEvent e, FullItem item, Inventory.SlotType slot)
    {
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            double damage = e.getDamage() / this.ratio;
            e.setDamage(damage);
        }

    }
}
