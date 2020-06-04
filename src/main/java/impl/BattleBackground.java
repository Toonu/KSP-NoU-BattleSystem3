package impl;

import crafts.Craft;
import enums.Side;
import ui.Gui;
import ui.battleSystem.BattleFrame;

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

        t.schedule(tt, 0, 1000);

        //Deselecting crafts on the map.
        while (!App.isFinished()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Craft craft : Side.WHITE.getCrafts()) {
                if (craft.getSelectedTime() > 0) {
                    craft.setSelectedTime(craft.getSelectedTime() - 1);
                }
                if (craft.getSelectedTime() == 0) {
                    craft.deselect();
                }

                craft.tick();
            }
            for (Craft craft : Side.BLACK.getCrafts()) {
                if (craft.getSelectedTime() > 0) {
                    craft.setSelectedTime(craft.getSelectedTime() - 1);
                }
                if (craft.getSelectedTime() == 0) {
                    craft.deselect();
                }

                craft.tick();
            }
            if (Gui.getCurrentWindow() instanceof BattleFrame) {
                ((BattleFrame) Gui.getCurrentWindow()).getMap().updateUI();
            }
        }

        tt.cancel();
        t.cancel();
    }
}
