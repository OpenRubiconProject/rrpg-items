package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class ThrustUp extends Socket {

    public double speed = 5;

    @Override
    public String getKey() {
        return "thrust_up";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.TOOLS;
    }

    @Override
    public String getName() {
        return "Thrust Up";
    }

    @Override
    public String getDescription() {
        return "Thrusts you in the direction you're facing";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = this.getItemSpecs().getPower() / 2.;
        double max = this.getItemSpecs().getPower();
        speed = Helpers.randomDouble(min, max);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("speed", this.speed);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.speed = this.getSocketProperties().getDouble("speed");

        return true;
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent e, SpecialItem item, EntityInventorySlotType slot)
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
