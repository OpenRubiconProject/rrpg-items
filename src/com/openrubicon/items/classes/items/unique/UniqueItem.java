package com.openrubicon.items.classes.items.unique;

import com.openrubicon.core.api.attributes.AttributeFactory;
import com.openrubicon.core.api.attributes.AttributeModifier;
import com.openrubicon.core.api.attributes.AttributeModifiers;
import com.openrubicon.core.api.clone.Cloner;
import com.openrubicon.core.api.nbt.NBT;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.sockets.SocketHandler;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class UniqueItem extends SpecialItem {

    public static final String UNIQUE = "RubiconUnique";

    private SocketHandler socketHandler;

    private AttributeModifiers attributeModifiers;

    public UniqueItem(UniqueItem oldSpecialItem) {
        super(oldSpecialItem);

        Cloner cloner = new Cloner();

        socketHandler = cloner.deepClone(oldSpecialItem.getSocketHandler());
        socketHandler.setItem(this.getItem());
        socketHandler.setItemSpecs(this.getItemSpecs());
        socketHandler.setNbt(this.getNbt());

        attributeModifiers = cloner.deepClone(oldSpecialItem.getAttributeModifiers());

        this.save();
    }

    public UniqueItem(ItemStack item) {
        super(item);
    }

    public UniqueItem(ItemStack item, boolean autoLoad) {
        super(item, autoLoad);
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }

    public AttributeModifiers getAttributeModifiers() {
        return attributeModifiers;
    }

    @Override
    public boolean generate() {
        super.generate();

        attributeModifiers = new AttributeModifiers();

        if(MaterialGroups.HAND_HELD.contains(this.getItem().getType()))
        {
            for(AttributeModifier attributeModifier : AttributeFactory.generate((int)this.getItemSpecs().getAttributePoints()))
            {
                attributeModifiers.getModifiers().put(attributeModifier.getName(), attributeModifier);
            }
        }

        socketHandler = new SocketHandler();
        socketHandler.setItem(this.getItem());
        socketHandler.setItemSpecs(this.getItemSpecs());
        socketHandler.setNbt(this.getNbt());
        socketHandler.generate();

        return true;
    }

    @Override
    public boolean save() {
        this.setItem(new ItemStack(this.getItem().getType()));

        this.setNbt(new NBT(this.getItem()));

        attributeModifiers.save(this.getNbt());

        this.socketHandler.setNbt(this.getNbt());

        this.socketHandler.save();

        this.getNbt().setString(UNIQUE, "");

        return super.save();
    }

    @Override
    public boolean load() {
        if(!super.load())
            return false;

        if(!this.getNbt().hasKey(UNIQUE))
        {
            this.setRightItemType(false);
            return false;
        }

        attributeModifiers = new AttributeModifiers();
        attributeModifiers.load(this.getNbt());

        socketHandler = new SocketHandler();
        socketHandler.setItem(this.getItem());
        socketHandler.setItemSpecs(this.getItemSpecs());
        socketHandler.setNbt(this.getNbt());
        socketHandler.load();

        return true;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = super.getLore();

        if(this.isSpecialItem() && this.getSocketHandler() != null)
        {
            if (this.getSocketHandler().getSockets().size() == 0) {
                lore.add(Constants.HEADING_COLOR + "No sockets on item");
            } else {
                lore.add(Constants.HEADING_COLOR + "Sockets " + Constants.TERTIARY_COLOR + this.getSocketHandler().getSockets().size() + "/" + (int)this.getItemSpecs().getSockets());
                lore.add(Constants.SECONDARY_COLOR+"================╝");
                lore.addAll(this.getSocketHandler().getLore());
                lore.add(Constants.SECONDARY_COLOR+"================╝");
            }

            if(this.getSocketHandler().isPartiallyObfuscated())
            {
                lore.add("");
                lore.add(Constants.MYSTIC_PRIMARY_COLOR + "This item has obfuscated sockets");
                lore.add(Constants.MYSTIC_SECONDARY_COLOR + "You are unable to do many things");
                lore.add(Constants.MYSTIC_SECONDARY_COLOR + "with this item unless you ");
                lore.add(Constants.MYSTIC_SECONDARY_COLOR + "un-obfuscate all the sockets");
            }

        }

        return Helpers.colorize(lore);
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> observation = super.getObservation();
        if(this.isSpecialItem()) {
            observation.add(Helpers.colorize(""));
            observation.add(Helpers.colorize("&6Stats"));

            observation.addAll(this.getAttributeModifiers().getObservation());

            observation.add(Helpers.colorize(""));

            if (this.getSocketHandler().getSockets().size() == 0) {
                observation.add(Helpers.colorize("&8---- No Sockets ----"));
            } else {
                observation.add(Helpers.colorize("&8---- Sockets ----"));
                observation.addAll(this.getSocketHandler().getObservation());
            }
        }
        return Helpers.colorize(observation);
    }
}
