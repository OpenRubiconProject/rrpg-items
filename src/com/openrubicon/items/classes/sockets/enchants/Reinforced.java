package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.HashSet;

public class Reinforced extends Socket {
    private int armor = 1;

    @Override
    public String getKey() {
        return "reinforced";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.ARMOR;
    }

    @Override
    public String getName() {
        return "Reinforced";
    }

    @Override
    public String getDescription() {
        return "Increases your chance of blocking an attack";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 0;
        double max = (this.getItemSpecs().getPower() / 2) * this.getItemSpecs().getRarity();

        this.armor = (int)Helpers.scale(Helpers.randomDouble(min, max), min, max, 1, 8);

        if(this.armor < 1)
            this.armor = 1;


        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addInteger("armor", this.armor);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.armor = this.getSocketProperties().getInteger("armor");

        return true;
    }


    public int getArmor() {
        return armor;
    }
}
