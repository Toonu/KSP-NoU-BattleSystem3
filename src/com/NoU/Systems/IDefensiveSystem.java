package com.NoU.Systems;

/**
 * @author Toonu
 */
public interface IDefensiveSystem {

    SystemType getType();

    double getMaxRange();

    double getStrength();

    double getMinRange();

    boolean isSaturable();

    boolean isOversaturated();

    Guidance getProtecting();
}
