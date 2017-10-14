package com.openrubicon.items.classes.items.orbs.types;

import com.openrubicon.core.api.interfaces.*;
import com.openrubicon.core.api.nbt.NBT;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.orbs.nbt.OrbProperties;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

abstract public class Orb implements Persistable, Observeable, Loreable, Metable, Materiable, Generatable, Keyable, NBTable {
    private OrbProperties orbProperties = new OrbProperties();

    private UUID uuid;

    private NBT nbt;

    private ItemStack item;

    private ItemSpecs itemSpecs;

    public OrbProperties getOrbProperties() {
        return orbProperties;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setNbt(NBT nbt) {
        this.nbt = nbt;
    }

    @Override
    public NBT getNbt() {
        return this.nbt;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemSpecs getItemSpecs() {
        return itemSpecs;
    }

    public void setItemSpecs(ItemSpecs itemSpecs) {
        this.itemSpecs = itemSpecs;
    }

    public void generateUUID()
    {
        this.uuid = UUID.randomUUID();
        orbProperties.add(Constants.UUID, this.uuid.toString());
    }

    @Override
    public boolean generate() {
        this.generateUUID();
        return true;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        String loreLine = Constants.PRIMARY_COLOR+this.getName();
        lore.add(Helpers.colorize(loreLine));
        return lore;
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> output = new ArrayList<>();

        output.add("&2Name&f: &a"+this.getName());
        output.add("&2Description&f: &a"+this.getDescription());

        output.addAll(this.orbProperties.getObservation());

        return Helpers.colorize(output);
    }

    @Override
    public boolean save() {

        return this.orbProperties.save();
    }

    @Override
    public boolean load() {
        orbProperties.load();

        if(!orbProperties.has(Constants.UUID)) {
            orbProperties.add(Constants.UUID, UUID.randomUUID().toString());
        }

        this.uuid = UUID.fromString(orbProperties.get(Constants.UUID));

        return true;
    }
}