package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.CooldownSocket;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashSet;

public class Stab extends CooldownSocket {

    public double damage = 5;

    @Override
    public String getKey() {
        return "stab";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.SWORDS;
    }

    @Override
    public String getName() {
        return "Stab";
    }

    @Override
    public String getDescription() {
        return "Right click a player to deal instant damage";
    }

    @Override
    public int getCooldownLengthTicks() {
        return Helpers.secondsToTicks(5);
    }

    @Override
    public boolean generate()
    {
        super.generate();

        damage = Helpers.randomInt(1, (int)this.getItemSpecs().getPower() + 1);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("damage", this.damage);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.damage = this.getSocketProperties().getDouble("damage");

        return true;
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e, SpecialItem item, EntityInventorySlotType slot)
    {
        if(this.isOnCooldown())
            return;

        if(!e.getPlayer().isSneaking())
            return;

        if(!(e.getRightClicked() instanceof LivingEntity))
            return;


        LivingEntity entity = (LivingEntity)e.getRightClicked();

        entity.damage(this.damage);

        this.startCooldown();
    }

}
