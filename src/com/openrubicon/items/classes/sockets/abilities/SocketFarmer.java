package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.orbs.OrbItem;
import com.openrubicon.items.classes.items.orbs.types.SocketOrb;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.CooldownSocket;
import com.openrubicon.items.configuration.SocketOrbSpawnChance;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

import java.util.HashSet;

public class SocketFarmer extends CooldownSocket {

    @Override
    public String getKey() {
        return "socket_farmer";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HOES;
    }

    @Override
    public String getName() {
        return "Socket Farmer";
    }

    @Override
    public String getDescription() {
        return "Destroy crops and occasionally a socket will drop, but always guarantees two taters will drop.";
    }

    @Override
    public int getCooldownLengthTicks() {
        int max = ((int)((22 - this.getItemSpecs().getRarity() - this.getItemSpecs().getSockets()) / 2) + 8) * 20;
        int min = max / 2;
        return Helpers.randomInt(min, max);
    }

    @Override
    public boolean generate()
    {
        super.generate();

        return true;
    }

    @Override
    public boolean save() {
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        return true;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        Block b = e.getBlock();

        if (b.getType() != Material.POTATO)
            return;

        Crops c = (Crops) (b.getState().getData());

        if (c.getState() != CropState.RIPE)
            return;

        b.setType(Material.AIR);

        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.POTATO_ITEM, 2));

        if(this.isOnCooldown())
            return;

        if ((Helpers.rng.nextInt(RRPGCore.config.get(SocketOrbSpawnChance.class).getInt()) + 1) == 1) {

            OrbItem orbItem = new OrbItem();
            orbItem.setOrb(new SocketOrb());
            orbItem.generate();
            orbItem.save();

            b.getWorld().dropItemNaturally(b.getLocation(), orbItem.getItem());

            e.getPlayer().sendMessage(Helpers.colorize("&5You Spawned an Epic Potato!"));

            this.startCooldown(e.getPlayer(), item);
        }

    }

}
