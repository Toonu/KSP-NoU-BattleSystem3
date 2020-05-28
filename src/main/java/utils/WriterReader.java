package utils;

import crafts.Craft;
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Toonu
 */
public class WriterReader {
    //TODO Add reading from online sheet maybe if possible integration with
    // google sheets is viable and easy enough to implement it?

    /**
     * Method saves crafts to file from pathname.
     *
     * @param path path to save to.
     * @return true if the save went successfully.
     */
    public static boolean saveSituation(String path) {
        return saveSituationFile(new File(path));
    }

    /**
     * Method saves crafts to save file.
     *
     * @param file file to load.
     * @return true if everything went correctly.
     */
    public static boolean saveSituationFile(File file) {
        int counter = 0;
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Craft craft : OOB.WHITE.getCrafts()) {
                o.writeObject(craft);
                if (App.isDebug()) {
                    System.out.println(String.format("[LOG %s] %-18s %s",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Craft saved:", craft.toLongString()));
                }
                ++counter;
            }
            //TODO eliminate duplicite code
            for (Craft craft : OOB.BLACK.getCrafts()) {
                o.writeObject(craft);
                if (App.isDebug()) {
                    System.out.println(String.format("[LOG %s] %-18s %s",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Craft saved:", craft.toLongString()));
                }
                ++counter;
            }

            System.out.println(String.format("[LOG %s] %-18s [%s]",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Crafts saved:", counter));
            return true;
        } catch (IOException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
            return false;
        }
    }

    /**
     * Method loads saved file.
     *
     * @param file file to load.
     * @return crafts loaded.
     */
    public static boolean loadSituationFile(File file) {
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file))) {
            int counter = 0;
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    Craft newCraft = (Craft) oi.readObject();
                    if (newCraft.getSide() == Side.WHITE) {
                        OOB.WHITE.addCraft(newCraft);
                    } else if (newCraft.getSide() == Side.BLACK) {
                        OOB.BLACK.addCraft(newCraft);
                    } else {
                        OOB.TEMPLATE.addCraft(newCraft);
                    }
                    ++counter;
                }
            } catch (EOFException e) {
                if (App.isDebug()) {
                    System.out.println(String.format("[LOG %s] %-18s [%s]",
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Crafts loaded:", counter));
                }
            }
            if (counter > 0) {
                return true;
            }
            throw new IOException("No craft has been loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s",
                    LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e));
            return false;
        }
    }

    /**
     * Method loads saved objects from save file.
     *
     * @param pathname path to target file.
     * @return true if file was loaded successfully.
     */
    public static boolean loadSituation(String pathname) {
        return loadSituationFile(new File(pathname));
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
            br.readLine(); //Skips first line with headline values.
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

                    try {
                        speed = Double.parseDouble(lines[4] + lines[16]);
                    } catch (NumberFormatException e) {
                        speed = Double.parseDouble(lines[10]);
                    }

                    Craft newCraft = new Craft.Builder().setName(name).setSpeed(speed).
                            setType(type).setCraftProductionYear(era).setSide(Side.TEMPLATE).build();
                    crafts.add(newCraft);

                    if (App.isDebug()) {
                        System.out.println(String.format("[LOG %s] %-18s %s",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                                "Crafts loaded:", newCraft.toLongString()));
                    }
                }
                line = br.readLine();
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
        }
        return crafts;
    }

    /**
     * Method returns new LinkedList of weapons from file.
     *
     * @param path Path to file.
     * @return LinkedList<Weapon> containing all weapons.
     */
    public static LinkedList<Weapon> readWeaponFile(Path path) {
        LinkedList<AbstractSystem> list = new LinkedList<>(loadCMWeaponFile(path));
        LinkedList<Weapon> weapons = new LinkedList<>();
        for (AbstractSystem sys : list) {
            if (sys instanceof Weapon) {
                weapons.add((Weapon) sys);
            }
        }
        return weapons;
    }

    /**
     * Method returns new LinkedList of countermeasures from file.
     *
     * @param path Path to file.
     * @return LinkedList<Countermeasure> containing all countermeasures.
     */
    public static LinkedList<Countermeasure> readCMFile(Path path) {
        LinkedList<AbstractSystem> list = new LinkedList<>(loadCMWeaponFile(path));
        LinkedList<Countermeasure> cm = new LinkedList<>();
        for (AbstractSystem sys : list) {
            if (sys instanceof Countermeasure) {
                cm.add((Countermeasure) sys);
            }
        }
        return cm;
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
    private static LinkedList<AbstractSystem> loadCMWeaponFile(Path path) {
        LinkedList<AbstractSystem> list = new LinkedList<>();

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                try {
                    String[] word = line.split(" ");

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
                            try {
                                guidanceType = GuidanceType.valueOf(word[7].toUpperCase());
                            } catch (IllegalArgumentException ex) {
                                throw new IllegalArgumentException(
                                        String.format("Error reading %s guidance type. %s", name, ex.getMessage()));
                            }
                            newWeapon = new Missile(strength, minRange, maxRange, targets, name, era, guidanceType);

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
