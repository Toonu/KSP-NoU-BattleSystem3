package utils;

import crafts.Craft;
import crafts.Vehicle;
import crafts.parts.Armor;
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

import javax.swing.JOptionPane;
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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    //TODO Add reading from online sheet maybe if possible integration with
    // google sheets is viable and easy enough to implement it?

    //TODO Add wrong save dialog warning

    /**
     * Method to save battle second and ongoing attacks or other in-battle situation.
     *
     * @param file file to save to.
     * @return if save was successful.
     */
    public static boolean saveSituationFile(File file) {
        return false;
        //TODO Add Battle Second Saving and Loading methods.
    }

    /**
     * Method saves crafts to file from pathname.
     *
     * @param path     path to save to.
     * @param template if templates should be saved into the file.
     * @return true if the save went successfully.
     */
    public static boolean saveSetup(String path, boolean template) {
        return saveSetupFile(new File(path), template);
    }

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
            Stream.of(OOB.WHITE.getCrafts().stream(), OOB.BLACK.getCrafts().stream()).flatMap(s -> s).forEach(s1 -> {
                try {
                    o.writeObject(s1);
                } catch (IOException e) {
                    System.err.println(String.format("[ERR %s] Error writing craft: %s",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), s1));
                }
                ++WriterReader.counter;
                if (App.isDebug()) {
                    System.out.println(String.format("[LOG %s] %-18s %s",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Craft saved:", s1.toLongString()));
                }
            });
            if (template) {
                for (Craft craft : OOB.TEMPLATE.getCrafts()) {
                    o.writeObject(craft);
                    ++counter;
                    if (App.isDebug()) {
                        System.out.println(String.format("[LOG %s] %-18s %s",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Craft saved:", craft.toLongString()));
                    }
                }
            }
            System.out.println(String.format("[LOG %s] %-18s [%s]",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Crafts saved:", counter));
            return true;
        } catch (IOException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
            JOptionPane.showMessageDialog(Gui.getCurrentWindow(), "Program couldn't have been saved.",
                    "Save failed", JOptionPane.ERROR_MESSAGE);
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
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Craft> whites = new ArrayList<>();
            ArrayList<Craft> blacks = new ArrayList<>();
            ArrayList<Craft> templ = new ArrayList<>();
            int counter = 0;
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    Craft newCraft = (Craft) oi.readObject();
                    if (newCraft.getSide() == Side.WHITE) {
                        whites.add(newCraft);
                    } else if (newCraft.getSide() == Side.BLACK) {
                        blacks.add(newCraft);
                    } else {
                        templ.add(newCraft);
                    }
                    ++counter;
                }
            } catch (EOFException e) {
                if (App.isDebug()) {
                    System.out.println(String.format("[LOG %s] %-18s [W: %s B: %s T: %s]",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Crafts loaded:",
                            whites.size(), blacks.size(), templ.size()));
                }
            }
            if (counter > 0) {
                OOB.WHITE.getCrafts().clear();
                OOB.BLACK.getCrafts().clear();
                OOB.TEMPLATE.getCrafts().clear();

                whites.forEach(OOB.WHITE::addCraft);
                blacks.forEach(OOB.BLACK::addCraft);
                templ.forEach(OOB.TEMPLATE::addCraft);
                return true;
            }
            throw new IOException("No craft has been loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream.",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
            JOptionPane.showMessageDialog(Gui.getCurrentWindow(), "Program couldn't have been loaded.",
                    "Loading failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method loads saved objects from save file.
     *
     * @param pathname path to target file.
     * @return true if file was loaded successfully.
     */
    public static boolean loadSetup(String pathname) {
        return loadSetupFile(new File(pathname));
    }

    /**
     * Method reads formatted craft csv file.
     *
     * @param path path to the file to import.
     * @return set containing all weapon templates from the file.
     */
    public static LinkedList<Craft> loadCSVFile(Path path) {
        LinkedList<Craft> crafts = new LinkedList<>();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] lines = line.split(",");

                if (!lines[1].equals("")) {
                    String name = lines[2];
                    String classification = lines[3];
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

                    //2 name, 3 class
                    //4 speed, 5 sys, 6 weap, 7 armor, 8 era, 9 missiles
                    //10 engines, 11 avionics, 12 internal, 13 fuel, 14 consumption, 15 hardpoints
                    //16 speed, 17 sys, 18 weap, 19 ciws, 20 missiles,
                    //21 software

                    //TODO ERA with tanks

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
                        String[] eraArmor = lines[8].split("/");
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

                    if (App.isDebug()) {
                        System.out.println(String.format("[LOG %s] %-18s %s",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                                "Crafts loaded:", newCraft.toExtraString()));
                    }
                }
                line = br.readLine();
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
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
                    String[] word = line.split(",");

                    String name = word[1];
                    double strength = Double.parseDouble(word[2]);
                    double minRange = Double.parseDouble(word[3]);
                    double maxRange = Double.parseDouble(word[4]);

                    if (strength < 1 || minRange < 0 || maxRange <= 0) {
                        throw new IllegalArgumentException(String.format("Error reading the weapon's %s values. " +
                                "One of them is negative or null.", name));
                    }

                    Era era;
                    if (Pattern.matches("[0-9]{4}", word[5])) {
                        era = Era.valueOf("Era" + word[5]);
                    } else {
                        era = Era.valueOf(word[5]);
                    }

                    AbstractSystem system;

                    try {
                        CMType type = CMType.valueOf(word[0].toUpperCase());

                        Countermeasure countermeasure =
                                new Countermeasure(strength, minRange, maxRange, name, era, type);
                        system = countermeasure;
                        list.add(countermeasure);
                    } catch (IllegalArgumentException e) {
                        Set<Theatre> targets = new HashSet<>();
                        for (String target : word[6].split(":")) {
                            try {
                                targets.add(Theatre.valueOf(target.toUpperCase()));
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
                            newWeapon = new Gun(strength, minRange, maxRange, targets, name, era, ammunition);

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
                            newWeapon = new Missile(strength, minRange, maxRange, targets, name, era, guidanceType, speed);

                        }
                        list.add(newWeapon);
                        system = newWeapon;
                    }
                    if (App.isDebug()) {
                        System.out.println(String.format("[LOG %s] %-16s %s",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Loaded from file:", system));
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(String.format("[ERR %s] %s",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
                } finally {
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            System.err.println(String.format("[ERR %s] %s", LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
        }
        return list;
    }
}
