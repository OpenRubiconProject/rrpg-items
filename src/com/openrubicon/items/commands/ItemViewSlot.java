package com.openrubicon.items.commands;

import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.api.utility.DynamicPrimitive;
import com.openrubicon.items.classes.inventory.LivingEntityInventory;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemViewSlot extends Command {

    @Override
    public String getCommandFormat() {
        return "item view $s";
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

        LivingEntityInventory livingEntityInventory = new LivingEntityInventory(player);

        EntityInventorySlotType entityInventorySlotType = EntityInventorySlotType.fromString(args.get(0).getString());

        if(entityInventorySlotType == null)
        {
            interactable.sendMessage("That is not a valid slot type");
            return;
        }

        ItemStack itemStack = livingEntityInventory.getItemInSlot(entityInventorySlotType);

        if(itemStack == null)
        {
            interactable.sendMessage("You don't have an item in this slot");
            return;
        }

        UniqueItem uniqueItem = new UniqueItem(itemStack);
        interactable.sendMessage(uniqueItem.getObservation());
    }
}