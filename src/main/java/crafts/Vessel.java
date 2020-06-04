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
    private Radar radar = null;
    private int limitCIWS;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                enums color of craft's side.
     */
    protected Vessel(double speed, String name, Type type, Era craftProductionYear, Side side, int limitSystems,
                     int limitWeapons, int limitGuns, int limitCIWS) {
        super(speed, name, type, craftProductionYear, side, limitSystems, limitWeapons, limitGuns);
        this.limitCIWS = limitCIWS;
    }


    /**
     * Method makes copy of vessel.
     *
     * @param newSide Side of copy.
     * @param t       What to copy.
     * @return a copy.
     */
    public static Vessel copy(Side newSide, Vessel t) {
        Vessel newCraft = new Vessel(t.getSpeed(), t.getName(), t.getType(), t.getCraftProductionYear(), newSide,
                t.getLimitSystems(), t.getLimitInternal(), t.getLimitGuns(), t.getLimitCIWS());
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
    public Radar getRadar() throws NullPointerException {
        return radar;
    }

    public int getLimitCIWS() {
        return limitCIWS;
    }

    public void setLimitCIWS(int limitCIWS) {
        this.limitCIWS = limitCIWS;
    }
}
