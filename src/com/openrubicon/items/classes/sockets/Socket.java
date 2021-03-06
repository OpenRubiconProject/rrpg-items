package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.interfaces.*;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import com.openrubicon.items.classes.sockets.interfaces.SocketEvents;
import com.openrubicon.items.classes.sockets.nbt.SocketProperties;

import java.util.ArrayList;
import java.util.UUID;

abstract public class Socket implements SocketEvents, Persistable, Obfuscatable, Observeable, Loreable, Metable, Materiable, Generatable, Keyable {

    private SocketProperties socketProperties = new SocketProperties();

    private UUID uuid;

    private ItemSpecs itemSpecs;

    public SocketProperties getSocketProperties() {
        return socketProperties;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemSpecs getItemSpecs() {
        return itemSpecs;
    }

    public void setItemSpecs(ItemSpecs itemSpecs) {
        this.itemSpecs = itemSpecs;
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
        this.setObfuscated(SocketFactory.socketObfuscated(this.getItemSpecs()));
        socketProperties.add(Constants.UUID, this.uuid.toString());
        return true;
    }


}
