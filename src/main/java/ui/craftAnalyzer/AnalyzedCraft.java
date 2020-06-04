package ui.craftAnalyzer;

import crafts.parts.Radar;
import impl.OOB;
import systems.Countermeasure;
import systems.Gun;
import systems.KSPPart;
import systems.Missile;
import systems.Weapon;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Toonu
 * <p>
 * Class rperesenting tool to analyze .craft files.
 */
public class AnalyzedCraft {
    private final LinkedList<String> partsUnnamed = new LinkedList<>();
    private final ArrayList<Missile> missiles = new ArrayList<>();
    private final ArrayList<Weapon> weapons = new ArrayList<>();
    private final ArrayList<Countermeasure> cm = new ArrayList<>();
    private final ArrayList<Radar> radars = new ArrayList<>();
    private final int hardpoints;
    private final String craftName;

    /**
     * Constructor.
     *
     * @param parts      parts.
     * @param hardpoints hardpoints.
     * @param craftName  craftName.
     * @param hasAI      if the craft has BDA AI manager present.
     */
    public AnalyzedCraft(LinkedList<String> parts, int hardpoints, String craftName, boolean hasAI) {
        this.hardpoints = hardpoints;
        this.craftName = craftName;

        LinkedList<KSPPart> partsSearch = new LinkedList<>();

        for (String sys : parts) {
            boolean added = false;
            if (sys.equals("\n")) {
                partsUnnamed.add(sys);
                continue;
            }
            for (Weapon system : OOB.getWeapons()) {
                if (sys.equals(system.getInternalKSPName())) {
                    partsSearch.add(system.copy());
                    added = true;
                    break;
                }
            }
            for (Countermeasure system : OOB.getCountermeasures()) {
                if (sys.equals(system.getInternalKSPName())) {
                    partsSearch.add(system.copy());
                    added = true;
                    break;
                }
            }
            for (Radar system : OOB.getRadars()) {
                if (sys.equals(system.getInternalKSPName())) {
                    partsSearch.add(system.copy());
                    added = true;
                    break;
                }
            }
            if (!added) {
                partsUnnamed.add(sys);
            }
        }

        for (KSPPart sys : partsSearch) {
            if (sys instanceof Countermeasure) {
                cm.add((Countermeasure) sys);
            } else if (sys instanceof Gun && ((Gun) sys).isInternal()) {
                weapons.add((Weapon) sys);
            } else if (sys instanceof Missile) {
                missiles.add((Missile) sys);
            } else {
                if (sys instanceof Radar) {
                    radars.add((Radar) sys);
                }
            }
            //noinspection SuspiciousMethodCalls
            parts.remove(sys);
        }


    }

    /**
     * Method returns string representation of the object.
     *
     * @return returns string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append(String.format("%s: Hardpoints: [%s] [Missile: %s Gun: %s Avionics: %s] parts: [%s]",
                craftName, hardpoints, missiles.size(), weapons.size(), cm.size(), partsUnnamed.size()));
        if (missiles.size() > 0) {
            message.append("\n[Missiles]");
            for (Missile sys : missiles) {
                message.append(String.format("\n%s", sys));
            }
        }
        if (weapons.size() > 0) {
            message.append("\n[Weapons]");
            for (Weapon sys : weapons) {
                message.append(String.format("\n%s", sys));
            }
        }
        if (cm.size() > 0) {
            message.append("\n[Countermeasures]");
            for (Countermeasure sys : cm) {
                message.append(String.format("\n%s", sys));
            }
        }
        if (radars.size() > 0) {
            message.append("\n[Sensors]");
            for (Radar sys : radars) {
                message.append(String.format("\n%s", sys));
            }
        }

        return message.toString();
    }

    /**
     * Method returns detailed string representation of the object.
     *
     * @return String.
     */
    public String toPartString() {
        return toString() + String.format("\n%-12s\n%5s", "[Parts]", partsUnnamed);
    }
}
