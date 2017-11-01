package com.openrubicon.items.commands;

import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.api.vault.items.Items;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemGenerateType extends Command {

    @Override
    public String getCommandFormat() {
        return "item generate type $";
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

        Material material = Items.itemMaterialByName(strings[0]);
        if(material == null)
            return;

        ItemStack item = new ItemStack(material);

        UniqueItem uniqueItem = new UniqueItem(item, false);
        uniqueItem.generate();
        uniqueItem.save();
        player.getInventory().addItem(uniqueItem.getItem());

        player.sendMessage(Helpers.colorize(Constants.MYSTIC_PRIMARY_COLOR + "Enjoy!"));
    }
}
