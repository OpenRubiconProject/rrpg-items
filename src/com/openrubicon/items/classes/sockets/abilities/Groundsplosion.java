package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;

public class Groundsplosion extends Socket {

    public double force = 5;

    @Override
    public String getKey() {
        return "groundsplosion";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.BOOTS;
    }

    @Override
    public String getName() {
        return "Groundsplosion";
    }

    @Override
    public String getDescription() {
        return "Throws all nearby entities away from you";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = this.getItemSpecs().getPower() / 2.;
        double max = this.getItemSpecs().getPower();
        force = Helpers.randomDouble(min, max);

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
    public void onPlayerLandOnGround(PlayerLandOnGroundEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        Player p = e.getPlayer();

        if(e.getTopSpeed() > -1.27)
            return;

        ArrayList<Entity> nearbyEntity = Helpers.getNearbyEntities(p, 15);

        p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 1000);

        //p.sendMessage(Helpers.colorize("&5GROUNDSPLOSION!!!"));

        for(Entity entity : nearbyEntity)
        {
            Vector velocity = entity.getVelocity();
            Location locationE = entity.getLocation();
            Location locationP = p.getLocation();

            double xDir = locationE.getX() - locationP.getX();
            double zDir = locationE.getZ() - locationP.getZ();

            Vector direction = new Vector(xDir, 1, zDir);
            direction.normalize();

            velocity.setX(direction.getX() * this.force);
            velocity.setZ(direction.getZ() * this.force);
            velocity.setY((this.force / 3.0) + 1);

            entity.setVelocity(velocity);
        }

    }
}
