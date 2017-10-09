package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.cooldowns.interfaces.Cooldownable;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldown;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldownManager;

abstract public class CooldownSocket extends Socket implements Cooldownable {

    private SocketCooldown cooldown;

    public SocketCooldown getCooldown() {
        return cooldown;
    }

    @Override
    public boolean save() {
        this.getSocketProperties().add(Constants.COOLDOWN, String.valueOf(this.cooldown.getLength()));
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();
        this.cooldown = new SocketCooldown(this.getUuid().toString(), this.getKey());
        this.cooldown.setLength(Integer.parseInt(this.getSocketProperties().get(Constants.COOLDOWN)));
        SocketCooldownManager.add(this.cooldown);
        return true;
    }

    @Override
    public boolean generate() {
        super.generate();
        this.cooldown = new SocketCooldown(this.getUuid().toString(), this.getKey());
        this.cooldown.setLength(this.getCooldownLengthTicks());
        SocketCooldownManager.add(this.cooldown);
        return true;
    }

    public void startCooldown()
    {
        if(this.cooldown.isOnCooldown())
            SocketCooldownManager.start(this.cooldown);
    }

    public boolean isOnCooldown()
    {
        return this.cooldown.isOnCooldown();
    }

}
