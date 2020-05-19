package com.NoU.Systems;

import com.NoU.Crafts.CClass;

import java.util.Set;

/**
 * @author Tomas Novotny
 */
public class MissileSystem extends AbstractWeaponSystem implements IWeaponSystem {

    protected MissileSystem(int craftProductionYear, String name, Set<CClass> targets, double damage) {
        super(craftProductionYear, name, targets, damage);
    }
}
