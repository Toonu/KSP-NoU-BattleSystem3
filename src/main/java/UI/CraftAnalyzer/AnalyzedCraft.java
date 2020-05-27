package UI.CraftAnalyzer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Toonu
 * <p>
 * Class rperesenting tool to analyze .craft files.
 */
public class AnalyzedCraft {
    private final LinkedList<String> parts;
    private final ArrayList<String> missiles;
    private final ArrayList<String> weapons;
    private final ArrayList<String> systems;
    private final int hardpoints;
    private final String craftName;


    public AnalyzedCraft(LinkedList<String> parts, ArrayList<String> missiles, ArrayList<String> weapons,
                         ArrayList<String> systems, int hardpoints, String craftName) {
        this.parts = parts;
        this.missiles = missiles;
        this.weapons = weapons;
        this.systems = systems;
        this.hardpoints = hardpoints;
        this.craftName = craftName;
    }

    @Override
    public String toString() {
        return String.format("%s: Hardpoints: [%s] [Missile: %s Gun: %s Avionics: %s] Parts: [%s]",
                craftName, missiles.size(), weapons.size(), hardpoints, systems.size(), parts.size());
    }

    public String toPartString() {
        LinkedList<String> newParts = new LinkedList<>(parts);
        newParts.removeAll(missiles);
        newParts.removeAll(weapons);
        newParts.removeAll(systems);
        StringBuilder message = new StringBuilder();
        if (missiles.size() > 0) {
            message.append(String.format("\n%-12s %5s", "[Missiles]", missiles));
        }
        if (weapons.size() > 0) {
            message.append(String.format("\n%-12s %5s", "[Weapons]", weapons));
        }
        if (systems.size() > 0) {
            message.append(String.format("\n%-12s %5s", "[Systems]", systems));
        }

        return message.append(String.format("\n%-12s\n%5s", "[Parts]", newParts)).toString();
    }
}
