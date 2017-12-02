package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.events.PlayerLookingAtEntityEvent;
import com.openrubicon.core.events.PlayerMovedLocationEvent;
import com.openrubicon.core.events.PlayerStandingStillEvent;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.events.PrepareSocketCooldownEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SocketEvent {

    public static void handlePlayer(Player player, Event e)
    {
        UniqueItem mainhand = new UniqueItem(player.getInventory().getItemInMainHand());
        UniqueItem offhand = new UniqueItem(player.getInventory().getItemInOffHand());
        UniqueItem helmet = new UniqueItem(player.getInventory().getHelmet());
        UniqueItem chestplate = new UniqueItem(player.getInventory().getChestplate());
        UniqueItem leggings = new UniqueItem(player.getInventory().getLeggings());
        UniqueItem boots = new UniqueItem(player.getInventory().getBoots());

        if(offhand.isValid() && offhand.isSpecialItem())
            SocketEvent.handler(offhand, e, EntityInventorySlotType.OFFHAND);

        if(mainhand.isValid() && mainhand.isSpecialItem())
            SocketEvent.handler(mainhand, e, EntityInventorySlotType.MAINHAND);

        if(helmet.isValid() && helmet.isSpecialItem())
            SocketEvent.handler(helmet, e, EntityInventorySlotType.HELMET);

        if(chestplate.isValid() && chestplate.isSpecialItem())
            SocketEvent.handler(chestplate, e, EntityInventorySlotType.CHESTPLATE);

        if(leggings.isValid() && leggings.isSpecialItem())
            SocketEvent.handler(leggings, e, EntityInventorySlotType.LEGGINGS);

        if(boots.isValid() && boots.isSpecialItem())
            SocketEvent.handler(boots, e, EntityInventorySlotType.BOOTS);

    }

    public static void handleLivingEntity(LivingEntity entity, Event e)
    {
        UniqueItem mainhand = new UniqueItem(entity.getEquipment().getItemInMainHand());
        UniqueItem offhand = new UniqueItem(entity.getEquipment().getItemInOffHand());
        UniqueItem helmet = new UniqueItem(entity.getEquipment().getHelmet());
        UniqueItem chestplate = new UniqueItem(entity.getEquipment().getChestplate());
        UniqueItem leggings = new UniqueItem(entity.getEquipment().getLeggings());
        UniqueItem boots = new UniqueItem(entity.getEquipment().getBoots());

        if(offhand.isValid() && offhand.isSpecialItem())
            SocketEvent.handler(offhand, e, EntityInventorySlotType.OFFHAND);

        if(mainhand.isValid() && mainhand.isSpecialItem())
            SocketEvent.handler(mainhand, e, EntityInventorySlotType.MAINHAND);

        if(helmet.isValid() && helmet.isSpecialItem())
            SocketEvent.handler(helmet, e, EntityInventorySlotType.HELMET);

        if(chestplate.isValid() && chestplate.isSpecialItem())
            SocketEvent.handler(chestplate, e, EntityInventorySlotType.CHESTPLATE);

        if(leggings.isValid() && leggings.isSpecialItem())
            SocketEvent.handler(leggings, e, EntityInventorySlotType.LEGGINGS);

        if(boots.isValid() && boots.isSpecialItem())
            SocketEvent.handler(boots, e, EntityInventorySlotType.BOOTS);

    }

    public static void handler(UniqueItem item, Event e, EntityInventorySlotType slot)
    {
        if(!item.isSpecialItem() || !item.isValid() || !item.isRightItemType())
            return;

        Durability durability = new Durability(item.getItem());

        if(durability.isBroken())
            return;

        for(Socket socket : item.getSocketHandler().getSockets().values())
        {
            if(socket.isObfuscated())
                continue;

            /*if(e instanceof PlayerInteractEvent)
            {
                socket.onPlayerInteract((PlayerInteractEvent) e, item, slot);
                continue;
            }


            if(e instanceof EntityDamageByEntityEvent)
            {
                socket.onEntityDamageByEntity((EntityDamageByEntityEvent) e, item, slot);
                continue;
            }


            if(e instanceof PlayerItemDamageEvent)
            {
                socket.onPlayerItemDamage((PlayerItemDamageEvent) e, item, slot);
                continue;
            }


            if(e instanceof PlayerLandOnGroundEvent)
            {
                socket.onPlayerLandOnGround((PlayerLandOnGroundEvent) e, item, slot);
                continue;
            }


            if(e instanceof PlayerMovedLocationEvent)
            {
                socket.onPlayerMovedLocation((PlayerMovedLocationEvent) e, item, slot);
                continue;
            }


            if(e instanceof PlayerStandingStillEvent)
            {
                socket.onPlayerStandingStill((PlayerStandingStillEvent) e, item, slot);
                continue;
            }


            if(e instanceof EntityDeathEvent)
            {
                socket.onEntityDeath((EntityDeathEvent) e, item, slot);
                continue;
            }


            if(e instanceof EntityDamageEvent)
            {
                socket.onEntityDamage((EntityDamageEvent) e, item, slot);
                continue;
            }


            if(e instanceof PlayerInteractEntityEvent)
            {
                socket.onPlayerInteractEntity((PlayerInteractEntityEvent) e, item, slot);
                continue;
            }


            if(e instanceof PlayerLookingAtEntityEvent)
            {
                socket.onPlayerLookingAtEntity((PlayerLookingAtEntityEvent) e, item, slot);
                continue;
            }


            if(e instanceof PrepareSocketCooldownEvent)
            {
                socket.onPrepareSocketCooldown((PrepareSocketCooldownEvent) e, item, slot);
                continue;
            }*/

            String simpleName = e.getClass().getSimpleName();
            String methodName = "on" + simpleName.substring(0, simpleName.length() - 5);
            //Bukkit.broadcastMessage(methodName);

            /*Class[] parameters = new Class[3];
            parameters[0] = e.getClass();
            parameters[1] = item.getClass();
            parameters[2] = slot.getClass();*/

            try {
                Method method = socket.getClass().getMethod(methodName, e.getClass(), item.getClass(), slot.getClass());
                try {
                    method.invoke(socket, e, item, slot);
                    //Bukkit.broadcastMessage("success");
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e2) {
                    e2.printStackTrace();
                }
            } catch (NoSuchMethodException e1) {
                //e1.printStackTrace();
                //Bukkit.broadcastMessage("fallback");
                for (Method m : socket.getClass().getMethods()) {

                    Class<?>[] params = m.getParameterTypes();
                    if(params.length != 3)
                        continue;

                    if(!params[2].isInstance(slot))
                        continue;

                    if(!params[1].isInstance(item))
                        continue;

                    if(params[0].isInstance(e))
                    {
                        try {
                            m.invoke(socket, e, item, slot);
                        } catch (IllegalAccessException e2) {
                            e2.printStackTrace();
                        } catch (InvocationTargetException e2) {
                            e2.printStackTrace();
                        }

                        break;
                    }

                }

            }

        }

    }

}
