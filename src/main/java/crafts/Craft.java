package crafts;

import enums.Era;
import enums.Side;
import enums.Theatre;
import enums.Type;
import impl.App;
import systems.AbstractSystem;
import systems.Countermeasure;
import systems.Weapon;
import utils.Movable;
import utils.Vertex2D;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;

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

    private double angle;
    private Vertex2D position;
    private double hp;

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
     * @param side                enums color of craft's side.
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
            default:
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
        this.hp = type.getHealth();
    }

    /**
     * Method returns String interpretation of the Craft object.
     *
     * @return String with type, subtype, name, HP and current position.
     */
    @Override
    public String toString() {
        return String.format("%s %s %-12s", type.getTheatre(), type, name);
    }

    /**
     * Method returns String interpretation of the Craft object.
     *
     * @return String with type and name of the craft.
     */
    public String toLongString() {

        return String.format("%s %s %-12s: %4s Pos: %s", type.getTheatre(), type, name, hp, position);
    }

    /**
     * Method makes formatted lined String from list.
     *
     * @param list List to convert.
     * @param <T>  List type.
     * @return String of all items in the list separated by new lines.
     */
    public <T> String printList(LinkedList<T> list) {
        StringBuilder result = new StringBuilder();
        for (T weapon : list) {
            result.append("\n").append(weapon);
        }
        return result.toString();
    }

    /**
     * Method returns String of weapons of the craft.
     *
     * @return String with all weapon Objects.
     */
    public String toWeaponsList() {
        return new StringBuilder().append(printList(weapons)).insert(0, "[Weapons]").toString();
    }

    /**
     * Method returns String of countermeasures of the craft.
     *
     * @return String with all countermeasure Objects.
     */
    public String toCountermeasuresList() {
        return new StringBuilder().append(printList(countermeasures)).insert(0, "[Countermeasures]")
                .toString();
    }

    /**
     * Method assign Sortedset to the craft.
     *
     * @param systems SortedSet to be implemented.
     * @param <T>     System class, either weapon or countermeasure.
     */
    public <T extends AbstractSystem> void addSystemSet(LinkedList<T> systems) {
        boolean test = true;
        try {
            Weapon testItem = (Weapon) systems.peekFirst();
        } catch (ClassCastException e) {
            Countermeasure testItem = (Countermeasure) systems.peekFirst();
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
                if (App.isDebug()) {
                    System.out.println(String.format("[LOG %s] %-17s %s",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Added to Craft:", system));
                }
            }
        } catch (NullPointerException e) {
            System.err.println(String.format(
                    "[ERR %s] %s Some values are null. Cannot add to database.",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
        }
    }

    /**
     * Method adds system to craft.
     *
     * @param system Object to be added.
     * @param <T>    system tag. Either Weapon or Countermeasure.
     */
    public <T extends AbstractSystem> void addSystem(T system) {
        try {
            if (system == null) {
                throw new IllegalArgumentException("Cannot add null system.");
            }
            Double rng = system.getMaxRange();
            if (system.getMaxRange() > 0) {
                try {
                    weapons.add((Weapon) system);
                } catch (ClassCastException e) {
                    //noinspection ConstantConditions
                    countermeasures.add((Countermeasure) system);
                }
            }

            if (App.isDebug()) {
                System.out.println(String.format("[LOG %s] %-17s %s",
                        LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Added to craft:", system));
            }
        } catch (IllegalArgumentException e) {
            System.err.println(String.format("[ERR %s] Weapon is null. Not added.",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
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
        } else if (sys instanceof Countermeasure) {
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
        if (hp - damage > 0) {
            hp -= damage;
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
    public void move(Vertex2D vertex2D) {
        position = new Vertex2D(position.getX() + vertex2D.getX(), position.getY() + vertex2D.getY());
    }

    /**
     * Method moves towards center point.
     */
    public void moveTowardCenter() {
        moveTowardVertex(new Vertex2D(0, 0));
    }

    /**
     * Method moves towards trg point.
     *
     * @param trg utils.Vertex2D as target.
     */
    @Override
    public void moveTowardVertex(Vertex2D trg) {
        Vertex2D delta = new Vertex2D(trg.getX() - getPosition().getX(), trg.getY() - getPosition().getY());
        double angle = Math.atan2(delta.getY(), delta.getX());
        position = new Vertex2D(
                position.getX() + (Math.cos(angle) * speed),
                position.getY() + (Math.sin(angle) * speed));
        this.angle = Math.toDegrees(Math.atan2(trg.getY() - position.getY(),
                trg.getX() - position.getX()));
    }

    //Getters

    public double getAngle() {
        return angle;
    }

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

    public double getHp() {
        return hp;
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

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Craft copy() {
        return new Craft(speed, name, type, craftProductionYear, side);
    }

    /**
     * Method sets new position.
     *
     * @param vertex2D utils.Vertex2D position.
     */
    @Override
    public void setPosition(Vertex2D vertex2D) {
        position = vertex2D;
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
     * @return comparing int.
     */
    public int compareTo(Craft o) {
        if (type.compareTo(o.getType()) == 0) {
            if (name.compareTo(o.getName()) == 0) {
                return Integer.compare(System.identityHashCode(this), System.identityHashCode(o));
            }
            return name.compareTo(o.getName());
        }
        return type.compareTo(o.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Craft craft = (Craft) o;

        if (Double.compare(craft.speed, speed) != 0) return false;
        if (Double.compare(craft.angle, angle) != 0) return false;
        if (Double.compare(craft.hp, hp) != 0) return false;
        if (isWithdrawing != craft.isWithdrawing) return false;
        if (time != craft.time) return false;
        if (tick != craft.tick) return false;
        if (name != null ? !name.equals(craft.name) : craft.name != null) return false;
        if (weapons != null ? !weapons.equals(craft.weapons) : craft.weapons != null) return false;
        if (countermeasures != null ? !countermeasures.equals(craft.countermeasures) : craft.countermeasures != null)
            return false;
        if (type != craft.type) return false;
        if (craftProductionYear != craft.craftProductionYear) return false;
        if (side != craft.side) return false;
        return position != null ? position.equals(craft.position) : craft.position == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(speed);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weapons != null ? weapons.hashCode() : 0);
        result = 31 * result + (countermeasures != null ? countermeasures.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (craftProductionYear != null ? craftProductionYear.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (position != null ? position.hashCode() : 0);
        temp = Double.doubleToLongBits(hp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isWithdrawing ? 1 : 0);
        result = 31 * result + time;
        result = 31 * result + tick;
        return result;
    }

    //Builder

    /**
     * @author Toonu
     * Builder class for building craft.
     */
    public static class Builder {
        private double hp;
        private double speed = 0.01;
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
                    "HP=" + hp +
                    ", speed=" + speed +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    ", craftProductionYear=" + craftProductionYear +
                    ", side=" + side +
                    ", position=" + position +
                    '}';
        }

        /**
         * Setter for name.
         *
         * @param name name.
         * @return Builder.
         */
        public Builder setName(String name) {
            if (name != null) {
                this.name = name;
            } else {
                System.err.println(
                        String.format("[ERR %s] Name is null, replacing with default name value.",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
            }
            return this;
        }

        /**
         * Setter for speed.
         *
         * @param speed name.
         * @return Builder.
         */
        public Builder setSpeed(double speed) {
            if (speed > 0) {
                this.speed = speed / 1000;
            } else {
                System.err.println(String.format(
                        "[ERR %s] Speed must be positive. Applying default speed of 10m/s.",
                        LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
            }
            return this;
        }

        /**
         * Setter for type.
         *
         * @param type type.
         * @return Builder.
         */
        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        /**
         * Setter for side.
         *
         * @param side side.
         * @return Builder.
         */
        public Builder setSide(Side side) {
            this.side = side;
            return this;
        }

        /**
         * Setter for craft Production Year.
         *
         * @param craftProductionYear craft Production Year.
         * @return Builder.
         */
        public Builder setCraftProductionYear(Era craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
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
                if (App.isDebug()) {
                    System.err.println(String.format("[LOG %s] Current state of Builder:\n%s", LocalTime.now(), this));
                }
                return null;
            }
        }
    }
}
