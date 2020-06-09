package crafts;

import comparators.WeaponComparator;
import crafts.parts.Armor;
import crafts.parts.Radar;
import enums.ArmorSide;
import enums.Era;
import enums.GuidanceType;
import enums.Side;
import enums.Theatre;
import enums.Type;
import impl.App;
import impl.Attack;
import impl.BattleSecond;
import impl.OOB;
import systems.AbstractSystem;
import systems.Countermeasure;
import systems.Gun;
import systems.KSPPart;
import systems.Missile;
import systems.Weapon;
import utils.Movable;
import utils.Vertex2D;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author Toonu
 * <p>
 * Craft class to simulate craft on battlefield.
 */
public class Craft implements Serializable, Movable, Comparable<Craft>, RadarVehicle {
    public static final int DELAY = 30;
    private final double speed;
    private final int limitInternal;
    private final LinkedList<Weapon> weapons = new LinkedList<>();
    private final LinkedList<Countermeasure> countermeasures = new LinkedList<>();
    private final LinkedList<KSPPart> parts = new LinkedList<>();
    private final Type type;
    private final Era craftProductionYear;
    private final Side side;
    private final int limitSystems;
    private final int limitGuns;
    private int amountOfGuns;
    private int amountOfInternal;
    private String name;
    private Radar radar;

    private double angle;
    private Vertex2D position;
    private double hp;

