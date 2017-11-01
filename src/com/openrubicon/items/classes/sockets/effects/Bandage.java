package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerStandingStillEvent;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class Bandage extends Socket {

    public double rate = 1.;

    @Override
    public String getKey() {
        return "bandage";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.ARMOR;
    }

    @Override
    public String getName() {
        return "Bandage";
    }

    @Override
    public String getDescription() {
        return "Regenerates health faster while standing still";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1;
        double max = ((this.getItemSpecs().getPower() + 1) / 2)+0.1;
        rate = Helpers.randomDouble(min, max);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("rate", this.rate);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.rate = this.getSocketProperties().getDouble("rate");

        return true;
    }


    @Override
    public void onPlayerStandingStill(PlayerStandingStillEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        Player p = e.getPlayer();

        if(!p.hasPotionEffect(PotionEffectType.REGENERATION))
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, (int)this.rate - 1), true);
    }
}
