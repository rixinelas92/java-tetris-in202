/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import tetris.Piece.ShapeType;
import tetris.Screen.OutOfScreenBoundsException;

/**
 *
 * @author felipeteles
 */
public class PieceTest extends TestCase {
   
    Screen.BorderRetriever borderRetriever;


    public PieceTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Screen sc = new Screen();
        borderRetriever = sc.new BorderRetriever();
        Position.setBorderRetriever(borderRetriever);

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

        Position oldPos = new Position(1,2);
        Piece instance = new Piece(ShapeType.Z, (short) 1, oldPos);
        Position newPos = new Position(2,3);
        instance.setPosition(newPos);
        assertEquals(instance.getPosition(), new Position(2,3));

    }

    /**
     * Test of getColor method, of class Piece.
     */
    public void testGetColor() {
        System.out.println("getColor");

        Vector<Color> colors = new Vector<Color>();
        for(Piece.ShapeType s: Piece.ShapeType.values()){
            Position oldPos;
            try {
                oldPos = new Position(1, 2);
                Piece instance = new Piece(ShapeType.Z, (short) 1, oldPos);
                colors.add(instance.getColor());
            } catch (OutOfScreenBoundsException ex) {
                Logger.getLogger(PieceTest.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

        for(int i = 0; i< colors.size(); i++)
            for(int j = i+1; j< colors.size(); j++){
                assertNotSame(colors.get(i).getRGB(), colors.get(j).getRGB());
            }
    }

    /**
     * Test of getPosition method, of class Piece.
     */
    public void testGetPosition() {
        try {
            System.out.println("getPosition");
            Position oldPos = new Position(1, 2);
            Piece instance = new Piece(ShapeType.Z, (short) 1, oldPos);
            Position newPos = new Position(2, 3);
            instance.setPosition(newPos);
            assertEquals(instance.getPosition(), new Position(2, 3));
        } catch (OutOfScreenBoundsException ex) {
            fail(ex.getStackTrace().toString());
        }
    }

    /**
     * Test of getAllPosition method, of class Piece.
     */
    public void testGetAllPosition() {
        try {
            System.out.println("getPosition");
            Position oldPos = new Position(1, 2);
            Piece instance = new Piece(ShapeType.Z, (short) 1, oldPos);
            Position[] ps = instance.getAllPosition();
            Piece.printTyle(ShapeType.Z, 1);
            for(Position p: ps){
                System.out.println(p);
            }
        } catch (OutOfScreenBoundsException ex) {
            fail(ex.getStackTrace().toString());
        }
    }

    /**
     * Test of rotation method, of class Piece.
     */
    public void testRotation() {

        try {
            System.out.println("rotation");
            Position oldPos = new Position(1, 2);
            Piece instance = new Piece(ShapeType.Z, (short) 1, oldPos);
            instance.rotation();
            Position[] ps = instance.getAllPosition();
            Piece.printTyle(ShapeType.Z, 1);
            for(Position p: ps){
                System.out.println(p);
            }
        } catch (OutOfScreenBoundsException ex) {
            fail(ex.getStackTrace().toString());
        }
        
    }

    /**
     * Test of rotationInversed method, of class Piece.
     */
    public void testRotationInversed() {

        try {
            System.out.println("rotationInversed");
            Position oldPos = new Position(1, 2);
            Piece instance = new Piece(ShapeType.Z, (short) 1, oldPos);
            instance.rotation();
            Position[] ps = instance.getAllPosition();
            Piece.printTyle(ShapeType.Z, 1);
            for(Position p: ps){
                System.out.println(p);
            }
        } catch (OutOfScreenBoundsException ex) {
            fail(ex.getStackTrace().toString());
        }
    }

    /**
     * Test of main method, of class Piece.
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Piece.main(args);
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
