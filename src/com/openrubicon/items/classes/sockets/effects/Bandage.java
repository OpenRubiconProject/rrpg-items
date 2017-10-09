package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Bandage extends Socket {
    public float rate = 1f;

    public Bandage() {
        super();
        this.name = "Bandage";
        this.key = "bandage";
        this.description = "Regenerates health faster while standing still.";
        this.materials.addAll(MaterialGroups.ARMOR);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i) {
        double min = 1;
        double max = (i.getPowerScore() + 1) / 2;
        //Bukkit.broadcastMessage("Max: " + max);
        //Bukkit.broadcastMessage("Min: " + min);
        //Bukkit.broadcastMessage("Random Num: " + ((Math.random() * (max - min)) + min) );
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

        //Bukkit.broadcastMessage("Hashmap was created");
        //Bukkit.broadcastMessage("Hashmap has size: " + settingsMap.size());

        /*settingsMap.forEach((key, value) -> {
            Bukkit.getLogger().info("setting : " + key + " value : " + value);
        });*/

        if (settingsMap.containsKey("rate"))
            this.rate = Float.parseFloat(settingsMap.get("rate"));

        //Bukkit.broadcastMessage("Speed property is: " + this.speed);

        return true;
    }

    @Override
    public void onPlayerStandingStill(PlayerStandingStillEvent e, FullItem item, Inventory.SlotType slot)
    {
        Player p = e.getPlayer();

        if(!p.hasPotionEffect(PotionEffectType.REGENERATION))
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, (int)this.rate - 1), true);
    }
}
