package crafts;

import crafts.parts.Radar;
import enums.Era;
import enums.Side;
import enums.Type;
import impl.App;

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

    /**
     * Method returns copy of the system with all of its attributes.
     *
     * @param newSide Side of the copied craft.
     * @param t       Aircraft to copy.
     * @return Aircraft.
     */
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
        if (Integer.parseInt(radar.getEra().toString()) <= Integer.parseInt(getCraftProductionYear().toString())) {
            this.radar = radar;
            App.err(radar + "added to the vehicle.", false, false);
        } else {
            App.err("Could not add " + radar + "to the craft because its software is newer than " +
                    "the software of the vehicle.", true, true);
        }
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
