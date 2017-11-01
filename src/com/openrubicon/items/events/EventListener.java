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
    public void onCreatureSpawn(CreatureSpawnEvent e)
    {
        // Generate a random int between 0 and n
        // digit 1: main hand spawn 1
        // digit 2: helmet 2
        // digit 3: chest 4
        // digit 4: legs 8
        // digit 5: boots 16
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL)
        {
            int itemChance = 0;

            if(e.getEntity() instanceof Spider || e.getEntity() instanceof Creeper || e.getEntity() instanceof Enderman || e.getEntity() instanceof Silverfish || e.getEntity() instanceof WitherSkeleton)
                itemChance = 1;

            if(e.getEntity() instanceof Skeleton || e.getEntity() instanceof Zombie || e.getEntity() instanceof PigZombie || e.getEntity() instanceof Husk)
                itemChance = 31;

            if(itemChance == 0)
                return;

            int chanceChoice = Helpers.rng.nextInt(itemChance + 1);

            if((chanceChoice & 1) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.HAND_HELD.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.HAND_HELD);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setItemInMainHand(item.getItem());
            }

            if((chanceChoice & 2) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.HELMETS.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.HELMETS);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setHelmet(item.getItem());
            }

            if((chanceChoice & 4) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.CHESTPLATES.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.CHESTPLATES);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setChestplate(item.getItem());
            }

            if((chanceChoice & 8) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.LEGGINGS.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.LEGGINGS);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setLeggings(item.getItem());
            }

            if((chanceChoice & 16) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.BOOTS.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.BOOTS);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setBoots(item.getItem());
            }

        }
    }
}
