package com.NoU.Utils;

import com.NoU.App;
import com.NoU.Crafts.Craft;
import com.NoU.Enum.Era;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Type;

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

/**
 * @author Toonu
 */
public class WriterReader {
    public static boolean save(Set<Craft> crafts) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File("savefile.txt")))) {
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

    public static Set<Craft> load() {
        Set<Craft> crafts = new HashSet<>();
        //noinspection SpellCheckingInspection
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("savefile.txt")))) {
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

    public static Set<Craft> loadFile(Path path) {
        SortedSet<Craft> crafts = new TreeSet<>();

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            br.readLine();
            String line = br.readLine();

            while (line != null) {
                String[] lines = line.split(",");

                if (!lines[1].equals("")) {
                    String name = lines[2];
                    String classification = lines[3];

                    Era era;
                    String software = lines[21];
                    switch (software) {
                        case "50s":
                            era = Era.Era1950;
                            break;
                        case "60s":
                            era = Era.Era1960;
                            break;
                        case "70s":
                            era = Era.Era1970;
                            break;
                        case "80s":
                            era = Era.Era1980;
                            break;
                        case "90s":
                            era = Era.Era1990;
                            break;
                        case "00s":
                            era = Era.Era2000;
                            break;
                        case "10s":
                            era = Era.Era2010;
                            break;
                        case "20s":
                            era = Era.Era2020;
                            break;
                        default:
                            era = App.DEFAULT_YEAR;
                    }

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


                    crafts.add(new Craft.Builder().setName(name).setSpeed(speed).
                            setType(type).setCraftProductionYear(era).setSide(Sides.NEUTRAL).build());

                    if (App.DEBUG) {
                        System.out.println(String.format("[LOG %s] Craft Loaded: %s", LocalTime.now(), crafts.first()));
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("X");
        }
        return crafts;
    }
}
