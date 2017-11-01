package com.openrubicon.items.commands;

import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.items.classes.items.unique.UniqueItem;

import java.util.ArrayList;

public class ItemView extends Command {

    @Override
    public String getCommandFormat() {
        return "item view";
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

        UniqueItem uniqueItem = new UniqueItem(player.getInventory().getItemInMainHand());
        interactable.sendMessage(uniqueItem.getObservation());
    }
}
