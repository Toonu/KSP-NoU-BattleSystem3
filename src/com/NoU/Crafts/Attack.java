package com.NoU.Crafts;

import com.NoU.Systems.MovableWeapon;
import com.NoU.Vertex2D;

/**
 * @author Tomas Novotny
 */
public class Attack {
    private final MovableWeapon weapon;
    private final Craft origin;
    private final Craft target;
    private Vertex2D position;

    public Attack(MovableWeapon weapon, Craft origin, Craft target) {
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

    public MovableWeapon getWeapon() {
        return weapon;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public void setPosition(Vertex2D position) {
        this.position = position;
    }
}
