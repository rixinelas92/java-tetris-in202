/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import junit.framework.TestCase;

/**
 *
 * @author felipeteles
 */
public class ControllerTest extends TestCase {
    
    public ControllerTest(String testName) {
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
     * Test of rotate method, of class Controller.
     */
    public void testRotate() {
        System.out.println("rotate");
        Controller instance = new ControllerImpl();
        instance.rotate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of goToBottom method, of class Controller.
     */
    public void testGoToBottom() {
        System.out.println("goToBottom");
        Controller instance = new ControllerImpl();
        instance.goToBottom();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of goLeft method, of class Controller.
     */
    public void testGoLeft() {
        System.out.println("goLeft");
        Controller instance = new ControllerImpl();
        instance.goLeft();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of goLight method, of class Controller.
     */
    public void testGoLight() {
        System.out.println("goLight");
        Controller instance = new ControllerImpl();
        instance.goRight();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class Controller.
     */
    public void testStop() {
        System.out.println("stop");
        Controller instance = new ControllerImpl();
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ControllerImpl implements Controller {

        public void rotate() {
        }

        public void goToBottom() {
        }

        public void goLeft() {
        }

        public void goRight() {
        }

        public void stop() {
        }
    }

}
