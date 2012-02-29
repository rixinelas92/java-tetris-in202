/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import sound.SoundManager.soundEffects;
import sound.SoundManager.soundTheme;

/**
 * This class was designed in order to solve a problem of types of extension of
 * the sample's sound.
 * @author gustavo
 */
public class SoundManagerFactory {
    static SoundManager.soundTheme theme = SoundManager.soundTheme.CLASSIC;
    final static public int MIDI = 0;
    final static public int WAVE = 1;
    /**
     * 
     * @param theme defines the theme.
     * @param effect defines the effect.
     * @return the file in according to its characteristic, a MIDI or a WAVE. 
     */
    SoundManager getSoundManager(SoundManager.soundTheme theme, SoundManager.soundEffects effect){
        switch(SoundManager.MAP_TYPE.get(SoundManager.codeET(effect,theme))){
            case MIDI:
                return new MidiSoundManager(effect,theme);
            case WAVE:
                return new WavSoundManager(effect,theme);
        }
        return null;
    }
    /**
     * Default getter of the theme, if IMPL is 0, it returns a MIDI and if it is 
     * 1, returns a WAVE file.
     * @return the file requested.
     */
    SoundManager getSoundManager(SoundManager.soundEffects effect){
        switch(SoundManager.MAP_TYPE.get(SoundManager.codeET(effect,theme))){
            case MIDI:
                return new MidiSoundManager(effect,theme);
            case WAVE:
                return new WavSoundManager(effect,theme);
        }
        return null;
    }
    /**
     * Default setter of a theme's sound.
     * @param theme defines the theme.
     */
    void setDefTheme(SoundManager.soundTheme theme){
        if(theme == null)
            return;
        this.theme = theme;
    }
}
