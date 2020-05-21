package com.NoU.Crafts;

import com.NoU.Side;
import com.NoU.Systems.IDefensiveSystem;
import com.NoU.Systems.IWeaponSystem;
import com.NoU.Systems.Years;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public class RadarVehicle extends AbstractCraft {
    private final boolean radarActive = true;
    private IDefensiveSystem radar;

    protected RadarVehicle(Type type, SortedMap<Double, List<IWeaponSystem>> weapons,
                           SortedMap<Double, List<IDefensiveSystem>> countermeasures, Years craftProductionYear,
                           String name, Side side, double speed) {
        super(type, weapons, countermeasures, craftProductionYear, name, side, speed);
    }
}
