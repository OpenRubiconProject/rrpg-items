package com.openrubicon.items.classes.sockets.cooldowns;

import com.openrubicon.core.api.cooldowns.CooldownManager;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

public class SocketCooldownManager extends CooldownManager {

    private static HashMap<LivingEntity, LivingEntityCooldownSockets> entitiesSocketCooldowns = new HashMap<>();

    public void setCdr(SocketCooldown cooldown, int cdrPercent)
    {
        cooldown.setCooldownReduction(cdrPercent);
    }

    public static HashMap<LivingEntity, LivingEntityCooldownSockets> getEntitiesSocketCooldowns() {
        return entitiesSocketCooldowns;
    }

}
