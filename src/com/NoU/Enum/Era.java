package com.NoU.Enum;

/**
 * @author Toonu
 * <p>
 * Enum to represent Eras and their modifiers to the craft's effectivity.
 */
public enum Era {
    Era1950(1), Era1960(1.2), Era1970(1.4), Era1980(1.6), Era1990(1.8), Era2000(2),
    Era2010(2.2), Era2020(2.5);

    private final double modifier;

    Era(double modifier) {
        this.modifier = modifier;
    }

    public double getModifier() {
        return modifier;
    }

    @Override
    public String toString() {
        return name().substring(3);
    }
}
