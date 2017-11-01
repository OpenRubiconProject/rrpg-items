package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import org.bukkit.Material;

import java.util.ArrayList;

public class SocketFactory {

    public static Socket make(String key)
    {
        return SocketFactory.keyToSocket(key);
    }

    public static Socket random()
    {
        int socketCount = SocketProvider.getSockets().size();
        if(socketCount < 1)
            return null;
        int choice = Helpers.rng.nextInt(socketCount);
        return RRPGCore.services.getSerivce(SocketProvider.class).get(choice);
    }

    public static Socket random(Material material)
    {
        ArrayList<Socket> validSockets = RRPGCore.services.getSerivce(SocketProvider.class).getValidSockets(material);
        int socketCount = validSockets.size();
        if(socketCount < 1)
            return null;
        int choice = Helpers.rng.nextInt(socketCount);
        return validSockets.get(choice);
    }

    public static Socket keyToSocket(String key)
    {
        if(SocketProvider.getSockets().containsKey(key))
            return Helpers.cloner.deepClone(SocketProvider.getSockets().get(key));

        return null;
    }

    public static boolean socketObfuscated(ItemSpecs specs)
    {
        int max = (int)(specs.getRarity() * 2 - specs.getSockets());
        if(max < 3)
            max = 3;
        int min = 1;

        int chance = Helpers.randomInt(min, max);

        if(chance == min)
            return true;

        return false;
    }

}
