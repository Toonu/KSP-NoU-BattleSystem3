package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.Theatre;

import java.util.Collections;
import java.util.Set;

/**
 * @author Tomas Novotny
 */
public class Weapon extends AbstractSystem {
    private final Set<Theatre> targets;

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
