package com.openrubicon.items.classes.items.orbs;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.orbs.types.Orb;

public class OrbFactory {
    public static Orb make(String key)
    {
        return OrbFactory.keyToSocket(key);
    }

    public static Orb random()
    {
        int socketCount = OrbProvider.getOrbs().size();
        if(socketCount < 1)
            return null;
        int choice = Helpers.rng.nextInt(socketCount);
        return OrbProvider.get(choice);
    }

    public static Orb keyToSocket(String key)
    {
        if(OrbProvider.getOrbs().containsKey(key))
            return Helpers.cloner.deepClone(OrbProvider.getOrbs().get(key));

        return null;
    }

}
