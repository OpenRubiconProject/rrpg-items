package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.items.classes.sockets.Socket;

import java.util.HashMap;

public class Reinforced extends Socket {
    protected int armor = 1;

    public Reinforced() {
        super();
        this.name = "Reinforced";
        this.key = "reinforced";
        this.description = "Increases your chance of blocking an attack.";
        this.materials.addAll(MaterialGroups.ARMOR);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        double min = 0;
        double max = (i.getPowerScore() / 2) * i.getRarityScore();

        this.armor = (int)Helpers.scale(Helpers.randomDouble(min, max), min, max, 1, 8);

        if(this.armor < 1)
            this.armor = 1;

        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",armor:" + this.armor;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("armor"))
            this.armor = Integer.parseInt(settingsMap.get("armor"));

        return true;
    }

    public int getArmor() {
        return armor;
    }
}
