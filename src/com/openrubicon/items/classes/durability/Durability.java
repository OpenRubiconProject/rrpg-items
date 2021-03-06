package com.openrubicon.items.classes.durability;

import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.api.vault.items.Items;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.durability.enums.DurabilityStatus;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Durability implements Observeable {

    private ItemStack i;

    public Durability(ItemStack item) {
        this.i = item;
    }

    public ItemStack getItem() {
        return i;
    }

    public void setItem(ItemStack i) {
        this.i = i;
    }

    public DurabilityStatus getStatus()
    {
        if(i.getDurability() <= 0)
            return DurabilityStatus.FULL;

        if(i.getDurability() == i.getType().getMaxDurability())
            return DurabilityStatus.EMPTY;

        return DurabilityStatus.USED;
    }

    public boolean isUseable()
    {
        if(this.getStatus() != DurabilityStatus.EMPTY)
            return true;
        return false;
    }

    public int getDurability()
    {
        SpecialItem item = new SpecialItem(this.i);

        if(!item.isSpecialItem() || !item.isValid())
        {
            return this.i.getType().getMaxDurability() - this.i.getDurability();
        }


        return (int) item.getItemSpecs().getDurabilityCurrent();
    }

    public int getVisualDurability()
    {
        SpecialItem item = new SpecialItem(this.i);

        if(!item.isSpecialItem())
            return this.i.getDurability();

        return i.getType().getMaxDurability() - ((int) Helpers.scale(this.getDurability(), 0, item.getItemSpecs().getDurabilityMax(), 0, i.getType().getMaxDurability()));
    }

    public void adjustDurability(int amount)
    {
        UniqueItem item = new UniqueItem(this.i);

        if(!item.isSpecialItem())
            return;

        item.getItemSpecs().setDurabilityCurrent(this.getDurability() + amount);

        item.save();

        /*ItemMeta meta = item.getItem().getItemMeta();
        String[] s = Items.itemNameByType(item.getItem().getType()).split(" ");
        meta.setDisplayName(Helpers.colorize(item.getRarity().getColoredName() + " " + s[s.length - 1]));
        meta.setLore(item.getLore());
        item.getItem().setItemMeta(meta);*/

        this.i = item.getItem();

        this.i.setDurability((short) this.getVisualDurability());


    }

    public boolean hasDurability(int amount)
    {
        return amount <= this.getDurability();
    }

    public boolean isBroken()
    {
        return !this.isUseable();
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> output = new ArrayList<>();
        output.add(Constants.PRIMARY_COLOR + "Current: " + Constants.SECONDARY_COLOR + this.getDurability());
        return Helpers.colorize(output);
    }

}