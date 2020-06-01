package crafts;

import crafts.parts.Radar;
import enums.Era;
import enums.Side;
import enums.Type;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class to simulate Aircraft Craft on the battlefield.
 */
public class Aircraft extends Craft implements Serializable, RadarVehicle {
    private Radar radar = null;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                enums color of craft's side.
     */
    protected Aircraft(double speed, String name, Type type, Era craftProductionYear, Side side, int limitSytems,
                       int limitWeapons, int limitGuns) {
        super(speed * 343, name, type, craftProductionYear, side, limitSytems, limitWeapons, limitGuns);
    }

    public static Aircraft copy(Side newSide, Aircraft t) {
        Aircraft newCraft = new Aircraft(t.getSpeed(), t.getName(), t.getType(), t.getCraftProductionYear(), newSide,
                t.getLimitSystems(), t.getLimitInternal(), t.getLimitGuns());
        if (t.getRadar() != null) {
            newCraft.addRadar(t.getRadar());
        }
        return newCraft;
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

    /**
     * Method returns radar object of the vehicle.
     *
     * @return Radar object.
     * @throws NullPointerException if there is no radar on the vehicle.
     */
    @Override
    public Radar getRadar() {
        return radar;
    }
}
