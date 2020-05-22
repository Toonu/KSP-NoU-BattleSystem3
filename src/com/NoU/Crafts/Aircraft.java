package com.NoU.Crafts;

import com.NoU.Enum.Age;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Type;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 * <p>
 * Class to simulate Aircraft Craft on the battlefield.
 */
public class Aircraft extends Craft {
    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param weapons             SortedMap<Double, List<Weapon>> containing values list of weapons sorted by their double range.
     * @param countermeasures     SortedMap<Double, List<Countermeasure>>
     *                            containing values list of countermeasures sorted by their double range.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Age enum of the Eras of crafts.
     * @param side                Enum color of craft's side.
     */
    protected Aircraft(double speed, String name, SortedMap<Double, List<Weapon>> weapons, SortedMap<Double,
            List<Countermeasure>> countermeasures, Type type, Age craftProductionYear, Sides side) {
        super(speed, name, weapons, countermeasures, type, craftProductionYear, side);
    }
}
