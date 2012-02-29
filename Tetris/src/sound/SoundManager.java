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
/**
 * this class was designed in order to manage the library of sound's samples. 
 */
public abstract class SoundManager {
    //private static final Map<soundEffects, SoundManager> instances = new EnumMap<soundEffects, SoundManager>(soundEffects.class);
    private static soundTheme theme;
    
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
    
    /**
     * Codifies the sound effects and sound theme.
     * @param se defines the sound effects.
     * @param st defines the sound themes.
     * @return the codification.
     */
    public static int codeET(soundEffects se, soundTheme st){
        return se.ordinal()*(soundTheme.values().length)+ st.ordinal();
    }
    static Integer volume = null;

    /**
     * Set the global theme.
     * @param soundTheme is the theme to be used
     */
    public static void setTheme(soundTheme soundTheme) {
        SoundManager.theme = soundTheme;
    }
    

    /**
     * Defines a standard to sound's effects.
     */
    static public enum soundEffects{
        ERASE, FALL, GAMEOVER, PAUSE, THEME;
    }
    /**
     * Defines a standard to theme's effects.
     */
    static public enum soundTheme{
        CLASSIC, MARIO, PACMAN, STARWARS;
    }
    protected SoundManager(){
    }
    /**
     * It configures the sound and the volume, by default.
     * @param se defines the sound effects.
     * @param st defines the theme effects.
     */
    public SoundManager(soundEffects se, soundTheme st){
        if(volume == null)
            volume = 100;
        setUp(st, se,volume);   
    }
    /**
     * It configures the sound and the volume through the parameters provided.
     * @param se defines the sound effects.
     * @param st defines the theme effects.
     */
    public SoundManager(soundEffects se, soundTheme st,int vvolume){
        volume = vvolume;

        setUp(st, se,vvolume);   
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
        for (SoundEffectWrapper sm : SoundEffectWrapper.values()){
            sm.setVolume(newVolume);
        }
    }
    /**
     * Default getter of the volume.
     * @return the current volume.
     */
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
    /**
     * Prepares the execution of the sound.
     * @param theme defines the sound theme.
     * @param effect defines the sound effect.
     * @param volume defines the sound's volume.
     */
    abstract protected void setUp(soundTheme theme,soundEffects effect,int volume);
}
