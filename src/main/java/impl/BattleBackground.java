package impl;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

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
     * @param currentSituation to resume battle from the situation.
     */
    public BattleBackground(BattleSecond currentSituation) {
        this.currentSituation = currentSituation;
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
        BattleBackground battle = new BattleBackground();
        Timer t = new Timer();
        TimerTask tt = new BattleSecond();
        TimerTask tm = new MapBackground();

        t.schedule(tt, 0, 1000);
        t.schedule(tm, 0, 1000);

        while (!App.isFinished()) {
            System.out.print("");
        }
        tt.cancel();
        tm.cancel();
        t.cancel();
    }

    public BattleSecond getCurrentSituation() {
        return currentSituation;
    }
}
