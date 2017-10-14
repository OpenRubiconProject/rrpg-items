package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class Bounce extends Socket {

    public double force = 5;

    @Override
    public String getKey() {
        return "bounce";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.BOOTS;
    }

    @Override
    public String getName() {
        return "Bounce";
    }

    @Override
    public String getDescription() {
        return "Landing on the ground will bounce you back up in the air";
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
    public void onPlayerLandOnGround(PlayerLandOnGroundEvent e, SpecialItem item, EntityInventorySlotType slot)
    {
        Player p = e.getPlayer();

        double topSpeed = e.getTopSpeed();

        if(topSpeed > -0.75)
            return;

        topSpeed = Math.abs(topSpeed) * this.force;

        Vector velocity = p.getVelocity();
        velocity.setY(topSpeed);
        p.setVelocity(velocity);

        p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 150);
    }
}
