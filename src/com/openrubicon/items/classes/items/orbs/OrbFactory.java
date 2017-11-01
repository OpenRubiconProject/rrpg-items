package com.openrubicon.items.classes.items.orbs;

import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.orbs.types.Orb;

public class OrbFactory {
    public static Orb make(String key)
    {
        return OrbFactory.keyToSocket(key);
    }

    public static Orb random()
    {
        int socketCount = RRPGCore.services.getSerivce(OrbProvider.class).getOrbs().size();
        if(socketCount < 1)
            return null;
        int choice = Helpers.rng.nextInt(socketCount);
        return RRPGCore.services.getSerivce(OrbProvider.class).get(choice);
    }

    public static Orb keyToSocket(String key)
    {
        if(RRPGCore.services.getSerivce(OrbProvider.class).getOrbs().containsKey(key))
            return Helpers.cloner.deepClone(RRPGCore.services.getSerivce(OrbProvider.class).getOrbs().get(key));

        return null;
    }

}
