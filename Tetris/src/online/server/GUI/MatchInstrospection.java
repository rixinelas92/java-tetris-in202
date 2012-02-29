package online.server.GUI;

import Tetris_interface.components.ActionJList;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import online.util.Match;

/**
 *
 * @author gustavo
 */
/**
 * This class was implemented in order to attend the necessity of menager the 
 * screens that show the players availables in the network and the packages that 
 * are change between the players and the server. 
 */
public class MatchInstrospection extends JPanel {
    JTabbedPane attachTo;
    JList messages;
    /**
     * It builds the screen that will shows the process running in the network.
     * @param attachTo defines what will be added like a tab.
     * @param m identifies a match by a number.
     */
    public MatchInstrospection(JTabbedPane attachTo,Match m){
        this.attachTo =  attachTo;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        int[] ids = m.getPlayersIds();
        messages = new JList();
       // userList.setCellRenderer(new CellRendererWithImage(null));
        messages.setModel(new DefaultListModel());
        messages.addMouseListener(new ActionJList(messages));
        JScrollPane scrollPane = new JScrollPane(messages);

        Dimension size = new Dimension(800,400);
        scrollPane.setMaximumSize(size);
        scrollPane.setPreferredSize(size);
        scrollPane.setMinimumSize(size);
        
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(scrollPane);
        this.setBorder(BorderFactory.createTitledBorder("Match Between "+
                m.getPlayerWithid(ids[0]).getName()+" and "+
                m.getPlayerWithid(ids[1]).getName()));
        attachTo.addTab("Match: "+m.getMatchid()+"",this);
        m.setInstrospector(this);

    }
    /**
     * Defines what will be remove of the tab.
     */
    public void detach(){
        attachTo.remove(this);
    }
    /**
     * It allows the visualization of the messages that are change among the
     * players and the server.
     * @param msg defines the message that will be printed.
     */
    public void addSignal(String msg) {
        DefaultListModel lm  = (DefaultListModel)messages.getModel();
        lm.addElement(msg);
        synchronized(MatchInstrospection.class){
            messages.ensureIndexIsVisible(lm.getSize()-1);
        }
    }
}
