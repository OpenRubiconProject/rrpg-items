package com.openrubicon.items.classes.sockets.events;

import com.openrubicon.core.api.actionbar.ActionBarManager;
import com.openrubicon.core.api.actionbar.ActionBarMessage;
import com.openrubicon.core.api.enums.Priority;
import com.openrubicon.core.api.events.enums.EventType;
import com.openrubicon.core.api.inventory.entities.PlayerInventory;
import com.openrubicon.core.events.PlayerLandOnGroundEvent;
import com.openrubicon.core.events.PlayerLookingAtEntityEvent;
import com.openrubicon.core.events.PlayerMovedLocationEvent;
import com.openrubicon.core.events.PlayerStandingStillEvent;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.durability.PlayerDurability;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.SocketEvent;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SocketActionEventListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e)
    {
        SocketEvent.handlePlayer(e.getPlayer(), e);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.getHand() == EquipmentSlot.OFF_HAND && e.getClickedBlock() != null)
        {
            return;
        }


        if(e.getHand() == EquipmentSlot.OFF_HAND && e.getClickedBlock() == null && e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR)
        {
            return;
        }

        SocketEvent.handlePlayer(e.getPlayer(), e);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e)
    {
        SocketEvent.handleLivingEntity(e.getEntity(), e);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e)
    {
        if(!e.getEntity().hasMetadata("BlockDamage"))
            return;

        e.blockList().clear();

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity)e.getEntity();
            SocketEvent.handleLivingEntity(entity, e);
        }

    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e)
    {
        if(e.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING)
        {
            if(e.getIgnitingEntity() instanceof LightningStrike)
            {
                if(e.getIgnitingEntity().hasMetadata("DamageMod"))
                {
                    e.setCancelled(true);
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof LightningStrike)
        {
            if(e.getDamager().hasMetadata("DamageMod"))
            {
                float damage = e.getDamager().getMetadata("DamageMod").get(0).asFloat();
                e.setDamage(damage);
            }
        }

        if(e.getDamager() instanceof Player)
        {
            Player p = (Player)e.getDamager();
            ItemStack i = p.getInventory().getItemInMainHand();

            if (i == null)
                return;

            UniqueItem item = new UniqueItem(i);

            if(item.isSpecialItem() && item.isRightItemType())
            {
                if(item.getItemSpecs().getDurabilityCurrent() <= 0)
                {
                    e.setCancelled(true);
                    return;
                }
            }

        }

        if(e.getEntity() instanceof Player)
        {
            Player p = (Player)e.getEntity();
            SocketEvent.handlePlayer(p, e);
        }

        if (e.getDamager() instanceof Player)
        {
            Player p = (Player) e.getDamager();

            SocketEvent.handlePlayer(p, e);
        }

    }

    @EventHandler
    public void onPlayerLandOnGround(PlayerLandOnGroundEvent e)
    {
        SocketEvent.handleLivingEntity(e.getPlayer(), e);
    }

    @EventHandler
    public void onPlayerMovedLocation(PlayerMovedLocationEvent e)
    {
        SocketEvent.handleLivingEntity(e.getPlayer(), e);
    }

    @EventHandler
    public void onPlayerStandingStill(PlayerStandingStillEvent e)
    {
        SocketEvent.handleLivingEntity(e.getPlayer(), e);
    }

    @EventHandler
    public void onPlayerLookingAtEntity(PlayerLookingAtEntityEvent e)
    {
        SocketEvent.handleLivingEntity(e.getPlayer(), e);
    }

    @EventHandler
    public void onPlayerItemBreakEvent(PlayerItemBreakEvent e)
    {

    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e)
    {
        ItemStack i = e.getPlayer().getInventory().getItemInMainHand();

        if (i == null)
            return;

        if(!MaterialGroups.GENERATABLE.contains(i.getType()))
            return;

        Durability durability = new Durability(i);

        if(!durability.hasDurability(1))
        {
            ActionBarMessage actionBarMessage = new ActionBarMessage(Constants.RED + Constants.BOLD + "Your Tool Doesn't Have Enough Durability", Priority.HIGH, 40);
            ActionBarManager.interrupt(e.getPlayer(), actionBarMessage);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        ItemStack i = e.getPlayer().getInventory().getItemInMainHand();

        if (i == null)
            return;

        UniqueItem item = new UniqueItem(i);

        if(!item.isSpecialItem() && !item.isRightItemType())
            return;

        if(item.getItemSpecs().getDurabilityCurrent() <= 0)
        {
            e.setCancelled(true);
            return;
        }

        /*Block b = e.getBlock();

        if (b.getType() != Material.POTATO)
            return;

        Crops c = (Crops) (b.getState().getData());

        if (c.getState() != CropState.RIPE)
            return;

        b.setType(Material.AIR);

        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.POTATO_ITEM, 2));

        if ((Helpers.rng.nextInt(Configuration.SOCKET_ORB_SPAWN_CHANCE) + 1) == 1)
        {
            FullOrb orb = new FullOrb();

            orb.generate(Orb.OrbType.SOCKET);

            b.getWorld().dropItemNaturally(b.getLocation(), orb.getItem());

            e.getPlayer().sendMessage(Helpers.colorize("&5An Epic Potato Has Spawned!"));
        }*/
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent e)
    {
        ItemStack i = e.getItem();

        if (i == null)
            return;

        PlayerDurability playerDurability = new PlayerDurability(e.getPlayer(), i, e.getDamage());

        playerDurability.handle();

        if(playerDurability.isCancel())
        {
            e.setCancelled(true);
            return;
        }

        UniqueItem item = new UniqueItem(i);

        PlayerInventory inventory = new PlayerInventory(e.getPlayer());

        if(item.isSpecialItem())
            SocketEvent.handler(item, e, inventory.getItemSlotType(i));

        if(playerDurability.isBroken())
        {
            playerDurability.breakItem();
        }
    }



}
