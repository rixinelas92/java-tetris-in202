/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import tetris.Screen.BorderRetriever;
import tetris.Screen.OutOfScreenBoundsException;

/**
 *
 * @author felipeteles
 */
public class PositionTest extends TestCase {
    
    public PositionTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Position.setBorderRetriever((new Screen()).new BorderRetriever());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of setX method, of class Position.
     */
    public void testSetX() throws Exception {
        System.out.println("setX");
        short newX = 0;
        Position instance = new Position(2, 2);
        instance.setX(newX);
        assertEquals(instance.getX(),newX);
    }

    /**
     * Test of setY method, of class Position.
     */
    public void testSetY() throws Exception {
        System.out.println("setY");
        short newY = 0;
        Position instance = new Position(2, 2);
        instance.setY(newY);
        assertEquals(instance.getY(),newY);
    }

    /**
     * Test of setPosition method, of class Position.
     */
    public void testSetPosition() {
        System.out.println("setPosition");
        short newX = 0;
        short newY = 6;
        Position instance;
        try {
            instance = new Position(1, 2);
            instance.setPosition(newX, newY);
            assertEquals(instance.getY(),newY);
            assertEquals(instance.getX(),newX);
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(PositionTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Exception");
        }
    }

    /**
     * Test of getX method, of class Position.
     */
    public void testGetX() {
        try {
            System.out.println("getX");
            short newX = 0;
            Position instance = new Position(2, 2);
            instance.setX(newX);
            assertEquals(instance.getX(), newX);
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(PositionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getY method, of class Position.
     */
    public void testGetY() {
        try {
            System.out.println("getY");
            short newY = 0;
            Position instance = new Position(2, 2);
            instance.setY(newY);
            assertEquals(instance.getY(), newY);
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(PositionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of setBorderRetriever method, of class Position.
     */
    public void testSetBorderRetriever() {
        try {
            System.out.println("setBorderRetriever");
            Screen screen = new Screen();
            BorderRetriever borderRetriever = screen.new BorderRetriever();
            Position.setBorderRetriever(borderRetriever);
        } catch (Exception ex) {
            fail(ex.getStackTrace().toString());
            Logger.getLogger(PositionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of equals method, of class Position.
     */
    public void testEquals() {
        try {
            System.out.println("getX");
            
            Position instance = new Position(2, 2);
            Position instance2 = new Position(2, 2);
            
            assertEquals(instance.equals(instance2), true);
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(PositionTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getStackTrace().toString());

        }
    }

    /**
     * Test of toString method, of class Position.
     */
    public void testToString() {
        // PASS
    }

}
