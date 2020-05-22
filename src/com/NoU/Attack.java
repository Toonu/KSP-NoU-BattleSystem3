package com.NoU;

import com.NoU.Crafts.Craft;
import com.NoU.Systems.Missile;

/**
 * @author Toonu
 * <p>
 * Class represents ongoing attack.
 */
public class Attack implements Movable {
    private final Missile weapon;
    private final Craft origin;
    private final Craft target;
    private Vertex2D position;

    /**
     * Constructor.
     *
     * @param weapon Missile object weapon of the attack.
     * @param origin Craft object aggressor.
     * @param target Craft object target of weapon.
     */
    public Attack(Missile weapon, Craft origin, Craft target) {
        this.weapon = weapon;
        this.origin = origin;
        this.position = origin.getPosition();
        this.target = target;
    }

    /**
     * Method calculates distance between weapon position and its target.
     *
     * @return double distance.
     */
    public double distanceOfWeapon() {
        return target.getPosition().distance(position);
    }

    /**
     * Method representing moving the craft by vertex coordinates.
     *
     * @param vertex2D to move by this coordinates amount.
     */
    @Override
    public Vertex2D move(Vertex2D vertex2D) {
        return new Vertex2D(position.getX() + vertex2D.getX(), position.getY() + vertex2D.getY());
    }

    public void setPosition(Vertex2D position) {
        this.position = position;
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


}
