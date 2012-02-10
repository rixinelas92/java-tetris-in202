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

    public MidiSoundManager(soundEffects se, soundTheme st){
        super(se,st);
    }
    @Override
    public void setVolume(int newVolume) {
    }

    @Override
    public void play() {
        if (sequencer != null) {
            sequencer.start();
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

    @Override
    protected void setUp(soundTheme theme,soundEffects effect){
        try {
            sequencer = MidiSystem.getSequencer();

            if (sequencer == null) {
                return;
            }
            sequencer.open();
            InputStream is = MidiPlayerSample.class.getResourceAsStream("files/"+theme+"_"+effect+".mid");
            Sequence mySeq = MidiSystem.getSequence(is);

            sequencer.setSequence(mySeq);
        } catch (InvalidMidiDataException ex) {
            System.err.println("It was not possible to read the midi file (invalid data)");
        } catch (IOException ex) {
            System.err.println("It was not possible to read the midi file (io problem)");
        } catch (MidiUnavailableException ex) {
            System.err.println("It was not possible to read the midi file (unavaible)");
        }

    }
}
