package com.NoU.Systems;

/**
 * @author Toonu
 */
public class Radar {
    private final double radarRange;
    private boolean radarActive = true;

    protected Radar(double radarRange) {
        this.radarRange = radarRange;
    }

    public double getRadarRange() {
        return radarRange;
    }

    public void switchRadar() {
        radarActive = !radarActive;
    }

    public boolean isRadarActive() {
        return radarActive;
    }
}
