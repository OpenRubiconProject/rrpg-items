package com.openrubicon.items.classes.items.specs;

import com.openrubicon.core.api.interfaces.Loreable;
import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;

import java.util.ArrayList;

public class ItemSpecs implements Loreable, Observeable {

    private double rarity;
    private double sockets;
    private double power;

    private double attributePoints;

    private double durabilityMax;
    private double durabilityCurrent;

    private double totalPossibilities;
    private double thisPossibility;

    public ItemSpecs(double rarity, double sockets, double power, double attributePoints, double durabilityMax, double durabilityCurrent, double totalPossibilities, double thisPossibility) {
        this.rarity = rarity;
        this.sockets = sockets;
        this.power = power;
        this.attributePoints = attributePoints;
        this.durabilityMax = durabilityMax;
        this.durabilityCurrent = durabilityCurrent;
        this.totalPossibilities = totalPossibilities;
        this.thisPossibility = thisPossibility;
    }

    public double getRarity() {
        return rarity;
    }

    public void setRarity(double rarity) {
        this.rarity = rarity;
    }

    public double getSockets() {
        return sockets;
    }

    public void setSockets(double sockets) {
        this.sockets = sockets;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getAttributePoints() {
        return attributePoints;
    }

    public void setAttributePoints(double attributePoints) {
        this.attributePoints = attributePoints;
    }

    public double getDurabilityMax() {
        return durabilityMax;
    }

    public void setDurabilityMax(double durabilityMax) {
        this.durabilityMax = durabilityMax;
    }

    public double getDurabilityCurrent() {
        return durabilityCurrent;
    }

    public void setDurabilityCurrent(double durabilityCurrent) {
        this.durabilityCurrent = durabilityCurrent;
    }

    public double getTotalPossibilities() {
        return totalPossibilities;
    }

    public void setTotalPossibilities(double totalPossibilities) {
        this.totalPossibilities = totalPossibilities;
    }

    public double getThisPossibility() {
        return thisPossibility;
    }

    public void setThisPossibility(double thisPossibility) {
        this.thisPossibility = thisPossibility;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Constants.PRIMARY_COLOR + "Power: " + Constants.SECONDARY_COLOR + (int)this.getPower());
        lore.add(Constants.PRIMARY_COLOR + "Sockets: " + Constants.SECONDARY_COLOR + (int)this.getSockets());
        lore.add(Constants.PRIMARY_COLOR + "Points: " + Constants.SECONDARY_COLOR + (int)this.getAttributePoints());
        lore.add(Constants.PRIMARY_COLOR + "Durability: " + Constants.SECONDARY_COLOR + (int)this.getDurabilityCurrent() + "/" + (int)this.getDurabilityMax());
        return lore;
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> view = new ArrayList<>();
        view.add(Constants.PRIMARY_COLOR + "Power: " + Constants.SECONDARY_COLOR + (int)this.getPower());
        view.add(Constants.PRIMARY_COLOR + "Sockets: " + Constants.SECONDARY_COLOR + (int)this.getSockets());
        view.add(Constants.PRIMARY_COLOR + "Points: " + Constants.SECONDARY_COLOR + (int)this.getAttributePoints());
        view.add(Constants.PRIMARY_COLOR + "Durability: " + Constants.SECONDARY_COLOR + (int)this.getDurabilityCurrent() + "/" + (int)this.getDurabilityMax());
        return Helpers.colorize(view);
    }

}
