package com.openrubicon.items.classes.items.orbs;

import com.openrubicon.core.api.services.interfaces.Service;
import com.openrubicon.items.classes.items.orbs.types.Orb;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class OrbProvider implements Service {

    protected static LinkedHashMap<String, Orb> orbs = new LinkedHashMap<>();

    public LinkedHashMap<String, Orb> getOrbs()
    {
        return OrbProvider.orbs;
    }

    public void add(Orb orb)
    {
        if(!OrbProvider.orbs.containsKey(orb.getKey()))
        {
            OrbProvider.orbs.put(orb.getKey(), orb);
        } else {
            Bukkit.getLogger().info("Failed to inject Socket " + orb.getName() + " because key " + orb.getKey() + " already exists.");
        }
    }

    public void addAll(HashSet<Orb> orbs)
    {
        for(Orb orb : orbs)
        {
            this.add(orb);
        }
    }

    public void remove(Orb orb)
    {
        if(this.getOrbs().containsKey(orb.getKey()))
            this.getOrbs().remove(orb.getKey());
    }

    public Orb get(int index)
    {
        if((index + 1) > this.getOrbs().size())
            return null;

        ArrayList<Orb> orbs = new ArrayList<>();
        orbs.addAll(this.getOrbs().values());

        return orbs.get(index);
    }

    public void clear()
    {
        OrbProvider.orbs.clear();
    }

    public ArrayList<Orb> getValidSockets()
    {
        ArrayList<Orb> validSockets = new ArrayList<>();
        validSockets.addAll(orbs.values());
        return validSockets;
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> observation = new ArrayList<>();
        for(Orb orb : this.getOrbs().values())
        {
            observation.add(orb.getName());
        }
        return observation;
    }
}
