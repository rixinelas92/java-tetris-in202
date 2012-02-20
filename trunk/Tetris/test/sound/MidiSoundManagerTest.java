/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import sound.SoundManager.soundEffects;
import sound.SoundManager.soundTheme;

/**
 *
 * @author gustavo
 */
public class MidiSoundManagerTest extends TestCase {
    
    SoundManager sm;
    
    public MidiSoundManagerTest(String testName) {
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
     * Test of setVolume method, of class MidiSoundManager.
     */
    public void testSetVolume() {
        System.out.println("setVolume");
        sm = new MidiSoundManager(soundEffects.THEME, soundTheme.STARWARS,50);
        sm.setLoop();
        sm.play();
        
        System.out.println("\tAdjusted to:"+50);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.setVolume(100);
        System.out.println("\tAdjusted to:"+100);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.setVolume(10);
        System.out.println("\tAdjusted to:"+10);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.stopSound();
    }

    /**
     * Test of play method, of class MidiSoundManager.
     */
    public void testPlay() {
        System.out.println("play");
        sm = new MidiSoundManager(soundEffects.THEME, soundTheme.STARWARS,50);
        sm.setLoop();
        sm.play();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.stopSound();
    }

    /**
     * Test of setLoop method, of class MidiSoundManager.
     */
    public void testSetLoop() {
        System.out.println("setLoop");
        sm = new MidiSoundManager(soundEffects.THEME, soundTheme.MARIO,50);
        sm.setLoop();
        sm.play();
        
        System.out.println("\tAdjusted to:"+50);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.stopSound();
    }

    /**
     * Test of stopSound method, of class MidiSoundManager.
     */
    public void testStopSound() {
        System.out.println("stopSound");
        sm = new MidiSoundManager(soundEffects.THEME, soundTheme.MARIO,50);
        sm.setLoop();
        sm.play();
        
        System.out.println("\tplay1");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.stopSound();
    
        sm.play();
        
        System.out.println("\tplay2");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiSoundManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.stopSound();
    }
}
