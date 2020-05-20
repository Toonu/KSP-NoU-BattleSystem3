package com.NoU.Systems;

import com.NoU.Crafts.Theatre;

import java.util.Set;

/**
 * @author Tomas Novotny
 */
public abstract class AbstractWeaponSystem extends AbstractSystem {
    private final Set<Theatre> targets;
    private final double damage;

    protected AbstractWeaponSystem(int craftProductionYear, String name, Set<Theatre> targets,
                                   double damage, double range) {
        super(craftProductionYear, name, range);
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
