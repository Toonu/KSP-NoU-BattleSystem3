package com.NoU.Systems;

import com.NoU.Enum.Era;
import com.NoU.Enum.Theatre;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class simulating Weapon and its targetable types.
 */
public class Weapon extends AbstractSystem implements Serializable {
    private final Set<Theatre> targets;

    /**
     * Constructor.
     *
     * @param damage   double strength value of system for attack damage.
     * @param maxRange double maximal range of system.
     * @param minRange double minimal range of system.
     * @param name     String name of the system.
     * @param era      Era enum of era of the system.
     * @param targets  Set of Theatre objects targetable by the system.
     */
    protected Weapon(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Era era) {
        super(damage, maxRange, minRange, name, era);
        this.targets = targets;
    }

    public Set<Theatre> getTargets() {
        return Collections.unmodifiableSet(targets);
    }

    public Weapon getWeapon() {
        return new Weapon(getStrength(), getMinRange(), getMaxRange(), targets, getName(), getEra());
    }
}
