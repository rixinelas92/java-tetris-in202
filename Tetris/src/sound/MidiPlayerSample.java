/* Java doc OK
 * 
 */

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
                    Sequencer sequencer2 = MidiSystem.getSequencer();

          if (sequencer == null)
              throw new MidiUnavailableException();
          sequencer.open();
          sequencer2.open();
           InputStream is = MidiPlayerSample.class.getResourceAsStream("battle.mid");
            InputStream is2 = MidiPlayerSample.class.getResourceAsStream("battle.mid");
          Sequence mySeq = MidiSystem.getSequence(is);
          Sequence mySeq2 = MidiSystem.getSequence(is2);
          sequencer.setSequence(mySeq);
          sequencer.start();
          sequencer2.setSequence(mySeq2);
          Thread.sleep(10000);
          sequencer2.start();
                    Thread.sleep(10000);

          sequencer.stop();
          sequencer2.stop();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}