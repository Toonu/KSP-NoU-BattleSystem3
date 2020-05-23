package com.NoU.Enum;

import java.util.EnumSet;

/**
 * @author Toonu
 * <p>
 * Enum representing countermeasure system types.
 */
@SuppressWarnings("ALL")
public enum CMType {
    SMOKE(false, EnumSet.of(GuidanceType.IR, GuidanceType.BEAM)),
    FLARE(false, EnumSet.of(GuidanceType.IR)),
    CHAFF(false, EnumSet.of(GuidanceType.IR, GuidanceType.RADAR)),
    CIWS(true, EnumSet.of(GuidanceType.IR, GuidanceType.RADAR, GuidanceType.BEAM, GuidanceType.GPS)),
    JAMM(true, EnumSet.of(GuidanceType.GPS, GuidanceType.RADAR, GuidanceType.BEAM)),
    SKAPS(true, EnumSet.of(GuidanceType.GPS, GuidanceType.RADAR, GuidanceType.BEAM)),
    HKAPS(true, EnumSet.of(GuidanceType.IR, GuidanceType.RADAR, GuidanceType.BEAM, GuidanceType.GPS)),
    DEW(true, EnumSet.of(GuidanceType.IR, GuidanceType.RADAR, GuidanceType.BEAM, GuidanceType.GPS));

    private final boolean isSaturable;
    private final EnumSet<GuidanceType> targets;

    CMType(boolean isSaturable, EnumSet enumSet) {
        this.isSaturable = isSaturable;
        this.targets = enumSet;
    }

    public boolean isSaturable() {
        return isSaturable;
    }

    public EnumSet<GuidanceType> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return String.format("%s", name());
    }
}
