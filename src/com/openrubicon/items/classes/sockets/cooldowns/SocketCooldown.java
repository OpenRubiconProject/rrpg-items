package com.openrubicon.items.classes.sockets.cooldowns;

import com.openrubicon.core.api.cooldowns.Cooldown;

public class SocketCooldown extends Cooldown {
    public SocketCooldown(String id, String modifier) {
        super(id, modifier);
        this.setModuleName("socket");
        this.setId(id, modifier);
    }
}
