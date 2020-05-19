package com.NoU.Systems;

import com.NoU.Crafts.CClass;

import java.util.Set;

/**
 * @author Tomas Novotny
 */
public abstract class AbstractWeaponSystem extends AbstractSystem {
    private final Set<CClass> targets;
    private final double damage;

    protected AbstractWeaponSystem(int craftProductionYear, String name, Set<CClass> targets,
                                   double damage, double range) {
        super(craftProductionYear, name, range);
        this.targets = targets;
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public Set<CClass> getTargets() {
        return targets;
    }
}
