/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import junit.framework.TestCase;

/**
 *
 * @author felipeteles
 */
public class BoxTest extends TestCase {
    
    public BoxTest(String testName) {
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
     * Test of getColor method, of class Box.
     */
    public void testGetColor() {
        System.out.println("getColor");
        Box instance = new Box();

        instance.setColor(Color.yellow);

        Color expResult = Color.yellow;
        Color result = instance.getColor();
        assertEquals(expResult, result);

        instance.setColor(Color.red);
        expResult = Color.red;
        result = instance.getColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setColor method, of class Box.
     */
    public void testSetColor() {
        Box instance = new Box();

        instance.setColor(Color.yellow);

        Color expResult = Color.yellow;
        Color result = instance.getColor();
        assertEquals(expResult, result);

        instance.setColor(Color.red);
        expResult = Color.red;
        result = instance.getColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of isFull method, of class Box.
     */
    public void testIsFull() {
        System.out.println("isFull");
        Box instance = new Box();
        boolean expResult = false;
        instance.setFull(true);
        boolean result = instance.isFull();
        assertNotSame(expResult, result);
        instance.setFull(false);
        result = instance.isFull();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFull method, of class Box.
     */
    public void testSetFull() {
        System.out.println("setFull");
        Box instance = new Box();
        boolean expResult = false;
        instance.setFull(true);
        boolean result = instance.isFull();
        assertNotSame(expResult, result);
        instance.setFull(false);
        result = instance.isFull();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEmptyColor method, of class Box.
     */
    public void testGetEmptyColor() {
        System.out.println("getEmptyColor");
        Color expResult = Color.white;
        Color result = Box.getEmptyColor();
        assertEquals(expResult, result);
    }

}
