/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import Tetris_interface.Layout1;
import javax.swing.SwingUtilities;
import sound.SoundEffect;

/**
 *
 * @author felipeteles
 */
public class Main {

    static SoundEffect theme;

    static Layout1 screen;

    static Game game;

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
            }
        });
        startThemeMusic();
    }

    private static void startThemeMusic() {
        Runnable r = new Runnable() {

            public void run() {
                try {
                    SoundEffect.EXPLODE.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    
    
    public static void updatePiecesPositions(){
        screen.setPiecePosition(game.getCurrentPiecePositions());
    }
    public static void setNewPiece(){
        screen.newPiece(game.getCurrentPiecePositions(), game.getCurrentPieceColorName());
    }
}
