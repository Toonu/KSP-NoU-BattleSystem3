package simulation.utils;

/**
 * @author Toonu
 * <p>
 * Interface implementing moving objects by Vertices.
 */
public interface Movable {
    /**
     * Method representing moving the craft by vertex coordinates.
     *
     * @param vertex2D to move by this coordinates amount.
     */
    void move(Vertex2D vertex2D);

    /**
     * Method gets position.
     *
     * @return battleGui.utils.Vertex2D position.
     */
    Vertex2D getPosition();

    /**
     * Method sets new position.
     *
     * @param vertex2D battleGui.utils.Vertex2D position.
     */
    void setPosition(Vertex2D vertex2D);

    /**
     * Method moves towards center point.
     * Best to call moveTowardVertex with 0, 0.
     */
    default void moveTowardCenter() {
    }

    /**
     * Method moves towards trg point.
     *
     * @param trg battleGui.utils.Vertex2D as target.
     */
    void moveTowardVertex(Vertex2D trg);

    /**
     * Method returns speed of the object.
     *
     * @return speed double.
     */
    double getSpeed();
}
