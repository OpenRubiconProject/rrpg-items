package com.openrubicon.items.classes.items.orbs.types;

import org.bukkit.Material;

import java.util.HashSet;

public class ElementalOrb extends Orb {
    @Override
    public String getKey() {
        return "elemental-orb";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.add(Material.POISONOUS_POTATO);
        return materials;
    }

    @Override
    public String getName() {
        return "Elemental Orb";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
