package com.openrubicon.items;

import org.bukkit.plugin.java.JavaPlugin;
import com.openrubicon.core.iModule;

public class RRPGItems extends JavaPlugin implements iModule {

    @Override
    public String getKey() {
        return "rrpg-items";
    }

    @Override
    public String getOverview() {
        return "The Fancy Items of RRPG";
    }

    @Override
    public String getConfiguration() {
        return this.getDataFolder().getAbsolutePath();
    }
}
