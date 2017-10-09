package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.sockets.Socket;
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
        return this.cdr;
    }

}
