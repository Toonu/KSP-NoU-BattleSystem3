package systems;

import enums.CMType;
import enums.Era;
import enums.GuidanceType;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * @author Toonu
 * <p>
 * Class representing Countermeasure and its properities.
 */
public class Countermeasure extends AbstractSystem implements Serializable, Comparable<Countermeasure>, KSPPart {
    private final CMType type;
    private boolean saturated = false;
    private int usages = 0;

    /**
     * Constructor.
     *
     * @param strength        double strength value of system for defense strength.
     * @param maxRange        double maximal range of system.
     * @param minRange        double minimal range of system.
     * @param name            String name of the system.
     * @param era             Era enum of era of the system.
     * @param type            CMType enum representing countermeasure type.
     * @param internalKSPName internal KSP part name.
     */
    public Countermeasure(double strength, double minRange, double maxRange, String name,
                          Era era, CMType type, String internalKSPName) {
        super(strength, maxRange, minRange, name, era, internalKSPName);
        this.type = type;
    }

    public CMType getType() {
        return type;
    }

    public EnumSet<GuidanceType> getTargets() {
        return type.getTargets();
    }

    public boolean isSaturated() {
        return saturated;
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

    /**
     * Shorter representation.
     *
     * @return String.
     */
    public String toShortString() {
        return String.format("%10s %s", type, this.getName());
    }

    /**
     * Copy countermeasure.
     *
     * @return new cloned Object.
     */
    public Countermeasure copy() {
        return new Countermeasure(getStrength(), getMinRange(), getMaxRange(), getName(),
                getEra(), type, getInternalKSPName());
    }

    /**
     * Method increases usage after countermeasure was used against attack and saturate it if it is over limits.
     */
    public void use() {
        ++usages;
        if (usages > Double.parseDouble(getEra().toString())) {
            saturated = true;
            usages = 3;
        }
    }

    /**
     * Method cools down the delay after usage of the countermeasure.
     */
    public void cooldown() {
        --usages;
    }
}
