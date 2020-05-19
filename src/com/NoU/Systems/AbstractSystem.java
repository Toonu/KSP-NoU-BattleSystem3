package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public abstract class AbstractSystem {
    private final int craftProductionYear;
    private final String name;

    protected AbstractSystem(int craftProductionYear, String name) {
        this.craftProductionYear = craftProductionYear;
        this.name = name;
    }
}
