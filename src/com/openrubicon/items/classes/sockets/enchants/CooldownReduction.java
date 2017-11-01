package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.inventory.LivingEntityInventory;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import com.openrubicon.items.classes.sockets.events.PrepareSocketCooldownEvent;
import org.bukkit.Material;

import java.util.HashSet;

public class CooldownReduction extends Socket {

    private int cdr = 5;

    @Override
    public String getKey() {
        return "cooldown_reduction";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.ARMOR;
    }

    @Override
    public String getName() {
        return "Cooldown Reduction";
    }

    @Override
    public String getDescription() {
        return "Reduces the cooldowns on all your items";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1;
        double max = (this.getItemSpecs().getPower() / 2) * this.getItemSpecs().getRarity();

        if(max <= min)
            max = 2 * min;

        this.cdr = (int) Helpers.scale(Helpers.randomDouble(min, max), min, max, 1, 10);

        if(this.cdr < 1)
            this.cdr = 1;

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addInteger("cdr", this.cdr);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.cdr = this.getSocketProperties().getInteger("cdr");

        return true;
    }

    public int getCdr() {
        return cdr;
    }

    @Override
    public void onPrepareSocketCooldown(PrepareSocketCooldownEvent e, UniqueItem item, EntityInventorySlotType slot) {
        LivingEntityInventory inventory = new LivingEntityInventory(e.getLivingEntity());

        int cdr = 0;
        for(UniqueItem uniqueItem : inventory.getArmorWithSocket(new CooldownReduction()))
        {
            CooldownReduction socket = (CooldownReduction)item.getSocketHandler().get(new CooldownReduction());
            cdr += socket.getCdr();
        }

        e.getCooldown().addCooldownReduction(cdr);
    }
}
