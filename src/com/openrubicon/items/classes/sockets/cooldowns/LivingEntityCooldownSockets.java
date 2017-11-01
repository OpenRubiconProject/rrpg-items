package com.openrubicon.items.classes.sockets.cooldowns;

import com.openrubicon.items.classes.sockets.CooldownSocket;

import java.util.LinkedList;
import java.util.UUID;

public class LivingEntityCooldownSockets {

    private LinkedList<CooldownSocket> sockets = new LinkedList<>();

    public LinkedList<CooldownSocket> getSockets() {
        return sockets;
    }

    public boolean containsSocket(UUID uuid)
    {
        String uuidStr = uuid.toString();
        for(CooldownSocket socket : this.getSockets())
        {
            if(uuidStr.equals(socket.getUuid().toString()))
                return true;
        }
        return false;
    }
}
