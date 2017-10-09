package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MarathonRunner extends Socket {
    public float rate = 0.01f;

    public MarathonRunner() {
        super();
        this.name = "Marathon Runner";
        this.key = "marathon_runner";
        this.description = "Gain XP as you move and run around.";
        this.materials.addAll(MaterialGroups.ARMOR);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i) {
        double min = i.getPowerScore() / 2;
        double max = i.getPowerScore();
        rate = (float) ((Math.random() * (max - min)) + min);
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",rate:" + this.rate;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("rate"))
            this.rate = Float.parseFloat(settingsMap.get("rate"));

        return true;
    }

    @Override
    public void onPlayerMovedLocation(PlayerMovedLocationEvent e, FullItem item, Inventory.SlotType slot) {
        Player p = e.getPlayer();

        double amount = Helpers.getDistance(e.getPreviousLocation(), e.getCurrentLocation());

        if(amount < 1)
            amount = 1;

        int chance = Helpers.rng.nextInt(100 + p.getLevel());

        if(chance <= this.rate)
            p.giveExp((int)amount);
    }
}
