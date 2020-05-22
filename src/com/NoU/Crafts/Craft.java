package com.NoU.Crafts;

import com.NoU.App;
import com.NoU.Enum.Era;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Movable;
import com.NoU.Systems.AbstractSystem;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Toonu
 * <p>
 * Craft class to simulate craft on battlefield.
 */
public class Craft implements Serializable, Movable, Comparable<Craft> {
    public static final int DELAY = 30;
    private final double speed;
    private final String name;
    private final SortedMap<Double, List<Weapon>> weapons;
    private final SortedMap<Double, List<Countermeasure>> countermeasures;
    private final Type type;
    private final Era craftProductionYear;
    private final Sides side;

    private Vertex2D position;
    private double HP;

    private boolean isWithdrawing = false;
    private int time;
    private int tick = -1;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param weapons             SortedMap<Double, List<Weapon>> containing values list of weapons sorted by their double range.
     * @param countermeasures     SortedMap<Double, List<Countermeasure>>
     *                            containing values list of countermeasures sorted by their double range.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                Enum color of craft's side.
     */
    protected Craft(double speed, String name, SortedMap<Double,
            List<Weapon>> weapons, SortedMap<Double, List<Countermeasure>> countermeasures,
                    Type type, Era craftProductionYear, Sides side) {
        this.speed = speed;
        this.name = name;

        this.weapons = weapons;
        this.countermeasures = countermeasures;

        this.type = type;
        this.craftProductionYear = craftProductionYear;
        this.side = side;

        switch (type.getTheatre()) {
            case GROUND:
                switch (side) {
                    case WHITE:
                        this.position = new Vertex2D(App.SPAWN_A.getX() + 90, App.SPAWN_A.getY());
                        break;
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() - 90, App.SPAWN_B.getY());
                        break;
                    default:
                        this.position = side.getSpawn();
                        break;
                }
                break;
            case AERIAL:
                switch (side) {
                    case WHITE:
                        this.position = App.SPAWN_A;
                        break;
                    case BLACK:
                        this.position = App.SPAWN_B;
                        break;
                    default:
                        this.position = side.getSpawn();
                        break;
                }
                break;
            case NAVAL:
                switch (side) {
                    case WHITE:
                        this.position = new Vertex2D(App.SPAWN_A.getX() + 75, App.SPAWN_A.getY());
                        break;
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() - 75, App.SPAWN_B.getY());
                        break;
                    default:
                        this.position = side.getSpawn();
                        break;
                }
                break;
        }
        this.HP = type.getHealth();
    }

    /**
     * Method returns String interpretation of the Craft object.
     *
     * @return String with type, subtype, name, HP and current position.
     */
    @Override
    public String toString() {
        return String.format("%s-%s %s: %s Pos: %s", type.getTheatre(), type,
                name, HP, position);
    }

    /**
     * Method returns String interpretation of the Craft object.
     *
     * @return String with type and name of the craft.
     */
    public String toShortString() {
        return String.format("%s %s", type, name);
    }

    /**
     * Method returns list of weapons of the craft.
     *
     * @return String with all weapon Objects.
     */
    public String toWeaponList() {
        return weapons.toString();
    }

    /**
     * Method returns list of countermeasures of the craft.
     *
     * @return String with all countermeasure Objects.
     */
    public String toCountermeasuresList() {
        return countermeasures.toString();
    }

    /**
     * Method removes weapon from list of all weapons of the craft.
     *
     * @param weapon Weapon to be added to the list of weapons.
     */
    public void removeWeapon(Weapon weapon) {
        weapons.get(weapon.getMaxRange()).remove(weapon);
    }

    /**
     * Method removes weapon from list of all countermeasure of the craft.
     *
     * @param cm Weapon to be added to the list of weapons.
     * @return returns true if the Countermeasure object is added to the craft. False otherwise.
     */
    public boolean removeDefense(Countermeasure cm) {
        if (countermeasures.get(cm.getMaxRange()).contains(cm)) {
            countermeasures.get(cm.getMaxRange()).remove(cm);
            return true;
        }
        return false;
    }

    /**
     * Method deduct damage from the craft's HP.
     *
     * @param damage double to be applied.
     * @return false if the HP gets bellow 0 resulting in death. True otherwise.
     */
    public boolean absorbDamage(double damage) {
        if (HP - damage > 0) {
            HP -= damage;
            return true;
        }
        return false;
    }

    /**
     * Method travels distance.
     *
     * @param distance double distance to be traveled.
     */
    public void travelDistance(double distance) {
        position.setX(position.getX() + distance);
    }

    /**
     * Method change status of withdrawal.
     */
    public void withdraw() {
        isWithdrawing = !isWithdrawing;
    }

    /**
     * Method removes tick from cool down counter.
     */
    public void tick() {
        --tick;
    }

    /**
     * Method sets ticks to specified value. Done to renew countdown for launching weapons.
     *
     * @param tick maximal int value of countdown.
     */
    public void setTick(int tick) {
        this.tick = tick;
    }

    /**
     * Method to return new Builder to start building new craft.
     *
     * @return new empty Builder.
     */
    public Builder newBuilder() {
        return new Builder();
    }

    /**
     * Method representing moving the craft by vertex coordinates.
     *
     * @param vertex2D to move by this coordinates amount.
     */
    @Override
    public Vertex2D move(Vertex2D vertex2D) {
        return new Vertex2D(position.getX() + vertex2D.getX(), position.getY() + vertex2D.getY());
    }

    //Getters

    public Sides getSide() {
        return side;
    }

    public String getName() {
        return name;
    }

    public Theatre getCraftClassification() {
        return type.getTheatre();
    }

    public Type getType() {
        return type;
    }

    public double getHP() {
        return HP;
    }

    public Era getCraftProductionYear() {
        return craftProductionYear;
    }

    public int getTime() {
        return time;
    }

    public int getTick() {
        return tick;
    }

    public double getSpeed() {
        return speed;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public SortedMap<Double, List<Countermeasure>> getCountermeasures() {
        return Collections.unmodifiableSortedMap(countermeasures);
    }

    public SortedMap<Double, List<Weapon>> getWeapons() {
        return Collections.unmodifiableSortedMap(weapons);
    }

    public boolean isWithdrawing() {
        return isWithdrawing;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o Object to compare this object with.
     */
    public int compareTo(Craft o) {
        return type.compareTo(o.getType());
    }

    //Builder

    /**
     * Builder class for building craft.
     */
    public static class Builder {
        private double HP;
        private double speed = 10;
        private String name = "Craft";

        private final SortedMap<Double, List<Weapon>> weapons = new TreeMap<>();
        private final SortedMap<Double, List<Countermeasure>> countermeasures = new TreeMap<>();

        private Type type;
        private Era craftProductionYear = App.DEFAULT_YEAR;
        private Sides side = Sides.WHITE;

        private Vertex2D position;

        /**
         * Method returns String representation of the Builder object.
         *
         * @return String with all values of Builder.
         */
        @Override
        public String toString() {
            return "Builder{" +
                    "HP=" + HP +
                    ", speed=" + speed +
                    ", name='" + name + '\'' +
                    ", weapons=" + weapons +
                    ", countermeasures=" + countermeasures +
                    ", type=" + type +
                    ", craftProductionYear=" + craftProductionYear +
                    ", side=" + side +
                    ", position=" + position +
                    '}';
        }

        public Builder setName(String name) {
            if (name != null) {
                this.name = name;
            } else {
                System.err.println(
                        String.format("[ERR %s] Name is null, replacing with default name value.", LocalTime.now()));
            }

            return this;
        }

        public Builder setSpeed(double speed) {
            if (speed > 0) {
                this.speed = speed;
            } else {
                System.err.println(String.format(
                        "[ERR %s] Speed must be positive. Applying default speed of 10m/s.", LocalTime.now()));
            }
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setSide(Sides side) {
            this.side = side;
            return this;
        }

        public Builder setCraftProductionYear(Era craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public void setPosition(Vertex2D position) {
            this.position = position;
        }

        /**
         * Method assign SortedMap to the builder.
         *
         * @param systems SortedMap to be implemented.
         * @return Builder back.
         */
        @SuppressWarnings("unchecked")
        public <T extends AbstractSystem> Builder readMap(SortedMap<Double, List<T>> systems) {
            boolean test = true;
            try {
                Weapon testItem = (Weapon) systems.get(systems.firstKey()).get(0);

            } catch (ClassCastException e) {
                Countermeasure testItem = (Countermeasure) systems.get(systems.firstKey()).get(0);
                test = false;
            }
            try {
                for (List<T> list : systems.values()) {
                    for (T sys : list) {
                        if (sys == null) {
                            throw new NullPointerException("System is null.");
                        }
                    }
                }
                for (Double value : systems.keySet()) {
                    if (value < 0) {
                        throw new NullPointerException("Key value cannot be negative.");
                    }
                }
            } catch (NullPointerException e) {
                System.err.println(String.format("[ERR %s] Some values are null. Cannot add to database. Exception:" +
                        "\n%s", LocalTime.now(), e));
                return this;
            }

            {
                for (Map.Entry<Double, List<T>> entry : systems.entrySet()) {
                    if (test) {
                        weapons.put(systems.firstKey(), (List<Weapon>) systems.get(systems.firstKey()));
                    } else {
                        countermeasures.put(systems.firstKey(), (List<Countermeasure>) systems.get(systems.firstKey()));
                    }
                }
            }

            if (App.DEBUG) {
                System.out.println(String.format("[LOG %s] Added:\n%s", LocalTime.now(), systems));
            }
            return this;
        }

        /**
         * Method adds object to the list of craft.
         *
         * @param system Object to be added.
         * @return Builder.
         */
        public <T extends AbstractSystem> Builder addSystem(T system) {
            Double rng = system.getMaxRange();
            Set<?> trg;
            if (system.getMaxRange() > 0) {
                try {
                    if (weapons.containsKey(rng)) {
                        weapons.get(rng).add((Weapon) system);
                    } else {
                        List<Weapon> newSystems = new ArrayList<>();
                        newSystems.add((Weapon) system);
                        weapons.put(rng, newSystems);
                    }
                    trg = ((Weapon) system).getTargets();
                } catch (NullPointerException e) {
                    if (countermeasures.containsKey(rng)) {
                        countermeasures.get(rng).add((Countermeasure) system);
                    } else {
                        List<Countermeasure> newSystems = new ArrayList<>();
                        newSystems.add((Countermeasure) system);
                        countermeasures.put(rng, newSystems);
                    }
                    trg = ((Countermeasure) system).getAgainst();
                }

                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] Weapon from %s: %s added. [MaxRng: %s MinRng: " +
                                    "%s Strength: %s] [Trg: %s]", LocalTime.now(), system.getEra(), system.getName(),
                            system.getMaxRange(), system.getMinRange(), system.getStrength(), trg));
                }
            } else {
                System.err.println(String.format("[ERR %s] Weapon is null. Not added.", LocalTime.now()));
            }
            return this;
        }

        /**
         * Method to read file and import weapons from it.
         *
         * @param path Path of file.
         * @return Builder.
         */
        public Builder readWeapons(Path path) {
            return this;
        }

        /**
         * Method to read file and import countermeasures from it.
         *
         * @param path Path of file.
         * @return Builder.
         */
        public Builder readCountermeasures(Path path) {
            return this;
        }

        /**
         * Builds the final Craft object.
         *
         * @return Craft object of theatre type.
         */
        public Craft build() {
            try {
                switch (type.getTheatre()) {
                    case GROUND:
                        return new Vehicle(speed, name, weapons, countermeasures, type, craftProductionYear, side);
                    case AERIAL:
                        return new Aircraft(speed, name, weapons, countermeasures, type, craftProductionYear, side);
                    case NAVAL:
                        return new Vessel(speed, name, weapons, countermeasures, type, craftProductionYear, side);
                    default:
                        throw new IllegalArgumentException(
                                String.format("[ERR %s] Wrong vehicle type.", LocalTime.now()));
                }
            } catch (IllegalArgumentException e) {
                System.err.println(String.format("[ERR %s] Vehicle couldn't have been created." +
                        "\n%s", LocalTime.now(), e));
                if (App.DEBUG) {
                    System.err.println(String.format("[LOG %s] Current state of Builder:\n%s", LocalTime.now(), this));
                }
                return null;
            }
        }
    }
}
