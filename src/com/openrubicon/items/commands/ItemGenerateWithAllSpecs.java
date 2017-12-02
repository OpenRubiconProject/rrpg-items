package com.openrubicon.items.commands;

import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.api.utility.DynamicPrimitive;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import com.openrubicon.items.classes.items.specs.ItemSpecsFactory;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemGenerateWithAllSpecs extends Command {

    @Override
    public String getCommandFormat() {
        return "item generate specs $n $n $n $n $n";
    }

    @Override
    public ArrayList<InteractableType> getAllowedSenderTypes() {

        ArrayList<InteractableType> interactableTypes = new ArrayList<>();
        interactableTypes.add(InteractableType.PLAYER);
        return interactableTypes;
    }

    @Override
    public void handle(Interactable interactable, ArrayList<DynamicPrimitive> args) {
        org.bukkit.entity.Player player = ((Player)interactable).getPlayer();

        double rarity = args.get(0).getInt();
        double sockets = args.get(1).getInt();
        double power = args.get(2).getInt();
        int durability = args.get(3).getInt();
        int points = args.get(4).getInt();

        ItemSpecs itemSpecs = ItemSpecsFactory.generateFromCoreValues(rarity, sockets, power, durability, points);

        int choice = Helpers.rng.nextInt(MaterialGroups.GENERATABLE.size());
        ArrayList<Material> materials = new ArrayList<>();
        materials.addAll(MaterialGroups.GENERATABLE);

        ItemStack i = new ItemStack(materials.get(choice));

        UniqueItem uniqueItem = new UniqueItem(i, false);
        uniqueItem.setItemSpecs(itemSpecs);
        uniqueItem.generate();
        uniqueItem.save();
        player.getInventory().addItem(uniqueItem.getItem());

        player.sendMessage(Helpers.colorize(Constants.MYSTIC_PRIMARY_COLOR + "Enjoy!"));
    }
}
