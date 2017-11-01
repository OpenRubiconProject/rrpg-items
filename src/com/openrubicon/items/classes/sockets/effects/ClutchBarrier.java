package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.CooldownSocket;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;

public class ClutchBarrier extends CooldownSocket {
    public double amount = 10;

    @Override
    public String getKey() {
        return "clutch_barrier";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.CHESTPLATES;
    }

    @Override
    public String getName() {
        return "Clutch Barrier";
    }

    @Override
    public String getDescription() {
        return "Upon taking a killing blow, activate a health barrier to save you from death";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 1;
        double max = this.getItemSpecs().getPower() * this.getItemSpecs().getRarity();
        if(max <= min)
            max = 2;
        this.amount = (int) Helpers.scale(Helpers.randomDouble(min, max), min, 121, 20, 60);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("amount", this.amount);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.amount = this.getSocketProperties().getDouble("amount");

        return true;
    }

    @Override
    public int getCooldownLengthTicks() {
        return Helpers.secondsToTicks(180);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(this.isOnCooldown())
            return;

        if(!(e.getEntity() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity)e.getEntity();

        if(entity.getHealth() - e.getFinalDamage() >= 1)
            return;

        e.setCancelled(true);

        entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * this.amount / 100);

        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 1f);
        e.getEntity().getWorld().spawnParticle(Particle.SPELL_INSTANT, e.getEntity().getLocation(), 50);

        this.startCooldown(entity, item);
    }
}
