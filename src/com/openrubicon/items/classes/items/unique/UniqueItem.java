package com.openrubicon.items.classes.items.unique;

import com.openrubicon.items.classes.items.SpecialItem;
import org.bukkit.inventory.ItemStack;

public class UniqueItem extends SpecialItem<UniqueItem> {

    public UniqueItem(ItemStack item) {
        super(item);
    }

    public UniqueItem(ItemStack item, boolean autoLoad) {
        super(item, autoLoad);
    }


}
