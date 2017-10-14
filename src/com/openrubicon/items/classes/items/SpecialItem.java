package com.openrubicon.items.classes.items;

import com.openrubicon.core.api.clone.Cloner;
import com.openrubicon.core.api.interfaces.*;
import com.openrubicon.core.api.nbt.NBT;
import com.openrubicon.core.api.vault.items.Items;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.rarity.Rarity;
import com.openrubicon.core.rarity.RarityFactory;
import com.openrubicon.items.classes.items.nbt.ItemProperties;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import com.openrubicon.items.classes.items.specs.ItemSpecsFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class SpecialItem implements Persistable, Observeable, Loreable, Generatable {

    public static final String SPECIAL_ITEM = "RubiconSpecialItem";

    public static final String SPECS = "RubiconItemSpecs";

    private ItemStack item;

    private NBT nbt;

    private ItemProperties itemProperties = new ItemProperties();

    private ItemSpecs itemSpecs;

    private Rarity rarity;

    private UUID uuid;

    private boolean validItem = true;

    private boolean specialItem = true;

    private boolean rightItemType = true;

    public SpecialItem() {
    }

    public SpecialItem(SpecialItem oldSpecialItem) {
        Cloner cloner = new Cloner();

        this.item = new ItemStack(oldSpecialItem.getItem().getType());

        this.nbt = new NBT(this.item);

        this.uuid = UUID.randomUUID();

        this.itemProperties = cloner.deepClone(oldSpecialItem.getItemProperties());

        this.itemSpecs = cloner.deepClone(oldSpecialItem.getItemSpecs());

        rarity = RarityFactory.make((int)itemSpecs.getRarity());
    }

    public SpecialItem(ItemStack item) {
        this.item = item;
        this.load();
    }

    public SpecialItem(ItemStack item, boolean autoLoad) {
        this.item = item;
        if(autoLoad)
            this.load();
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public NBT getNbt() {
        return nbt;
    }

    public ItemProperties getItemProperties() {
        return itemProperties;
    }

    public ItemSpecs getItemSpecs() {
        return itemSpecs;
    }

    public void setItemSpecs(ItemSpecs itemSpecs) {
        this.itemSpecs = itemSpecs;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public UUID getUuid() {
        return uuid;
    }

    protected void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public boolean isValid() {
        return this.validItem;
    }

    public boolean isSpecialItem() {
        return specialItem;
    }

    public boolean isRightItemType() {
        return rightItemType;
    }

    public void setRightItemType(boolean rightItemType) {
        this.rightItemType = rightItemType;
    }

    @Override
    public boolean generate()
    {
        this.nbt = new NBT(this.item);

        this.itemProperties.addBoolean(SPECIAL_ITEM, true);

        this.uuid = UUID.randomUUID();

        this.itemProperties.add(Constants.UUID, this.uuid.toString());

        if(itemSpecs == null)
            itemSpecs = ItemSpecsFactory.generateSpecs();

        rarity = RarityFactory.make((int)itemSpecs.getRarity());

        return true;
    }

    @Override
    public boolean save()
    {
        if(!specialItem || !validItem)
            return false;

        this.itemProperties.save();

        this.nbt.setString(ItemProperties.NBT_PROPERTIES, this.itemProperties.getPersistenceString());

        this.nbt.setObject(SPECS, this.itemSpecs);

        this.item = this.nbt.getItem();

        this.initializeItemMeta();

        return true;
    }

    @Override
    public boolean load()
    {
        if(this.item == null)
        {
            this.validItem = false;
            return false;
        }

        this.nbt = new NBT(this.item);

        if(!this.nbt.hasKey(ItemProperties.NBT_PROPERTIES))
        {
            this.specialItem = false;
            return false;
        }

        this.itemProperties.setPersistenceString(this.nbt.getString(ItemProperties.NBT_PROPERTIES));

        this.itemProperties.load();

        if(!(this.itemProperties.getBoolean(SPECIAL_ITEM)))
        {
            this.specialItem = false;
            return false;
        }

        this.uuid = UUID.fromString(this.itemProperties.get(Constants.UUID));

        this.itemSpecs = this.nbt.getObject(SPECS, ItemSpecs.class);

        this.rarity = RarityFactory.make((int)this.getItemSpecs().getRarity());

        return true;
    }

    protected void initializeItemMeta()
    {
        ItemMeta meta = this.item.getItemMeta();
        String[] s = Items.itemNameByType(this.item.getType()).split(" ");
        meta.setDisplayName(Helpers.colorize(this.getRarity().getColor() + this.getRarity().getName() + " " + s[s.length - 1]));
        meta.setLore(this.getLore());
        this.item.setItemMeta(meta);
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();

        if(this.specialItem)
        {
            lore.add(Constants.HEADING_COLOR + "Core Stats");
            lore.addAll(this.rarity.getLore());
            lore.addAll(this.itemSpecs.getLore());
            lore.add("");
        }

        return Helpers.colorize(lore);
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> view = new ArrayList<>();

        if(this.specialItem) {
            view.add("&6Core Stats");

            view.addAll(this.rarity.getObservation());
            view.add("");
            view.addAll(this.getItemSpecs().getObservation());
        }

        return Helpers.colorize(view);
    }

    public float getCostModifier()
    {
        return this.getRarity().getCostModifier();
    }
}
