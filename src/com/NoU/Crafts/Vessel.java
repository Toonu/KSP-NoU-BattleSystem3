package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;

import java.util.List;
import java.util.Map;

/**
 * @author Tomas Novotny
 */
public class Vessel extends AbstractCraft{
    public Vessel(CClass cClass, CSubclass cSubclass, Map<CClass, List<IWeaponSystem>> weapons,
                  List<IDefensiveSystem> countermeasures, int craftProductionYear, String name, double health,
                  Side side) {
        super(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name, health, side);
    }
}
