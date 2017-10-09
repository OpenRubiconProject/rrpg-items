package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;


public class GroundPound extends Socket {

    public float force = 5;

    public GroundPound() {
        super();
        this.name = "Ground Pound";
        this.key = "ground_pound";
        this.description = "Pounds all nearby entities straight up into the air";
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
