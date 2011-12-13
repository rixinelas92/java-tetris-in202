/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import junit.framework.TestCase;
import tetris.Piece.ShapeType;

/**
 *
 * @author felipeteles
 */
public class PieceTest extends TestCase {
    
    public PieceTest(String testName) {
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
     * Test of setPosition method, of class Piece.
     */
    public void testSetPosition() throws Exception {
        System.out.println("setPosition");

        Position newPos = new Position(1,2);
        newPos.setPosition((short)2, (short)3);

        Piece instance = new Piece(ShapeType.Z, (short) 1, newPos);

        if(newPos.getX() != 2)
            fail("setter or getter not working");
        if(newPos.getY() != 3)
            fail("setter or getter not working");
    }

    /**
     * Test of getColor method, of class Piece.
     */
    public void testGetColor() {
        System.out.println("getColor");
        Piece instance = null;
        Color expResult = null;
        Color result = instance.getColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosition method, of class Piece.
     */
    public void testGetPosition() {
        System.out.println("getPosition");
        Piece instance = null;
        Position expResult = null;
        Position result = instance.getPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPosition method, of class Piece.
     */
    public void testGetAllPosition() {
        System.out.println("getAllPosition");
        Piece instance = null;
        Position[] expResult = null;
        Position[] result = instance.getAllPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotation method, of class Piece.
     */
    public void testRotation() {
        System.out.println("rotation");
        Piece instance = null;
        instance.rotation();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotationInversed method, of class Piece.
     */
    public void testRotationInversed() {
        System.out.println("rotationInversed");
        Piece instance = null;
        instance.rotationInversed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Piece.
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Piece.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printTyle method, of class Piece.
     */
    public void testPrintTyle() {
        System.out.println("printTyle");
        ShapeType s = null;
        int rot = 0;
        Piece.printTyle(s, rot);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
