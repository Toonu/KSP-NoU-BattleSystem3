package com.NoU;


import java.util.Objects;

/**
 * @author Tomas Novotny
 */
public class Vertex2D {
    private double x;
    private double y;

    /**
     * Initiator
     *
     * @param x double x coordinate
     * @param y double y coordinate
     */
    public Vertex2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates middle point
     *
     * @param secondVertex Vertex2D set of coordinates
     * @return Vertex2D middle point between this Vertex and otherVertex
     */
    public Vertex2D createMiddle(Vertex2D secondVertex) {
        return new Vertex2D(((x + secondVertex.x) / 2), ((y + secondVertex.y) / 2));
    }

    /**
     * Function to return distance between two sets of coordinates.
     *
     * @param vertex Vertex2D second set of coordinates
     * @return double distance
     */
    public double distance(Vertex2D vertex) {
        if (vertex != null) {
            return Math.sqrt(Math.pow(getX() - vertex.getX(), 2) + Math.pow(getY() - vertex.getY(), 2));
        }
        return -1;
    }

    /**
     * Equals
     *
     * @param o Object to compare.
     * @return boolean result.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex2D vertex2D = (Vertex2D) o;
        return Double.compare(vertex2D.x, x) == 0 &&
                Double.compare(vertex2D.y, y) == 0;
    }

    /**
     * Support method for equals.
     *
     * @return int result.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @return double x coordinate
     */
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return double y coordinate
     */
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return String of formatted x and y coordinates
     */
    @Override
    public String toString() {
        return String.format("[%s, %s]", x, y);
    }
}
