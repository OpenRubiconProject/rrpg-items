package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;

public class Stab extends Socket {
    public float damage = 5;

    public Stab() {
        super();
        this.name = "Stab";
        this.key = "stab";
        this.description = "Right click a player to deal instant damage.";
        this.materials.addAll(MaterialGroups.SWORDS);

        this.usingCooldown = true;
        this.cooldownLength = 5 * 20;
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        damage = Helpers.randomInt(1, (int)i.getPowerScore() + 1);
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString()+",damage:" + this.damage;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if(settingsMap.containsKey("damage"))
            this.damage = Float.parseFloat(settingsMap.get("damage"));

        return true;
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e, FullItem item, Inventory.SlotType slot)
    {
        if(this.onCooldown())
            return;

        if(!e.getPlayer().isSneaking())
            return;

        if(!(e.getRightClicked() instanceof LivingEntity))
            return;


        LivingEntity entity = (LivingEntity)e.getRightClicked();

        entity.damage(this.damage);

        this.startCooldown(e.getPlayer(), slot);
    }

}
