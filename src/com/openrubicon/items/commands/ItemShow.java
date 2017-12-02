package com.openrubicon.items.commands;

import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.api.server.players.Players;
import com.openrubicon.core.api.utility.DynamicPrimitive;
import com.openrubicon.items.classes.items.unique.UniqueItem;

import java.util.ArrayList;

public class ItemShow extends Command {

    @Override
    public String getCommandFormat() {
        return "item show";
    }

    @Override
    public ArrayList<InteractableType> getAllowedSenderTypes() {

        ArrayList<InteractableType> interactableTypes = new ArrayList<>();
        interactableTypes.add(InteractableType.PLAYER);
        return interactableTypes;
    }

    @Override
    public void handle(Interactable interactable, ArrayList<DynamicPrimitive> args) {
        org.bukkit.entity.Player playerWithItem = ((Player)interactable).getPlayer();

        UniqueItem uniqueItem = new UniqueItem(playerWithItem.getInventory().getItemInMainHand());

        Players playersAPI = new Players();
        for(org.bukkit.entity.Player player : playersAPI.getOnlinePlayers())
        {
            for(int i = 0; i < uniqueItem.getObservation().size(); i++) {
                player.sendMessage(uniqueItem.getLore().get(i));
            }
        }

    }
}
