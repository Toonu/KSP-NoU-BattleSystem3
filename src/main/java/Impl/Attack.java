package Impl;

import Crafts.Craft;
import Systems.Missile;
import Utils.Movable;
import Utils.Vertex2D;

/**
 * @author Toonu
 * <p>
 * Class represents ongoing attack.
 */
public class Attack implements Movable {
    private final Missile weapon;
    private final Craft origin;
    private final Craft target;
    private Vertex2D weapPos;

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
        this.weapPos = origin.getPosition();
        this.target = target;
    }

    /**
     * Method calculates distance between weapon position and its target.
     *
     * @return double distance.
     */
    public double distanceOfWeapon() {
        return target.getPosition().distance(weapPos);
    }

    /**
     * Method representing moving the craft by vertex coordinates.
     *
     * @param vertex2D to move by this coordinates amount.
     */
    @Override
    public void move(Vertex2D vertex2D) {
        weapPos = new Vertex2D(weapPos.getX() + vertex2D.getX(), weapPos.getY() + vertex2D.getY());
    }

    @Override
    public Vertex2D getPosition() {
        return weapPos;
    }

    /**
     * Method sets new position.
     *
     * @param vertex2D Utils.Vertex2D position.
     */
    @Override
    public void setPosition(Vertex2D vertex2D) {
        weapPos = vertex2D;
    }

    /**
     * Method moves towards center point.
     */
    @Override
    public void moveTowardCenter() {
        moveTowardVertex(new Vertex2D(0, 0));
    }

    /**
     * Method moves towards trg point.
     *
     * @param trg Utils.Vertex2D as target.
     */
    @Override
    public void moveTowardVertex(Vertex2D trg) {
        Vertex2D delta = new Vertex2D(trg.getX() - getPosition().getX(), trg.getY() - getPosition().getY());
        double angle = Math.atan2(delta.getY(), delta.getX());
        int speedX = 1;
        weapPos = new Vertex2D(
                getPosition().getX() + (Math.cos(angle) * speedX),
                getPosition().getY() + (Math.sin(angle) * speedX));
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

    public void setWeapPos(Vertex2D weapPos) {
        this.weapPos = weapPos;
    }


}
