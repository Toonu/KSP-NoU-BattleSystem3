package com.NoU.Crafts;

import com.NoU.App;
import com.NoU.Enum.Era;
import com.NoU.Enum.Side;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Movable;
import com.NoU.Systems.AbstractSystem;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;

/**
 * @author Toonu
 * <p>
 * Craft class to simulate craft on battlefield.
 */
public class Craft implements Serializable, Movable, Comparable<Craft> {
    public static final int DELAY = 30;
    private final double speed;
    private final String name;
    private final LinkedList<Weapon> weapons = new LinkedList<>();
    private final LinkedList<Countermeasure> countermeasures = new LinkedList<>();
    private final Type type;
    private final Era craftProductionYear;
    private final Side side;

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
     * @param type                Type enum of the craft.
     * @param craftProductionYear Era enum of the Eras of crafts.
     * @param side                Enum color of craft's side.
     */
    protected Craft(double speed, String name, Type type, Era craftProductionYear, Side side) {
        this.speed = speed;
        this.name = name;

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
     * Method assign Sortedset to the craft.
     *
     * @param systems SortedSet to be implemented.
     */
    public <T extends AbstractSystem> void addSystemSet(SortedSet<T> systems) {
        boolean test = true;
        try {
            Weapon testItem = (Weapon) systems.first();

        } catch (ClassCastException e) {
            Countermeasure testItem = (Countermeasure) systems.first();
            test = false;
        }
        try {
            for (T system : systems) {
                if (system == null) {
                    throw new NullPointerException("System is null.");
                } else if (test) {
                    weapons.add((Weapon) system);
                } else {
                    countermeasures.add((Countermeasure) system);
                }
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] Added: %s", LocalTime.now(), system));
                }
            }
        } catch (NullPointerException e) {
            System.err.println(String.format(
                    "[ERR %s] %s Some values are null. Cannot add to database.", LocalTime.now(), e));
        }
    }

    /**
     * Method adds system to craft.
     *
     * @param system Object to be added.
     */
    public <T extends AbstractSystem> void addSystem(T system) {
        try {
            if (system == null) {
                throw new IllegalArgumentException("Cannot add null system.");
            }
            Double rng = system.getMaxRange();
            Set<?> trg = null;
            if (system.getMaxRange() > 0) {
                try {
                    weapons.add((Weapon) system);
                    trg = ((Weapon) system).getTargets();
                } catch (NullPointerException e) {
                    countermeasures.add((Countermeasure) system);
                    trg = ((Countermeasure) system).getAgainst();
                    //TODO Try add CM
                }
            }

            if (App.DEBUG) {
                System.out.println(String.format("[LOG %s] Weapon from %s: %s added. [MaxRng: %s MinRng: " +
                                "%s Strength: %s] [Trg: %s]", LocalTime.now(), system.getEra(), system.getName(),
                        system.getMaxRange(), system.getMinRange(), system.getStrength(), trg));
            }
        } catch (IllegalArgumentException e) {
            System.err.println(String.format("[ERR %s] Weapon is null. Not added.", LocalTime.now()));
        }
    }

    /**
     * Method removes CM or weapon system from the craft.
     *
     * @param sys System to be removed.
     */
    public void removeSystem(AbstractSystem sys) {
        if (sys instanceof Weapon) {
            weapons.remove(sys);
        } else {
            countermeasures.remove(sys);
        }
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

    public Side getSide() {
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

    public LinkedList<Countermeasure> getCountermeasures() {
        return (LinkedList<Countermeasure>) Collections.unmodifiableList(countermeasures);
    }

    public LinkedList<Weapon> getWeapons() {
        return (LinkedList<Weapon>) Collections.unmodifiableList(weapons);
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

        private Type type;
        private Era craftProductionYear = App.DEFAULT_YEAR;
        private Side side = Side.WHITE;

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

        public Builder setSide(Side side) {
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
         * Builds the final Craft object.
         *
         * @return Craft object of theatre type.
         */
        public Craft build() {
            try {
                switch (type.getTheatre()) {
                    case GROUND:
                        return new Vehicle(speed, name, type, craftProductionYear, side);
                    case AERIAL:
                        return new Aircraft(speed, name, type, craftProductionYear, side);
                    case NAVAL:
                        return new Vessel(speed, name, type, craftProductionYear, side);
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
