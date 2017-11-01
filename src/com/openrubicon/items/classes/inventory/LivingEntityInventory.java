package com.openrubicon.items.classes.inventory;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class LivingEntityInventory extends com.openrubicon.core.api.inventory.entities.LivingEntityInventory {
    public LivingEntityInventory(LivingEntity entity) {
        super(entity);
    }

    public ArrayList<UniqueItem> getArmorWithSocket(Socket socket)
    {
        ArrayList<UniqueItem> items = new ArrayList<>();

        UniqueItem item = new UniqueItem(this.getItemInSlot(EntityInventorySlotType.HELMET));
        if(item.isSpecialItem() && item.getSocketHandler().has(socket))
            items.add(item);

        item = new UniqueItem(this.getItemInSlot(EntityInventorySlotType.CHESTPLATE));
        if(item.isSpecialItem() && item.getSocketHandler().has(socket))
            items.add(item);

        item = new UniqueItem(this.getItemInSlot(EntityInventorySlotType.LEGGINGS));
        if(item.isSpecialItem() && item.getSocketHandler().has(socket))
            items.add(item);

        item = new UniqueItem(this.getItemInSlot(EntityInventorySlotType.BOOTS));
        if(item.isSpecialItem() && item.getSocketHandler().has(socket))
            items.add(item);

        return items;
    }

    public boolean isLivingEntityWearingSocket(Socket socket)
    {
        return this.getArmorWithSocket(socket).size() > 0;
    }
}
