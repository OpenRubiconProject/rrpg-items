package com.openrubicon.items.classes.sockets.events;

import com.openrubicon.core.api.cooldowns.Cooldown;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.entity.LivingEntity;

public class SocketCooldownStartedEvent extends PrepareSocketCooldownEvent {
    public SocketCooldownStartedEvent(Cooldown cooldown, UniqueItem uniqueItem, LivingEntity livingEntity) {
        super(cooldown, uniqueItem, livingEntity);
    }

    public SocketCooldownStartedEvent(Cooldown cooldown, UniqueItem uniqueItem) {
        super(cooldown, uniqueItem);
    }
}
