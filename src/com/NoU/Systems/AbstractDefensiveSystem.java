package com.NoU.Systems;

/**
 * @author Toonu
 */
public abstract class AbstractDefensiveSystem extends AbstractSystem implements IDefensiveSystem {
    private final SystemType type;
    private final double strength;
    private final boolean saturable;
    private final boolean oversaturated = false;
    private final Guidance protecting;

    protected AbstractDefensiveSystem(int craftProductionYear, String name, SystemType type,
                                      double strength, double minRange, double maxRange, Guidance protecting,
                                      boolean saturable, Years years) {
        super(craftProductionYear, name, maxRange, minRange, years);
        this.type = type;
        this.strength = 20 * years.getModifier();
        this.protecting = protecting;
        this.saturable = saturable;
    }

    public double getStrength() {
        return strength;
    }

    public SystemType getType() {
        return type;
    }

    public boolean isSaturable() {
        return saturable;
    }

    public boolean isOversaturated() {
        return oversaturated;
    }

    public Guidance getProtecting() {
        return protecting;
    }
}
