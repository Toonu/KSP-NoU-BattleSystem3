package com.NoU.Utils;

import com.NoU.Crafts.Craft;
import com.NoU.Enum.Age;
import com.NoU.Enum.Sides;
import com.NoU.Enum.Type;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Toonu
 */
public class WriterReader {
    public static void main(String[] args) {

        Craft craft = new Craft.Builder()
                .setCraftProductionYear(Age.X1960)
                .setSide(Sides.WHITE)
                .setCSubclass(Type.AFV)
                .setName("Test")
                .setSpeed(25)
                .buildGround();

        try {
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(craft);
            o.close();

            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            Craft readCraft = (Craft) oi.readObject();

            System.out.println(craft.toString());

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
