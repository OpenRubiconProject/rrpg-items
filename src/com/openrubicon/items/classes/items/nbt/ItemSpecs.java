package com.openrubicon.items.classes.items.nbt;

public class ItemSpecs {

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

}
