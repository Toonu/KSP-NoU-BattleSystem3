package com.NoU.Systems;

import com.NoU.Crafts.Theatre;

import java.util.Set;

/**
 * @author Toonu
 */
public interface IWeaponSystem {
    double getDamage();

    Set<Theatre> getTargets();

    double getRange();
}
