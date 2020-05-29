package ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.util.LinkedList;

/**
 * @author Toonu
 */
public class JCraftList<Craft> extends JList<Craft> {
    private final DefaultListModel<Craft> dlm;

    public JCraftList(DefaultListModel<Craft> dlm) {
        super(dlm);
        this.dlm = dlm;
    }

    public void updateUI(LinkedList<Craft> list) {
        dlm.clear();
        for (Craft craft : list) {
            dlm.addElement(craft);
        }

        super.updateUI();
    }
}
