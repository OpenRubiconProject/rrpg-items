package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class RadialMark extends Socket {
    public double length = 5;
    public double radius = 10;

    @Override
    public String getKey() {
        return "radial_mark";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.TOOLS;
    }

    @Override
    public String getName() {
        return "Radial Mark";
    }

    @Override
    public String getDescription() {
        return "Reveals all nearby entities for a short time";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = this.getItemSpecs().getPower() / 2.;
        double max = this.getItemSpecs().getPower();
        length = Helpers.randomDouble(min, max);
        length *= 20;

        min = this.getItemSpecs().getPower();
        max = this.getItemSpecs().getPower() * 2;
        radius = Helpers.randomDouble(min, max);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("length", this.length);
        this.getSocketProperties().addDouble("radius", this.radius);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.length = this.getSocketProperties().getDouble("length");
        this.radius = this.getSocketProperties().getDouble("radius");

        return true;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e, UniqueItem item, EntityInventorySlotType slot)
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
