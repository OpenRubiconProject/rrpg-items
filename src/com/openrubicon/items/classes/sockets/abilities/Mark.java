package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Mark extends Socket {
    public float length = 5;

    public Mark() {
        super();
        this.name = "Mark";
        this.key = "mark";
        this.description = "Attacking a player will cause them to glow";
        this.materials.addAll(MaterialGroups.TOOLS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        length = (float)(Math.random() * (i.getPowerScore() - (i.getPowerScore() / 2)) + (i.getPowerScore() / 2)) + 3;
        length *= 20; // Duration is in ticks. 20 ticks per second.
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString()+",length:" + this.length;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        //Bukkit.broadcastMessage("Hashmap was created");
        //Bukkit.broadcastMessage("Hashmap has size: " + settingsMap.size());

        /*settingsMap.forEach((key, value) -> {
            Bukkit.getLogger().info("setting : " + key + " value : " + value);
        });*/

        if(settingsMap.containsKey("length"))
            this.length = Float.parseFloat(settingsMap.get("length"));

        //Bukkit.broadcastMessage("Speed property is: " + this.speed);

        return true;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, FullItem item, Inventory.SlotType slot)
    {
        if(!(e.getEntity() instanceof Player))
            return;

        Player target = (Player)e.getEntity();

        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, (int)this.length, 1);

        target.addPotionEffect(effect, true);
    }
}
