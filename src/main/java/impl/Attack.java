package impl;

import crafts.Craft;
import systems.Missile;
import utils.Movable;
import utils.Vertex2D;

/**
 * @author Toonu
 * <p>
 * Class represents ongoing attack.
 */
public class Attack implements Movable {
    private final Missile weapon;
    private final Craft target;
    private Vertex2D position;
    private double speed;
    private final double maximalSpeed;

    /**
     * Constructor.
     *
     * @param weapon Missile object weapon of the attack.
     * @param origin Craft object aggressor.
     * @param target Craft object target of weapon.
     */
    public Attack(Missile weapon, Craft origin, Craft target) {
        this.weapon = weapon;
        this.position = origin.getPosition();
        this.target = target;
        this.speed = origin.getSpeed();
        this.maximalSpeed = weapon.getSpeed();
    }

    /**
     * Method moves towards trg point.
     *
     * @param trg utils.Vertex2D as target.
     */
    @Override
    public void moveTowardVertex(Vertex2D trg) {
        Vertex2D delta = new Vertex2D(trg.getX() - getPosition().getX(), trg.getY() - getPosition().getY());
        double angle = Math.atan2(delta.getY(), delta.getX());
        if (position.distance(target.getPosition()) < speed) {
            position = target.getPosition();
        } else {
            position = new Vertex2D(getPosition().getX() + (Math.cos(angle) * speed),
                    getPosition().getY() + (Math.sin(angle) * speed));
        }
    }

    /**
     * Method representing moving the craft by vertex coordinates.
     *
     * @param vertex2D to move by this coordinates amount.
     */
    @Override
    public void move(Vertex2D vertex2D) {
        position = new Vertex2D(position.getX() + vertex2D.getX(), position.getY() + vertex2D.getY());
    }

    /**
     * Method simulates missile startup and increase in speed in first seconds.
     */
    public void increaseSpeed() {
        if (speed < maximalSpeed && speed + weapon.getSpeed() / 3 > maximalSpeed) {
            speed = maximalSpeed;
        } else if (speed < maximalSpeed) {
            speed += weapon.getSpeed() / 3;
        }
    }

    //Getters & Setters

    /**
     * Method returns speed of the object.
     *
     * @return speed double.
     */
    @Override
    public double getSpeed() {
        return 0;
    }

    public Craft getTarget() {
        return target;
    }

    public Missile getWeapon() {
        return weapon;
    }

    @Override
    public Vertex2D getPosition() {
        return position;
    }

    /**
     * Method sets new position.
     *
     * @param vertex2D utils.Vertex2D position.
     */
    @Override
    public void setPosition(Vertex2D vertex2D) {
        position = vertex2D;
    }
}
