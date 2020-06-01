package crafts;

import crafts.parts.Armor;
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
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Toonu
 * <p>
 * Craft class to simulate craft on battlefield.
 */
public class Craft implements Serializable, Movable, Comparable<Craft> {
    public static final int DELAY = 30;
    private final double speed;
    private final int limitInternal;
    private final LinkedList<Weapon> weapons = new LinkedList<>();
    private final LinkedList<Countermeasure> countermeasures = new LinkedList<>();
    private final Type type;
    private final Era craftProductionYear;
    private final Side side;
    private final int limitSystems;
    private final int limitGuns;
    private int amountOfGuns;
    private int amountOfInternal;
    private String name;

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
    protected Craft(double speed, String name, Type type, Era craftProductionYear, Side side,
                    int limitSystems, int limitInternal, int limitGuns) {
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
        this.limitSystems = limitSystems;
        this.limitInternal = limitInternal;
        this.limitGuns = limitGuns;
    }

    /**
     * Method returns String interpretation of the Craft object.
     *
     * @return String with type, subtype, name, HP and current position.
     */
    @Override
    public String toString() {
        return String.format("%s %s %-12s %s", type.getTheatre(), type, name, weapons.size() + countermeasures.size());
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
     * Extra logging info about every part of the craft.
     *
     * @return String of info.
     */
    public String toExtraString() {
        return String.format("%s {%.2f", type, speed) +
                ", name='" + name + '\'' +
                ", weapons=" + weapons +
                ", countermeasures=" + countermeasures +
                ", craftProductionYear=" + craftProductionYear +
                ", limitWeapons=" + limitInternal +
                ", limitSystems=" + limitSystems +
                ", limitGuns=" + limitGuns +
                ", angle=" + angle +
                ", position=" + position +
                ", hp=" + hp +
                '}';
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
                if (system instanceof Weapon) {
                    if (((Weapon) system).isInternal() && amountOfInternal + 1 <= limitInternal) {
                        weapons.add((Weapon) system);
                        ++amountOfInternal;
                    } else if (amountOfGuns + 1 <= limitGuns) {
                        weapons.add((Weapon) system);
                        ++amountOfGuns;
                    }
                } else if (countermeasures.size() + 1 <= limitSystems) {
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

    /**
     * Method makes copy of template with new side of it.
     *
     * @param newSide side.
     * @return new copy of craft.
     */
    public Craft copy(Side newSide) {
        return new Craft(speed, name, type, craftProductionYear, newSide, limitSystems, limitInternal, limitGuns);
    }
    //TODO Copy for each vehicle class differently.

    /**
     * Method sets new position.
     *
     * @param vertex2D utils.Vertex2D position.
     */
    @Override
    public void setPosition(Vertex2D vertex2D) {
        position = vertex2D;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Countermeasure> getCountermeasures() {
        return new LinkedList<>(countermeasures);
    }

    public LinkedList<Weapon> getWeapons() {
        return new LinkedList<>(weapons);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Craft craft = (Craft) o;

        if (Double.compare(craft.speed, speed) != 0) {
            return false;
        }
        if (Double.compare(craft.angle, angle) != 0) {
            return false;
        }
        if (Double.compare(craft.hp, hp) != 0) {
            return false;
        }
        if (isWithdrawing != craft.isWithdrawing) {
            return false;
        }
        if (time != craft.time) {
            return false;
        }
        if (tick != craft.tick) {
            return false;
        }
        if (!Objects.equals(name, craft.name)) {
            return false;
        }
        if (!weapons.equals(craft.weapons)) {
            return false;
        }
        if (!countermeasures.equals(craft.countermeasures)) {
            return false;
        }
        if (type != craft.type) {
            return false;
        }
        if (craftProductionYear != craft.craftProductionYear) {
            return false;
        }
        if (side != craft.side) {
            return false;
        }
        return Objects.equals(position, craft.position);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(speed);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + weapons.hashCode();
        result = 31 * result + countermeasures.hashCode();
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
        private Armor armor;
        private int limitSystems;
        private int limitWeapons;
        private int limitGuns;
        private int limitCIWS;

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
                this.speed = speed;
            } else {
                System.err.println(String.format(
                        "[ERR %s] Speed must be positive. Applying default speed of 10m/s.",
                        LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
            }
            return this;
        }

        /**
         * Method sets new Armor to craft.
         *
         * @param armor Armor object.
         * @return Builder.
         */
        public Builder setArmor(Armor armor) {
            this.armor = armor;
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
         * Method sets new limit to countermeasures on craft.
         *
         * @param limitSystems Int limit.
         * @return Builder.
         */
        public Builder setLimitSystems(int limitSystems) {
            this.limitSystems = limitSystems;
            return this;
        }

        /**
         * Method sets new limit to weapons on craft.
         *
         * @param limitWeapons Int limit.
         * @return Builder.
         */
        public Builder setLimitWeapons(int limitWeapons) {
            this.limitWeapons = limitWeapons;
            return this;
        }

        /**
         * Method sets new limit to guns on craft.
         *
         * @param limitGuns Int limit.
         * @return Builder.
         */
        public Builder setLimitGuns(int limitGuns) {
            this.limitGuns = limitGuns;
            return this;
        }

        /**
         * Method sets new limit to CIWS on craft.
         *
         * @param limitCIWS Int limit.
         * @return Builder.
         */
        public Builder setLimitCIWS(int limitCIWS) {
            this.limitCIWS = limitCIWS;
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
                        return new Vehicle(speed, name, type, craftProductionYear,
                                side, limitSystems, limitWeapons, limitGuns, armor);
                    case AERIAL:
                        return new Aircraft(speed, name, type, craftProductionYear,
                                side, limitSystems, limitWeapons, limitGuns);
                    case NAVAL:
                        return new Vessel(speed, name, type, craftProductionYear,
                                side, limitSystems, limitWeapons, limitGuns, limitCIWS);
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
