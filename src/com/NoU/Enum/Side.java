package com.NoU.Enum;

import com.NoU.Crafts.Craft;
import com.NoU.Vertex2D;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Toonu
 * <p>
 * Enum representing sides of conflict as colors and their default spawn points.
 */
public enum Side {
    WHITE(new Vertex2D(-100, 0)), BLACK(new Vertex2D(100, 0)), NEUTRAL(new Vertex2D(5000, 5000));

    private final Vertex2D spawn;
    private SortedSet<Craft> crafts = new TreeSet<>();

    Side(Vertex2D spawn) {
        this.spawn = spawn;
    }

    public Vertex2D getSpawn() {
        return spawn;
    }

    public SortedSet<Craft> getCrafts() {
        return Collections.unmodifiableSortedSet(crafts);
    }

    public void setCrafts(SortedSet<Craft> crafts) {
        this.crafts = crafts;
    }

    public void addCrafts(Craft craft) {
        crafts.add(craft);
    }
}
