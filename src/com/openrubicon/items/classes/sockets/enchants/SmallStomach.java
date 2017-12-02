package com.openrubicon.items.classes.sockets.enchants;

import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Material;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.HashSet;

public class SmallStomach extends Socket {
    private double modifier = 1;

    @Override
    public String getKey() {
        return "small_stomach";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.CHESTPLATES;
    }

    @Override
    public String getName() {
        return "Small Stomach";
    }

    @Override
    public String getDescription() {
        return "Increases the hunger level benefit when eating food.";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 0;
        double max = (this.getItemSpecs().getPower() / 2) * this.getItemSpecs().getRarity();

        max = Helpers.clampLowerBound(max, 1);

        this.modifier = (int) Helpers.scale(Helpers.randomDouble(min, max), min, max, 1.1, 2);

        this.modifier = Helpers.clamp(this.modifier, 1.1, 2);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("modifier", this.modifier);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.modifier = this.getSocketProperties().getDouble("modifier");

        return true;
    }

    @Override
    public void onFoodLevelChange(FoodLevelChangeEvent e, UniqueItem item, EntityInventorySlotType slot) {
        e.setFoodLevel((int)(e.getFoodLevel() * this.modifier));
    }
}
