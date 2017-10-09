package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Unbreakable extends Socket {

    public Unbreakable() {
        super();
        this.name = "Unbreakable";
        this.key = "unbreakable";
        this.description = "When your item runs out of durability, it will no longer break. Instead, it will stick around in your inventory, unusable, until you repair it.";
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString();
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        return true;
    }

    @Override
    public void onPlayerItemDamage(PlayerItemDamageEvent e, FullItem item, Inventory.SlotType slot)
    {
        if(!item.isSpecialItem())
            return;

        int itemDurability = (int) Helpers.scale(0, 0, item.getStats().getItem().getMaxDurability(), 0, item.getItem().getType().getMaxDurability());
        int adjustedDurability = item.getItem().getType().getMaxDurability() - itemDurability;

        item.getStats().getItem().setCurrentDurability(-1);

        item.save();

        ItemStack i = item.getItem();

        i.setDurability((short)adjustedDurability);

        PlayerInventory inventory = new PlayerInventory(e.getPlayer());
        inventory.setSlot(slot, i);

        e.setCancelled(true);
    }

}
