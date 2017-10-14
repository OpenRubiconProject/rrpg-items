package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.enums.EventType;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.events.PlayerMovedLocationEvent;
import com.openrubicon.core.events.PlayerStandingStillEvent;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.items.interfaces.Socketable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class SocketEvent {

    public static void handlePlayer(Player player, EventType type, Event e)
    {
        SpecialItem mainhand = new SpecialItem(player.getInventory().getItemInMainHand());
        SpecialItem offhand = new SpecialItem(player.getInventory().getItemInOffHand());
        SpecialItem helmet = new SpecialItem(player.getInventory().getHelmet());
        SpecialItem chestplate = new SpecialItem(player.getInventory().getChestplate());
        SpecialItem leggings = new SpecialItem(player.getInventory().getLeggings());
        SpecialItem boots = new SpecialItem(player.getInventory().getBoots());

        if(offhand.isValid() && offhand.isSpecialItem())
            SocketEvent.handler(offhand, type, e, EntityInventorySlotType.OFFHAND);

        if(mainhand.isValid() && mainhand.isSpecialItem())
            SocketEvent.handler(mainhand, type, e, EntityInventorySlotType.MAINHAND);

        if(helmet.isValid() && helmet.isSpecialItem())
            SocketEvent.handler(helmet, type, e, EntityInventorySlotType.HELMET);

        if(chestplate.isValid() && chestplate.isSpecialItem())
            SocketEvent.handler(chestplate, type, e, EntityInventorySlotType.CHESTPLATE);

        if(leggings.isValid() && leggings.isSpecialItem())
            SocketEvent.handler(leggings, type, e, EntityInventorySlotType.LEGGINGS);

        if(boots.isValid() && boots.isSpecialItem())
            SocketEvent.handler(boots, type, e, EntityInventorySlotType.BOOTS);

    }

    public static void handleLivingEntity(LivingEntity entity, EventType type, Event e)
    {
        SpecialItem mainhand = new SpecialItem(entity.getEquipment().getItemInMainHand());
        SpecialItem offhand = new SpecialItem(entity.getEquipment().getItemInOffHand());
        SpecialItem helmet = new SpecialItem(entity.getEquipment().getHelmet());
        SpecialItem chestplate = new SpecialItem(entity.getEquipment().getChestplate());
        SpecialItem leggings = new SpecialItem(entity.getEquipment().getLeggings());
        SpecialItem boots = new SpecialItem(entity.getEquipment().getBoots());

        if(offhand.isValid() && offhand.isSpecialItem())
            SocketEvent.handler(offhand, type, e, EntityInventorySlotType.OFFHAND);

        if(mainhand.isValid() && mainhand.isSpecialItem())
            SocketEvent.handler(mainhand, type, e, EntityInventorySlotType.MAINHAND);

        if(helmet.isValid() && helmet.isSpecialItem())
            SocketEvent.handler(helmet, type, e, EntityInventorySlotType.HELMET);

        if(chestplate.isValid() && chestplate.isSpecialItem())
            SocketEvent.handler(chestplate, type, e, EntityInventorySlotType.CHESTPLATE);

        if(leggings.isValid() && leggings.isSpecialItem())
            SocketEvent.handler(leggings, type, e, EntityInventorySlotType.LEGGINGS);

        if(boots.isValid() && boots.isSpecialItem())
            SocketEvent.handler(boots, type, e, EntityInventorySlotType.BOOTS);

    }

    public static void handler(SpecialItem item, EventType type, Event e, EntityInventorySlotType slot)
    {
        if(!item.isSpecialItem() || !item.isValid() || !(item instanceof Socketable))
            return;

        for(Socket socket : ((Socketable) item).getSocketHandler().getSockets().values())
        {
            if(socket.isObfuscated())
                continue;

            switch(type)
            {
                case PLAYER_INTERACT:
                    socket.onPlayerInteract((PlayerInteractEvent) e, item, slot);
                    break;
                case ENTITY_DAMAGE_BY_ENTITY:
                    socket.onEntityDamageByEntity((EntityDamageByEntityEvent) e, item, slot);
                    break;
                case PLAYER_ITEM_DAMAGE:
                    socket.onPlayerItemDamage((PlayerItemDamageEvent) e, item, slot);
                    break;
                case PLAYER_LAND_ON_GROUND:
                    socket.onPlayerLandOnGround((PlayerLandOnGroundEvent) e, item, slot);
                    break;
                case PLAYER_MOVED_LOCATION:
                    socket.onPlayerMovedLocation((PlayerMovedLocationEvent) e, item, slot);
                    break;
                case PLAYER_STANDING_STILL:
                    socket.onPlayerStandingStill((PlayerStandingStillEvent) e, item, slot);
                    break;
                case ENTITY_DEATH:
                    socket.onEntityDeath((EntityDeathEvent) e, item, slot);
                    break;
                case ENTITY_DAMAGE:
                    socket.onEntityDamage((EntityDamageEvent) e, item, slot);
                    break;
                case PLAYER_INTERACT_ENTITY:
                    socket.onPlayerInteractEntity((PlayerInteractEntityEvent) e, item, slot);
                    break;
            }
        }


    }

}
