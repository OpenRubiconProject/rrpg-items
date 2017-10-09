package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class ThrustUp extends Socket {

    public float speed = 5;

    public ThrustUp() {
        super();
        this.name = "Thrust Up";
        this.key = "thrust_up";
        this.description = "Thrusts you in the direction you're facing";
        this.materials.addAll(MaterialGroups.TOOLS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        speed = (float)(Math.random() * (i.getPowerScore() - (i.getPowerScore() / 2)) + (i.getPowerScore() / 2));
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString()+",speed:" + this.speed;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        //Bukkit.broadcastMessage("Hashmap was created");
        //Bukkit.broadcastMessage("Hashmap has size: " + settingsMap.size());

        /*settingsMap.forEach((key, value) -> {
            Bukkit.getLogger().info("setting : " + key + " value : " + value);
        });*/

        if(settingsMap.containsKey("speed"))
            this.speed = Float.parseFloat(settingsMap.get("speed"));

        //Bukkit.broadcastMessage("Speed property is: " + this.speed);

        return true;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e, FullItem item, Inventory.SlotType slot)
    {
        if((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) || !e.getPlayer().isSneaking())
            return;

        Player p = e.getPlayer();
        Vector velocity = p.getVelocity();
        Location location = p.getLocation();

        double yaw = location.getYaw();

        yaw += 90;

        if(yaw > 360)
            yaw -= 360;

        yaw = Math.toRadians(yaw);

        velocity.setX(velocity.getX() + (Math.cos(yaw) * speed));
        velocity.setZ(velocity.getZ() + (Math.sin(yaw) * speed));
        velocity.setY(velocity.getY() + (this.speed / 3.0));

        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation(), 500);

        p.setVelocity(velocity);
    }


}
