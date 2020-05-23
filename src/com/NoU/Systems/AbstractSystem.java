package com.NoU.Systems;

import com.NoU.Enum.Era;

import java.io.Serializable;
import java.time.LocalTime;

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
        if (strength > 0 && maxRange > 0 && minRange >= 0) {
            this.strength = strength;
            this.maxRange = maxRange;
            this.minRange = minRange;
        } else {
            System.err.println(
                    String.format("[ERR %s] Error in creating the system. Values must be positive", LocalTime.now()));
            throw new IllegalArgumentException("Invalid Arguments for creation of AbstractSystem.");
        }
        if (name.equals("")) {
            this.name = "DefaultName";
            System.err.println(String.format("[LOG %s] Error in creating the system. " +
                    "No name present > Name changed to default.", LocalTime.now()));
        } else {
            this.name = name;
        }
        this.era = era;
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

    @Override
    public String toString() {
        return String.format("System %s %s [S: %s, Min: %s, Max: %s]", era, name, strength, minRange, maxRange);
    }
}
