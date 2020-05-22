package com.NoU.Crafts;

import com.NoU.Crafts.Parts.Radar;
import com.NoU.Enum.Era;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Type;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;

import java.io.Serializable;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 * <p>
 * Class to simulate Ship or Vessel Craft on the battlefield.
 */
public class Vessel extends Craft implements RadarVehicle, Serializable {
    private Radar radar;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param weapons             SortedMap<Double, List<Weapon>> containing values list of weapons sorted by their double range.
     * @param countermeasures     SortedMap<Double, List<Countermeasure>>
     *                            containing values list of countermeasures sorted by their double range.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                Enum color of craft's side.
     */
    protected Vessel(double speed, String name,
                     SortedMap<Double, List<Weapon>> weapons, SortedMap<Double, List<Countermeasure>> countermeasures,
                     Type type, Era craftProductionYear, Sides side) {
        super(speed, name, weapons, countermeasures, type, craftProductionYear, side);
    }


    /**
     * Method returns radar object of the vehicle.
     *
     * @return Radar object.
     * @throws NullPointerException if there is no radar on the vehicle.
     */
    @Override
    public Radar getRadar() throws NullPointerException {
        if (radar != null) {
            return radar;
        }
        throw new NullPointerException("No radar object on the vehicle.");
    }

    /**
     * Method assigns radar to the vehicle.
     *
     * @param radar Radar to add.
     */
    @Override
    public void addRadar(Radar radar) {
        this.radar = radar;
    }
}
