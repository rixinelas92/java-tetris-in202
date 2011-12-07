/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import Tetris_interface.Layout1;
import javax.swing.SwingUtilities;
import sound.MusicI;
import sound.ThemeMusic;

/**
 *
 * @author felipeteles
 */
public class Main {
    static MusicI theme;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Layout1 ex = new Layout1();
                ex.setVisible(true);
            }
        });
        startThemeMusic();
        


    }

    private static void startThemeMusic() {
        Runnable r = new Runnable() {

            public void run() {
                theme = new ThemeMusic();
                theme.prepare();
                try {
                    theme.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }



}
