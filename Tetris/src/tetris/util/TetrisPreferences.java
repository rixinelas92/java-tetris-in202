/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */

public class TetrisPreferences {
    Properties p;

    public void readProperties(){
        FileInputStream propFile = null;
        try {
            propFile = new FileInputStream("properties.tet");
            p = new Properties(System.getProperties());
            p.load(propFile);
        } catch (IOException ex) {
            Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                propFile.close();
            } catch (IOException ex) {
                Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}