package com.openrubicon.items.classes.items.orbs.types;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.sockets.SocketHandler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class SocketOrb extends Orb {

    private SocketHandler socketHandler = new SocketHandler();

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }

    @Override
    public String getKey() {
        return "socket-orb";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.add(Material.POISONOUS_POTATO);
        return materials;
    }

    @Override
    public String getName() {
        return "Socket Orb";
    }

    @Override
    public String getDescription() {
        return "Holds a socket";
    }

    @Override
    public boolean generate() {
        super.generate();

        double temp = this.getItemSpecs().getSockets();
        this.getItemSpecs().setSockets(1);

        int total = MaterialGroups.GENERATABLE.size();
        int index = Helpers.randomInt(0, total);
        int i = 0;

        for(Material material : MaterialGroups.GENERATABLE)
        {
            if(i == index)
            {
                socketHandler.setItem(new ItemStack(material));
                break;
            }
            i++;
        }

        socketHandler.setItemSpecs(this.getItemSpecs());
        socketHandler.setNbt(this.getNbt());
        socketHandler.generate();

        this.getItemSpecs().setSockets(temp);

        return true;
    }

    @Override
    public ArrayList<String> getLore() {
        //ArrayList<String> lore = super.getLore();
        ArrayList<String> lore = new ArrayList<>();
        lore.addAll(this.socketHandler.getLore());
        return lore;
    }

    @Override
    public ArrayList<String> getObservation() {
        return super.getObservation();
    }

    @Override
    public boolean save() {
        this.socketHandler.setNbt(this.getNbt());
        this.socketHandler.save();

        return super.save();
    }

    @Override
    public boolean load() {
        if(!super.load())
            return false;

        socketHandler.setItem(this.getItem());
        socketHandler.setItemSpecs(this.getItemSpecs());
        socketHandler.setNbt(this.getNbt());
        socketHandler.load();

        return true;
    }
}
