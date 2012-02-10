/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author gustavo
 */
public abstract class SoundManager {
    private static final Map<soundEffects, SoundManager> instances = new EnumMap<soundEffects, SoundManager>(soundEffects.class);

    static public enum soundEffects{
        ERASE, FALL, GAMEOVER, PAUSE, THEME;
    }
    static public enum soundTheme{
        CLASSIC, MARIO, PACMAN, STARWARS;
    }
    
    protected SoundManager(){

    }
    
    public SoundManager(soundEffects se, soundTheme st){
        synchronized(SoundManager.class){
            if(!instances.containsKey(se))
                instances.put(se, this);
        }
        setUp(st, se);
        
    }
    public static SoundManager getInstance(Object key) {
        return instances.get(key);
    }
        
     /**
     * Adjusts the level of volume of the game.
     * @param newVolume defines the updated volume, should be between 0 and 100 (inclusively).
     */
    abstract public void setVolume(int newVolume);
    
    /**
     * Adjuts the level of volume of the effets of game.
     * @param newVolume defines the updated volume.
     */    
    static public void setGlobalVolume(int newVolume) {
        for (SoundManager sm : instances.values()) {
            sm.setVolume(newVolume);
        }
    }
    
    /*
     * Plays in a continous way a clip by rewinding a theme.
     */
    abstract public void play();
    /**
     * Maintains the execution of a clip.
     */
    abstract public void setLoop();
    /**
     * Determines the end in a execution of a clip.
     */
    abstract public void stopSound();
    
    abstract protected void setUp(soundTheme theme,soundEffects effect);
}
