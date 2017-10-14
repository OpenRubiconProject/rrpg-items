package com.openrubicon.items.classes.items.orbs;

import com.openrubicon.core.api.interfaces.Generatable;
import com.openrubicon.core.api.interfaces.Loreable;
import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.api.interfaces.Persistable;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.SpecialItem;
import com.openrubicon.items.classes.items.orbs.types.Orb;
import de.tr7zw.itemnbtapi.NBTCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class OrbItem extends SpecialItem implements Persistable, Loreable, Observeable, Generatable {

    public static final String ORB_COMPOUND = "RubiconOrbCompound";
    public static final String ORB = "RubiconOrb";

    private Orb orb;

    public OrbItem() {
        super();
    }

    public OrbItem(Orb orb) {
        this.orb = orb;
        this.setItemSpecs(orb.getItemSpecs());
        this.setItem(orb.getItem());

        super.generate();
    }

    public OrbItem(ItemStack item) {
        super(item);
    }

    public OrbItem(ItemStack item, boolean autoLoad) {
        super(item, autoLoad);
    }

    public void setOrb(Orb orb) {
        this.orb = orb;
    }

    public Orb getOrb() {
        return orb;
    }

    @Override
    public boolean generate() {
        if(orb == null)
        {
            orb = OrbFactory.random();
        }

        this.setItem(new ItemStack(Helpers.random(this.orb.getMaterials())));

        if(!super.generate())
            return false;

        orb.setNbt(this.getNbt());
        orb.setItem(this.getItem());
        orb.setItemSpecs(this.getItemSpecs());

        if(!orb.generate())
            return false;

        return true;
    }

    @Override
    public boolean save() {
        NBTCompound orbComp = this.getNbt().addCompound(ORB_COMPOUND);

        this.orb.setNbt(this.getNbt());

        if(!this.orb.save())
            return false;

        orbComp.setString(this.orb.getKey(), this.orb.getOrbProperties().getPersistenceString());

        this.getNbt().setString(ORB, "");

        return super.save();
    }

    @Override
    public boolean load()
    {
        if(!super.load())
            return false;

        if(!this.getNbt().hasKey(ORB))
        {
            this.setRightItemType(false);
            return false;
        }

        NBTCompound orbComp = this.getNbt().getCompound(ORB_COMPOUND);

        Set<String> orbs = orbComp.getKeys();

        if (orbs.size() == 0)
            return true;

        for (String key : orbs) {
            if (!orbComp.hasKey(key))
                continue;

            String persistenceString = orbComp.getString(key);

            Orb orb = OrbFactory.keyToSocket(key);
            if (orb == null)
                continue;

            orb.getOrbProperties().setPersistenceString(persistenceString);

            orb.setNbt(this.getNbt());
            orb.setItem(this.getItem());
            orb.setItemSpecs(this.getItemSpecs());

            orb.load();

            this.orb = orb;

            break;
        }

        return true;
    }

    @Override
    public ArrayList<String> getLore() {
        return this.orb.getLore();
    }

    @Override
    public ArrayList<String> getObservation() {
        return super.getObservation();
    }
}
