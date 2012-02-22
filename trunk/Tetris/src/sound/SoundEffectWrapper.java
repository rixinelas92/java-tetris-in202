/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import sound.SoundManager.soundEffects;
import sound.SoundManager.soundTheme;

/**
 *
 * @author gustavo
 */
public enum SoundEffectWrapper {
    
    
    CERASE(soundTheme.CLASSIC,soundEffects.ERASE),      
    CFALL(soundTheme.CLASSIC,soundEffects.FALL),   
    MERASE(soundTheme.MARIO,soundEffects.ERASE),      
    MFALL(soundTheme.MARIO,soundEffects.FALL),  
    MGAMEOVER5(soundTheme.MARIO,soundEffects.GAMEOVER),
    MPAUSE(soundTheme.MARIO,soundEffects.PAUSE),      
    PERASE(soundTheme.PACMAN,soundEffects.ERASE),      
    PFALL(soundTheme.PACMAN,soundEffects.FALL),  
    PGAMEOVER5(soundTheme.PACMAN,soundEffects.GAMEOVER),
    PPAUSE(soundTheme.PACMAN,soundEffects.PAUSE),     
    SERASE(soundTheme.STARWARS,soundEffects.ERASE),      
    SFALL(soundTheme.STARWARS,soundEffects.FALL);
    
    SoundManager sm;
     SoundEffectWrapper(SoundManager.soundTheme theme, SoundManager.soundEffects effect) {
         SoundManagerFactory smf = new SoundManagerFactory();
         sm = smf.getSoundManager(theme, effect);
         
     }

     
     
     
    /**
     * Adjuts the level of volume of the game.
     * @param newVolume defines the updated volume.
     */
    public void setVolume(int newVolume) {
        sm.setVolume(newVolume);
    }
    /**
     * Adjuts the level of volume of the effets of game.
     * @param newVolume defines the updated volume.
     */    
    static public void setGlobalVolume(int newVolume) {
        SoundManager.setGlobalVolume(newVolume);
    }
    /*
     * Plays in a continous way a clip by rewinding a theme.
     */
    public void play() {
        sm.play();
    }
    /**
     * Maintains the execution of a clip.
     */
    public void setLoop() {
        sm.setLoop();
    }
    /**
     * Determines the end in a execution of a clip.
     */
    public void setStop(){
        sm.stopSound();
    }

    /**
     * Optional static method to pre-load all the sound files.
     */ 
    public static void init() {
        values(); // calls the constructor for all the elements
    }
    
}
