package com.openrubicon.items.commands;

import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemGenerate extends Command {

    @Override
    public String getCommandFormat() {
        return "item generate";
    }

    @Override
    public ArrayList<InteractableType> getAllowedSenderTypes() {

        ArrayList<InteractableType> interactableTypes = new ArrayList<>();
        interactableTypes.add(InteractableType.PLAYER);
        return interactableTypes;
    }

    @Override
    public void handle(Interactable interactable, String[] strings) {
        org.bukkit.entity.Player player = ((Player)interactable).getPlayer();

        int choice = Helpers.rng.nextInt(MaterialGroups.GENERATABLE.size());
        ArrayList<Material> materials = new ArrayList<>();
        materials.addAll(MaterialGroups.GENERATABLE);

        ItemStack i = new ItemStack(materials.get(choice));

        UniqueItem uniqueItem = new UniqueItem(i, false);
        uniqueItem.generate();
        uniqueItem.save();
        player.getInventory().addItem(uniqueItem.getItem());

    }
}
