package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.interfaces.*;
import com.openrubicon.core.api.inventory.enums.InventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.events.PlayerMovedLocationEvent;
import com.openrubicon.core.events.PlayerStandingStillEvent;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.ArrayList;
import java.util.UUID;

abstract public class Socket implements Persistable, Obfuscatable, Observeable, Loreable, Metable, Materiable, Generatable {

    private SocketProperties socketProperties = new SocketProperties();

    private UUID uuid;

    abstract public String getKey();

    public SocketProperties getSocketProperties() {
        return socketProperties;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setObfuscated(boolean obfuscated) {
        socketProperties.add(Constants.OBFUSCATED, String.valueOf(obfuscated));
    }

    @Override
    public boolean isObfuscated() {
        if(!this.socketProperties.has(Constants.OBFUSCATED))
            return false;
        return Boolean.parseBoolean(this.socketProperties.get(Constants.OBFUSCATED));
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        String loreLine = Constants.PRIMARY_COLOR+this.getName();
        lore.add(Helpers.colorize(loreLine));
        return lore;
    }

    @Override
    public ArrayList<String> getObservation() {

        ArrayList<String> output = new ArrayList<>();

        if(this.isObfuscated())
        {
            output.add("&dThis socket is obfuscated");
            output.add("&5Please obtain knowledge to learn this socket");
            output.add("&5Visit a socket specialist to un-obfuscate this socket");
            return output;
        }

        output.add("&2Name&f: &a"+this.getName());
        output.add("&2Description&f: &a"+this.getDescription());

        output.addAll(this.socketProperties.getObservation());

        return Helpers.colorize(output);
    }

    @Override
    public boolean save() {
        return socketProperties.save();
    }

    @Override
    public boolean load()
    {
        socketProperties.load();

        if(!socketProperties.has(Constants.UUID)) {
            socketProperties.add(Constants.UUID, UUID.randomUUID().toString());
        }

        this.uuid = UUID.fromString(socketProperties.get(Constants.UUID));

        return true;
    }

    @Override
    public boolean generate() {
        this.uuid = UUID.randomUUID();
        socketProperties.add(Constants.UUID, this.uuid.toString());
        return true;
    }

    public void onPlayerInteract(PlayerInteractEvent e, FullItem item, InventorySlotType slot) {}

    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, FullItem item, InventorySlotType slot) {}

    public void onPlayerLandOnGround(PlayerLandOnGroundEvent e, FullItem item, InventorySlotType slot) {}

    public void onPlayerItemDamage(PlayerItemDamageEvent e, FullItem item, InventorySlotType slot) {}

    public void onPlayerMovedLocation(PlayerMovedLocationEvent e, FullItem item, InventorySlotType slot) {}

    public void onPlayerStandingStill(PlayerStandingStillEvent e, FullItem item, InventorySlotType slot) {}

    public void onEntityDeath(EntityDeathEvent e, FullItem item, InventorySlotType slot) {}

    public void onEntityDamage(EntityDamageEvent e, FullItem item, InventorySlotType slot) {}

    public void onPlayerInteractEntity(PlayerInteractEntityEvent e, FullItem item, InventorySlotType slot) {}
}
