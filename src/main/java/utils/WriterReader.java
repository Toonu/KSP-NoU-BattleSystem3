package utils;

import crafts.Craft;
import crafts.Vehicle;
import crafts.parts.Armor;
import crafts.parts.Radar;
import enums.ArmorSide;
import enums.CMType;
import enums.Era;
import enums.GuidanceType;
import enums.Side;
import enums.Theatre;
import enums.Type;
import impl.App;
import impl.OOB;
import systems.AbstractSystem;
import systems.Ammunition;
import systems.Countermeasure;
import systems.Gun;
import systems.Missile;
import systems.Weapon;
import ui.Gui;
import ui.battleSystem.BattleFrame;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Toonu
 */
public class WriterReader {
    private static int counter = 0;

    //TODO Add reading from online sheet maybe if possible integration with google sheets is viable

    /**
     * Method saves crafts to save file.
     *
     * @param file     file to load.
     * @param template if templates should be saved into the file.
     * @return true if everything went correctly.
     */
    public static boolean saveSetupFile(File file, boolean template) {
        counter = 0;
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file))) {
            if (Gui.getCurrentWindow() instanceof BattleFrame) {
                o.writeObject(OOB.getBattleBackground());
            }

            Stream.of(Side.WHITE.getCrafts().stream(), Side.BLACK.getCrafts().stream()).flatMap(s -> s).forEach(s1 -> {
                try {
                    o.writeObject(s1);
                } catch (IOException e) {
                    App.err("Error saving craft:" + s1, true, true);
                }
                ++WriterReader.counter;
                App.err(String.format("%-18s %s", "Craft saved:", s1.toLongString()), false, false);
            });
            if (template) {
                for (Craft craft : Side.TEMPLATE.getCrafts()) {
                    o.writeObject(craft);
                    ++counter;
                    App.err(String.format("%-18s %s", "Craft saved:", craft.toLongString()), false, false);
                }
            }
            App.err(String.format("%-18s [%s]", "Crafts saved:", counter), false, false);
            return true;
        } catch (IOException e) {
            App.err("Program could not have been saved:" + e, true, true);
            return false;
        }
    }

    /**
     * Method loads saved file.
     *
     * @param file file to load.
     * @return crafts loaded.
     */
    public static boolean loadSetupFile(File file) {
        Runnable r = null;
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Craft> whites = new ArrayList<>();
            ArrayList<Craft> blacks = new ArrayList<>();
            ArrayList<Craft> templates = new ArrayList<>();
            int counter = 0;
            try {
                try {
                    r = (Runnable) oi.readObject();
                    App.setRunnable(true);
                } catch (ClassCastException e) {
                    Craft newCraft = (Craft) oi.readObject();
                    if (newCraft.getSide() == Side.WHITE) {
                        whites.add(newCraft);
                    } else if (newCraft.getSide() == Side.BLACK) {
                        blacks.add(newCraft);
                    } else {
                        templates.add(newCraft);
                    }
                    ++counter;
                }

                //noinspection InfiniteLoopStatement
                while (true) {
                    Craft newCraft = (Craft) oi.readObject();
                    if (newCraft.getSide() == Side.WHITE) {
                        whites.add(newCraft);
                    } else if (newCraft.getSide() == Side.BLACK) {
                        blacks.add(newCraft);
                    } else {
                        templates.add(newCraft);
                    }
                    ++counter;
                }
            } catch (EOFException e) {
                App.err(String.format("%-18s [W: %s B: %s T: %s]",
                        "Crafts loaded:", whites.size(), blacks.size(), templates.size()), false, false);
            }
            if (counter > 0) {
                Side.WHITE.getCrafts().clear();
                Side.BLACK.getCrafts().clear();
                Side.TEMPLATE.getCrafts().clear();

                whites.forEach(Side.WHITE::addCraft);
                blacks.forEach(Side.BLACK::addCraft);
                templates.forEach(Side.TEMPLATE::addCraft);
                if (App.isRunnable() && r != null) {
                    r.run();
                }
                return true;
            }
            throw new IOException("No craft could have been loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            App.err(String.format("%s", e), true, true);
            return false;
        }
    }

    /**
     * Method reads formatted craft csv file.
     *
     * @param path path to the file to import.
     * @return set containing all weapon templates from the file.
     */
    public static LinkedList<Craft> loadTemplatesFile(Path path) {
        LinkedList<Craft> crafts = new LinkedList<>();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] lines = line.split(",");

                if (!lines[1].equals("")) {
                    String name = lines[2];
                    Era era = Era.valueOf(
                            new StringBuilder().append("Era19").append(lines[21]).deleteCharAt(7).toString());

                    Type type;
                    String subclass = lines[23] + lines[24] + lines[25];
                    switch (subclass) {
                        case "IFV / LT":
                            type = Type.IFV;
                            break;
                        case "AFV / MT":
                            type = Type.AFV;
                            break;
                        case "MBT / HT":
                            type = Type.MBT;
                            break;
                        case "SAM":
                            type = Type.SAM;
                            break;
                        case "Light Airframe":
                            type = Type.LIGHTMULTIROLE;
                            break;
                        case "Medium Airframe":
                            type = Type.MEDIUMMULTIROLE;
                            break;
                        case "Heavy Airframe":
                            type = Type.HEAVYMULTIROLE;
                            break;
                        case "Large Airframe":
                            type = Type.LARGEAIRFRAME;
                            break;
                        case "Very Large Airframe":
                            type = Type.VERYLARGEAIRFRAME;
                            break;
                        case "Corvette":
                            type = Type.CORVETTE;
                            break;
                        case "Frigate":
                            type = Type.FRIGATE;
                            break;
                        case "Destroyer":
                            type = Type.DESTROYER;
                            break;
                        case "Cruiser":
                            type = Type.CRUISER;
                            break;
                        case "Battlecruiser / SCS (LHA / LHD / LPD / LSD / Light Carrier)":
                            type = Type.LIGHTCARRIER;
                            break;
                        case "Battleship / Carrier":
                            type = Type.CARRIER;
                            break;
                        default:
                            type = Type.APC;
                    }

                    double speed;
                    String armor = lines[7];

                    /*   2 name, 3 class, 4 speed, 5 sys, 6 weapon, 7 armor, 8 era, 9 missiles
                    10 engines, 11 avionics, 12 internal, 13 fuel, 14 consumption, 15 hard points
                    16 speed, 17 sys, 18 weapon, 19 CIWS, 20 missiles, 21 software                   */

                    try {
                        if (type.getTheatre() == Theatre.AERIAL) {
                            speed = Math.sqrt(Double.parseDouble(lines[10]) / 120);
                        } else {
                            speed = Double.parseDouble(lines[4] + lines[16]);
                        }
                    } catch (NumberFormatException e) {
                        speed = Double.parseDouble(lines[10]);
                    }

                    Craft newCraft;
                    if (type.getTheatre() == Theatre.GROUND && armor != null) {
                        Armor newArmor = new Armor(armor);

                        Vehicle newVehicle = (Vehicle) new Craft.Builder()
                                .setName(name).setSpeed(speed)
                                .setType(type)
                                .setCraftProductionYear(era)
                                .setSide(Side.TEMPLATE)
                                .setArmor(newArmor)
                                .setSpeed(speed)
                                .setLimitSystems((int) Math.round(Double.parseDouble(lines[5])))
                                .setLimitWeapons((int) Math.round(Double.parseDouble(lines[9])))
                                .setLimitGuns((int) Math.round(Double.parseDouble(lines[6])))
                                .build();
                        newArmor.getPenetrated().put(ArmorSide.FRONT,
                                newVehicle.getArmor().getPenetrated().get(ArmorSide.FRONT) - 1);
                        newArmor.getPenetrated().put(ArmorSide.SIDE,
                                newVehicle.getArmor().getPenetrated().get(ArmorSide.SIDE) - 1);
                        newArmor.getPenetrated().put(ArmorSide.REAR,
                                newVehicle.getArmor().getPenetrated().get(ArmorSide.REAR) - 1);

                        newCraft = newVehicle;
                    } else if (type.getTheatre() == Theatre.AERIAL) {
                        newCraft = new Craft.Builder()
                                .setName(name)
                                .setSpeed(speed)
                                .setType(type)
                                .setCraftProductionYear(era)
                                .setSide(Side.TEMPLATE)
                                .setLimitSystems((int) Math.round(Double.parseDouble(lines[11] + lines[17])))
                                .setLimitWeapons((int) Math.round(Double.parseDouble(lines[15] + lines[20])))
                                .setLimitGuns((int) Math.round(Double.parseDouble(lines[12] + lines[18])))
                                .build();
                    } else {
                        newCraft = new Craft.Builder()
                                .setName(name)
                                .setSpeed(speed)
                                .setType(type)
                                .setCraftProductionYear(era)
                                .setSide(Side.TEMPLATE)
                                .setLimitSystems((int) Math.round(Double.parseDouble(lines[11] + lines[17])))
                                .setLimitWeapons((int) Math.round(Double.parseDouble(lines[15] + lines[18])))
                                .setLimitGuns((int) Math.round(Double.parseDouble(lines[12] + lines[18])))
                                .setLimitCIWS((int) Math.round(Double.parseDouble(lines[19])))
                                .build();
                    }
                    crafts.add(newCraft);
                    App.err(String.format("%-18s %s", "Crafts loaded:", newCraft.toExtraString()), false, false);
                }
                line = br.readLine();
            }
        } catch (IOException | IllegalArgumentException e) {
            App.err("Program couldn't have been loaded due to corrupted template crafts file." + e, true, true);
        }
        return crafts;
    }

    /**
     * Method filter input list and arranges systems to their categories.
     *
     * @param list    systems unsorted list.
     * @param weapons weapons list.
     * @param cm      countermeasure list.
     */
    public static void filterSystems(LinkedList<AbstractSystem> list, LinkedList<Weapon> weapons,
                                     LinkedList<Countermeasure> cm) {
        for (AbstractSystem sys : list) {
            if (sys instanceof Weapon) {
                weapons.add((Weapon) sys);
            } else if (sys instanceof Countermeasure) {
                cm.add((Countermeasure) sys);
            }
        }
    }

    /**
     * Method reads systems file formatted in style:
     * <p>
     * Weapon_type name damage min_range max_range EraYYYY target:target:target
     * ammo_speed:ammo_mass:ammo_calibre/guidance_type
     * OR
     * Enum_CM_Type name strength min_range max_range EraYYYY
     *
     * @param path path to the file to import.
     * @return set containing all weapon templates from the file.
     */
    public static LinkedList<AbstractSystem> loadSystemsFile(Path path) {
        LinkedList<AbstractSystem> list = new LinkedList<>();

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            br.readLine(); //Skips first headline with no values.
            String line = br.readLine();
            while (line != null) {
                try {
                    if (line.equals(",,,,,,,,,FALSE,")) {
                        break;
                    }
                    String[] word = line.split(",");

                    String name = word[1];
                    double strength = Double.parseDouble(word[2]);
                    double minRange = Double.parseDouble(word[3]);
                    double maxRange = Double.parseDouble(word[4]);

                    if (strength < 1 || minRange < 0 || maxRange <= 0) {
                        throw new IllegalArgumentException(String.format("Error reading the system %s values. " +
                                "One of them is negative or null.", name));
                    }

                    Era era;
                    if (Pattern.matches("[0-9]{4}", word[5])) {
                        era = Era.valueOf("Era" + word[5]);
                    } else {
                        era = Era.valueOf(word[5]);
                    }

                    AbstractSystem system;

                    if (word[0].equals("Radar")) {
                        Radar newRadar = new Radar(name, strength, maxRange, era, word[10]);
                        OOB.addRadar(newRadar);
                        App.err(String.format("%-16s %s", "Loaded from file:", newRadar), false, false);
                        continue;
                    }

                    try {
                        CMType type = CMType.valueOf(word[0].toUpperCase());

                        Countermeasure countermeasure =
                                new Countermeasure(strength, minRange, maxRange, name, era, type, word[10]);
                        system = countermeasure;
                        list.add(countermeasure);
                    } catch (IllegalArgumentException e) {
                        Set<Theatre> targets = new HashSet<>();
                        for (String target : word[6].split(":")) {
                            try {
                                targets.add(Theatre.valueOf(target.toUpperCase().trim()));
                            } catch (IllegalArgumentException ex) {
                                throw new IllegalArgumentException(String.format("Error reading %s targets. %s",
                                        name, ex.getMessage()));
                            }
                        }

                        Weapon newWeapon;
                        if (word[0].equals("Gun")) {
                            String[] ammo = word[7].split(":");
                            Ammunition ammunition;
                            try {
                                ammunition = new Ammunition(Double.parseDouble(ammo[0]),
                                        Double.parseDouble(ammo[1]), Double.parseDouble(ammo[2]));
                            } catch (IndexOutOfBoundsException ex) {
                                throw new IllegalArgumentException(String.format("Error reading %s ammunition " +
                                        "values. Expected 3: Present <%s>", name, ammo.length));
                            }
                            newWeapon = new Gun(strength, minRange, maxRange,
                                    targets, name, era, ammunition, Boolean.parseBoolean(word[9]), word[10]);

                        } else {
                            GuidanceType guidanceType;
                            double speed;
                            try {
                                guidanceType = GuidanceType.valueOf(word[7].toUpperCase());
                                try {
                                    speed = Double.parseDouble(word[8]);
                                } catch (NumberFormatException ex) {
                                    throw new IllegalArgumentException(
                                            String.format("Error reading %s speed. %s", name, ex.getMessage()));
                                }
                            } catch (IllegalArgumentException ex) {
                                throw new IllegalArgumentException(
                                        String.format("Error reading %s guidance type. %s", name, ex.getMessage()));
                            }
                            newWeapon = new Missile(strength, minRange, maxRange, targets,
                                    name, era, guidanceType, speed, word[10]);

                        }
                        list.add(newWeapon);
                        system = newWeapon;
                    }
                    App.err(String.format("%-16s %s", "Loaded from file:", system), false, false);
                } catch (IllegalArgumentException e) {
                    App.err(e.getMessage(), true, true);
                } finally {
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            App.err("Program couldn't have been loaded due to wrong system database.", true, true);
        }
        return list;
    }
}
