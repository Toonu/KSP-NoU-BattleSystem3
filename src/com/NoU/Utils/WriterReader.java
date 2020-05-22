package com.NoU.Utils;

import com.NoU.App;
import com.NoU.Crafts.Craft;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Toonu
 */
public class WriterReader {
    public static boolean save(Set<Craft> crafts) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File("savefile.txt")))) {
            for (Craft craft : crafts) {
                o.writeObject(craft);
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] Wrote object to file:\n%s\n\n", LocalTime.now(), craft));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream\n%s", LocalTime.now(), e));
            return false;
        }
    }

    public static Set<Craft> load() {
        Set<Craft> crafts = new HashSet<>();
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("savefile.txt")))) {
            try {
                while (true) {
                    crafts.add((Craft) oi.readObject());
                }
            } catch (EOFException e) {
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s] End of file. Crafts loaded.", LocalTime.now()));
                }
            }
            return crafts;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(String.format("[ERR %s] Error initializing stream\n%s", LocalTime.now(), e));
            return null;
        }
    }
}
