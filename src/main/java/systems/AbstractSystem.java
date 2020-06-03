package systems;

import enums.Era;
import impl.App;

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
    private final String internalKSPName;

    /**
     * Constructor.
     *
     * @param strength        double strength value of system for either defense or attack damage.
     * @param maxRange        double maximal range of system.
     * @param minRange        double minimal range of system.
     * @param name            String name of the system.
     * @param era             Era enum of era of the system.
     * @param internalKSPName internal KSP name to search by analyzer.
     */
    public AbstractSystem(double strength, double maxRange, double minRange, String name,
                          Era era, String internalKSPName) {
        if (strength < 0 && maxRange < 0 && minRange <= 0) {
            App.err("Error in creating the system. Values must be positive", true, true);
        }

        this.strength = strength;
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.internalKSPName = internalKSPName;

        if (name.equals("")) {
            this.name = "DefaultName";
            App.err("Name is null. Assigning default name.", true, true);
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

    public String getInternalKSPName() {
        return internalKSPName;
    }

    @Override
    public String toString() {
        String nameString = name;
        if (name.length() > 14) {
            nameString = nameString.substring(0, 13);
        }
        return String.format("System %s %-14s [S: %5s, %5skm-%7skm]", era, nameString, strength, minRange, maxRange);
    }

    /**
     * Shorter representation.
     *
     * @return String.
     */
    public String toShortString() {
        return null;
    }

    /**
     * Makes  copy of this AbstractSystem.
     *
     * @return copy.
     */
    public AbstractSystem copy() {
        return new AbstractSystem(strength, maxRange, minRange, name, era, internalKSPName);
    }
}
