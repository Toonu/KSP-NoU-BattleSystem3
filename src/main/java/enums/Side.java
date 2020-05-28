package enums;

import crafts.Craft;
import utils.Vertex2D;

import java.util.LinkedList;

/**
 * @author Toonu
 * <p>
 * enums representing sides of conflict as colors and their default spawn points.
 */
public enum Side {
    WHITE(new Vertex2D(-100, 0)), BLACK(new Vertex2D(100, 0)), TEMPLATE(new Vertex2D(5000, 5000));

    private Vertex2D spawn;
    private LinkedList<Craft> crafts = new LinkedList<>();

    Side(Vertex2D spawn) {
        this.spawn = spawn;
    }

    /**
     * Method adds craft to side.
     *
     * @param craft craft to be added.
     */
    public void addCraft(Craft craft) {
        crafts.add(craft);
        crafts.sort(Craft::compareTo);
    }

    // Setters & Getters.

    public void setCrafts(LinkedList<Craft> crafts) {
        this.crafts = crafts;
    }

    public void setSpawn(Vertex2D spawn) {
        this.spawn = spawn;
    }

    public Vertex2D getSpawn() {
        return spawn;
    }

    public LinkedList<Craft> getCrafts() {
        return new LinkedList<>(crafts);
    }
}
