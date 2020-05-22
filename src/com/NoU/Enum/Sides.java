package com.NoU.Enum;

import com.NoU.Vertex2D;

/**
 * @author Toonu
 *
 * Enum representing sides of conflict as colors and their default spawn points.
 */
public enum Sides {
    WHITE(new Vertex2D(-100, 0)), BLACK(new Vertex2D(100, 0));

    private final Vertex2D spawn;

    Sides(Vertex2D spawn) {
        this.spawn = spawn;
    }

    public Vertex2D getSpawn() {
        return spawn;
    }
}
