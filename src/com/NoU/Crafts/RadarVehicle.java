package com.NoU.Crafts;

import com.NoU.Systems.Radar;

/**
 * @author Toonu
 *
 * Interface to group all radar equipped vehicles to be targetable by Anti-radiation weapons.
 */
public interface RadarVehicle {
    /**
     * Method returns radar object of the vehicle.
     *
     * @return Radar object.
     */
    Radar getRadar();
}
