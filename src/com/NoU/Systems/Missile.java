package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.Theatre;

import java.util.Set;

/**
 * @author Toonu
 * <p>
 * Class represents fired Missile
 */
public class Missile extends Weapon {
    private final GuidanceType guidanceType;

    /**
     * Constructor.
     *
     * @param damage       double strength value of system for attack damage.
     * @param maxRange     double maximal range of system.
     * @param minRange     double minimal range of system.
     * @param name         String name of the system.
     * @param age          Age enum of era of the system.
     * @param targets      Set of Theatre objects targetable by the system.
     * @param guidanceType GuidanceType enum representing missile's guidance type.
     */
    public Missile(double damage, double minRange, double maxRange, Set<Theatre> targets, String name, Age age,
                   GuidanceType guidanceType) {
        super(damage, minRange, maxRange, targets, name, age);
        this.guidanceType = guidanceType;
    }

    public GuidanceType getGuidanceType() {
        return guidanceType;
    }
}
