package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;


public class GroundPound extends Socket {

    public double force = 5;

    @Override
    public String getKey() {
        return "ground_pound";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.BOOTS;
    }

    @Override
    public String getName() {
        return "Ground Pound";
    }

    @Override
    public String getDescription() {
        return "Pounds all nearby entities straight up into the air";
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

        if(e.getTopSpeed() > -0.96)
            return;

        ArrayList<Player> nearbyPlayers = Helpers.getNearbyPlayers(p, 15);

        p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 250);

        //p.sendMessage(Helpers.colorize("&5Ground Pound!"));

        for(Player player : nearbyPlayers)
        {
            Vector velocity = player.getVelocity();
            velocity.setY(this.force);

            player.setVelocity(velocity);
        }

    }
}
