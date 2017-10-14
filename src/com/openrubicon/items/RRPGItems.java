package com.openrubicon.items;

import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.database.interfaces.DatabaseModel;
import com.openrubicon.core.api.recipes.interfaces.Recipe;
import com.openrubicon.core.interfaces.Module;
import com.openrubicon.items.classes.items.orbs.OrbProvider;
import com.openrubicon.items.classes.items.orbs.types.*;
import com.openrubicon.items.classes.sockets.SocketProvider;
import com.openrubicon.items.classes.sockets.abilities.*;
import com.openrubicon.items.classes.sockets.enchants.*;
import com.openrubicon.items.classes.sockets.effects.*;
import com.openrubicon.items.classes.sockets.events.SocketActionEventListener;
import com.openrubicon.items.commands.ItemGenerate;
import com.openrubicon.items.events.EventListener;
import com.openrubicon.items.recipes.anvil.ItemToSocketOrb;
import com.openrubicon.items.recipes.anvil.SocketOrbToItem;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 *
 * TODO:
 *
 * - v UniqueItem
 * - v Orbs
 * - Anvils
 * - v (Moved to Combat repo) Combat
 * - Switch SocketProvider to use the Service Provider API
 * - Create command classes
 * - Create configuration commands
 * - Create database models
 * - Create events
 *
 */
public class RRPGItems extends JavaPlugin implements Module {

    public static Plugin plugin;

    @Override
    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new SocketOrbToItem());
        recipes.add(new ItemToSocketOrb());
        return recipes;
    }

    @Override
    public ArrayList<DatabaseModel> getDatabaseModels() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Command> getCommands() {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new ItemGenerate());
        return commands;
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
        RRPGItems.plugin = this;

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new SocketActionEventListener(), this);
        getLogger().info("Registered Events");

        this.addOrbsToProvider();
        getLogger().info("Orbs injected");

        this.addSocketsToProvider();
        getLogger().info("Sockets injected");
    }

    @Override
    public void onDisable()
    {

    }

    public void addOrbsToProvider()
    {
        OrbProvider.add(new AttributeOrb());
        OrbProvider.add(new ElementalOrb());
        OrbProvider.add(new RarityOrb());
        OrbProvider.add(new RepairOrb());
        OrbProvider.add(new SocketOrb());
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
