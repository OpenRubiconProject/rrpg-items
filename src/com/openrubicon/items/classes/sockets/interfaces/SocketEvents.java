package com.openrubicon.items.classes.sockets.interfaces;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.events.PlayerLookingAtEntityEvent;
import com.openrubicon.core.events.PlayerMovedLocationEvent;
import com.openrubicon.core.events.PlayerStandingStillEvent;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.events.PrepareSocketCooldownEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public interface SocketEvents {
    default void onPlayerInteract(PlayerInteractEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onEntityDamageByEntity(EntityDamageByEntityEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPlayerLandOnGround(PlayerLandOnGroundEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPlayerItemDamage(PlayerItemDamageEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPlayerMovedLocation(PlayerMovedLocationEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPlayerStandingStill(PlayerStandingStillEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onEntityDeath(EntityDeathEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onEntityDamage(EntityDamageEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPlayerInteractEntity(PlayerInteractEntityEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPlayerLookingAtEntity(PlayerLookingAtEntityEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPrepareSocketCooldown(PrepareSocketCooldownEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onBlockBreak(BlockBreakEvent e, UniqueItem item, EntityInventorySlotType slot) {}
}
