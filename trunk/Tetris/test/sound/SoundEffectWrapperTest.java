/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author gustavo
 */
public class SoundEffectWrapperTest extends TestCase {
    
    public SoundEffectWrapperTest(String testName) {
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
     * Test of values method, of class SoundEffectWrapper.
     */
    public void testValues() {
        // loading values
        SoundEffectWrapper.values();
        for(SoundEffectWrapper sef: SoundEffectWrapper.values()){
            sef.play();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
            sef.setStop();
        }
            
    }

}
