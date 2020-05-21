package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Systems.Missile;

/**
 * @author Toonu
 */
public class Attack {
    private final Missile weapon;
    private final Craft origin;
    private final Craft target;
    private Vertex2D position;

    public Attack(Missile weapon, Craft origin, Craft target) {
        this.weapon = weapon;
        this.origin = origin;
        this.position = origin.getPosition();
        this.target = target;
    }

    public double distanceOfWeapon() {
        return target.getPosition().distance(weapon.getPosition());
    }

    public Craft getOrigin() {
        return origin;
    }

    public Craft getTarget() {
        return target;
    }

    public Missile getWeapon() {
        return weapon;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public void setPosition(Vertex2D position) {
        this.position = position;
    }
}
