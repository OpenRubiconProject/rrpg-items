package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;

public class NaughtyCreeper extends Socket {

    @Override
    public String getKey() {
        return "naughty_creeper";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HAND_HELD;
    }

    @Override
    public String getName() {
        return "Naughty Creeper";
    }

    @Override
    public String getDescription() {
        return "Stops creepers from exploding and freezes them in place";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        return true;
    }

    @Override
    public boolean save() {

        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        return true;
    }


    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, SpecialItem item, EntityInventorySlotType slot)
    {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Creeper)
        {
            ((Creeper) e.getEntity()).setAI(false);
        }
    }

}
