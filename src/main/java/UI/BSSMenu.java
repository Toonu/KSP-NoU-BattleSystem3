package ui;

import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Toonu
 */
public class BSSMenu extends BSMenu {
    private final JMenuItem save = new JMenuItem("Save File");

    public BSSMenu() {
        super();
        super.getFile().add(save);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO Add saver
            }
        });
    }
}
