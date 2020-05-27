package UI;

import Crafts.Craft;
import Enum.Era;
import Impl.App;
import UI.CraftAnalyzer.CraftAnalyzer;
import Utils.WriterReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;


/**
 * @author Toonu
 */
public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Container c = getContentPane();

        gc.gridwidth = 3;
        gc.anchor = GridBagConstraints.CENTER;

        // First column.
        gc.gridx = 0;
        JLabel Picture = new JLabel();
        //2Picture.setIcon(new ImageIcon(getClass().getResource("resources/Imperial Seal x4.png")));
        gc.gridy = 1;
        gc.weighty = 4;
        c.add(Picture, gc);

        JLabel welcomeText = new JLabel(MainUI.convertToMultiline("Welcome to the Battle System 3.0!\n\n" +
                "Made by Toonu with support of the Nations of Unity Project Team."));
        gc.gridy = 2;
        gc.weighty = 2;
        c.add(welcomeText, gc);

        JButton nextWindow = new JButton("Confirm");
        gc.gridy = 6;
        gc.fill = GridBagConstraints.BOTH;
        c.add(nextWindow, gc);

        JLabel infoYears = new JLabel("Default year: ");
        gc = new GridBagConstraints();
        gc.weighty = 1;
        gc.weightx = 3;
        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_END;
        c.add(infoYears, gc);

        JLabel infoTeamI = new JLabel("1st Team spawn coordinates: ");
        gc.gridy = 4;
        gc.weightx = 4;
        c.add(infoTeamI, gc);

        JLabel infoTeamII = new JLabel("2nd Team spawn coordinates: ");
        gc.gridy = 5;
        c.add(infoTeamII, gc);

        //First column
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(2, 2, 2, 2);
        JComboBox<Era> eras = new JComboBox<>();
        eras.addItem(Era.Era1950);
        eras.addItem(Era.Era1960);
        eras.addItem(Era.Era1970);
        eras.addItem(Era.Era1980);
        eras.addItem(Era.Era1990);
        eras.addItem(Era.Era2000);
        eras.addItem(Era.Era2010);
        eras.addItem(Era.Era2020);
        gc.gridy = 3;
        gc.weightx = 6;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.HORIZONTAL;
        c.add(eras, gc);
        gc.gridwidth = 1;

        DocumentFilter nfs = new DocumentFilter();

        //Second column
        JTextField coordXI = new JTextField("-100");
        JTextField coordYI = new JTextField("0");
        gc.weightx = 3;
        gc.gridy = 4;
        c.add(coordXI, gc);
        gc.gridx = 2;
        c.add(coordYI, gc);

        JTextField coordXII = new JTextField("100");
        JTextField coordYII = new JTextField("0");
        gc.gridy = 5;
        gc.gridx = 1;
        c.add(coordXII, gc);
        gc.gridx = 2;
        c.add(coordYII, gc);

        c.setBackground(MainUI.BACKGROUND);
        welcomeText.setForeground(MainUI.FOREGROUND);
        infoYears.setForeground(MainUI.FOREGROUND);
        infoTeamI.setForeground(MainUI.FOREGROUND);
        infoTeamII.setForeground(MainUI.FOREGROUND);

        nextWindow.addActionListener(e -> System.out.println("Clicked me."));
        coordXI.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                coordXI.selectAll();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                coordXI.selectAll();
            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        coordXI.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Double.parseDouble(coordXI.getText());
                    coordXI.setBackground(Color.WHITE);
                    if (coordXI.getBackground() == coordXII.getBackground() &&
                            coordYI.getBackground() == coordYII.getBackground() &&
                            coordYII.getBackground() == Color.WHITE) {
                        nextWindow.setEnabled(true);
                    }
                } catch (NumberFormatException ex) {
                    coordXI.setBackground(Color.RED);
                    nextWindow.setEnabled(false);
                }
            }
        });
        coordYI.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                coordYI.selectAll();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                coordYI.selectAll();
            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        coordYI.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Double.parseDouble(coordYI.getText());
                    coordYI.setBackground(Color.WHITE);
                    if (coordXI.getBackground() == coordXII.getBackground() &&
                            coordYI.getBackground() == coordYII.getBackground() &&
                            coordYII.getBackground() == Color.WHITE) {
                        nextWindow.setEnabled(true);
                    }
                } catch (NumberFormatException ex) {
                    coordYI.setBackground(Color.RED);
                    nextWindow.setEnabled(false);
                }
            }
        });
        coordXII.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                coordXII.selectAll();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                coordXII.selectAll();
            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        coordXII.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Double.parseDouble(coordXII.getText());
                    coordXII.setBackground(Color.WHITE);
                    if (coordXI.getBackground() == coordXII.getBackground() &&
                            coordYI.getBackground() == coordYII.getBackground() &&
                            coordYII.getBackground() == Color.WHITE) {
                        nextWindow.setEnabled(true);
                    }
                } catch (NumberFormatException ex) {
                    coordXII.setBackground(Color.RED);
                    nextWindow.setEnabled(false);
                }
            }
        });
        coordYII.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                coordYII.selectAll();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                coordYII.selectAll();
            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        coordYII.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Double.parseDouble(coordYII.getText());
                    coordYII.setBackground(Color.WHITE);
                    if (coordXI.getBackground() == coordXII.getBackground() &&
                            coordYI.getBackground() == coordYII.getBackground() &&
                            coordYII.getBackground() == Color.WHITE) {
                        nextWindow.setEnabled(true);
                    }
                } catch (NumberFormatException ex) {
                    coordYII.setBackground(Color.RED);
                    nextWindow.setEnabled(false);
                }
            }
        });

        Picture.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                App.DEBUG = !App.DEBUG;
                System.out.println(String.format("[LOG %s] %s: %s",
                        LocalTime.now().truncatedTo(ChronoUnit.SECONDS), "Debug set to", App.DEBUG));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JMenuBar menu = new JMenuBar();
        gc.insets = new Insets(0, 0,0,0);
        gc.anchor = GridBagConstraints.NORTH;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 3;
        gc.weighty = 0.5;
        c.add(menu, gc);

        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load File");
        JMenuItem craftAnalyzer = new JMenuItem("Craft Analyzer");

        menu.add(file);
        file.add(craftAnalyzer);

        file.add(load);

        load.addActionListener(e -> {
            JDialog frameLoad = new JDialog();
            JFileChooser jfc = new JFileChooser();


            //Monitor size
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            frameLoad.setLocation((width / 2) - (MainUI.WIDTH / 2), (height / 2) - (MainUI.HEIGHT / 2));
            frameLoad.add(jfc);
            frameLoad.setSize(MainUI.WIDTH, MainUI.HEIGHT);
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.setCurrentDirectory(new File(""));


            jfc.addActionListener(e1 -> {
                if (e1.getActionCommand().equals("CancelSelection")) {
                    frameLoad.dispatchEvent(new WindowEvent(frameLoad, WindowEvent.WINDOW_CLOSING));
                } else if (e1.getActionCommand().equals("ApproveSelection")) {
                    File file1 = jfc.getSelectedFile();
                    LinkedList<Craft> read = WriterReader.loadFromFile(file1);
                    if (read == null) {
                        System.err.println(String.format("[ERR %s] Error initializing stream. Exception: %s",
                                LocalTime.now().truncatedTo(ChronoUnit.SECONDS), e1));
                    }
                    frameLoad.dispatchEvent(new WindowEvent(frameLoad, WindowEvent.WINDOW_CLOSING));
                    //TODO Add teleport here to battle directly.
                    //TODO Add setup for save to catch if the saved file is from battle or setup.
                }
            });


            frameLoad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frameLoad.setVisible(true);
        });
        craftAnalyzer.addActionListener(e -> {
            String[] str = new String[1];
            str[0] = "";
            CraftAnalyzer.main(str);
        });


    }

}

