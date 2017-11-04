package com.openrubicon.items.classes.sockets.cooldowns;

import com.openrubicon.core.api.cooldowns.CooldownManager;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.CooldownSocket;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

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

    public static void removeCooldownFromLivingEntity(LivingEntity livingEntity, CooldownSocket socket)
    {
        if(entitiesSocketCooldowns.get(livingEntity) == null)
            return;

        String uuidStr = socket.getUuid().toString();
        for(CooldownSocket sockets : entitiesSocketCooldowns.get(livingEntity).getSockets())
        {
            if(uuidStr.equals(sockets.getUuid().toString()))
                entitiesSocketCooldowns.get(livingEntity).getSockets().remove(sockets);
        }

        //if(entitiesSocketCooldowns.get(livingEntity).getSockets().contains(socket))
        //    Bukkit.broadcastMessage("wtf");
    }

    public static void removeCooldownFromLivingEntity(LivingEntity livingEntity, ItemStack item)
    {
        UniqueItem uniqueItem = new UniqueItem(item);

        if(!uniqueItem.isValid() || !uniqueItem.isSpecialItem() || !uniqueItem.isRightItemType())
            return;

        for(Socket socket : uniqueItem.getSocketHandler().getSockets().values())
        {
            if(socket instanceof CooldownSocket)
            {
                removeCooldownFromLivingEntity(livingEntity, (CooldownSocket)socket);
            }
        }

    }

    public static void addCooldownToLivingEntity(LivingEntity livingEntity, CooldownSocket socket)
    {
        if(!SocketCooldownManager.getEntitiesSocketCooldowns().containsKey(livingEntity))
        {
            SocketCooldownManager.getEntitiesSocketCooldowns().put(livingEntity, new LivingEntityCooldownSockets());
        }

        if(!SocketCooldownManager.getEntitiesSocketCooldowns().get(livingEntity).containsSocket(socket.getUuid()))
        {
            SocketCooldownManager.getEntitiesSocketCooldowns().get(livingEntity).getSockets().add(socket);
        }

        //entitiesSocketCooldowns.get(livingEntity).getSockets().add(socket);
    }

    public static void addCooldownToLivingEntity(LivingEntity livingEntity, ItemStack item)
    {
        UniqueItem uniqueItem = new UniqueItem(item);

        if(!uniqueItem.isValid() || !uniqueItem.isSpecialItem() || !uniqueItem.isRightItemType())
            return;

        for(Socket socket : uniqueItem.getSocketHandler().getSockets().values())
        {
            if(socket instanceof CooldownSocket)
            {
                addCooldownToLivingEntity(livingEntity, (CooldownSocket)socket);
            }
        }
    }

}
