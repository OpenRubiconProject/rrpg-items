package com.openrubicon.items.classes.items.orbs.types;

import com.openrubicon.core.api.attributes.AttributeFactory;
import com.openrubicon.core.api.attributes.AttributeModifier;
import com.openrubicon.core.api.attributes.AttributeModifiers;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import com.openrubicon.items.classes.items.specs.ItemSpecsFactory;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;

public class AttributeOrb extends Orb {

    AttributeModifiers attributeModifiers;

    @Override
    public String getKey() {
        return "attribute-orb";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.add(Material.POISONOUS_POTATO);
        return materials;
    }

    @Override
    public String getName() {
        return "Attribute Orb";
    }

    @Override
    public String getDescription() {
        return "Holds some attribute things for you to use to your delight";
    }

    @Override
    public boolean generate() {
        if(!super.generate())
            return false;

        ItemSpecs itemSpecs = ItemSpecsFactory.generateSpecs();

        attributeModifiers = new AttributeModifiers();
        for(AttributeModifier attributeModifier : AttributeFactory.generate((int)itemSpecs.getAttributePoints()))
        {
            attributeModifiers.getModifiers().put(attributeModifier.getName(), attributeModifier);
        }

        return true;
    }

    @Override
    public ArrayList<String> getLore() {
        return super.getLore();
    }

    @Override
    public ArrayList<String> getObservation() {
        return super.getObservation();
    }

    @Override
    public boolean save() {
        attributeModifiers.save(this.getNbt());

        return super.save();
    }

    @Override
    public boolean load() {
        if(!super.load())
            return false;

        attributeModifiers = new AttributeModifiers();
        attributeModifiers.load(this.getNbt());

        return true;
    }
}
