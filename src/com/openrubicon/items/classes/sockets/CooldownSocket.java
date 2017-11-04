package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.actionbar.ActionBarManager;
import com.openrubicon.core.api.actionbar.ActionBarMessage;
import com.openrubicon.core.api.cooldowns.interfaces.Cooldownable;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.cooldowns.LivingEntityCooldownSockets;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldown;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldownManager;
import com.openrubicon.items.classes.sockets.events.PrepareSocketCooldownEvent;
import com.openrubicon.items.classes.sockets.events.SocketCooldownStartedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
        if(SocketCooldownManager.has(new SocketCooldown(this.getUuid().toString(), this.getKey())))
        {
            this.cooldown = (SocketCooldown)SocketCooldownManager.get(new SocketCooldown(this.getUuid().toString(), this.getKey()).getId());
        } else {
            this.cooldown = new SocketCooldown(this.getUuid().toString(), this.getKey());
            this.cooldown.setLength(Integer.parseInt(this.getSocketProperties().get(Constants.COOLDOWN)));
            SocketCooldownManager.add(this.cooldown);
        }
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
        if(!this.cooldown.isOnCooldown())
        {
            SocketCooldownManager.start(this.cooldown);
        }

    }

    public void startCooldown(LivingEntity entity, UniqueItem item)
    {
        if(this.cooldown.isOnCooldown())
           return;

        /*LivingEntityInventory inventory = new LivingEntityInventory(entity);

        int cdr = 0;
        for(UniqueItem uniqueItem : inventory.getArmorWithSocket(new CooldownReduction()))
        {
            CooldownReduction socket = (CooldownReduction)item.getSocketHandler().get(new CooldownReduction());
            cdr += socket.getCdr();
        }

        if(item.isSpecialItem() && item.isValid() && item.isRightItemType())
        {
            if(item.getSocketHandler().has(new Spam()))
            {
                Spam socket = (Spam)item.getSocketHandler().get(new Spam());
                cdr += socket.getCdr();
            }
        }

        this.cooldown.setCooldownReduction(cdr);*/

        //Bukkit.broadcastMessage(entity.getName());

        SocketCooldownManager.addCooldownToLivingEntity(entity, this);

        //Bukkit.broadcastMessage(SocketCooldownManager.getEntitiesSocketCooldowns().get(entity).getSockets().size()+"");

        Bukkit.getPluginManager().callEvent(new PrepareSocketCooldownEvent(cooldown, item, entity));

        this.startCooldown();

        Bukkit.getPluginManager().callEvent(new SocketCooldownStartedEvent(cooldown, item, entity));

    }

    public boolean isOnCooldown()
    {
        return this.cooldown.isOnCooldown();
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        String loreLine = Constants.PRIMARY_COLOR+this.getName();

        if(this.isOnCooldown())
        {
            loreLine += Constants.PRIMARY_COLOR + " (" + Constants.RED + this.cooldown.getCurrent() / 20 + Constants.PRIMARY_COLOR + ")";
        } else {
            loreLine += " (" + this.cooldown.getCooldownLengthSeconds() + ")";
        }


        lore.add(Helpers.colorize(loreLine));
        return lore;
    }
}
