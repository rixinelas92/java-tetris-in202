/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import Tetris_interface.Layout1;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import online.client.Client;
import online.util.Player.PlayerState;
import online.util.PlayerDescriptor;
import sound.SoundEffect;

/**
 *
 * @author felipeteles
 */
public class Main  {

    static SoundEffect theme;

    static Layout1 screen;

    static Game game;

    static Client internet;

    static String playerName = "TetrisPlayer";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                screen = new Layout1();
                screen.setVisible(true);
                game = new Game();
                Layout1.addGameViewReady(game.new GameViewReadyListener());
                try{
                    SoundEffect.THEME.setLoop();
                    SoundEffect.THEME.play();
                }catch(Exception e){
                }
            }
        });
    }
    
    
    public static void updatePiecesPositions(){
        screen.setPiecePosition(game.getCurrentPiecePositions());
    }
    public static void setNewPiece(){
        screen.newPiece(game.getCurrentPiecePositions(),game.getNextPiecePositions(), game.getNextPieceColorName());
    }
    public static void setNewFirstPiece(){
        screen.newFirstPiece(game.getNextPiecePositions(), game.getNextPieceColorName());
    }
    public static void setListeners(Controller c){
        screen.setFocusable(true);
        /*
         * Requesting focus so that the menu dont get the actual focus from the 
         * keyboard annoying the user with useless actions.
         */
        screen.requestFocusInWindow();
        screen.addKeyListener(c);
        screen.addMouseMotionListener(c);
        screen.addMouseListener(c);
    }

    static void terminateControllerAction() {
        updatePiecesPositions();
        screen.toggleVisiblePropOnGame();
    }

    public static void terminateInternetConnection(){
        try{
            internet.sayBye();
        }catch(Exception e){
        }
    }
    static void callScreenRemoveLine(int lineC) {
        screen.eraseLine(lineC);
    }
    public static void togglePause(){
        game.stopToggleVariable();
        screen.requestFocusInWindow();
        screen.clock.togglePause();
    }

    static void setPointsAndLevel(int points, int level, int pointsToNextLevel) {
        screen.setScore(points, level, 0, pointsToNextLevel);
    }

    public static void start2pConnection(){
        try {
            internet = new ClientImpl("localhost", playerName);
            internet.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    static class ClientImpl extends Client{

        HashSet<PlayerDescriptor> playerSet;
        
        public ClientImpl(String serverAddress, String playername) throws UnknownHostException, IOException {
            super(serverAddress,playername);
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
                PlayerDescriptor pd = new PlayerDescriptor(name, state.ONLINE, key);
                playerSet.add(pd);
            }
            screen.setPlayerList(playerSet);
        }

        @Override
        public void receiveMatchRequest(String uid) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void matchStart(String mid) {
            throw new UnsupportedOperationException("Not supported yet.");
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

    }


}
