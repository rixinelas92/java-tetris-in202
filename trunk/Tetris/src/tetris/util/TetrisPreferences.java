/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
/**
 * This class was designed in order to manage the preferences of the game. 
 * @author tacilo
 */
public class TetrisPreferences {
    private Properties p;

    private String filename = "properties.xml";
    /**
     * Define the standard to the parameters.
     */
    public enum ImplementedProperties{
        INT_KEYPAUSE, INT_KEYGOLEFT, INT_KEYGORIGHT, INT_KEYGODOWN, INT_KEYROTATE, INT_KEYDOWN, INT_KEYHOLD,
        STR_USERNAME, STR_IP, INT_SOUNDLEVEL
    }

    /**
     * Default setter of the identification of the file.
     * @param fn informes the file name.
     */
    public void setFilename(String fn){
        this.filename = fn;
    }
    /**
     * Default reader of the properties of the game.
     */
    public void readProperties(){
        FileInputStream propFile = null;
        try {
            p = new Properties();
            propFile = new FileInputStream(filename);
            p.loadFromXML(propFile);

        } catch (IOException ex) {
          //  Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                propFile.close();
            } catch (Exception ex) {
                Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Default saver of the properties changed.
     */
    public void saveProperties(){
        FileOutputStream propFile = null;
        PrintStream ps = null;
        try {
            propFile = new FileOutputStream(filename);

            p.storeToXML(propFile,"xxx","UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try{
            }catch (Exception e){}
            try {
                propFile.close();
            } catch (IOException ex) {
                Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Default setter of the properties.
     * @param key to define a property.
     */
    public void setProperty(ImplementedProperties key, Object o){
        if(key.toString().startsWith("INT")){
            if(Integer.class.isInstance(o)){
                p.setProperty("tetris."+key.toString().toLowerCase(), o.toString());
            }
        } else if(key.toString().startsWith("STR")){
            if(String.class.isInstance(o)){
                p.setProperty("tetris."+key.toString().toLowerCase(), o.toString());
            }
        }
    }
    /**
     * Default getter (in int) of the properties.
     */
    public Integer getIntProperty(ImplementedProperties key){
        if(key == null)
            return null;
        Object o = p.getProperty("tetris."+key.toString().toLowerCase());
        if(Integer.class.isInstance(o)){
            return (Integer)o;
        }else if(String.class.isInstance(o)){
            try{
                return Integer.parseInt(o.toString());
            }catch(Exception e){
            }
        }
        return null;
    }
    /**
     * Default getter (in string) of the properties.
     */
    public String getStrProperty(ImplementedProperties key){
        if(key == null)
            return "";
        Object o = p.getProperty("tetris."+key.toString().toLowerCase());
        if(String.class.isInstance(o)){
            return (String)o;
        }
        return null;
    }
    /**
     * Removes properties.
     */
    public void cleanProperties(){
        for(ImplementedProperties prop : ImplementedProperties.values()){
            p.remove("tetris."+prop.toString().toLowerCase());
        }
    }

}