package enums;

import crafts.Craft;
import systems.AbstractSystem;
import systems.Countermeasure;
import systems.KSPPart;
import systems.Weapon;
import utils.Vertex2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Toonu
 * <p>
 * enums representing sides of conflict as colors and their default spawn points.
 */
public enum Side {
    WHITE(new Vertex2D(-100, 0)), BLACK(new Vertex2D(100, 0)), TEMPLATE(new Vertex2D(5000, 5000));

    private Vertex2D spawn;
    private LinkedList<Craft> crafts = new LinkedList<>();
    private final List<Weapon> lostWeapons = new ArrayList<>();
    private final List<Countermeasure> lostCountermeasures = new ArrayList<>();
    private final List<KSPPart> lostSystems = new ArrayList<>();
    private final List<Craft> lostVehicles = new ArrayList<>();

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

    /**
     * Method removes craft from the side's list of crafts.
     *
     * @param craft Craft to remove.
     */
    public void removeCraft(Craft craft) {
        if (crafts.contains(craft)) {
            this.crafts.remove(craft);
        }
    }

    /**
     * Method adds lost system to its side of losses.
     *
     * @param system lost system.
     */
    public void lostSystem(AbstractSystem system) {
        if (system instanceof Countermeasure) {
            lostCountermeasures.add((Countermeasure) system);
        } else if (system instanceof Weapon) {
            lostWeapons.add((Weapon) system);
        } else if (system instanceof KSPPart) {
            lostSystems.add((KSPPart) system);
        }
    }

    /**
     * Method adds destroyed vehicle to his side of losses.
     *
     * @param craft lost vehicle.
     */
    public void lostVehicle(Craft craft) {
        lostVehicles.add(craft);
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
        return crafts;
    }

    public List<Countermeasure> getLostCountermeasures() {
        return lostCountermeasures;
    }

    public List<Weapon> getLostWeapons() {
        return lostWeapons;
    }

    public List<Craft> getLostVehicles() {
        return lostVehicles;
    }

    public List<KSPPart> getLostSystems() {
        return lostSystems;
    }
}
