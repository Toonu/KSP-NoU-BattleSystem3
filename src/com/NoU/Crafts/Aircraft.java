package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Tomas Novotny
 */
public class Aircraft extends AbstractCraft {
    public Aircraft(CClass cClass, CSubclass cSubclass, SortedMap<Double, List<IWeaponSystem>> weapons,
                    List<IDefensiveSystem> countermeasures, int craftProductionYear, String name, double health,
                    Side side, double speed) {
        super(cClass, cSubclass, weapons, countermeasures, craftProductionYear, name, health, side, speed);
    }
}
