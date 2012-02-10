/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

/**
 *
 * @author gustavo
 */
public class SoundManagerFactory {
    static SoundManager.soundTheme theme = SoundManager.soundTheme.CLASSIC;
    final static private int MIDI = 0;
    final static private int WAVE = 1;
    final static private int IMPL = MIDI;
    
    SoundManager getSoundManager(SoundManager.soundTheme theme, SoundManager.soundEffects effect){
        switch(IMPL){
            case MIDI:
                return new MidiSoundManager(effect,theme);
            case WAVE:
                return new WavSoundManager(effect,theme);
        }
        return null;
    }
    SoundManager getSoundManager(SoundManager.soundEffects effect){
        switch(IMPL){
            case MIDI:
                return new MidiSoundManager(effect,theme);
            case WAVE:
                return new WavSoundManager(effect,theme);
        }
        return null;
    }
    void setDefTheme(SoundManager.soundTheme theme){
        this.theme = theme;
    }
}
