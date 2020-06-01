package systems;

import enums.Era;
import enums.GuidanceType;
import enums.Theatre;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class represents fired Missile
 */
public class Missile extends Weapon implements Serializable {
    private final GuidanceType guidanceType;
    private final double speed;

    /**
     * Constructor.
     *
     * @param damage       double strength value of system for attack damage.
     * @param maxRange     double maximal range of system.
     * @param minRange     double minimal range of system.
     * @param name         String name of the system.
     * @param era          Era enum of era of the system.
     * @param targets      Set of Theatre objects targetable by the system.
     * @param guidanceType GuidanceType enum representing missile's guidance type.
     */
    public Missile(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Era era,
                   GuidanceType guidanceType, double speed) {
        super(damage, minRange, maxRange, targets, name, era);
        this.guidanceType = guidanceType;
        this.speed = speed;
    }

    public GuidanceType getGuidanceType() {
        return guidanceType;
    }

    @Override
    public String toString() {

        return String.format("%10s %s M%.2f", "[Missile]", super.toString(), speed / 343);
    }

    /**
     * Shorter representation.
     *
     * @return String.
     */
    public String toShortString() {
        return String.format("%10s %s", "[Missile]", this.getName());
    }
}
