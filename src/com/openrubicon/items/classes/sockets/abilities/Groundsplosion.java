package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class Groundsplosion extends Socket {
    public float force = 5;

    public Groundsplosion() {
        super();
        this.name = "Groundsplosion";
        this.key = "groundsplosion";
        this.description = "Throws all nearby entities away from you";
        this.materials.addAll(MaterialGroups.BOOTS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        force = (float)(Math.random() * (i.getPowerScore() - (i.getPowerScore() / 2)) + (i.getPowerScore() / 2));
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString()+",force:" + this.force;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        //Bukkit.broadcastMessage("Hashmap was created");
        //Bukkit.broadcastMessage("Hashmap has size: " + settingsMap.size());

        /*settingsMap.forEach((key, value) -> {
            Bukkit.getLogger().info("setting : " + key + " value : " + value);
        });*/

        if(settingsMap.containsKey("force"))
            this.force = Float.parseFloat(settingsMap.get("force"));

        //Bukkit.broadcastMessage("Speed property is: " + this.speed);

        return true;
    }

    @Override
    public void onPlayerLandOnGround(PlayerLandOnGroundEvent e, FullItem item, Inventory.SlotType slot)
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
