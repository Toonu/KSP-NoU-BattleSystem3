package com.NoU.UI;

import com.NoU.App;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Tomas Novotny
 */
public class WelcomeWindow {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private JPanel panel;
    private JButton confirmation;
    private JLabel Label;
    private JComboBox Eras;
    private JFormattedTextField WHITEX;
    private JFormattedTextField BLACKX;
    private JFormattedTextField WHITEY;
    private JFormattedTextField BLACKY;
    private JLabel Picture;
    private boolean values = false;


    public WelcomeWindow() {
        $$$setupUI$$$();
        Label.setText(convertToMultiline("Welcome to the Battle System 3.0!\n\nMade by Toonu with " +
                "support of the Nations of Unity Project Team.\nEnjoy!"));
        panel.setBackground(Color.DARK_GRAY);
        Label.setForeground(Color.ORANGE);

        confirmation.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello world.");

            }
        });
        Eras.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                WHITEX.setEnabled(true);
                WHITEY.setEnabled(true);
                BLACKX.setEnabled(true);
                BLACKY.setEnabled(true);
                if (!confirmation.isEnabled() && WHITEX.getBackground() == Color.WHITE &&
                        WHITEY.getBackground() == Color.WHITE && BLACKX.getBackground() == Color.WHITE
                        && BLACKY.getBackground() == Color.WHITE) {
                    confirmation.setEnabled(true);
                }
            }
        });


        WHITEX.addPropertyChangeListener(new PropertyChangeListener() {
            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source
             *            and the property that has changed.
             */
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Double.parseDouble(WHITEX.getText());
                    WHITEX.setBackground(Color.WHITE);
                    values = true;
                } catch (NumberFormatException e) {
                    WHITEX.setBackground(Color.RED);
                    confirmation.setEnabled(false);
                } catch (NullPointerException e) {
                    System.err.println(String.format("[ERR %s %s]", LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                            "Wrong input on coordinate inputs."));
                }
                if (!confirmation.isEnabled() && WHITEX.getBackground() == Color.WHITE &&
                        WHITEY.getBackground() == Color.WHITE && BLACKX.getBackground() == Color.WHITE
                        && BLACKY.getBackground() == Color.WHITE) {
                    confirmation.setEnabled(true);
                }
            }
        });

        WHITEY.addPropertyChangeListener(new PropertyChangeListener() {
            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source
             *            and the property that has changed.
             */
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Double.parseDouble(WHITEY.getText());
                    WHITEY.setBackground(Color.WHITE);
                    values = true;
                } catch (NumberFormatException e) {
                    WHITEY.setBackground(Color.RED);
                    confirmation.setEnabled(false);
                } catch (NullPointerException e) {
                    System.err.println(String.format("[ERR %s %s]", LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                            "Wrong input on coordinate inputs."));
                }
                if (!confirmation.isEnabled() && WHITEX.getBackground() == Color.WHITE &&
                        WHITEY.getBackground() == Color.WHITE && BLACKX.getBackground() == Color.WHITE
                        && BLACKY.getBackground() == Color.WHITE) {
                    confirmation.setEnabled(true);
                }
            }
        });

        BLACKX.addPropertyChangeListener(new PropertyChangeListener() {
            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source
             *            and the property that has changed.
             */
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Double.parseDouble(BLACKX.getText());
                    BLACKX.setBackground(Color.WHITE);
                    values = true;
                } catch (NumberFormatException e) {
                    BLACKX.setBackground(Color.RED);
                    confirmation.setEnabled(false);
                } catch (NullPointerException e) {
                    System.err.println(String.format("[ERR %s %s]", LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                            "Wrong input on coordinate inputs."));
                }
                if (!confirmation.isEnabled() && WHITEX.getBackground() == Color.WHITE &&
                        WHITEY.getBackground() == Color.WHITE && BLACKX.getBackground() == Color.WHITE
                        && BLACKY.getBackground() == Color.WHITE) {
                    confirmation.setEnabled(true);
                }
            }
        });

        BLACKY.addPropertyChangeListener(new PropertyChangeListener() {
            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source
             *            and the property that has changed.
             */
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Double.parseDouble(BLACKY.getText());
                    BLACKY.setBackground(Color.WHITE);
                    values = true;
                } catch (NumberFormatException e) {
                    BLACKY.setBackground(Color.RED);
                    confirmation.setEnabled(false);
                } catch (NullPointerException e) {
                    System.err.println(String.format("[ERR %s %s]", LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                            "Wrong input on coordinate inputs."));
                }
                if (!confirmation.isEnabled() && WHITEX.getBackground() == Color.WHITE &&
                        WHITEY.getBackground() == Color.WHITE && BLACKX.getBackground() == Color.WHITE
                        && BLACKY.getBackground() == Color.WHITE) {
                    confirmation.setEnabled(true);
                }
            }
        });


        Picture.addComponentListener(new ComponentAdapter() {
        });
        Picture.addMouseListener(new MouseAdapter() {
        });
        Picture.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                App.DEBUG = !App.DEBUG;
                if (App.DEBUG) {
                    System.out.println(String.format("[LOG %s %s]", LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                            "Debug disabled."));
                } else {
                    System.out.println(String.format("[LOG %s %s]", LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                            "Debug switched."));
                }
            }
        });
    }

    public static void main(String[] args) {
        WelcomeWindow window = new WelcomeWindow();
        JFrame panel = new JFrame("WelcomeWindow");
        panel.setContentPane(new WelcomeWindow().panel);
        panel.setTitle("Battle System 3.0");

        panel.pack();
        panel.setLocationRelativeTo(null);

        //Monitor size
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        panel.setLocation((width / 2) - (WIDTH / 2), (height / 2) - (HEIGHT / 2));
        panel.setSize(WIDTH, HEIGHT);
        panel.setVisible(true);
    }

    public static String convertToMultiline(String orig) {
        String newLine = "<html> <div style='text-align: center;'>" + orig.replaceAll("\n", "<br>");
        return newLine + "</div></html>";
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel.setMinimumSize(new Dimension(20, 20));
        panel.setPreferredSize(new Dimension(50, 20));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-14336)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, null, new Color(-4473925)));
        confirmation = new JButton();
        confirmation.setBackground(new Color(-7895161));
        confirmation.setEnabled(false);
        confirmation.setForeground(new Color(-14336));
        confirmation.setText("Confirm.");
        panel.add(confirmation, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-14336));
        label1.setText("Team One");
        panel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-14336));
        label2.setText("Team Two");
        panel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BLACKX = new JFormattedTextField();
        BLACKX.setEnabled(false);
        BLACKX.setText("100");
        BLACKX.setToolTipText("X coordinate");
        panel.add(BLACKX, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        WHITEY = new JFormattedTextField();
        WHITEY.setEnabled(false);
        WHITEY.setText("0");
        WHITEY.setToolTipText("Y coordinate");
        panel.add(WHITEY, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 2, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Label = new JLabel();
        Label.setHorizontalAlignment(0);
        Label.setHorizontalTextPosition(0);
        Label.setText("Welcome to the bS");
        panel.add(Label, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Eras = new JComboBox();
        Eras.setForeground(new Color(-14336));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Era1950");
        defaultComboBoxModel1.addElement("Era1960");
        defaultComboBoxModel1.addElement("Era1970");
        defaultComboBoxModel1.addElement("Era1980");
        defaultComboBoxModel1.addElement("Era1990");
        defaultComboBoxModel1.addElement("Era2000");
        defaultComboBoxModel1.addElement("Era2010");
        defaultComboBoxModel1.addElement("Era2020");
        defaultComboBoxModel1.addElement("Era2030");
        Eras.setModel(defaultComboBoxModel1);
        Eras.setToolTipText("Default year for your crafts.");
        panel.add(Eras, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-14336));
        label3.setText("Default Year:");
        panel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        WHITEX = new JFormattedTextField();
        WHITEX.setEnabled(false);
        WHITEX.setText("-100");
        WHITEX.setToolTipText("X coordinate");
        panel.add(WHITEX, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        BLACKY = new JFormattedTextField();
        BLACKY.setEnabled(false);
        BLACKY.setText("0");
        BLACKY.setToolTipText("Y coordinate");
        panel.add(BLACKY, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-16505495));
        panel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Picture = new JLabel();
        Picture.setIcon(new ImageIcon(getClass().getResource("/resources/Imperial Seal x4.png")));
        Picture.setText("Label");
        panel1.add(Picture, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(256, 256), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}

