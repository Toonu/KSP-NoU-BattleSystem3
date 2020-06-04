package crafts;

import enums.Era;
import enums.Side;
import enums.Type;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class to simulate Aircraft Craft on the battlefield.
 */
public class Aircraft extends Craft implements Serializable {

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                enums color of craft's side.
     */
    protected Aircraft(double speed, String name, Type type, Era craftProductionYear, Side side, int limitSystems,
                       int limitWeapons, int limitGuns) {
        super(speed * 343, name, type, craftProductionYear, side, limitSystems, limitWeapons, limitGuns);
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
}
