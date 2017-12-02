package com.openrubicon.items;

import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.configuration.ConfigurationProperty;
import com.openrubicon.core.api.database.interfaces.DatabaseModel;
import com.openrubicon.core.api.recipes.interfaces.Recipe;
import com.openrubicon.core.api.scoreboard.ScoreboardSectionService;
import com.openrubicon.core.api.scoreboard.interfaces.ScoreboardSection;
import com.openrubicon.core.api.services.interfaces.Service;
import com.openrubicon.core.configuration.DevMode;
import com.openrubicon.core.interfaces.Module;
import com.openrubicon.items.classes.items.orbs.OrbProvider;
import com.openrubicon.items.classes.items.orbs.types.*;
import com.openrubicon.items.classes.scoreboard.Cooldowns;
import com.openrubicon.items.classes.sockets.Socket;
import com.openrubicon.items.classes.sockets.SocketProvider;
import com.openrubicon.items.classes.sockets.abilities.*;
import com.openrubicon.items.classes.sockets.enchants.*;
import com.openrubicon.items.classes.sockets.effects.*;
import com.openrubicon.items.classes.sockets.events.SocketActionEventListener;
import com.openrubicon.items.commands.*;
import com.openrubicon.items.configuration.SocketOrbSpawnChance;
import com.openrubicon.items.events.EventListener;
import com.openrubicon.items.recipes.anvil.ItemToSocketOrb;
import com.openrubicon.items.recipes.anvil.SocketOrbToItem;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * TODO:
 *
 * - v UniqueItem
 * - v Orbs
 * - v Anvils
 * - v (Moved to Combat repo) Combat
 * - v Switch SocketProvider to use the Service Provider API
 * - v Create command classes
 * - Create configuration classes
 * - Create database models
 * - Create events
 *
 */
public class RRPGItems extends JavaPlugin implements Module {

    public static Plugin plugin;

    @Override
    public ArrayList<ScoreboardSection> getScoreboardSections() {
        ArrayList<ScoreboardSection> scoreboardSections = new ArrayList<>();
        scoreboardSections.add(new Cooldowns());
        return scoreboardSections;
    }

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
        commands.add(new ItemGenerate$());
        commands.add(new ItemGenerateType());
        commands.add(new ItemGenerateWithAllSpecs());
        commands.add(new ItemGenerateWithCoreSpecs());
        commands.add(new ItemGenerateWithSpecsAndDurability());
        commands.add(new ItemView());
        commands.add(new ItemShow());
        commands.add(new ItemViewSlot());
        return commands;
    }

    @Override
    public LinkedList<ConfigurationProperty> getConfigurationProperties() {
        LinkedList<ConfigurationProperty> properties = new LinkedList<>();
        properties.add(new SocketOrbSpawnChance());
        return properties;
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

        RRPGCore.services.create(new SocketProvider());
        RRPGCore.services.create(new OrbProvider());

        this.addOrbsToProvider();
        getLogger().info("Orbs injected");

        this.addSocketsToProvider();
        getLogger().info("Sockets injected");

        if(RRPGCore.config.get(DevMode.class).getBoolean())
        {
            getLogger().info(" ");
            getLogger().info("===== DUMPING SOCKET INFORMATION =====");
            for(Socket socket : SocketProvider.getSockets().values())
            {
                getLogger().info("  " + socket.getName());
            }

            getLogger().info(" ");

            getLogger().info("===== DUMPING ORB INFORMATION =====");
            for(Orb orb : RRPGCore.services.getSerivce(OrbProvider.class).getOrbs().values())
            {
                getLogger().info("  " + orb.getName());
            }
            getLogger().info(" ");

        }

    }

    @Override
    public void onDisable()
    {

    }

    public void addOrbsToProvider()
    {
        RRPGCore.services.getSerivce(OrbProvider.class).add(new AttributeOrb());
        RRPGCore.services.getSerivce(OrbProvider.class).add(new ElementalOrb());
        RRPGCore.services.getSerivce(OrbProvider.class).add(new RarityOrb());
        RRPGCore.services.getSerivce(OrbProvider.class).add(new RepairOrb());
        RRPGCore.services.getSerivce(OrbProvider.class).add(new SocketOrb());
    }

    public void addSocketsToProvider()
    {
        // Abilities
        RRPGCore.services.getSerivce(SocketProvider.class).add(new ThrustUp());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new GroundPound());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new GroundSlam());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Groundsplosion());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Mark());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new RadialMark());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Launch());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Bounce());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Unbreakable());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Scare());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new NaughtyCreeper());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new TreeHugger());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Stab());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new SocketFarmer());

        // Effects
        RRPGCore.services.getSerivce(SocketProvider.class).add(new MarathonRunner());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Bandage());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Martyrdom());
        //RRPGCore.services.getSerivce(SocketProvider.class).add(new Jarvis()); TODO: Move to combat repo
        RRPGCore.services.getSerivce(SocketProvider.class).add(new FlightSuit());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new SteelFeet());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Striking());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new ClutchBarrier());

        //Enchants
        //RRPGCore.services.getSerivce(SocketProvider.class).add(new Reinforced()); TODO: Move to combat repo
        RRPGCore.services.getSerivce(SocketProvider.class).add(new CooldownReduction());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Spam());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new SmallStomach());
    }
}
