package com.openrubicon.items;

import com.openrubicon.core.RRPGCore;
import org.bukkit.plugin.java.JavaPlugin;
import com.openrubicon.core.interfaces.iModule;

public class RRPGItems extends JavaPlugin implements iModule {

    @Override
    public void onLoad()
    {
        RRPGCore.modules.addModule(this);
    }

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
