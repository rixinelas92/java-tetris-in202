/*
Java doc em construcao.
 */
package tetris;

import Tetris_interface.Layout1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import online.client.Client;
import online.util.Player.PlayerState;
import online.util.PlayerDescriptor;
import sound.SoundEffect;

/**
 *
 * @author felipeteles
 */
public class Main {

    static SoundEffect theme;
    static Layout1 screen;
    static Game game;
    static Client internet;
    static String playerName = "TetrisPlayer";
    private static SoundEffect themeSom=SoundEffect.CTHEME;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                screen = new Layout1();
                screen.setVisible(true);
                game = new Game();
                Layout1.addGameViewReady(game.new GameViewReadyListener());
                Layout1.addConfigChanger(new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        ConfigChanger();
                    }
                });
                Layout1.addSomChanger(new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        SomChanger();
                    }
                });
                game.setSomTheme(0);
                try {
                    themeSom.setLoop();
                } catch (Exception e) {
                }
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

        try {
            internet = new ClientImpl("147.250.8.16", playerName);
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
        //      try{
        //          Thread.sleep(5000);
        //      }catch(Exception e){}
        //      screen.removeGameOver(go);
        //      screen.func_newgame();
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

            int response = JOptionPane.showConfirmDialog(screen, "Game Request From User " + uid + "\n Do you Accept?", "dd", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (response == JOptionPane.OK_OPTION) {
                acceptMatchWith(uid);
            }

        }

        @Override
        public void matchStart(String mid) {
            screen.configureScreenForGameType(true);
            screen.func_1player();
        }

        @Override
        public void receiveGamePunn(String mid) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void endGame(String mid) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void receivedError() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void receiveBoard(String str) {
            str = str.trim();
            String[] ints = str.split("#");
            Vector<Integer> values = new Vector<Integer>();
            for (String s : ints) {
                s = s.trim();
                if (s.length() == 0) {
                    continue;
                }
                int key = Integer.valueOf(s);
                values.add(key);
            }
            Integer[] v;
            v = (Integer[]) values.toArray(new Integer[values.size()]);
            boolean[][] desc = Game.getGameDescWithMask(v);
            screen.set2pScreenGame(desc);
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
        game.setControllers(screen.getConfigChange());
        game.setMouseController(screen.getMouseControler());
        ///mandar o IP
    }

    public static void SomChanger() {
        SoundEffect.setGlobalVolume(screen.getSomVolume());
        int aux = screen.getSomTheme();
        themeSom.setStop();
        if (aux == 0) {
            themeSom = SoundEffect.CTHEME;
        }
        if (aux == 1) {
            themeSom = SoundEffect.NOTHING;
        }
        if (aux == 2) {
            themeSom = SoundEffect.NOTHING;
        }
        if (aux == 3) {
            themeSom = SoundEffect.STHEME;
        }
        themeSom.setLoop();
        game.setSomTheme(aux);
    }
}
