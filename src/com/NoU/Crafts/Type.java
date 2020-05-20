package com.NoU.Crafts;

/**
 * @author Toonu
 */
public enum Type {
    APC(100, Theatre.GROUND), IFV(125, Theatre.GROUND), AFV(160, Theatre.GROUND),
    MBT(200, Theatre.GROUND), SAM(100, Theatre.GROUND), SPAAG(80, Theatre.GROUND),
    LIGHTMULTIROLE(100, Theatre.AERIAL), MEDIUMMULTIROLE(120, Theatre.AERIAL),
    HEAVYMULTIROLE(160, Theatre.AERIAL), LARGEAIRFRAME(200, Theatre.AERIAL),
    VERYLARGEAIRFRAME(250, Theatre.AERIAL), CORVETTE(600, Theatre.NAVAL), FRIGATE(800, Theatre.NAVAL),
    DESTROYER(1000, Theatre.NAVAL), CRUISER(1200, Theatre.NAVAL), BATTLECRUISER(1500, Theatre.NAVAL),
    BATTLESHIP(2000, Theatre.NAVAL), LIGHTCARRIER(1600, Theatre.NAVAL), CARRIER(2500, Theatre.NAVAL);

    private double health;
    private Theatre theatre;

    Type(double health, Theatre classification) {
        this.health = health;
        this.theatre = classification;
    }

    public double getHealth() {
        return health;
    }

    public Theatre getTheatre() {
        return theatre;
    }
}
