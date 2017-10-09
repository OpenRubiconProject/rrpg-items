package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class NaughtyCreeper extends Socket {

    public NaughtyCreeper() {
        super();
        this.name = "Naughty Creeper";
        this.key = "naughty_creeper";
        this.description = "Stops creepers from exploding and freezes them in place.";
        this.materials.addAll(MaterialGroups.HAND_HELD);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString();
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);
        return true;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, FullItem item, Inventory.SlotType slot)
    {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Creeper)
        {
            ((Creeper) e.getEntity()).setAI(false);
        }
    }

}
