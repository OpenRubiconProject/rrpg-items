package com.openrubicon.items.classes.items.orbs;

import com.openrubicon.items.classes.items.orbs.types.Orb;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class OrbProvider {

    protected static LinkedHashMap<String, Orb> orbs = new LinkedHashMap<>();

    public static LinkedHashMap<String, Orb> getOrbs()
    {
        return OrbProvider.orbs;
    }

    public static void add(Orb orb)
    {
        if(!OrbProvider.orbs.containsKey(orb.getKey()))
        {
            OrbProvider.orbs.put(orb.getKey(), orb);
        } else {
            Bukkit.getLogger().info("Failed to inject Socket " + orb.getName() + " because key " + orb.getKey() + " already exists.");
        }
    }

    public static void addAll(HashSet<Orb> orbs)
    {
        for(Orb orb : orbs)
        {
            OrbProvider.add(orb);
        }
    }

    public static void remove(Orb orb)
    {
        if(OrbProvider.getOrbs().containsKey(orb.getKey()))
            OrbProvider.getOrbs().remove(orb.getKey());
    }

    public static Orb get(int index)
    {
        if((index + 1) > OrbProvider.getOrbs().size())
            return null;

        ArrayList<Orb> orbs = new ArrayList<>();
        orbs.addAll(OrbProvider.getOrbs().values());

        return orbs.get(index);
    }

    public static void clear()
    {
        OrbProvider.orbs.clear();
    }

    public static ArrayList<Orb> getValidSockets()
    {
        ArrayList<Orb> validSockets = new ArrayList<>();
        validSockets.addAll(orbs.values());
        return validSockets;
    }
}
