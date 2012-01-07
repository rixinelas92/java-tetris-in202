package sound;

import javax.sound.midi.*;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MidiPlayerSample {

  public static void main(String[] args) {
      try {
          Sequencer sequencer = MidiSystem.getSequencer();
          if (sequencer == null)
              throw new MidiUnavailableException();
          sequencer.open();
          InputStream is = MidiPlayerSample.class.getResourceAsStream("Korobeiniki.mid");
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