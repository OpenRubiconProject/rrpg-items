package com.openrubicon.items.events;

import com.openrubicon.core.api.actionbar.ActionBarManager;
import com.openrubicon.core.api.actionbar.ActionBarMessage;
import com.openrubicon.core.api.cooldowns.Cooldown;
import com.openrubicon.core.api.cooldowns.CooldownManager;
import com.openrubicon.core.events.FiveTickEvent;
import com.openrubicon.core.events.OneSecondEvent;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.CooldownSocket;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldown;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldownManager;
import com.openrubicon.items.classes.sockets.events.PrepareSocketCooldownEvent;
import com.openrubicon.items.classes.sockets.events.SocketCooldownStartedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EventListener implements Listener {

    @EventHandler
    public void onFiveTick(FiveTickEvent e)
    {
        /*for(Player p : Bukkit.getOnlinePlayers())
        {
            ActionBarManager.queueMessage(p, new ActionBarMessage("test", 5));
        }*/
    }

    @EventHandler
    public void onSocketCooldownStarted(SocketCooldownStartedEvent e)
    {
        if(e.getLivingEntity() instanceof Player){

            Player p = (Player)e.getLivingEntity();

            String message = "";
            for(CooldownSocket socket : SocketCooldownManager.getEntitiesSocketCooldowns().get(p).getSockets())
            {

                if(socket.getCooldown().isOnCooldown())
                {
                    message += Constants.BOLD + Constants.HEADING_COLOR + socket.getName() + ": " + Constants.RESET_FORMAT + Constants.RED + socket.getCooldown().getCooldownSeconds() + "s ";
                }
            }

            ActionBarManager.interrupt(p, new ActionBarMessage(message, 20));
        }

    }

    @EventHandler
    public void onOneSecond(OneSecondEvent e)
    {

        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(SocketCooldownManager.getEntitiesSocketCooldowns().get(p) == null)
                continue;

            //Bukkit.broadcastMessage("wtfman?");
            //if(SocketCooldownManager.getEntitiesSocketCooldowns().get(p) == null)
            //    Bukkit.broadcastMessage("problem");
            //String message = SocketCooldownManager.getEntitiesSocketCooldowns().get(p).getSockets().size()+"";
            //ActionBarManager.queueMessage(p, new ActionBarMessage(message, 10));
            String message = "";
            //Bukkit.broadcastMessage(SocketCooldownManager.getEntitySocketCooldowns(p).size()+"");
            //Bukkit.broadcastMessage(SocketCooldownManager.getEntitiesSocketCooldowns().get(p).getSockets().size()+"");
            for(CooldownSocket socket : SocketCooldownManager.getEntitiesSocketCooldowns().get(p).getSockets())
            {

                if(socket.getCooldown().isOnCooldown())
                {
                    message += Constants.BOLD + Constants.HEADING_COLOR + socket.getName() + ": " + Constants.RESET_FORMAT + Constants.RED + socket.getCooldown().getCooldownSeconds() + "s ";
                }
            }

            ActionBarManager.interrupt(p, new ActionBarMessage(message, 20));
        }
    }

    @EventHandler
    public void onPlayerItemBreakEvent(PlayerItemBreakEvent e)
    {
        SocketCooldownManager.removeCooldownFromLivingEntity(e.getPlayer(), e.getBrokenItem());
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        SocketCooldownManager.removeCooldownFromLivingEntity(e.getPlayer(), e.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        for (ItemStack item : e.getDrops()) {
            SocketCooldownManager.removeCooldownFromLivingEntity(e.getEntity(), item);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e)
    {
        SocketCooldownManager.addCooldownToLivingEntity(e.getPlayer(), e.getItem().getItemStack());
    }

}
