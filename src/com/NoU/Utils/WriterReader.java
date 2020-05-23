package com.NoU.Utils;

import com.NoU.App;
import com.NoU.Crafts.Craft;
import com.NoU.Enum.Era;
import com.NoU.Enum.GuidanceType;
import com.NoU.Enum.Side;
import com.NoU.Enum.Theatre;
import com.NoU.Enum.Type;
import com.NoU.Systems.Ammunition;
import com.NoU.Systems.Gun;
import com.NoU.Systems.Missile;
import com.NoU.Systems.Weapon;

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
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * @author Toonu
 */
public class WriterReader {
    /**
     * Method saves crafts to save file.
     *
     * @param crafts SortedSet of craft to save.
     * @return true if everything went correctly.
     */
    public static boolean save(SortedSet<Craft> crafts, String pathname) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File(pathname)))) {
            for (Craft craft : crafts) {
                o.writeObject(craft);
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] Wrote object to file:%s", LocalTime.now(), craft));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s", LocalTime.now(), e));
            return false;
        }
    }

    /**
     * Method loads saved objects from save file.
     *
     * @return Craft set.
     */
    public static SortedSet<Craft> load(String pathname) {
        SortedSet<Craft> crafts = new TreeSet<>();
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File(pathname)))) {
            int counter = 0;
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    crafts.add((Craft) oi.readObject());
                    ++counter;
                }
            } catch (EOFException e) {
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] End of file. %s Crafts loaded.",
                            LocalTime.now(), counter));
                }
            }
            return crafts;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s", LocalTime.now(), e));
            return null;
        }
    }

    /**
     * Method reads formatted craft csv file.
     *
     * @param path path to the file to import.
     * @return set containing all weapon templates from the file.
     */
    public static SortedSet<Craft> loadFile(Path path) {
        SortedSet<Craft> crafts = new TreeSet<>();

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
                            setType(type).setCraftProductionYear(era).setSide(Side.NEUTRAL).build();
                    crafts.add(newCraft);

                    if (App.DEBUG) {
                        System.out.println(String.format("[LOG %s] Craft Loaded: %s", LocalTime.now(), newCraft));
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
     * Method reads weapons file formatted in style:
     * <p>
     * Weapon_type name damage min_range max_range EraYYYY target:target:target
     * ammo_speed:ammo_mass:ammo_calibre/guidance_type
     *
     * @param path path to the file to import.
     * @return set containing all weapon templates from the file.
     */
    public static SortedSet<Weapon> readWeaponFile(Path path) {
        SortedSet<Weapon> weapons = new TreeSet<>();

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                try {
                    String[] lines = line.split(" ");

                    String name = lines[1];
                    double damage = Double.parseDouble(lines[2]);
                    double minRange = Double.parseDouble(lines[3]);
                    double maxRange = Double.parseDouble(lines[4]);
                    if (damage < 1 || minRange < 0 || maxRange < 1) {
                        throw new IllegalArgumentException(String.format("Error reading the weapon's %s values. " +
                                "One of them is negative or null.", name));
                    }

                    Era era;
                    if (Pattern.matches("[0-9]{4}", lines[5])) {
                        era = Era.valueOf("Era" + lines[5]);
                    } else {
                        era = Era.valueOf(lines[5]);
                    }

                    Set<Theatre> targets = new HashSet<>();
                    for (String target : lines[6].split(":")) {
                        try {
                            targets.add(Theatre.valueOf(target.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException(String.format("Error reading %s targets. %s",
                                    name, e.getMessage()));
                        }
                    }

                    Weapon newWeapon;
                    if (lines[0].equals("Gun")) {
                        String[] ammo = lines[7].split(":");
                        Ammunition ammunition;
                        try {
                            ammunition = new Ammunition(Double.parseDouble(ammo[0]),
                                    Double.parseDouble(ammo[1]), Double.parseDouble(ammo[2]));
                        } catch (IndexOutOfBoundsException e) {
                            throw new IllegalArgumentException(String.format("Error reading %s ammunition " +
                                    "values. Expected 3: Present <%s>", name, ammo.length));
                        }
                        newWeapon = new Gun(damage, minRange, maxRange, targets, name, era, ammunition);

                    } else {
                        GuidanceType guidanceType;
                        try {
                            guidanceType = GuidanceType.valueOf(lines[7].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException(
                                    String.format("Error reading %s guidance type. %s", name, e.getMessage()));
                        }
                        newWeapon = new Missile(damage, minRange, maxRange, targets, name, era, guidanceType);

                    }
                    weapons.add(newWeapon);

                    if (App.DEBUG) {
                        System.out.println(String.format("[LOG %s] Loaded: %s", LocalTime.now(), newWeapon));
                    }
                } catch (NumberFormatException e) {
                    System.err.println(String.format("[ERR %s] %s Numbers expected here.",
                            LocalTime.now(), e));
                } catch (IllegalArgumentException e) {
                    System.err.println(String.format("[ERR %s] %s", LocalTime.now(), e));
                } finally {
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            System.err.println(String.format("[ERR %s] %s", LocalTime.now(), e));
        }
        return weapons;
    }
}
