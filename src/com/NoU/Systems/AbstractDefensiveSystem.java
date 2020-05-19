package com.NoU.Systems;

/**
 * @author Tomas Novotny
 */
public abstract class AbstractDefensiveSystem extends AbstractSystem {
    private final SystemType type;
    private final double protection;
    private final boolean oversaturated = false;

    protected AbstractDefensiveSystem(int craftProductionYear, String name, SystemType type,
                                      double protection, double range) {
        super(craftProductionYear, name, range);
        this.type = type;
        this.protection = protection;
    }

    public double getProtection() {
        return protection;
    }

    public SystemType getType() {
        return type;
    }

    public boolean isOversaturated() {
        return oversaturated;
    }
}
