package simulation.crafts.parts;

import simulation.crafts.systems.KSPPart;
import simulation.enums.Era;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class representing radar system and its atributes.
 */
public class Radar implements Serializable, KSPPart {
    private final String name;
    private final double radarRange;
    private final double strength;
    private final String internalKSPName;
    private final Era era;
    private boolean radarActive = true;

    /**
     * Constructor.
     *
     * @param radarRange      double radar range in km.
     * @param strength        strength of radar lock in RCS m at maximal range.
     * @param internalKSPName internal KSP name to search by analyzer.
     * @param name            name of the radar system.
     * @param era             Era the battleGui.crafts.systems belongs to.
     */
    public Radar(String name, double strength, double radarRange, Era era, String internalKSPName) {
        this.name = name;
        this.radarRange = radarRange;
        this.strength = strength;
        this.internalKSPName = internalKSPName;
        this.era = era;
    }

    /**
     * Method switches radar on and off.
     */
    public void switchRadar() {
        radarActive = !radarActive;
    }

    /**
     * Method returns true if radar is switched on.
     *
     * @return radarActive state.
     */
    public boolean isRadarActive() {
        return radarActive;
    }

    public double getRadarRange() {
        return radarRange;
    }

    public double getStrength() {
        return strength;
    }

    public Era getEra() {
        return era;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getInternalKSPName() {
        return internalKSPName;
    }

    /**
     * Method copy radar object.
     *
     * @return copy.
     */
    public Radar copy() {
        return new Radar(name, strength, radarRange, era, internalKSPName);
    }

    @Override
    public String toString() {
        String nameString = name;
        if (name.length() > 14) {
            nameString = nameString.substring(0, 13);
        }
        return String.format("%10s System %s %-14s [%25skm]", "RADAR", era, nameString, radarRange);
    }
}
