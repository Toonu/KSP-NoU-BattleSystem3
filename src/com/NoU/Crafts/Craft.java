package com.NoU.Crafts;

import com.NoU.App;
import com.NoU.Enum.Age;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Systems.Countermeasure;
import com.NoU.Systems.Weapon;
import com.NoU.Vertex2D;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Toonu
 */
public class Craft implements Serializable {
    public static final int DELAY = 30;
    private final double speed;
    private final String name;
    private final SortedMap<Double, List<Weapon>> weapons;
    private final SortedMap<Double, List<Countermeasure>> countermeasures;
    private final Type type;
    private final Age craftProductionYear;
    private final Sides side;
    private final Vertex2D position;
    private double HP;
    private boolean isWithdrawing = false;
    private int time;
    private int tick;

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
                        position = new Vertex2D(App.SPAWN_A.getX() + 90, App.SPAWN_A.getY());
                    case BLACK:
                        position = new Vertex2D(App.SPAWN_B.getX() - 90, App.SPAWN_B.getY());
                }
            case AERIAL:
                switch (side) {
                    case WHITE:
                        position = App.SPAWN_A;
                    case BLACK:
                        position = App.SPAWN_B;
                }
            case NAVAL:
                switch (side) {
                    case WHITE:
                        position = new Vertex2D(App.SPAWN_A.getX() + 75, App.SPAWN_A.getY());
                    case BLACK:
                        position = new Vertex2D(App.SPAWN_B.getX() - 75, App.SPAWN_B.getY());
                    default:
                        position = new Vertex2D(0, 0);
                }
        }
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s: %s Pos: %s", type.getTheatre(), type,
                name, HP, position);
    }

    public String toShortString() {
        return String.format("%s %s", type, name);
    }

    public String toWeaponList() {
        return weapons.toString();
    }

    public String toCountermeasuresList() {
        return countermeasures.toString();
    }

    public boolean removeWeapon(Weapon weaponSystem) {
        if (weapons.get(weaponSystem.getMaxRange()).contains(weaponSystem)) {
            weapons.get(weaponSystem.getMaxRange()).remove(weaponSystem);
            return true;
        }
        return false;
    }

    public boolean removeDefense(Countermeasure cm) {
        if (countermeasures.get(cm.getMaxRange()).contains(cm)) {
            countermeasures.get(cm.getMaxRange()).remove(cm);
            return true;
        }
        return false;
    }

    public boolean absorbDamage(double damage) {
        if (HP - damage > 0) {
            HP -= damage;
            return true;
        }
        return false;
    }

    public void travelDistance(double distance) {
        position.setX(position.getX() + distance);
    }

    public void withdraw() {
        isWithdrawing = !isWithdrawing;
    }

    public void tick() {
        --tick;
    }

    public Sides getSide() {
        return side;
    }

    //Getters

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

    public Builder newBuilder() {
        return new Builder();
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    /**
     *
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

        public Builder setCSubclass(Type type) {
            this.type = type;
            return this;
        }

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
                System.err.println("Name is null, replacing with default name value.");
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
                System.err.println("Speed must be positive. Applying default speed of 10m/s.");
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

        public Builder addCountermeasures(SortedMap<Double, List<Countermeasure>> countermeasures) {
            this.countermeasures = countermeasures;
            if (App.DEBUG) {
                System.out.println(countermeasures);
            }
            return this;
        }

        public Builder addWeapons(SortedMap<Double, List<Weapon>> weapons) {
            this.weapons = weapons;
            if (App.DEBUG) {
                System.out.println(weapons);
            }
            return this;
        }

        public Builder addWeapon(Weapon weapon) {
            if (weapon != null) {
                weapons.get(weapon.getMaxRange()).add(weapon);
                if (App.DEBUG) {
                    System.out.println(String.format("Weapon from %s: %s added. [MaxRng: %s MinRng: %s Strength: %s]" +
                                    "\n[Trg: %s]", weapon.getAge(), weapon.getName(), weapon.getMaxRange(),
                            weapon.getMinRange(), weapon.getStrength(), weapon.getTargets()));
                }
            } else {
                System.err.println("Weapon is null. Not added.");
            }
            return this;
        }

        public Builder addCountermeasure(Countermeasure system) {
            if (system != null) {
                countermeasures.get(system.getMaxRange()).add(system);
                if (App.DEBUG) {
                    System.out.println(String.format("CM type %s from %s: %s added. [MaxRng: %s MinRng: %s " +
                                    "Strength: %s]\n[Trg: %s]", system.getType(), system.getAge(), system.getName(),
                            system.getMaxRange(), system.getMinRange(), system.getStrength(), system.getAgainst()));
                }
            } else {
                System.err.println("CM is null. Not added.");
            }
            return this;
        }

        public Builder readWeapons(Path path) {
            return this;
        }

        public Builder readCountermeasures(Path path) {
            return this;
        }

        public Craft build() {
            switch (type.getTheatre()) {
                case GROUND:

            }
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Vehicle buildGround() {
            if (side == Sides.WHITE && position == null) {
                position = new Vertex2D(-10, 0);
            } else if (position == null) {
                position = new Vertex2D(10, 0);
            }

            if (type.getTheatre() == Theatre.GROUND) {
                return new Vehicle(speed, name, weapons, countermeasures, type, craftProductionYear, side);
            }
            System.err.println("Vehicle couldn't have been created.");
            if (App.DEBUG) {
                System.err.println(this);
            }
            return null;
        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Aircraft buildAerial() {
            if (side == Sides.WHITE && position == null) {
                position = new Vertex2D(-100, 0);
            } else if (position == null) {
                position = new Vertex2D(100, 0);
            }

            if (type.getTheatre() == Theatre.AERIAL) {
                return new Aircraft(speed, name, weapons, countermeasures, type, craftProductionYear, side);
            }
            System.err.println("Vehicle couldn't have been created.");
            if (App.DEBUG) {
                System.err.println(this);
            }
            return null;

        }

        /**
         * Build an instance of {@link Vehicle}
         *
         * @return instance of {@link Vehicle}
         */
        public Vessel buildNaval() {
            if (side == Sides.WHITE && position == null) {
                position = new Vertex2D(-25, 0);
            } else if (position == null) {
                position = new Vertex2D(25, 0);
            }

            if (type.getTheatre() == Theatre.AERIAL) {
                return new Vessel(speed, name, weapons, countermeasures, type, craftProductionYear, side);
            }
            System.err.println("Vehicle couldn't have been created.");
            if (App.DEBUG) {
                System.err.println(this);
            }
            return null;
        }
    }
}
