package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.enums.InventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class Mark extends Socket {

    public double length = 5;

    @Override
    public String getKey() {
        return "mark";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.TOOLS;
    }

    @Override
    public String getName() {
        return "Mark";
    }

    @Override
    public String getDescription() {
        return "Attacking a player will cause them to glow";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = this.getItemSpecs().getPower() / 2.;
        double max = this.getItemSpecs().getPower();
        length = Helpers.randomDouble(min, max) + 3;
        length *= 20;

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("length", this.length);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.length = this.getSocketProperties().getDouble("length");

        return true;
    }


    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e, SpecialItem item, InventorySlotType slot)
    {
        if(!(e.getEntity() instanceof Player))
            return;

        Player target = (Player)e.getEntity();

        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, (int)this.length, 1);

        target.addPotionEffect(effect, true);
    }
}
