package ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.util.LinkedList;

/**
 * @param <Craft> Craft to be used in the list.
 * @author Toonu
 */
public class JCraftList<Craft> extends JList<Craft> {
    private final DefaultListModel<Craft> dlm;

    /**
     * Constructor.
     *
     * @param dlm DefaultListModel
     */
    public JCraftList(DefaultListModel<Craft> dlm) {
        super(dlm);
        this.dlm = dlm;
    }

    /**
     * Updates list with the craft in list.
     *
     * @param list craft list.
     */
    public void updateUI(LinkedList<Craft> list) {
        dlm.clear();
        for (Craft craft : list) {
            dlm.addElement(craft);
        }

        super.updateUI();
    }
}
