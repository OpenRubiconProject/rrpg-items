package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;

import java.util.HashMap;

public class Jarvis extends Socket {
    //public float fuse = 40f;

    public Jarvis() {
        super();
        this.name = "Jarvis";
        this.key = "jarvis";
        this.description = "Alerts you of your chance of survival when taking on a target.";
        this.materials.addAll(MaterialGroups.HELMETS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        return true;
    }

    @Override
    public String save()
    {
        return this.getDefaultSaveString();
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);
        return true;
    }

}
