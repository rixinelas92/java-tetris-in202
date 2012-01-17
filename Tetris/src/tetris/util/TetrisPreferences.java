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

public class TetrisPreferences {
    private Properties p;

    public enum ImplementedProperties{
        INT_KEYPAUSE, INT_KEYGOLEFT, INT_KEYGORIGHT, INT_KEYGODOWN, INT_KEYROTATE, INT_KEYDOWN, INT_KEYHOLD,
        STR_USERNAME, STR_IP
    }

    


    public void readProperties(){
        FileInputStream propFile = null;
        try {
            p = new Properties();
            propFile = new FileInputStream("properties.tet");
            p.load(propFile);

        } catch (IOException ex) {
            Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                propFile.close();
            } catch (Exception ex) {
                Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveProperties(){
        FileOutputStream propFile = null;
        PrintStream ps = null;
        System.out.println("Saving properties");
        try {
            propFile = new FileOutputStream("properties.xml");
           // ps = new PrintStream(propFile);

            for(Entry e: p.entrySet()){
                System.out.println(e.getKey()+" "+e.getValue());
            }

            p.storeToXML(propFile,"xxx","UTF-8");

         //   ps.flush();


        } catch (IOException ex) {
            Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try{

         //       ps.close();
            }catch (Exception e){}
            
            try {
                propFile.close();
            } catch (IOException ex) {
                Logger.getLogger(TetrisPreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void setProperty(ImplementedProperties key, Object o){
        if(key.toString().startsWith("INT")){
            if(Integer.class.isInstance(o)){
                System.out.println(key+" "+o);
                p.setProperty("tetris."+key.toString().toLowerCase(), o.toString());
            }
        } else if(key.toString().startsWith("STR")){
            if(String.class.isInstance(o)){
                p.setProperty("tetris."+key.toString().toLowerCase(), o.toString());
            }
        }
    }
    
    public Integer getIntProperty(ImplementedProperties key){
        Object o = p.getProperty("tetris."+key.toString().toLowerCase());
        if(Integer.class.isInstance(o)){
            return (Integer)o;
        }
        return null;
    }

    public String getStrProperty(ImplementedProperties key){
        Object o = p.getProperty("tetris."+key.toString().toLowerCase());
        if(String.class.isInstance(o)){
            return (String)o;
        }
        return null;
    }



}