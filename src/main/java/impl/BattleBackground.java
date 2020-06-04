package impl;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Toonu
 * <p>
 * Class simulating the battle itself in the background of GUI.
 */
public class BattleBackground implements Runnable {
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
}
