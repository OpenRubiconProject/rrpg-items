package com.openrubicon.items.classes.items;

import com.openrubicon.core.api.clone.Cloner;
import com.openrubicon.core.api.clone.interfaces.DeepCloneable;
import com.openrubicon.core.api.interfaces.*;
import org.bukkit.inventory.ItemStack;

abstract public class SpecialItem<T> implements Persistable, Observeable, Loreable, Generatable, DeepCloneable<T> {

    private ItemStack item;

    public SpecialItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public T deepClone() {
        Cloner cloner = new Cloner();

        return (T)cloner.deepClone(this);
    }
}
