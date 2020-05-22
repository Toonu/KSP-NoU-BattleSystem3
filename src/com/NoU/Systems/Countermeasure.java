package com.NoU.Systems;

import com.NoU.Enum.Age;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.SystemType;

import java.util.EnumSet;

/**
 * @author Toonu
 */
public class Countermeasure extends AbstractSystem {
    private final boolean isSaturable;
    private final SystemType type;
    private final EnumSet<GuidanceType> against;
    private boolean oversaturated = false;

    public Countermeasure(double strength, double minRange, double maxRange, String name, Age age,
                          boolean isSaturable, SystemType type, EnumSet<GuidanceType> against) {
        super(strength, maxRange, minRange, name, age);
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

    public Countermeasure getCountermeasure() {
        return new Countermeasure(getStrength(), getMinRange(), getMaxRange(), getName(), getAge(),
                isSaturable, type, against);
    }
}
