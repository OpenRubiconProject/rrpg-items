package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Launch extends Socket {

    public float force = 5;

    public Launch() {
        super();
        this.name = "Launch";
        this.key = "launch";
        this.description = "Launches you straight up into the air.";
        this.materials.addAll(MaterialGroups.DIAMOND_TOOLS);
        this.materials.addAll(MaterialGroups.GOLD_TOOLS);
        this.materials.addAll(MaterialGroups.IRON_TOOLS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i) {
        force = (float) (Math.random() * (i.getPowerScore() - (i.getPowerScore() / 2)) + (i.getPowerScore() / 2));
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",force:" + this.force;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        //Bukkit.broadcastMessage("Hashmap was created");
        //Bukkit.broadcastMessage("Hashmap has size: " + settingsMap.size());

        /*settingsMap.forEach((key, value) -> {
            Bukkit.getLogger().info("setting : " + key + " value : " + value);
        });*/

        if (settingsMap.containsKey("force"))
            this.force = Float.parseFloat(settingsMap.get("force"));

        //Bukkit.broadcastMessage("Speed property is: " + this.speed);

        return true;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e, FullItem item, Inventory.SlotType slot)
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

