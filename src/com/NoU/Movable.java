package com.NoU;

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
    Vertex2D move(Vertex2D vertex2D);
}
