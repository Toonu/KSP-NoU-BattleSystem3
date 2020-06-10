package simulation.enums;

import simulation.crafts.Craft;
import simulation.crafts.systems.AbstractSystem;
import simulation.crafts.systems.Countermeasure;
import simulation.crafts.systems.KSPPart;
import simulation.crafts.systems.Weapon;
import simulation.utils.Vertex2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Toonu
 * <p>
 * battleGui.enums representing sides of conflict as colors and their default spawn points.
 */
public enum Side {
    WHITE(new Vertex2D(-100, 0)), BLACK(new Vertex2D(100, 0)), TEMPLATE(new Vertex2D(5000, 5000));

    private final List<Weapon> lostWeapons = new ArrayList<>();
    private final List<Countermeasure> lostCountermeasures = new ArrayList<>();
    private final List<KSPPart> lostSystems = new ArrayList<>();
    private final List<Craft> lostVehicles = new ArrayList<>();
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

    /**
     * Method removes craft from the side's list of battleGui.crafts.
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

    public Vertex2D getSpawn() {
        return spawn;
    }

    public void setSpawn(Vertex2D spawn) {
        this.spawn = spawn;
    }

    public LinkedList<Craft> getCrafts() {
        return crafts;
    }

    public void setCrafts(LinkedList<Craft> crafts) {
        this.crafts = crafts;
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
