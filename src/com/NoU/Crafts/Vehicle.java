package com.NoU.Crafts;

import com.NoU.Enum.Age;
import com.NoU.Enum.Colors;
import com.NoU.Enum.Type;
import com.NoU.Systems.Armor;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Radar;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public class Vehicle extends Craft implements RadarVehicle {
    private Armor armor;
    private Radar radar;

    protected Vehicle(double speed, String name, SortedMap<Double, List<Weapon>> weapons, SortedMap<Double,
            List<Countermeasure>> countermeasures, Type type, Age craftProductionYear, Colors side, Vertex2D position) {
        super(speed, name, weapons, countermeasures, type, craftProductionYear, side, position);
    }

    /**
     * Method returns radar object of the vehicle.
     *
     * @return Radar object.
     */
    @Override
    public Radar getRadar() {
        return radar;
    }

    public Armor getArmor() {
        return armor;
    }
}
