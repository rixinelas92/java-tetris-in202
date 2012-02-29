/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gustavo
 */
public abstract class SoundManager {
    private static final Map<soundEffects, SoundManager> instances = new EnumMap<soundEffects, SoundManager>(soundEffects.class);
    
    public static final Map<Integer,Integer> MAP_TYPE = Collections.unmodifiableMap(new HashMap<Integer,Integer>(){{
        put(codeET(soundEffects.ERASE,soundTheme.CLASSIC),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.ERASE,soundTheme.MARIO),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.ERASE,soundTheme.PACMAN),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.ERASE,soundTheme.STARWARS),SoundManagerFactory.WAVE);
        
        put(codeET(soundEffects.FALL,soundTheme.CLASSIC),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.FALL,soundTheme.MARIO),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.FALL,soundTheme.PACMAN),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.FALL,soundTheme.STARWARS),SoundManagerFactory.WAVE);
        
        put(codeET(soundEffects.PAUSE,soundTheme.CLASSIC),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.PAUSE,soundTheme.MARIO),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.PAUSE,soundTheme.PACMAN),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.PAUSE,soundTheme.STARWARS),SoundManagerFactory.WAVE);
        
        put(codeET(soundEffects.THEME,soundTheme.CLASSIC),SoundManagerFactory.MIDI);
        put(codeET(soundEffects.THEME,soundTheme.MARIO),SoundManagerFactory.MIDI);
        put(codeET(soundEffects.THEME,soundTheme.PACMAN),SoundManagerFactory.MIDI);
        put(codeET(soundEffects.THEME,soundTheme.STARWARS),SoundManagerFactory.MIDI);
        
        put(codeET(soundEffects.GAMEOVER,soundTheme.CLASSIC),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.GAMEOVER,soundTheme.MARIO),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.GAMEOVER,soundTheme.PACMAN),SoundManagerFactory.WAVE);
        put(codeET(soundEffects.GAMEOVER,soundTheme.STARWARS),SoundManagerFactory.WAVE);
   }});
    
    
    public static int codeET(soundEffects se, soundTheme st){
        return se.ordinal()*(soundTheme.values().length)+ st.ordinal();
    }
    static Integer volume = null;
    
    static public enum soundEffects{
        ERASE, FALL, GAMEOVER, PAUSE, THEME;
    }
    static public enum soundTheme{
        CLASSIC, MARIO, PACMAN, STARWARS;
    }
    
   
    
    protected SoundManager(){

    }
    
    public SoundManager(soundEffects se, soundTheme st){
        if(volume == null)
            volume = 100;
        synchronized(SoundManager.class){
            //if(!instances.containsKey(se))
                instances.put(se, this);
        }
        setUp(st, se,volume);   
    }
    
    
    public SoundManager(soundEffects se, soundTheme st,int vvolume){
        volume = vvolume;
        synchronized(SoundManager.class){
           // if(!instances.containsKey(se))
                instances.put(se, this);
        }
        setUp(st, se,vvolume);   
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
        // validation of variables.
        if(newVolume > 100)
            newVolume = 100;
        if(newVolume < 0)
            newVolume = 0;
        
        volume = newVolume;
        for (SoundManager sm : instances.values()) {
            System.out.println("Setting volume for: "+sm.toString());
            sm.setVolume(newVolume);
        }
    }
    
    static public int getGlobalVolume(){
        return volume;
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
    
    abstract protected void setUp(soundTheme theme,soundEffects effect,int volume);
}
