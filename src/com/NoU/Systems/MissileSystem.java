package com.NoU.Systems;

import com.NoU.Crafts.Theatre;

import java.util.Set;

/**
 * @author Tomas Novotny
 */
public class MissileSystem extends AbstractWeaponSystem implements IWeaponSystem {

    protected MissileSystem(int craftProductionYear, String name, Set<Theatre> targets, double damage) {
        super(craftProductionYear, name, targets, damage);
    }
}
