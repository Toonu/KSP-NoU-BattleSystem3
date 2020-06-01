package crafts;

import crafts.parts.Radar;
import enums.Era;
import enums.Side;
import enums.Type;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class to simulate Ship or Vessel Craft on the battlefield.
 */
public class Vessel extends Craft implements Serializable, RadarVehicle {
    private Radar radar;
    private final int limitCIWS;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                enums color of craft's side.
     */
    protected Vessel(double speed, String name, Type type, Era craftProductionYear, Side side, int limitSytems,
                     int limitWeapons, int limitGuns, int limitCIWS) {
        super(speed, name, type, craftProductionYear, side, limitSytems, limitWeapons, limitGuns);
        this.limitCIWS = limitCIWS;
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
