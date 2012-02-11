/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris_interface.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.ListModel;
import online.util.PlayerDescriptor;
import tetris.Main;

/**
 *
 * @author gustavo
 */
public class ActionJList extends MouseAdapter {

    protected JList list;

    /**
     * Setter of the parameter <em>list</em>.
    //########################################################
     * @param l defines the player
     */
    public ActionJList(JList l) {
        list = l;
    }

    @Override
    //############################################################
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int index = list.locationToIndex(e.getPoint());
            ListModel dlm = list.getModel();
            Object item = dlm.getElementAt(index);
            list.ensureIndexIsVisible(index);
            Main.requestMatchWith((PlayerDescriptor) item);
        }
    }
}
