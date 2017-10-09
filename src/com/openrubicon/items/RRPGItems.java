package com.openrubicon.items;

import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.database.interfaces.DatabaseModel;
import com.openrubicon.core.interfaces.Module;
import com.openrubicon.items.classes.sockets.SocketProvider;
import com.openrubicon.items.classes.sockets.abilities.*;
import com.openrubicon.items.classes.sockets.enchants.*;
import com.openrubicon.items.classes.sockets.effects.*;
import com.openrubicon.items.events.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class RRPGItems extends JavaPlugin implements Module {

    @Override
    public ArrayList<DatabaseModel> getDatabaseModels() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Command> getCommands() {
        return new ArrayList<>();
    }

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

    @Override
    public void onEnable()
    {
        this.addSocketsToProvider();
        getLogger().info("Sockets injected");

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getLogger().info("Registered Events");
    }

    @Override
    public void onDisable()
    {

    }

    public void addSocketsToProvider()
    {
        // Abilities
        SocketProvider.add(new ThrustUp());
        SocketProvider.add(new GroundPound());
        SocketProvider.add(new GroundSlam());
        SocketProvider.add(new Groundsplosion());
        SocketProvider.add(new Mark());
        SocketProvider.add(new RadialMark());
        SocketProvider.add(new Launch());
        SocketProvider.add(new Bounce());
        SocketProvider.add(new Unbreakable());
        SocketProvider.add(new Scare());
        SocketProvider.add(new NaughtyCreeper());
        SocketProvider.add(new TreeHugger());
        SocketProvider.add(new Stab());

        // Effects
        SocketProvider.add(new MarathonRunner());
        SocketProvider.add(new Bandage());
        SocketProvider.add(new Martyrdom());
        SocketProvider.add(new Jarvis());
        SocketProvider.add(new FlightSuit());
        SocketProvider.add(new SteelFeet());
        SocketProvider.add(new Striking());
        SocketProvider.add(new ClutchBarrier());

        //Enchants
        SocketProvider.add(new Reinforced());
        SocketProvider.add(new CooldownReduction());
        SocketProvider.add(new Spam());
    }
}
