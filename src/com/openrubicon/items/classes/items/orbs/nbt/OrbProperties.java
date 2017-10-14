package com.openrubicon.items.classes.items.orbs.nbt;

import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.api.nbt.Properties;

import java.util.ArrayList;

public class OrbProperties extends Properties implements Observeable {
    @Override
    public ArrayList<String> getObservation() {
        return new ArrayList<>();
    }
}
