package impl;

import crafts.Craft;
import enums.Side;
import systems.Weapon;

import java.util.LinkedList;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * @author Toonu
 * <p>
 * Class representing second of battle time.
 */
public class BattleSecond extends TimerTask {

    //Following Final results, fired weapons, lost weapons, lost vehicles, retreated vehicles.

    /**
     * Constructor.
     */
    public BattleSecond() {
        run();
    }


    /**
     * Method calculates impact angle between the targets.
     *
     * @param aggressor vehicle that fired.
     * @param target    vehicle that are hit and calculated.
     * @return angle of hit.
     */
    public static double hitAngle(Craft aggressor, Craft target) {
        double angle = (Math.toDegrees(Math.atan2(aggressor.getPosition().getY() - target.getPosition().getY(),
                aggressor.getPosition().getX() - target.getPosition().getX())));
        angle -= target.getAngle();
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println(App.returnRealTime());
        App.setGlobalTime(App.getGlobalTime() + 1);
        for (Craft craft : Side.WHITE.getCrafts()) {
            TreeMap<Craft, LinkedList<Weapon>> enemies = craft.findClosest();
            craft.checkIncoming();
        }

        for (Attack attack : OOB.getAttacks()) {
            if (attack.getTarget().getPosition().distance(attack.getPosition()) == 0) {
                attack.getTarget().absorbDamage(attack.getWeapon().getStrength());
            } else {
                attack.moveTowardVertex(attack.getTarget().getPosition());
            }
        }
    }
}
