package com.openrubicon.items.classes.sockets.effects;

import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class ClutchBarrier extends Socket {
    public float amount = 10f;

    public ClutchBarrier() {
        super();
        this.name = "Clutch Barrier";
        this.key = "clutch_barrier";
        this.description = "Upon taking a killing blow, activate a health barrier to save you from death.";
        this.materials.addAll(MaterialGroups.CHESTPLATES);

        this.usingCooldown = true;
        this.cooldownLength = 180 * 20;
    }

    @Override
    public boolean generateSocket(Item.ItemNbt i) {
        double min = 1;
        double max = i.getPowerScore() * i.getRarityScore();
        if(max <= min)
            max = 2 * min;
        this.amount = (int) Helpers.scale(Helpers.randomDouble(min, max), min, 121, 20, 60);
        return true;
    }

    @Override
    public String save() {
        return this.getDefaultSaveString() + ",amount:" + this.amount;
    }

    @Override
    public boolean load(String settings, UUID uuid) {
        HashMap<String, String> settingsMap = settingsToArray(settings, uuid);

        if (settingsMap.containsKey("amount"))
            this.amount = Float.parseFloat(settingsMap.get("amount"));

        return true;
    }

    @Override
    public void onEntityDamage(EntityDamageEvent e, FullItem item, Inventory.SlotType slot) {
        if(this.onCooldown())
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

        //CooldownManager.start((Player)e.getEntity(), this.getKey(), this.getCooldown(), slot);
        this.startCooldown((LivingEntity)e.getEntity(), slot);
    }
}
