/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.*;

/**
 *
 * @author gustavo
 */
public class MidiSoundManager extends SoundManager {

    Sequencer sequencer;
    
    soundTheme theme;
    soundEffects effect;
    

    public MidiSoundManager(soundEffects se, soundTheme st,int vvolume){
        super(se,st,vvolume);
        theme = st;
        effect = se;
    }
    @Override
    public void setVolume(int newVolume) {
        setUp(theme, effect, newVolume);
    }

    @Override
    public void play() {
        if (sequencer != null) {
            sequencer.start();
        }else{
            System.err.append("sequencer null");
        }
    }

    @Override
    public void setLoop() {
        if (sequencer != null) {
           sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void stopSound() {
        if (sequencer != null) {
            sequencer.stop();
        }
    }

    int volumeTransfFunction(int volume){
        if(volume > 100)
            volume = 100;
        if(volume < 0)
            volume = 0;
        return volume/10;
    }
    
    @Override
    protected void setUp(soundTheme theme,soundEffects effect,int volume){
        volume = volumeTransfFunction(volume);
        boolean isplaying = false;
        if(sequencer != null){
            isplaying = sequencer.isRunning();
            stopSound();
        }
        if(volume == 0){
            stopSound();
            sequencer = null;
            return;
        }
        try {
            sequencer = MidiSystem.getSequencer();

            if (sequencer == null) {
                System.err.println("Not possible to get sequencer, problems comming...");
                return;
            }
            sequencer.open();
            System.out.println("tracks/"+theme+"_"+effect+"_"+volume+".mid");
            InputStream is = getClass().getResourceAsStream("tracks/"+theme+"_"+effect+"_"+volume+".mid");
            Sequence mySeq = MidiSystem.getSequence(is);
            sequencer.setSequence(mySeq);
            
            if(isplaying)
                play();
            
        } catch (InvalidMidiDataException ex) {
            System.err.println("It was not possible to read the midi file (invalid data)");
        } catch (IOException ex) {
            System.err.println("It was not possible to read the midi file (io problem)");
        } catch (MidiUnavailableException ex) {
            System.err.println("It was not possible to read the midi file (unavaible)");
        }

    }
}
