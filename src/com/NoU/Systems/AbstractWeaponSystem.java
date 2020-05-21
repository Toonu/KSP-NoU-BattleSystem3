package com.NoU.Systems;

import com.NoU.Crafts.Theatre;

import java.util.Set;

/**
 * @author Toonu
 */
public abstract class AbstractWeaponSystem extends AbstractSystem {
    private final Set<Theatre> targets;
    private final double damage;

    protected AbstractWeaponSystem(int craftProductionYear, String name, Set<Theatre> targets,
                                   double damage, double minRange, double maxRange, Years years) {
        super(craftProductionYear, name, minRange, maxRange, years);
        this.targets = targets;
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public Set<Theatre> getTargets() {
        return targets;
    }
}
