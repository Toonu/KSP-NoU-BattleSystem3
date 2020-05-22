package com.NoU.Systems;

import com.NoU.Enum.Era;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.Theatre;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class represents fired Missile
 */
public class Missile extends Weapon implements Serializable {
    private final GuidanceType guidanceType;

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
                   GuidanceType guidanceType) {
        super(damage, minRange, maxRange, targets, name, era);
        this.guidanceType = guidanceType;
    }

    public GuidanceType getGuidanceType() {
        return guidanceType;
    }
}
