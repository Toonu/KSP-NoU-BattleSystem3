package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Tomas Novotny
 */
public class Vessel extends AbstractCraft {
    public Vessel(Type type, SortedMap<Double, List<IWeaponSystem>> weapons,
                  SortedMap<Double, List<IDefensiveSystem>> countermeasures, int craftProductionYear, String name,
                  Side side, double speed) {
        super(type, weapons, countermeasures, craftProductionYear, name, side, speed);
    }
}
