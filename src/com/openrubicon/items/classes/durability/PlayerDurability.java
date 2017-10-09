package com.openrubicon.items.classes.durability;

import com.openrubicon.core.api.inventory.PlayerInventory;
import com.openrubicon.core.api.inventory.enums.InventorySlotType;
import com.openrubicon.items.classes.durability.enums.DurabilityStatus;
import com.openrubicon.items.classes.items.SpecialItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerDurability {

    private Player player;
    private ItemStack i;
    private int damage;
    private boolean broken = false;
    private boolean cancel = false;

    public PlayerDurability(Player player, ItemStack i, int damage) {
        this.player = player;
        this.i = i;
        this.damage = damage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ItemStack getItem() {
        return i;
    }

    public void setItem(ItemStack i) {
        this.i = i;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public void handle()
    {
        SpecialItem item = new SpecialItem(i);

        if(!item.isSpecialItem())
            return;

        PlayerInventory inventory = new PlayerInventory(this.player);

        InventorySlotType slot = inventory.getItemSlotType(this.getItem());

        Durability durability = new Durability(this.i);
        durability.adjustDurability(this.damage * -1);

        this.i = durability.getItem();

        inventory.setSlotItem(slot, i);

        if(durability.getStatus() != DurabilityStatus.EMPTY)
        {
            this.cancel = true;
        } else {
            this.setBroken(true);
        }

    }

    public void breakItem()
    {
        PlayerInventory inventory = new PlayerInventory(this.player);

        InventorySlotType slot = inventory.getItemSlotType(this.getItem());

        inventory.setSlotItem(slot, null);

        this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
    }


}
