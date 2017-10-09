package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;

import java.util.HashSet;

public class Jarvis extends Socket {

    @Override
    public String getKey() {
        return "jarvis";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HELMETS;
    }

    @Override
    public String getName() {
        return "Jarvis";
    }

    @Override
    public String getDescription() {
        return "Alerts you of your chance of survival when taking on a target";
    }
}
