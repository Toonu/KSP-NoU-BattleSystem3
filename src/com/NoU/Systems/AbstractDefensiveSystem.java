package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public abstract class AbstractDefensiveSystem extends AbstractSystem {
    private final SystemType type;
    private final double protection;
    private final boolean oversaturated = false;

    protected AbstractDefensiveSystem(int craftProductionYear, String name, SystemType type, double protection) {
        super(craftProductionYear, name);
        this.type = type;
        this.protection = protection;
    }
}
