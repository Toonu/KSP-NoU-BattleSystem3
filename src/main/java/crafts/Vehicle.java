package crafts;


import crafts.parts.Armor;
import crafts.parts.Radar;
import enums.Era;
import enums.Side;
import enums.Type;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class to simulate Ground Vehicle Craft on the battlefield.
 */
public class Vehicle extends Craft implements RadarVehicle, ArmoredVehicle, Serializable {
    private Armor armor;
    private Radar radar;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                enums color of craft's side.
     */
    protected Vehicle(double speed, String name, Type type, Era craftProductionYear, Side side, int limitSytems,
                      int limitWeapons, int limitGuns, Armor armor) {
        super(speed, name, type, craftProductionYear, side, limitSytems, limitWeapons, limitGuns);
        this.armor = armor;
    }

    /**
     * Method returns armor layout Object from the vehicle.
     *
     * @return Armor object.
     * @throws NullPointerException if there is no armor present.
     */
    @Override
    public Armor getArmor() throws NullPointerException {
        if (armor != null) {
            return armor;
        }
        throw new NullPointerException("No armor present.");
    }

    /**
     * Method assign radar to the vehicle.
     *
     * @param armor Armor to add.
     */
    @Override
    public void addArmor(Armor armor) {
        this.armor = armor;
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
        throw new NullPointerException("No radar present.");
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

    @Override
    public String toString() {
        return String.format("%s", super.toString());
    }

    @Override
    public String toLongString() {
        return String.format("%s %s", super.toLongString(), armor);
    }

    @Override
    public String toExtraString() {
        return super.toExtraString() + armor;
    }
}
