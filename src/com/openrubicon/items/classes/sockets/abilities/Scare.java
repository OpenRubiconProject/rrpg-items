package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class Scare extends Socket {

    public Scare() {
        super();
        this.name = "Scare";
        this.key = "scare";
        this.description = "Plays a creeper hissing sound on right click";
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
    public void onPlayerInteract(PlayerInteractEvent e, FullItem item, Inventory.SlotType slot)
    {
        if((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) || !e.getPlayer().isSneaking())
            return;

        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_CREEPER_PRIMED, SoundCategory.MASTER, 1f, 1f);
    }

}
