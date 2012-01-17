/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */

public class TetrisPreferences {
    private Properties p;

    enum ImplementedProperties{
        INT_KEYPAUSE, INT_KEYGOLEFT, INT_KEYGORIGHT, INT_KEYGODOWN, INT_KEYROTATE, INT_KEYDOWN, INT_KEYHOLD,
        STR_USERNAME, STR_IP

    }

    


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

    public void saveProperties(){
        FileOutputStream propFile = null;
        PrintStream ps = null;
        try {
            propFile = new FileOutputStream("properties.tet");
            ps = new PrintStream(propFile);
            p.list(ps);
        } catch (IOException ex) {
            Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try{
                ps.close();
            }catch (Exception e){}
            
            try {
                propFile.close();
            } catch (IOException ex) {
                Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    void setProperty(ImplementedProperties key, Object o){
        if(key.toString().startsWith("INT")){
            if(Integer.class.isInstance(o)){
                p.put(key, o);
            }
        } else if(key.toString().startsWith("STR")){
            if(String.class.isInstance(o)){
                p.put(key, o);
            }
        }
    }
    
    public Integer getIntProperty(ImplementedProperties key){
        Object o = p.getProperty(key.toString());
        if(Integer.class.isInstance(o)){
            return (Integer)o;
        }
        return null;
    }

    public String getStrProperty(ImplementedProperties key){
        Object o = p.getProperty(key.toString());
        if(String.class.isInstance(o)){
            return (String)o;
        }
        return null;
    }



}