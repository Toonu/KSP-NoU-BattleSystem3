package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.Theatre;

import java.util.Collections;
import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class simulating Weapon and its targetable types.
 */
public class Weapon extends AbstractSystem {
    private final Set<Theatre> targets;

    /**
     * Constructor.
     *
     * @param damage   double strength value of system for attack damage.
     * @param maxRange double maximal range of system.
     * @param minRange double minimal range of system.
     * @param name     String name of the system.
     * @param age      Age enum of era of the system.
     * @param targets  Set of Theatre objects targetable by the system.
     */
    protected Weapon(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Age age) {
        super(damage, maxRange, minRange, name, age);
        this.targets = targets;
    }

    public Set<Theatre> getTargets() {
        return Collections.unmodifiableSet(targets);
    }

    public Weapon getWeapon() {
        return new Weapon(getStrength(), getMinRange(), getMaxRange(), targets, getName(), getAge());
    }
}
