/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.util;

import junit.framework.TestCase;
import tetris.util.TetrisPreferences.ImplementedProperties;

/**
 *
 * @author gustavo
 */
public class TetrisPreferencesTest extends TestCase {
    
    public TetrisPreferencesTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of saveProperties method, of class TetrisPreferences.
     */
    public void testSingleProperties() {
        System.out.println("saveProperties");
        TetrisPreferences instance = new TetrisPreferences();
        // reading properties.
        instance.readProperties();
        instance.cleanProperties();
        
        for(ImplementedProperties prop: ImplementedProperties.values()){
            Object value;
            if(prop.toString().toUpperCase().startsWith("STR")){
                value = prop.toString().trim()+"test";
            }else{
                value = prop.ordinal();
            }
            instance.setProperty(prop, value);
            instance.saveProperties();
            instance.cleanProperties();
            instance.readProperties();
            
            if(prop.toString().toUpperCase().startsWith("STR")){

                assertTrue("Propertie not well saved to file",instance.getStrProperty(prop).equals(value));
            }else{

                assertTrue("Propertie not well saved to file",instance.getIntProperty(prop).equals(prop.ordinal()));
            }

        }
    }
    
    public void testAllProperties() {
        System.out.println("saveProperties");
        TetrisPreferences instance = new TetrisPreferences();
        // reading properties.
        instance.readProperties();
        instance.cleanProperties();

        for (ImplementedProperties prop : ImplementedProperties.values()) {
            Object value;
            if (prop.toString().startsWith("STR")) {
                value = prop.toString().trim() + "test";
            } else {
                value = prop.ordinal();
            }
            instance.setProperty(prop, value);
        }
        instance.saveProperties();
        instance.cleanProperties();
        instance.readProperties();
        for (ImplementedProperties prop : ImplementedProperties.values()) {
            String value = "";
            if (prop.toString().startsWith("STR")) {
                value = prop.toString().trim() + "test";
            }
            if (prop.toString().startsWith("STR")) {
                assertTrue("Propertie not well saved to file", instance.getStrProperty(prop).equals(value));
            } else {
                assertTrue("Propertie not well saved to file", instance.getIntProperty(prop) == prop.ordinal());
            }

        }
    }


}
