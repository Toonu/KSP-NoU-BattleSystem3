package simulation.crafts;


import simulation.crafts.parts.Armor;
import simulation.enums.Era;
import simulation.enums.Side;
import simulation.enums.Type;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class to simulate Ground Vehicle Craft on the battlefield.
 */
public class Vehicle extends Craft implements Serializable {
    private final Armor armor;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of battleGui.crafts.
     * @param side                battleGui.enums color of craft's side.
     */
    protected Vehicle(double speed, String name, Type type, Era craftProductionYear, Side side, int limitSystems,
                      int limitWeapons, int limitGuns, Armor armor) {
        super(speed, name, type, craftProductionYear, side, limitSystems, limitWeapons, limitGuns);
        this.armor = armor;
    }

    /**
     * Method makes copy of object.
     *
     * @param newSide Side of new vehicle.
     * @param t       vehicle to copy.
     * @return Vehicle copy.
     */
    public static Vehicle copy(Side newSide, Vehicle t) {
        Vehicle newCraft = new Vehicle(t.getSpeed(), t.getName(), t.getType(), t.getCraftProductionYear(), newSide,
                t.getLimitSystems(), t.getLimitInternal(), t.getLimitGuns(), t.getArmor());
        if (t.getRadar() != null) {
            newCraft.addRadar(t.getRadar());
        }
        return newCraft;
    }

    /**
     * Method returns armor layout Object from the vehicle.
     *
     * @return Armor object.
     * @throws NullPointerException if there is no armor present.
     */
    public Armor getArmor() throws NullPointerException {
        if (armor != null) {
            return armor;
        }
        throw new NullPointerException("No armor present.");
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
