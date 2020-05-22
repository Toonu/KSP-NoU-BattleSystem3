package com.NoU.Systems;

import com.NoU.Enum.Era;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class representing System basic values extended in subclasses.
 */
public class AbstractSystem implements Serializable {
    private final double strength;
    private final double maxRange;
    private final double minRange;
    private final Era era;
    private final String name;

    /**
     * Constructor.
     *
     * @param strength double strength value of system for either defense or attack damage.
     * @param maxRange double maximal range of system.
     * @param minRange double minimal range of system.
     * @param name     String name of the system.
     * @param era      Era enum of era of the system.
     */
    public AbstractSystem(double strength, double maxRange, double minRange, String name, Era era) {
        this.strength = strength;
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.era = era;
        this.name = name;
    }

    public double getStrength() {
        return strength;
    }

    public Era getEra() {
        return era;
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
