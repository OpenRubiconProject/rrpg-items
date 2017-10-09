package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;

import java.util.HashSet;

public class Spam extends Socket {
    private int cdr = 10;

    @Override
    public String getKey() {
        return "spam";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.GENERATABLE;
    }

    @Override
    public String getName() {
        return "Spam";
    }

    @Override
    public String getDescription() {
        return "Significantly reduces the cooldown on this item";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1;
        double max = (this.getItemSpecs().getPower() / 2) * this.getItemSpecs().getRarity();

        if(max <= min)
            max = 2 * min;

        this.cdr = (int) Helpers.scale(Helpers.randomDouble(min, max), min, max, 1, 35);

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
