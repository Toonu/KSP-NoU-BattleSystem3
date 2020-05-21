package com.NoU.Systems;

import com.NoU.Crafts.Theatre;

import java.util.Set;

/**
 * @author Toonu
 */
public class GunSystem extends AbstractWeaponSystem implements IWeaponSystem {
    private int ammunition;

    protected GunSystem(int craftProductionYear, String name, Set<Theatre> targets, double damage, double minRange,
                        double maxRange, Years years) {
        super(craftProductionYear, name, targets, damage, minRange, maxRange, years);
    }

    @Override
    public double getRange() {
        return getMaxRange();
    }
}
