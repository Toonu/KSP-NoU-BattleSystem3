package impl;

import crafts.Craft;
import enums.Side;
import ui.Gui;
import ui.battleSystem.BattleFrame;

import java.util.TimerTask;

/**
 * @author Toonu
 * <p>
 * Class simulates map updates in the background.
 */
public class MapBackground extends TimerTask {
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        //Deselecting crafts on the map.
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
}
