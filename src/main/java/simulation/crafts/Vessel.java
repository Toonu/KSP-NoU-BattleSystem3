package simulation.crafts;

import simulation.enums.Era;
import simulation.enums.Side;
import simulation.enums.Type;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class to simulate Ship or Vessel Craft on the battlefield.
 */
public class Vessel extends Craft implements Serializable {
    private int limitCIWS;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of battleGui.crafts.
     * @param side                battleGui.enums color of craft's side.
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

    public int getLimitCIWS() {
        return limitCIWS;
    }

    public void setLimitCIWS(int limitCIWS) {
        this.limitCIWS = limitCIWS;
    }
}
