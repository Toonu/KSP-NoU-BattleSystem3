package systems;

import enums.Era;
import enums.Theatre;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class representing gun and its properities.
 */
public class Gun extends Weapon implements Serializable {
    private final Ammunition ammunition;
    private int ammoBox = 36;

    /**
     * Constructor excluding ammunition count of the weapon..
     *
     * @param damage     double strength value of system for attack damage.
     * @param maxRange   double maximal range of system.
     * @param minRange   double minimal range of system.
     * @param name       String name of the system.
     * @param era        Era enum of era of the system.
     * @param targets    Set of Theatre objects targetable by the system.
     * @param ammunition Ammunition object representing projectiles of this weapon.
     */
    public Gun(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Era era,
               Ammunition ammunition, boolean internal) {
        super(damage, minRange, maxRange, targets, name, era, internal);
        this.ammunition = ammunition;
    }

    /**
     * Constructor including ammo count.
     *
     * @param damage     double strength value of system for attack damage.
     * @param maxRange   double maximal range of system.
     * @param minRange   double minimal range of system.
     * @param name       String name of the system.
     * @param era        Era enum of era of the system.
     * @param targets    Set of Theatre objects targetable by the system.
     * @param ammunition Ammunition object representing projectiles of this weapon.
     * @param ammo       int of amount of ammunition of the system.
     */
    public Gun(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Era era,
               Ammunition ammunition, boolean internal, int ammo) {
        this(damage, minRange, maxRange, targets, name, era, ammunition, internal);
        ammoBox = ammo;
    }

    /**
     * Method represents firing the gun and reduce ammo count by one.
     */
    public void fireGun() {
        --ammoBox;
    }

    public Ammunition getAmmunition() {
        return ammunition;
    }

    @Override
    public String toString() {
        return String.format("%10s %s", "[Gun]", super.toString());
    }

    /**
     * Shorter representation.
     *
     * @return String.
     */
    public String toShortString() {
        return String.format("%10s %s", "[Gun]", super.toShortString());
    }
}
