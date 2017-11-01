package com.openrubicon.items.classes.sockets.abilities;

import com.openrubicon.core.api.inventory.entities.PlayerInventory;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Sapling;

import java.util.HashSet;

public class TreeHugger extends Socket {

    public int species = 4;

    @Override
    public String getKey() {
        return "tree_hugger";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.AXES;
    }

    @Override
    public String getName() {
        return "Tree Hugger";
    }

    @Override
    public String getDescription() {
        return "Consumes 45 durability to plant a sapling when right clicking grass";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        this.species = Helpers.randomInt(1, 6);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addInteger("species", this.species);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.species = this.getSocketProperties().getInteger("species");

        return true;
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent e, UniqueItem item, EntityInventorySlotType slot)
    {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.getPlayer().isSneaking())
            return;

        if(e.getBlockFace() != BlockFace.UP)
            return;

        if(e.getClickedBlock().getType() != Material.GRASS && e.getClickedBlock().getType() != Material.DIRT)
            return;

        Durability durability = new Durability(item.getItem());

        if(!durability.hasDurability(45))
        {
            e.getPlayer().sendMessage(Helpers.colorize(Constants.YELLOW + "Your item doesn't have enough durability to plant a sapling."));
            return;
        }

        Location location = e.getClickedBlock().getLocation();
        location.setY(location.getBlockY() + 1);

        if(location.getBlock().getType() != Material.AIR)
            return;

        durability.adjustDurability(-45);

        PlayerInventory inventory = new PlayerInventory(e.getPlayer());
        inventory.setSlotItem(slot, durability.getItem());

        Sapling sapling;

        switch(this.species) {
            case 1:
                sapling = new Sapling(TreeSpecies.ACACIA);
                break;
            case 2:
                sapling = new Sapling(TreeSpecies.BIRCH);
                break;
            case 3:
                sapling = new Sapling(TreeSpecies.DARK_OAK);
                break;
            case 4:
                sapling = new Sapling(TreeSpecies.JUNGLE);
                break;
            case 5:
                sapling = new Sapling(TreeSpecies.REDWOOD);
                break;
            default:
                sapling = new Sapling(TreeSpecies.GENERIC);
                break;

        }

        Block b = location.getBlock();
        b.setType(Material.SAPLING);
        BlockState bs = b.getState();

        bs.setType(Material.SAPLING);
        bs.setData(sapling);
        bs.update();
    }

}
