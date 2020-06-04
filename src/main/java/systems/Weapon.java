package systems;

import enums.Era;
import enums.Theatre;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class simulating Weapon and its targetable types.
 */
public class Weapon extends AbstractSystem implements Serializable, Comparable<Weapon>, KSPPart {
    private final Set<Theatre> targets;
    private final boolean internal;

    /**
     * Constructor.
     *
     * @param damage   double strength value of system for attack damage.
     * @param maxRange double maximal range of system.
     * @param minRange double minimal range of system.
     * @param name     String name of the system.
     * @param era      Era enum of era of the system.
     * @param targets  Set of Theatre objects can be targeted by the system.
     */
    protected Weapon(double damage, double minRange, double maxRange, Set<Theatre> targets,
                     String name, Era era, boolean internal, String internalName) {
        super(damage, maxRange, minRange, name, era, internalName);
        this.targets = targets;
        this.internal = internal;
    }

    public Set<Theatre> getTargets() {
        return Collections.unmodifiableSet(targets);
    }

    /**
     * Method returns copy of the weapon as new object.
     *
     * @return copy of the object.
     */
    public Weapon copy() {
        return new Weapon(getStrength(), getMinRange(), getMaxRange(), targets,
                getName(), getEra(), internal, getInternalKSPName());
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
    public int compareTo(Weapon o) {
        return Double.compare(getMaxRange(), o.getMaxRange());
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public boolean isInternal() {
        return internal;
    }
}
