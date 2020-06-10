package ui.battleGui;

import impl.MapBackground;
import impl.OOB;
import simulation.crafts.Craft;
import simulation.enums.Side;
import ui.Gui;
import ui.JCraftList;
import ui.JCraftPanel;
import ui.JMenuExt;
import ui.PopupMenuRemove;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * @author Toonu
 */
public class OOBFrame extends JFrame {
    private final Container c = getContentPane();
    private final DefaultListModel<Craft> textForWhite = new DefaultListModel<>();
    private final DefaultListModel<Craft> textForBlack = new DefaultListModel<>();
    private final JCraftList<Craft> whiteListedCrafts = new JCraftList<>(textForWhite);
    private final JCraftList<Craft> blackListedCrafts = new JCraftList<>(textForBlack);
    private final JScrollPane scrollerWhite = new JScrollPane(whiteListedCrafts);
    private final JScrollPane scrollerBlack = new JScrollPane(blackListedCrafts);
    private final SortedMap<Craft, ArrayList<JButton>> templateButtons = new TreeMap<>();
    private final JButton nextScreen = new JButton("Finish");
    private final JButton editing = new JButton("Edit selected");
    private final ArrayList<Craft> selectedCraftsFromList = new ArrayList<>();
    private JCraftPanel details;
    private final GridBagConstraints gcDetails;
    private JList<Craft> lastSelectedList;

    /**
     * Constructor.
     *
     * @param title  title of the frame.
     * @param battle next frame after this one to launch.
     */
    public OOBFrame(String title, JFrame battle) {
        super(title);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        c.setBackground(Gui.BACKGROUND);
        c.setLayout(new GridBagLayout());
        c.setPreferredSize(Gui.getMonitorSize());
        c.setMinimumSize(Gui.SIZE);

        gc.weighty = 0.05;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = 6;
        add(new JMenuExt(1), gc);

        whiteListedCrafts.setBackground(Gui.BACKGROUND);
        whiteListedCrafts.setForeground(Color.WHITE);

        blackListedCrafts.setBackground(Gui.BACKGROUND);
        blackListedCrafts.setForeground(Color.WHITE);

        gc = new GridBagConstraints();
        gc.gridheight = Side.TEMPLATE.getCrafts().size();
        gc.weighty = 0.7;
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 4;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        add(scrollerWhite, gc);

        gc.gridx = 4;
        gc.gridwidth = 2;
        add(scrollerBlack, gc);


        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridy = 0;
        gc.gridx = 1;
        gc.weightx = 0.1;

        for (Craft craft : Side.TEMPLATE.getCrafts()) {
            ++gc.gridy;
            gc.fill = GridBagConstraints.VERTICAL;
            templateButtons.put(craft, new ArrayList<>());
            templateButtons.get(craft).add(new JButton("+"));
            templateButtons.get(craft).add(new JButton("+"));

            c.add(templateButtons.get(craft).get(0), gc);
            ++gc.gridx;
            gc.weightx = 8;

            JLabel newLabel = new JLabel(craft.getName());
            newLabel.setForeground(Gui.FOREGROUND);
            newLabel.setHorizontalAlignment(SwingConstants.CENTER);
            newLabel.setPreferredSize(new Dimension(200, 20));
            c.add(newLabel, gc);

            gc.weightx = 0.1;
            ++gc.gridx;
            c.add(templateButtons.get(craft).get(1), gc);
            gc.gridx -= 2;

            templateButtons.get(craft).get(0).addActionListener(e -> {
                Craft newCraft = craft.copy(Side.WHITE);
                Side.WHITE.addCraft(newCraft);
                textForWhite.addElement(newCraft);
                whiteListedCrafts.updateUI();
            });
            templateButtons.get(craft).get(1).addActionListener(e -> {
                Craft newCraft = craft.copy(Side.BLACK);
                Side.BLACK.addCraft(newCraft);
                textForBlack.addElement(newCraft);
                blackListedCrafts.updateUI();
            });
        }

        gc.anchor = GridBagConstraints.SOUTH;
        ++gc.gridy;
        ++gc.gridy;
        gc.weighty = 0.15;
        gc.gridx = 5;
        gc.fill = GridBagConstraints.BOTH;
        add(nextScreen, gc);

        whiteListedCrafts.addListSelectionListener(e -> {
            selectedCraftsFromList.clear();
            if (whiteListedCrafts.getSelectedValuesList().size() == 1) {
                selectedCraftsFromList.add(whiteListedCrafts.getSelectedValue());
                Gui.getOob().getDetails().getNewName().
                        setText(whiteListedCrafts.getSelectedValuesList().get(0).getName());
                details.updateUI(whiteListedCrafts);
            } else {
                selectedCraftsFromList.addAll(whiteListedCrafts.getSelectedValuesList());
            }
            lastSelectedList = whiteListedCrafts;
        });
        whiteListedCrafts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    new PopupMenuRemove(whiteListedCrafts.getSelectedValuesList());
                }
            }
        });

        blackListedCrafts.addListSelectionListener(e -> {
            selectedCraftsFromList.clear();
            if (blackListedCrafts.getSelectedValuesList().size() == 1) {
                selectedCraftsFromList.add(blackListedCrafts.getSelectedValue());
                Gui.getOob().getDetails().getNewName().
                        setText(blackListedCrafts.getSelectedValuesList().get(0).getName());
                details.updateUI(blackListedCrafts);
            } else {
                selectedCraftsFromList.addAll(blackListedCrafts.getSelectedValuesList());
            }
            lastSelectedList = blackListedCrafts;
        });
        blackListedCrafts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    new PopupMenuRemove(blackListedCrafts.getSelectedValuesList());
                }
            }
        });
        nextScreen.addActionListener(e -> {
            setVisible(false);
            dispose();
            Gui.setCurrentWindow(new BattleFrame(title));
            TimerTask tm = new MapBackground();
            OOB.getT().schedule(tm, 0, 1000);
        });

        details = new JCraftPanel(" ", selectedCraftsFromList, false);
        gcDetails = new GridBagConstraints();
        gcDetails.gridx = 0;
        gcDetails.gridy = gc.gridy;
        gcDetails.gridwidth = 4;
        gcDetails.weighty = 0.15;
        gcDetails.fill = GridBagConstraints.BOTH;
        add(details, gcDetails);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        validate();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(false);
    }

    public Container getC() {
        return c;
    }

    public JScrollPane getScrollerBlack() {
        return scrollerBlack;
    }

    public JScrollPane getScrollerWhite() {
        return scrollerWhite;
    }

    public SortedMap<Craft, ArrayList<JButton>> getTemplateButtons() {
        return templateButtons;
    }

    public JButton getNextScreen() {
        return nextScreen;
    }

    public ArrayList<Craft> getSelectedCraftsFromList() {
        return selectedCraftsFromList;
    }

    public DefaultListModel<Craft> getTextForBlack() {
        return textForBlack;
    }

    public DefaultListModel<Craft> getTextForWhite() {
        return textForWhite;
    }

    public JCraftList<Craft> getBlackListedCrafts() {
        return blackListedCrafts;
    }

    public JCraftList<Craft> getWhiteListedCrafts() {
        return whiteListedCrafts;
    }

    public JButton getEditing() {
        return editing;
    }

    public JCraftPanel getDetails() {
        return details;
    }

    public void setDetails(JCraftPanel details) {
        this.details = details;
    }

    public GridBagConstraints getGcDetails() {
        return gcDetails;
    }

    public JList<Craft> getLastSelectedList() {
        return lastSelectedList;
    }
}
