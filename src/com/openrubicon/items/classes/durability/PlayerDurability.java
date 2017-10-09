package com.openrubicon.items.classes.durability;

import com.shawnclake.economics.item.FullItem;
import com.shawnclake.economics.item.inventory.Inventory;
import com.shawnclake.economics.item.inventory.PlayerInventory;
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
        FullItem item = new FullItem(i);

        if(!item.isSpecialItem())
            return;

        PlayerInventory inventory = new PlayerInventory(this.player);

        Inventory.SlotType slot = inventory.getSlotType(this.getItem());

        Durability durability = new Durability(this.i);
        durability.adjustDurability(this.damage * -1);

        this.i = durability.getItem();

        inventory.setSlot(slot, i);

        if(durability.getStatus() != Durability.Status.EMPTY)
        {
            this.cancel = true;
        } else {
            this.setBroken(true);
        }

    }

    public void breakItem()
    {
        PlayerInventory inventory = new PlayerInventory(this.player);

        Inventory.SlotType slot = inventory.getSlotType(this.getItem());

        inventory.setSlot(slot, null);

        this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
    }


}
