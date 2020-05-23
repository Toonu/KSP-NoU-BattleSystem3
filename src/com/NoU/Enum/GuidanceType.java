package com.NoU.Enum;

/**
 * @author Toonu
 * <p>
 * GuidanceType Enum represents missile guidance type.
 */
public enum GuidanceType {
    IR, RADAR, GPS, BEAM;

    @Override
    public String toString() {
        return String.format("%6s", name());
    }
}
