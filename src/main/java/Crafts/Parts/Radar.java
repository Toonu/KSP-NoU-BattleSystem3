package Crafts.Parts;

import java.io.Serializable;

/**
 * @author Toonu
 * <p>
 * Class representing radar system and its atributes.
 */
public class Radar implements Serializable {
    private final double radarRange;
    private boolean radarActive = true;

    /**
     * Constructor.
     *
     * @param radarRange double radar range in km.
     */
    protected Radar(double radarRange) {
        this.radarRange = radarRange;
    }

    /**
     * Method switches radar on and off.
     */
    public void switchRadar() {
        radarActive = !radarActive;
    }

    /**
     * Method returns true if radar is switched on.
     *
     * @return radarActive state.
     */
    public boolean isRadarActive() {
        return radarActive;
    }

    public double getRadarRange() {
        return radarRange;
    }
}
