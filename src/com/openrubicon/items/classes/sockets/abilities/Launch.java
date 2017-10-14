package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;

public class Launch extends Socket {

    public double force = 5;

    @Override
    public String getKey() {
        return "launch";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.addAll(MaterialGroups.DIAMOND_TOOLS);
        materials.addAll(MaterialGroups.GOLD_TOOLS);
        materials.addAll(MaterialGroups.IRON_TOOLS);
        return materials;
    }

    @Override
    public String getName() {
        return "Launch";
    }

    @Override
    public String getDescription() {
        return "Launches you straight up into the air";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = this.getItemSpecs().getPower() / 2.;
        double max = this.getItemSpecs().getPower();
        force = Helpers.randomDouble(min, max);
        force = Helpers.scale(force, 1, 11, 0.25, 0.95);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("force", this.force);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.force = this.getSocketProperties().getDouble("force");

        return true;
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent e, SpecialItem item, EntityInventorySlotType slot)
    {
        if((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) || !e.getPlayer().isSneaking())
            return;

        Player p = e.getPlayer();

        if(!p.isOnGround())
            return;

        Vector velocity = p.getVelocity();

        velocity.setY(velocity.getY() + (this.force / 1.2));

        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation(), 750);

        p.setVelocity(velocity);
    }
}

