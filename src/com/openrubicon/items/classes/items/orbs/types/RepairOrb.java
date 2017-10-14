package com.openrubicon.items.classes.items.orbs.types;

import org.bukkit.Material;

import java.util.HashSet;

public class RepairOrb extends Orb {
    @Override
    public String getKey() {
        return "repair-orb";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.add(Material.POISONOUS_POTATO);
        return materials;
    }

    @Override
    public String getName() {
        return "Repair Orb";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
