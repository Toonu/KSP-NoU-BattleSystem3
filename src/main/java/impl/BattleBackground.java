package impl;

import simulation.crafts.Craft;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class simulating the battle itself in the background of GUI.
 */
public class BattleBackground implements Runnable, Serializable {
    private BattleSecond currentSituation;

    /**
     * Constructor.
     */
    public BattleBackground() {
    }

    /**
     * Constructor when loading battle.
     *
     * @param currentSituation to resume battle from the situation.
     */
    public BattleBackground(BattleSecond currentSituation) {
        this.currentSituation = currentSituation;
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
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (!App.isFinished()) {
            OOB.addStage(new BattleSecond());
        }
        OOB.getT().cancel();
    }

    public BattleSecond getCurrentSituation() {
        return currentSituation;
    }
}
