package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class RadialMark extends Socket {
    public float length = 5;
    public float radius = 10;

    public RadialMark() {
        super();
        this.name = "Radial Mark";
        this.key = "radial_mark";
        this.description = "Reveals all nearby entities for a short time";
        this.materials.addAll(MaterialGroups.TOOLS);
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i)
    {
        length = (float)(Math.random() * (i.getPowerScore() - (i.getPowerScore() / 2)) + (i.getPowerScore() / 2));
        length *= 20; // Duration is in ticks. 20 ticks per second.
        radius = (float)(Math.random() * ((i.getPowerScore() * 2) - (i.getPowerScore())) + (i.getPowerScore()));
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString()+",length:" + this.length+",radius:"+this.radius;
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
        if(settingsMap.containsKey("radius"))
            this.radius = Float.parseFloat(settingsMap.get("radius"));

        //Bukkit.broadcastMessage("Speed property is: " + this.speed);

        return true;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e, FullItem item, Inventory.SlotType slot)
    {
        if((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) || !e.getPlayer().isSneaking())
            return;

        for(Entity target : Helpers.getNearbyEntities(e.getPlayer(), this.radius))
        {
            if(!(target instanceof LivingEntity))
                continue;

            PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, (int)this.length, 1);

            ((LivingEntity) target).addPotionEffect(effect, true);
        }
    }
}
