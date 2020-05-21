package com.NoU.Crafts;

import com.NoU.Enum.Age;
import com.NoU.Enum.Colors;
import com.NoU.Enum.Type;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public class Aircraft extends Craft {

    protected Aircraft(double speed, String name, SortedMap<Double, List<Weapon>> weapons, SortedMap<Double,
            List<Countermeasure>> countermeasures, Type type, Age craftProductionYear, Colors side, Vertex2D position) {
        super(speed, name, weapons, countermeasures, type, craftProductionYear, side, position);
    }
}
