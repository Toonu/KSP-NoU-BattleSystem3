package com.NoU.Crafts;

import com.NoU.Crafts.Parts.Radar;

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
     * @throws NullPointerException if there is no radar on the vehicle.
     */
    Radar getRadar() throws NullPointerException;

    /**
     * Method assigns radar to the vehicle.
     *
     * @param radar Radar to add.
     */
    void addRadar(Radar radar);
}
