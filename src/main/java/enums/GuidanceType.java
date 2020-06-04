package enums;

/**
 * @author Toonu
 * <p>
 * GuidanceType enums represents missile guidance type.
 */
public enum GuidanceType {
    IR, RADAR, GPS, BEAM, FALL;

    @Override
    public String toString() {
        return String.format("%6s", name());
    }
}
