package com.NoU.Systems;


/**
 * @author Toonu
 */
public abstract class AbstractSystem {
    private final int craftProductionYear;
    private final String name;
    private final double maxRange;
    private final double minRange;
    private Years years;

    protected AbstractSystem(int craftProductionYear, String name, double maxRange, double minRange, Years years) {
        this.craftProductionYear = craftProductionYear;
        this.name = name;
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.years = years;
    }

    public int getCraftProductionYear() {
        return craftProductionYear;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public double getMinRange() {
        return minRange;
    }

    public String getName() {
        return name;
    }

}
