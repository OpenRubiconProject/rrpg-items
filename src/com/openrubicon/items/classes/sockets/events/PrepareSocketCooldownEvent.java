package com.openrubicon.items.classes.sockets.events;

import com.openrubicon.core.api.cooldowns.Cooldown;
import com.openrubicon.core.api.cooldowns.events.PrepareCooldownEvent;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.entity.LivingEntity;

public class PrepareSocketCooldownEvent extends PrepareCooldownEvent {

    UniqueItem uniqueItem;
    LivingEntity livingEntity;

    public PrepareSocketCooldownEvent(Cooldown cooldown, UniqueItem uniqueItem, LivingEntity livingEntity) {
        super(cooldown);
        this.uniqueItem = uniqueItem;
        this.livingEntity = livingEntity;
    }

    public PrepareSocketCooldownEvent(Cooldown cooldown, UniqueItem uniqueItem) {
        super(cooldown);
        this.uniqueItem = uniqueItem;
    }

    public UniqueItem getUniqueItem() {
        return uniqueItem;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }
}
