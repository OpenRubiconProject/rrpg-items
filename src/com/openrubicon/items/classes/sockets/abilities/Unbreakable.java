package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.PlayerInventory;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class Unbreakable extends Socket {

    @Override
    public String getKey() {
        return "unbreakable";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.addAll(MaterialGroups.TOOLS);
        materials.addAll(MaterialGroups.ARMOR);
        return materials;
    }

    @Override
    public String getName() {
        return "Unbreakable";
    }

    @Override
    public String getDescription() {
        return "When your item runs out of durability, it will no longer break. Instead, it will stick around in your inventory, unusable, until you repair it.";
    }

    @Override
    public void onPlayerItemDamage(PlayerItemDamageEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        /*if(!item.isSpecialItem())
            return;

        int itemDurability = (int) Helpers.scale(0, 0, item.getItemSpecs().getDurabilityMax(), 0, item.getItem().getType().getMaxDurability());
        int adjustedDurability = item.getItem().getType().getMaxDurability() - itemDurability;

        item.getItemSpecs().setDurabilityCurrent(-1);

        item.save();

        ItemStack i = item.getItem();

        i.setDurability((short)adjustedDurability);

        PlayerInventory inventory = new PlayerInventory(e.getPlayer());
        inventory.setSlotItem(slot, i);*/

        e.setCancelled(true);
    }

}
