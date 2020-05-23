package com.NoU.Enum;

/**
 * @author Toonu
 * <p>
 * Enum representing classes and subclasses of all crafts.
 */
@SuppressWarnings("ALL")
public enum Type {
    APC(100, Theatre.GROUND), IFV(125, Theatre.GROUND), AFV(160, Theatre.GROUND),
    MBT(200, Theatre.GROUND), SAM(100, Theatre.GROUND), SPAAG(80, Theatre.GROUND),
    LIGHTMULTIROLE(100, Theatre.AERIAL, "LM"), MEDIUMMULTIROLE(120, Theatre.AERIAL, "MM"),
    HEAVYMULTIROLE(160, Theatre.AERIAL, "HM"), LARGEAIRFRAME(200, Theatre.AERIAL, "LH"),
    VERYLARGEAIRFRAME(250, Theatre.AERIAL, "VLH"), CORVETTE(600, Theatre.NAVAL, "FS"), FRIGATE(800, Theatre.NAVAL, "FF"),
    DESTROYER(1000, Theatre.NAVAL, "DD"), CRUISER(1200, Theatre.NAVAL, "CC"), BATTLECRUISER(1500, Theatre.NAVAL, "BC"),
    BATTLESHIP(2000, Theatre.NAVAL, "BB"), LIGHTCARRIER(1600, Theatre.NAVAL, "LHD"), CARRIER(2500, Theatre.NAVAL, "CV");

    private final double health;
    private final Theatre theatre;
    private String nickname;

    Type(double health, Theatre classification) {
        this.health = health;
        this.theatre = classification;
        this.nickname = this.name();
    }

    Type(double health, Theatre classification, String nickname) {
        this(health, classification);
        this.nickname = nickname;
    }

    public double getHealth() {
        return health;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public String toString() {
        return String.format("%-4s", nickname);
    }
}
