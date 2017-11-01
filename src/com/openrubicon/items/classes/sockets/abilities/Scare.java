package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;

public class Scare extends Socket {

    @Override
    public String getKey() {
        return "scare";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HAND_HELD;
    }

    @Override
    public String getName() {
        return "Scare";
    }

    @Override
    public String getDescription() {
        return "Plays a creeper hissing sound on right click";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        return true;
    }

    @Override
    public boolean save()
    {
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        return true;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        if((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) || !e.getPlayer().isSneaking())
            return;

        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_CREEPER_PRIMED, SoundCategory.MASTER, 1f, 1f);
    }

}
