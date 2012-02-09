/*
Java doc em construcao.
 */
package tetris;

import Tetris_interface.Interface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import online.client.Client;
import online.util.Player;
import online.util.Player.PlayerState;
import online.util.PlayerDescriptor;
import sound.SoundEffect;
import tetris.util.TetrisPreferences;
import tetris.util.TetrisPreferences.ImplementedProperties;

/**
 *
 * @author felipeteles
 */
public class Main {

    static Interface screen;
    static Game game;
    static Client internet;
    private static SoundEffect themeSom = null;
    static TetrisPreferences prop;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        prop = new TetrisPreferences();
        prop.readProperties();

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                screen = new Interface();
                screen.setVisible(true);
                game = new Game();

                try {

                    Integer[] keys = {prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYGOLEFT),
                        prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYDOWN),
                        prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYGORIGHT),
                        prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYGODOWN),
                        prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYROTATE),
                        prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYHOLD),
                        prop.getIntProperty(TetrisPreferences.ImplementedProperties.INT_KEYPAUSE)};
                    boolean isok = true;
                    for (Integer i : keys) {
                        if (i == null) {
                            isok = false;
                        }
                    }
                    if (isok) {
                        game.setControllers(keys);
                    }
                    screen.setUserName(prop.getStrProperty(TetrisPreferences.ImplementedProperties.STR_USERNAME));
                    screen.setIP(prop.getStrProperty(TetrisPreferences.ImplementedProperties.STR_IP));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Interface.addGameViewReady(game.new GameViewReadyListener());
                Interface.addConfigChanger(new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        ConfigChanger();
                    }
                });
                Interface.addSomChanger(new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        SomChanger();
                    }
                });
                SomChanger();
           }
        });
    }

    public static void updatePiecesPositions() {
        screen.setPiecePosition(game.getCurrentPiecePositions());
        screen.setShadowPosition(game.getShadowPiecePositions());
    }

    public static void updateShadowPositions() {

        screen.setShadowPosition(game.getShadowPiecePositions());
    }

    public static void setNewPiece() {
        screen.newPiece(game.getCurrentPiecePositions(), game.getNextPiecePositions(), game.getNextPieceColorName(), game.getCurrentPieceColorName());
    }

    public static void setHold() {
        screen.holdPiece(game.getCurrentPiecePositions(), game.getHoldPiecePositions(), game.getCurrentPieceColorName());
    }

    public static void setFirstHold() {
        screen.holdFirstPiece(game.getHoldPiecePositions());
    }

    public static void setNewFirstPiece() {
        screen.newFirstPiece(game.getNextPiecePositions(), game.getNextPieceColorName());
    }

    public static void setListeners(Controller c) {
        screen.setFocusable(true);
        /*
         * Requesting focus so that the menu dont get the actual focus from the 
         * keyboard annoying the user with useless actions.
         */
        screen.requestFocusInWindow();
        Main.removeListeners();
        screen.addKeyListener(c);
        screen.addMouseMotionListener(c);
        screen.addMouseListener(c);
    }

    public static void removeListeners() {
        screen.removeKeyListener(game);
        screen.removeMouseMotionListener(game);
        screen.removeMouseListener(game);
    }

    static void terminateControllerAction() {
        updatePiecesPositions();
        screen.toggleVisiblePropOnGame();
    }

    public static void terminateInternetConnection() {
        try {
            internet.sayBye();
        } catch (Exception e) {
        }
    }

    static void callScreenRemoveLine(int lineC) {
        screen.eraseLine(lineC);
    }

    public static void togglePause() {
        game.stopToggleVariable();
        screen.requestFocusInWindow();
        screen.clock.togglePause();
    }

    public static void pauseGame() {
        game.pauseGame();
        if (screen != null && screen.clock != null) {
            screen.clock.pauseScreen();
        }
    }

    static void setPointsAndLevel(int points, int level, int pointsToNextLevel) {
        screen.setScore(points, level, 0, pointsToNextLevel);
    }

    public static void restart1pScreen() {
        screen.restart1pScreen();
    }

    public static void start2pConnection() {
        if (internet != null) {
            terminateInternetConnection();
        }
        try {
            String ip = prop.getStrProperty(TetrisPreferences.ImplementedProperties.STR_IP);
            String name = prop.getStrProperty(TetrisPreferences.ImplementedProperties.STR_USERNAME);
            if (ip == null || ip.trim().length() == 0) {
                ip = "localhost";
            }
            if (name == null) {
                name = "Default";
            }
            internet = new ClientImpl(ip, name);
            internet.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void requestMatchWith(PlayerDescriptor pd) {
        try {
            internet.requestMatchWith(((Integer) pd.getId()).toString());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void showGameOverAndReturnToNewGame() {
        Main.removeListeners();
        JLabel go = screen.showGameOver();
        sendGameOver();

    }

    public static void showGameWinAndReturnToNewGame() {
        Main.removeListeners();
        JLabel go = screen.showGameWin();
    }

    public static void saveProp() {
        try {
            prop.saveProperties();
        } catch (Exception e) {
        }
    }


    public static void sendGameOver(){
        try {
            internet.gameOver();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void sendGamePoint() {
        try {
            internet.gamePoint();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static class ClientImpl extends Client {

        HashSet<PlayerDescriptor> playerSet;

        public ClientImpl(String serverAddress, String playername) throws UnknownHostException, IOException {
            super(serverAddress, playername);
            playerSet = new HashSet<PlayerDescriptor>();
        }

        @Override
        public void receivePlayerList(String list) {
            list = list.trim();
            String[] players = list.split("#");

            playerSet.clear();
            for (String s : players) {
                String[] data = s.split(">");
                if (data.length < 3) {
                    continue;
                }
                int key = Integer.valueOf(data[0]);
                String name = data[1];
                PlayerState state = PlayerState.valueOf(data[2]);
                PlayerDescriptor pd = new PlayerDescriptor(name, state, key);
                playerSet.add(pd);
            }
            while (screen == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            screen.setPlayerList(playerSet);
        }

        @Override
        public void receiveMatchRequest(String uid) throws IOException {
            String name  = new String(uid);
            for(PlayerDescriptor pd: playerSet){
                if((pd.getId()+"").endsWith(uid)){
                    name = pd.getName();
                    break;
                }
            }
            int response = JOptionPane.showConfirmDialog(screen, "Game Request From User " + name + "\n Do you Accept?", "dd", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (response == JOptionPane.OK_OPTION) {
                acceptMatchWith(uid);
            }

        }

        @Override
        public void matchStart(String mid) {
            screen.configureScreenForGameType(true);
            screen.func_showPanel(4);
        }

        @Override
        public void receiveGamePunn(String mid) {
            game.punnition();
        }

        @Override
        public void endGame(String mid) throws IOException {
            game.pauseGame();
            Main.showGameWinAndReturnToNewGame();
        }

        @Override
        public void receivedError() {
            System.out.println("Error from server");
        }

        @Override
        public void receiveBoard(String str) {
            str = str.trim();
            String[] ints = str.split("#");
            int[] v = new int[Screen.SIZE_X];
            int i = 0;
            for (String s : ints) {
                s = s.trim();
                if (s.length() == 0) {
                    continue;
                }
                int key = Integer.valueOf(s);
                v[i++] = key;
            }
            screen.set2pScreenGame(v);
        }

        @Override
        protected int[] returnBoard() {
            if (game == null) {
                return new int[0];
            }
            return game.getGameMask();
        }
    }

    public static void ConfigChanger() {

        System.out.println("Changing configurations");
        Integer[] cc = screen.getConfigChange();
        game.setControllers(cc);
        game.setMouseController(screen.getMouseControler());
        prop.setProperty(TetrisPreferences.ImplementedProperties.STR_IP, screen.getIPChange());
        prop.setProperty(ImplementedProperties.INT_KEYGOLEFT, cc[0]);
        prop.setProperty(ImplementedProperties.INT_KEYDOWN, cc[1]);
        prop.setProperty(ImplementedProperties.INT_KEYGORIGHT, cc[2]);
        prop.setProperty(ImplementedProperties.INT_KEYGODOWN, cc[3]);
        prop.setProperty(ImplementedProperties.INT_KEYROTATE, cc[4]);
        prop.setProperty(ImplementedProperties.INT_KEYHOLD, cc[5]);
        prop.setProperty(ImplementedProperties.INT_KEYPAUSE, cc[6]);
        String username = Player.validName(screen.getUserName());
        prop.setProperty(TetrisPreferences.ImplementedProperties.STR_USERNAME, username);
        System.out.println("Restarting ServerConnection");
        start2pConnection();
    }

    public static void SomChanger() {
        SoundEffect.setGlobalVolume(screen.getSomVolume());
        int aux = screen.getSomTheme();
        
        game.setSomTheme(aux);        
        if (themeSom != null) {
            themeSom.setStop();
        }
        if (aux == 0) {
            themeSom = null;
        } else if (aux == 1) {
            themeSom = null;
        } else if (aux == 2) {
            themeSom = null;
        } else if (aux == 3) {
            themeSom = SoundEffect.STHEME;
        }
        if (aux == 4) {
            themeSom = null;
        }
        if (themeSom != null) {
            themeSom.setLoop();
        }
        
    }
}
