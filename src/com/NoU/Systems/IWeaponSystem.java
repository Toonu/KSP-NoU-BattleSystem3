package com.NoU.Systems;

import com.NoU.Crafts.CClass;

import java.util.Set;

/**
 * @author Toonu
 */
public interface IWeaponSystem {
    double getDamage();

    Set<CClass> getTargets();

    double getRange();
}
