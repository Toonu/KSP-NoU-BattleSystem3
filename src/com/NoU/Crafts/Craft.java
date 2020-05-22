package com.NoU.Crafts;

import com.NoU.App;
import com.NoU.Enum.Age;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Movable;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 * <p>
 * Craft class to simulate craft on battlefield.
 */
public class Craft implements Serializable, Movable {
    public static final int DELAY = 30;
    private final double speed;
    private final String name;
    private final SortedMap<Double, List<Weapon>> weapons;
    private final SortedMap<Double, List<Countermeasure>> countermeasures;
    private final Type type;
    private final Age craftProductionYear;
    private final Sides side;

    private Vertex2D position;
    private double HP;
    private boolean isWithdrawing = false;
    private int time;
    private int tick;

    /**
     * Constructor.
     *
     * @param speed               double Speed in m/s of the craft.
     * @param name                String name of the craft.
     * @param weapons             SortedMap<Double, List<Weapon>> containing values list of weapons sorted by their double range.
     * @param countermeasures     SortedMap<Double, List<Countermeasure>>
     *                            containing values list of countermeasures sorted by their double range.
     * @param type                Type enum of the craft.
     * @param craftProductionYear Age enum of the Eras of crafts.
     * @param side                Enum color of craft's side.
     */
    protected Craft(double speed, String name, SortedMap<Double,
            List<Weapon>> weapons, SortedMap<Double, List<Countermeasure>> countermeasures,
                    Type type, Age craftProductionYear, Sides side) {
        this.HP = type.getHealth();
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
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() - 90, App.SPAWN_B.getY());
                }
            case AERIAL:
                switch (side) {
                    case WHITE:
                        this.position = App.SPAWN_A;
                    case BLACK:
                        this.position = App.SPAWN_B;
                }
            case NAVAL:
                switch (side) {
                    case WHITE:
                        this.position = new Vertex2D(App.SPAWN_A.getX() + 75, App.SPAWN_A.getY());
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() - 75, App.SPAWN_B.getY());
                }
        }
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
     * @return returns true if the Weapon object is added to the craft. False otherwise.
     */
    public boolean removeWeapon(Weapon weapon) {
        if (weapons.get(weapon.getMaxRange()).contains(weapon)) {
            weapons.get(weapon.getMaxRange()).remove(weapon);
            return true;
        }
        return false;
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
     * Method removes tick from cooldown counter.
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

    public Age getCraftProductionYear() {
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

    //Builder

    /**
     * Builder class for building craft.
     */
    public static class Builder {
        private double HP;
        private double speed = 10;
        private String name = "Craft";

        private SortedMap<Double, List<Weapon>> weapons;
        private SortedMap<Double, List<Countermeasure>> countermeasures;

        private Type type;
        private Age craftProductionYear = App.DEFAULT_YEAR;
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

        public Builder setType(Type type) {
            this.type = type;
            return this;
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

        public Builder setSide(Sides side) {
            this.side = side;
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

        public Builder setCraftProductionYear(Age craftProductionYear) {
            this.craftProductionYear = craftProductionYear;
            return this;
        }

        public void setPosition(Vertex2D position) {
            this.position = position;
        }

        /**
         * Method assign SortedMap to the builder.
         *
         * @param countermeasures SortedMap to be implemented.
         * @return Builder back.
         */
        public Builder addCountermeasures(SortedMap<Double, List<Countermeasure>> countermeasures) {
            this.countermeasures = countermeasures;
            if (App.DEBUG) {
                System.out.println(String.format("[LOG %s] Added:\n%s", LocalTime.now(), countermeasures));
            }
            return this;
        }

        /**
         * Method assign SortedMap to the builder.
         *
         * @param weapons SortedMap to be implemented.
         * @return Builder back.
         */
        public Builder addWeapons(SortedMap<Double, List<Weapon>> weapons) {
            this.weapons = weapons;
            if (App.DEBUG) {
                System.out.println(String.format("[LOG %s] Added:\n%s", LocalTime.now(), weapons));
            }
            return this;
        }

        /**
         * Method adds Weapon object to the weapons list of craft.
         *
         * @param weapon Weapon Object to be added.
         * @return Builder.
         */
        public Builder addWeapon(Weapon weapon) {
            if (weapon != null) {
                weapons.get(weapon.getMaxRange()).add(weapon);
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] Weapon from %s: %s added. [MaxRng: %s MinRng: " +
                                    "%s Strength: %s]\n[Trg: %s]", LocalTime.now(), weapon.getAge(), weapon.getName(),
                            weapon.getMaxRange(), weapon.getMinRange(), weapon.getStrength(), weapon.getTargets()));
                }
            } else {
                System.err.println(String.format("[ERR %s] Weapon is null. Not added.", LocalTime.now()));
            }
            return this;
        }

        /**
         * Method adds CM object to the CM list of craft.
         *
         * @param system Countermeasure Object to be added.
         * @return Builder.
         */
        public Builder addCountermeasure(Countermeasure system) {
            if (system != null) {
                countermeasures.get(system.getMaxRange()).add(system);
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] CM type %s from %s: %s added. [MaxRng: %s MinRng: %s " +
                                    "Strength: %s]\n[Trg: %s]", LocalTime.now(), system.getType(), system.getAge(),
                            system.getName(), system.getMaxRange(), system.getMinRange(), system.getStrength(),
                            system.getAgainst()));
                }
            } else {
                System.err.println(String.format("[ERR %s] CM is null. Not added.", LocalTime.now()));
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
