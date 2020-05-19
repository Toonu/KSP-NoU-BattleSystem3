package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public abstract class AbstractSystem {
    private final int craftProductionYear;
    private final String name;
    private final double range;

    protected AbstractSystem(int craftProductionYear, String name, double range) {
        this.craftProductionYear = craftProductionYear;
        this.name = name;
        this.range = range;
    }

    public int getCraftProductionYear() {
        return craftProductionYear;
    }

    public String getName() {
        return name;
    }
}
