package sound;

import javax.sound.midi.*;
import java.io.InputStream;

/**
 * This class is disigned in order to executes a sample of sound.
 */
public class MidiPlayerSample {
    
  /**
     * Main program of the class.
     * @param args in according to the default argument.
     */  
  public static void main(String[] args) {
      try {
          Sequencer sequencer = MidiSystem.getSequencer();

          if (sequencer == null)
              throw new MidiUnavailableException();
          sequencer.open();
           InputStream is = MidiPlayerSample.class.getResourceAsStream("tracks/STARWARS_THEME.mid");
          Sequence mySeq = MidiSystem.getSequence(is);
          sequencer.setSequence(mySeq);
          sequencer.start();
          Thread.sleep(10000);

          
          sequencer.stop();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}