package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.SystemType;
import com.NoU.Enum.Theatre;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Toonu
 */
public class Countermeasure extends AbstractSystem {
    private final boolean isSaturable;
    private final SystemType type;
    private final EnumSet<GuidanceType> against;
    private boolean oversaturated = false;

    public Countermeasure(double strength, double minRange, double maxRange, Set<Theatre> targets, Age age,
                          boolean isSaturable, SystemType type, EnumSet<GuidanceType> against) {
        super(strength, maxRange, minRange, age);
        this.isSaturable = isSaturable;
        this.type = type;
        this.against = against;
    }

    public SystemType getType() {
        return type;
    }

    public EnumSet<GuidanceType> getAgainst() {
        return against;
    }

    public boolean isSaturable() {
        return isSaturable;
    }

    public boolean isOversaturated() {
        return oversaturated;
    }

    public void saturate() {
        oversaturated = !oversaturated;
    }
}