    private boolean isWithdrawing = false;
    private boolean isSelected = false;
    private int selectedTime = 0;
    private int time;
    private int tick = -1;
    private int lastFired = 0;

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
                        this.position = new Vertex2D(App.SPAWN_A.getX() + 90 + new Random().nextInt(5),
                                App.SPAWN_A.getY() + new Random().nextInt(5));
                        break;
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() - 90 + new Random().nextInt(5),
                                App.SPAWN_B.getY() + new Random().nextInt(5));
                        break;
                    default:
                        this.position = side.getSpawn();
                        break;
                }
                break;
            case AERIAL:
                switch (side) {
                    case WHITE:
                        this.position = new Vertex2D(App.SPAWN_A.getX() + new Random().nextInt(5),
                                App.SPAWN_A.getY() + new Random().nextInt(5));
                        break;
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() + new Random().nextInt(5),
                                App.SPAWN_B.getY() + new Random().nextInt(5));
                        break;
                    default:
                        this.position = side.getSpawn();
                        break;
                }
                break;
            default:
                switch (side) {
                    case WHITE:
                        this.position = new Vertex2D(App.SPAWN_A.getX() + 75 + new Random().nextInt(5),
                                App.SPAWN_A.getY() - 300 + new Random().nextInt(5));
                        break;
                    case BLACK:
                        this.position = new Vertex2D(App.SPAWN_B.getX() - 75 + new Random().nextInt(5),
                                App.SPAWN_B.getY() - 300 + new Random().nextInt(5));
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
     * Method returns parts list.
     *
     * @return parts list.
     */
    public String toPartsList() {
        return printList(parts);
    }

    /**
     * Method adds system to craft.
     *
     * @param <T>    system tag. Either Weapon or Countermeasure.
     * @param system Object to be added.
     */
    public <T extends AbstractSystem> void addSystem(AbstractSystem system) {
        try {
            if (system == null) {
                throw new IllegalArgumentException("Cannot add null system.");
            }
            Double rng = (system).getMaxRange();
            if ((system).getMaxRange() > 0 && Integer.parseInt((system).getEra().toString())
                    <= Integer.parseInt(craftProductionYear.toString())) {
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
            App.err(String.format("%-17s %s", "Added to craft:", system), false, false);
        } catch (IllegalArgumentException e) {
            App.err("Weapon is null or newer than craft's software. Rejected and not added.", true, true);
        }
    }

    /**
     * Method adds part to the craft systems.
     *
     * @param system to add.
     */
    public void addPart(KSPPart system) {
        if (system != null) {
            parts.add(system);
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
            App.err(String.format("Received %s damage", damage), false, true);
            return true;
        }
        App.err(String.format("%s DESTROYED after receiving %s damage", name, damage), false, false);
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

    /**
     * Method select the craft on map and makes it more visible.
     */
    public void deselect() {
        isSelected = false;
        selectedTime = 0;
    }

    /**
     * Method select the craft on map and makes it more visible.
     */
    public void select() {
        isSelected = true;
        selectedTime = 3;
    }

    /**
     * Method makes copy of template with new side of it.
     *
     * @param newSide side.
     * @return new copy of craft.
     */
    public Craft copy(Side newSide) {
        if (type.getTheatre() == Theatre.GROUND) {
            return Vehicle.copy(newSide, (Vehicle) this);
        } else if (type.getTheatre() == Theatre.AERIAL) {
            return Aircraft.copy(newSide, (Aircraft) this);
        } else {
            return Vessel.copy(newSide, (Vessel) this);
        }
    }


    /**
     * Method representing firing weapon against aggressor from target.
     * Simulates failure to launch a missile and relaunch of another missile against the target.
     * Simulates adding fired weapons to lost systems.
     *
     * @param target Craft object targeted and weapon with range to it.
     * @return true if the weapon fired.
     */
    public boolean fire(TreeMap<Craft, LinkedList<Weapon>> target) {
        for (Weapon weaponSystem : target.firstEntry().getValue()) {
            int counter = 0;

            if (weaponSystem instanceof Missile) {
                if (new Random().nextInt(1) != 1) {
                    App.err("Failure to launch the" + weaponSystem, false, false);
                    ++counter;
                } else {
                    OOB.addAttack(new Attack((Missile) weaponSystem, this, target.firstKey()));
                    removeSystem(weaponSystem);
                    side.getLostWeapons().add(weaponSystem);
                    return true;
                }
                if (counter > 3) {
                    return false;
                }
            } else if (weaponSystem instanceof Gun) {
                double angle = BattleSecond.hitAngle(this, target.firstKey());
                target.firstKey().projectileIncoming((Gun) weaponSystem, angle);
                ((Gun) weaponSystem).fireGun();
                return true;
            }
        }
        return false;
    }

    /**
     * Method representing gun projectile incoming against the target armor.
     * If there is no armor, it randomly decide if it is hit or not and do direct damage.
     * If there is armor, calculates angle and penetration values and then determine damage in other method.
     *
     * @param weaponSystem gun attacking.
     */
    private void projectileIncoming(Gun weaponSystem, double angle) {
        boolean armor = false;
        boolean penetrated = false;
        for (KSPPart part : parts) {
            if (part instanceof Armor) {
                for (ArmorSide side : ArmorSide.values()) {
                    if (angle < side.getMaxAngle() && angle > side.getMinAngle()) {
                        if (((Armor) part).isPenetrated(side, weaponSystem.getAmmunition().getPenetration(), angle)) {
                            absorbDamage(weaponSystem.getStrength());
                        }
                    }
                }
                armor = true;
                break;
            }
        }
        if (!armor && new Random().nextInt(1) != 1) {
            absorbDamage(weaponSystem.getStrength());
        }
    }

    /**
     * Method checks all incoming attacks and apply countermeasures on them if they are in range.
     * If successful, removes the attack, otherwise it continues.
     */
    public void checkIncoming() {
        for (Attack attack : OOB.getAttacks()) {
            if (attack.getTarget() == this) {
                for (Countermeasure cm : countermeasures) {
                    if (cm.getMaxRange() > attack.getPosition().distance(position)
                            && cm.getTargets().contains(attack.getWeapon().getGuidanceType())) {
                        if (attack.getWeapon().getEra().getModifier() > cm.getEra().getModifier()
                                + new Random().nextInt(1)) {
                            OOB.getAttacks().remove(attack);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method finds closest craft and weapons to engage it.
     *
     * @return Target and weapons that can engage it.
     */
    public TreeMap<Craft, LinkedList<Weapon>> findClosest() {
        double shortestDistance = 11000;
        Side targetSide = Side.BLACK;
        if (side == Side.WHITE) {
            targetSide = Side.WHITE;
        }

        //All enemies sorted by range.
        LinkedList<Craft> targetsByDistance = new LinkedList<>();
        targetsByDistance.add(targetSide.getCrafts().getFirst());
        Craft target = null;

        //All weapons sorted by range and damage.
        LinkedList<Weapon> possibleWeapons = new LinkedList<>();

        for (Craft potentialTarget : targetSide.getCrafts()) {
            double potentialDistance = position.distance(potentialTarget.getPosition());
            if (potentialDistance < shortestDistance) {
                targetsByDistance.addFirst(potentialTarget);
                shortestDistance = potentialDistance;
            }
        }

        while (possibleWeapons.isEmpty()) {
            target = targetsByDistance.pollFirst();
            possibleWeapons = getPossibleWeapons(target, shortestDistance);
        }


        TreeMap<Craft, LinkedList<Weapon>> result = new TreeMap<>();
        result.put(target, possibleWeapons);

        return result;
    }

    /**
     * Method returns all weapons with range against the target.
     *
     * @param target   target.
     * @param distance to the target.
     * @return weapons in range by damage.
     */
    private LinkedList<Weapon> getPossibleWeapons(Craft target, double distance) {
        LinkedList<Weapon> possibleWeapons = new LinkedList<>();

        for (Weapon wp : weapons) {
            try {
                if (((Missile) wp).getGuidanceType() == GuidanceType.ANTIRAD && target.getRadar() != null) {
                    possibleWeapons.add(wp);
                }
            } catch (ClassCastException e) {
                if (wp.getMaxRange() > distance && wp.getTargets().contains(target.getCraftClassification())) {
                    possibleWeapons.add(wp);
                }
            }
        }
        possibleWeapons.sort(new WeaponComparator());
        return possibleWeapons;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(int selectedTime) {
        this.selectedTime = selectedTime;
    }

    public int getAmountOfGuns() {
        return amountOfGuns;
    }

    public int getAmountOfInternal() {
        return amountOfInternal;
    }

    public int getLastFired() {
        return lastFired;
    }

    public void setLastFired(int lastFired) {
        this.lastFired = lastFired;
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

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Countermeasure> getCountermeasures() {
        return new LinkedList<>(countermeasures);
    }

    public LinkedList<Weapon> getWeapons() {
        return new LinkedList<>(weapons);
    }

    public LinkedList<KSPPart> getParts() {
        return new LinkedList<>(parts);
    }

    public int getLimitSystems() {
        return limitSystems;
    }

    public int getLimitInternal() {
        return limitInternal;
    }

    public int getLimitGuns() {
        return limitGuns;
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

    /**
     * Method returns radar object of the vehicle.
     *
     * @return Radar object.
     * @throws NullPointerException if there is no radar on the vehicle.
     */
    @Override
    public Radar getRadar() throws NullPointerException {
        return radar;
    }

    /**
     * Method assigns radar to the vehicle.
     *
     * @param radar Radar to add.
     */
    @Override
    public void addRadar(Radar radar) {
        if (radar != null ||
                Integer.parseInt(radar.getEra().toString()) <= Integer.parseInt(craftProductionYear.toString())) {
            this.radar = radar;
            App.err(radar + "added to the vehicle.", false, false);
        }
        App.err("Radar is null or newer than the software allows.", true, true);
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
                App.err("Name is null. Replacing with default name.", true, true);
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
                App.err("Speed must be positive. Applying default speed. (10m/s)", false, false);
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
                        throw new IllegalArgumentException("Wrong vehicle type.");
                }
            } catch (IllegalArgumentException e) {
                App.err("Vehicle couldn't have been created." + e, true, true);
                App.err("Current state of builder when it failed.\n" + this, false, false);
                return null;
            }
        }
    }
}
