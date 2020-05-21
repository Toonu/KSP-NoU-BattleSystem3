package com.NoU.Enum;

/**
 * @author Toonu
 */
public enum Age {
    X1950(1), X1960(1.2), X1970(1.4), X1980(1.6), X1990(1.8), X2000(2),
    X2010(2.2), X2020(2.5);

    private final double modifier;

    Age(double modifier) {
        this.modifier = modifier;
    }

    public double getModifier() {
        return modifier;
    }
}
