package impl;

import simulation.crafts.Craft;
import simulation.crafts.systems.Weapon;
import simulation.enums.Side;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author Toonu
 * <p>
 * Class representing second of battle time.
 */
public class BattleSecond {

    //Following Final results, fired weapons, lost weapons, lost vehicles, retreated vehicles.

    /**
     * Constructor.
     */
    public BattleSecond() {
        System.out.println(App.returnRealTime());
        App.setGlobalTime(App.getGlobalTime() + 1);

        Stream.of(Side.WHITE.getCrafts().stream(), Side.BLACK.getCrafts().stream()).flatMap(s -> s).forEach(s1 -> {
            TreeMap<Craft, LinkedList<Weapon>> selection = s1.findAndEngage();
            s1.fire(selection);
            s1.checkIncomingAttacks();
            s1.tick();
        });

        for (Attack attack : OOB.getAttacks()) {
            if (attack.getTarget().getPosition().distance(attack.getPosition()) == 0) {
                attack.getTarget().absorbDamage(attack.getWeapon().getStrength());
            } else {
                attack.increaseSpeed();
                attack.moveTowardVertex(attack.getTarget().getPosition());
            }
        }
    }
}
