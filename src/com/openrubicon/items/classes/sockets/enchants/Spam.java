package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.items.classes.sockets.Socket;
import java.util.HashMap;

public class Spam extends Socket {
    protected int cdr = 10;

    public Spam() {
        super();
        this.name = "Spam";
        this.key = "spam";
        this.description = "Significantly reduces the cooldown on this item.";
        this.materials.addAll(MaterialGroups.GENERATABLE);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        double min = 1;
        double max = (i.getPowerScore() / 2) * i.getRarityScore();

        if(max <= min)
            max = 2 * min;

        this.cdr = (int) Helpers.scale(Helpers.randomDouble(min, max), min, max, 1, 35);

        if(this.cdr < 1)
            this.cdr = 1;

        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",cdr:" + this.cdr;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("cdr"))
            this.cdr = Integer.parseInt(settingsMap.get("cdr"));

        return true;
    }

    public int getCDR() {
        return this.cdr;
    }
}
