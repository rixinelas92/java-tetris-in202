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
public class SoundManagerFactory {
    static SoundManager.soundTheme theme = SoundManager.soundTheme.CLASSIC;
    final static public int MIDI = 0;
    final static public int WAVE = 1;
    
    SoundManager getSoundManager(SoundManager.soundTheme theme, SoundManager.soundEffects effect){
        switch(SoundManager.MAP_TYPE.get(SoundManager.codeET(effect,theme))){
            case MIDI:
                return new MidiSoundManager(effect,theme);
            case WAVE:
                return new WavSoundManager(effect,theme);
        }
        return null;
    }
    SoundManager getSoundManager(SoundManager.soundEffects effect){
        switch(SoundManager.MAP_TYPE.get(SoundManager.codeET(effect,theme))){
            case MIDI:
                return new MidiSoundManager(effect,theme);
            case WAVE:
                return new WavSoundManager(effect,theme);
        }
        return null;
    }
    void setDefTheme(SoundManager.soundTheme theme){
        if(theme == null)
            return;
        this.theme = theme;
    }
}
