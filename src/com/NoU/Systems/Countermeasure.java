package com.NoU.Systems;

import com.NoU.Enum.CMType;
import com.NoU.Enum.Era;
import com.NoU.Enum.GuidanceType;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * @author Toonu
 * <p>
 * Class representing Countermeasure and its properities.
 */
public class Countermeasure extends AbstractSystem implements Serializable, Comparable<Countermeasure> {
    private final CMType type;
    private boolean oversaturated = false;

    /**
     * Constructor.
     *
     * @param strength double strength value of system for defense strength.
     * @param maxRange double maximal range of system.
     * @param minRange double minimal range of system.
     * @param name     String name of the system.
     * @param era      Era enum of era of the system.
     * @param type     CMType enum representing countermeasure type.
     */
    public Countermeasure(double strength, double minRange, double maxRange, String name, Era era, CMType type) {
        super(strength, maxRange, minRange, name, era);
        this.type = type;
    }

    public CMType getType() {
        return type;
    }

    public EnumSet<GuidanceType> getTargets() {
        return type.getTargets();
    }

    public boolean isOversaturated() {
        return oversaturated;
    }

    public void saturate() {
        oversaturated = !oversaturated;
    }

    public Countermeasure copyCountermeasure() {
        return new Countermeasure(getStrength(), getMinRange(), getMaxRange(), getName(), getEra(), type);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Countermeasure o) {
        return Double.compare(getMaxRange(), o.getMaxRange());
    }

    @Override
    public String toString() {
        return String.format("%10s %s", type, super.toString());
    }
}
