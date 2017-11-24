package com.openrubicon.items.configuration;

import com.openrubicon.core.api.configuration.ConfigurationProperty;

public class SocketOrbSpawnChance extends ConfigurationProperty<Integer> {

    public SocketOrbSpawnChance() {
        super("socket-orb-spawn-chance", 128, true);
    }
}