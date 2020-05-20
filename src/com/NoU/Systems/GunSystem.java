package com.NoU.Systems;

import com.NoU.Crafts.Theatre;

import java.util.Set;

/**
 * @author Tomas Novotny
 */
public class GunSystem extends AbstractWeaponSystem implements IWeaponSystem {

    protected GunSystem(int craftProductionYear, String name, Set<Theatre> targets, double damage) {
        super(craftProductionYear, name, targets, damage);
    }
}
