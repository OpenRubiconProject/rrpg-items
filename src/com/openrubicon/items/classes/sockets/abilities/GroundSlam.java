package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class GroundSlam extends Socket {
    public float force = 5;

    public GroundSlam() {
        super();
        this.name = "Ground Slam";
        this.key = "ground_slam";
        this.description = "Slams all nearby entities straight up into the air";
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

        if(e.getTopSpeed() > -1.10)
            return;

        ArrayList<Entity> nearbyEntity = Helpers.getNearbyEntities(p, 15);

        p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 500);

        //p.sendMessage(Helpers.colorize("&5Ground Slam!!!"));

        for(Entity entity : nearbyEntity)
        {
            Vector velocity = entity.getVelocity();
            velocity.setY(this.force);

            entity.setVelocity(velocity);
        }

    }
}
