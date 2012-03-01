/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package online.server.GUI;

/**
 *
 * @author gustavo
 */
import Tetris_interface.components.ActionJList;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;
import online.util.Match;
import online.util.Player;

/**
 * This class was implemented in order to implement the pane that will show the
 * users of the network that are availabels and the comunication in the network.
 */
public class SIM extends JPanel {

    static SIM instance;
    JList userList;
    JList matchList;
    JTabbedPane tabbedPane;

    /**
     * Creates a new network's interface, with a format by default.
     */
    public SIM() {
        super(new BorderLayout());
        tabbedPane = new JTabbedPane();
        JPanel buttonRow = new JPanel();
        JButton exit = new JButton("Terminate Server");
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Use default FlowLayout.
        buttonRow.add(createUserListComp());
        buttonRow.add(createMatchListComp());
        tabbedPane.addTab("Users and Matchs", buttonRow);
        //Add tabbedPane to this panel.
        add(tabbedPane, BorderLayout.CENTER);
        add(exit, BorderLayout.PAGE_END);
    }

    /**
     * It creates a user list of the players availables in the network.
     *
     * @return a panel with the user list.
     */
    protected JPanel createUserListComp() {
        userList = new JList();
        // userList.setCellRenderer(new CellRendererWithImage(null));
        userList.setModel(new DefaultListModel());
        userList.addMouseListener(new ActionJList(userList));
        JScrollPane scrollPane = new JScrollPane(userList);
        Dimension size = new Dimension(400, 400);
        scrollPane.setMaximumSize(size);
        scrollPane.setPreferredSize(size);
        scrollPane.setMinimumSize(size);
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createTitledBorder("User List"));
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.add(scrollPane);
        return pane;
    }

    /**
     * It creates a list showing the matchs that are running and its
     * information.
     *
     * @return a pane with the match list.
     */
    protected JPanel createMatchListComp() {
        matchList = new JList();
        // userList.setCellRenderer(new CellRendererWithImage(null));
        matchList.setModel(new DefaultListModel());
        matchList.addMouseListener(new ActionJList(matchList));
        JScrollPane scrollPane = new JScrollPane(matchList);
        Dimension size = new Dimension(400, 400);
        scrollPane.setMaximumSize(size);
        scrollPane.setPreferredSize(size);
        scrollPane.setMinimumSize(size);
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createTitledBorder("Match List"));
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.add(scrollPane);
        return pane;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        JFrame frame = null;
        try {
            //Create and set up the window.
            frame = new JFrame("Tetris Server");
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
        }
        //Create and set up the content pane.
        instance = new SIM();
        instance.setOpaque(true); //content panes must be opaque
        if (frame == null) {
            return;
        }
        frame.setContentPane(instance);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Function main used as a test.
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Default setter of user list. It realises the mapping of the players and
     * the updating of the list.
     *
     * @param playerMap identification of the player.
     */
    public void setUserList(HashMap<Integer, Player> playerMap) {
        DefaultListModel model = (DefaultListModel) userList.getModel();
        for (Entry<Integer, Player> e : playerMap.entrySet()) {
            if (!model.contains(e.getValue())) {
                model.addElement(e.getValue());
            }
        }
    }

    /**
     * Default setter of match list. It realises the mapping of the matchs and
     * the updating of the list.
     *
     * @param matchMap identification of the match.
     */
    public void setMatchList(HashMap<Integer, Match> matchMap) {
        DefaultListModel model = (DefaultListModel) matchList.getModel();
        for (Entry<Integer, Match> e : matchMap.entrySet()) {
            if (!model.contains(e.getValue())) {
                model.addElement(e.getValue());
            }
        }
    }

    /**
     * It realises the addition of a match in the list.
     *
     * @param m identifies the match.
     * @return the panel updated.
     */
    public MatchInstrospection addMatch(Match m) {
        DefaultListModel model = (DefaultListModel) matchList.getModel();
        if (!model.contains(m)) {
            model.addElement(m);
        }
        return new MatchInstrospection(tabbedPane, m);
    }

    /**
     * It implements a getter of the content of a pane.
     *
     * @return the content.
     */
    static public SIM getInstance() {
        if (instance == null) {
            createAndShowGUI();
        }


        return instance;
    }

    /**
     * It removes a player of the available's player list.
     *
     * @param player informes the player.
     */
    public void removePlayer(Player player) {
        DefaultListModel model = (DefaultListModel) userList.getModel();
        if (model.contains(player)) {
            model.removeElement(player);
        }
    }

    /**
     * Removes a match of the available's match list.
     *
     * @param match informes the match to be removed.
     */
    public void removeMatch(Match m) {
        DefaultListModel model = (DefaultListModel) matchList.getModel();
        if (model.contains(m)) {
            model.removeElement(m);
        }
    }
}
