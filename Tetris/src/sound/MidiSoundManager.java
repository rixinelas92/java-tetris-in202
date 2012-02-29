/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

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
    

    public MidiSoundManager(soundEffects se, soundTheme st){
        super(se,st);
        theme = st;
        effect = se;
    }
    
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
            System.out.println("Playing: "+effect+" .. "+theme);
            sequencer.setTickPosition(5);
            sequencer.start();
        }else{
            System.err.println("sequencer null :"+effect+" .. "+theme);
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
      //  if(volume == 0)
        //    volume = 10;
        if(volume == 0){
            stopSound();
            sequencer = null;
            if(sequencer == null)
                System.err.println("Sounds were turned off for this effect: [ops] "+theme+">"+effect+">"+volume+" ");
            
            return;
        }
        InputStream is = null;
        try {
            sequencer = MidiSystem.getSequencer();

            if (sequencer == null) {
                System.err.println("Not possible to get sequencer, problems comming...");
                return;
            }
            sequencer.open();
            
            is = getClass().getResourceAsStream("tracks/"+theme+"_"+effect+"_"+volume+".mid");
                
            Sequence mySeq = MidiSystem.getSequence(is);
            sequencer.setSequence(mySeq);
            sequencer.start();
            sequencer.stop();
            if(isplaying)
                play();
            
            if(sequencer == null)
                System.err.println("Sounds were turned off for this effect: [ops] "+theme+">"+effect+">"+volume+" ");
     /*   } catch (InvalidMidiDataException ex) {
            System.err.println("It was not possible to read the midi file (invalid data)");
            
        } catch (IOException ex) {
            System.err.println("It was not possible to read the midi file (io problem)");
        } catch (MidiUnavailableException ex) {
            System.err.println("It was not possible to read the midi file (unavaible)"); */
        } catch (Exception e){
            System.err.println("Sounds were turned off for this effect: "+theme+">"+effect+">"+volume+" | cause: ");
//            e.printStackTrace();
            try{
                is.close();
            } catch(Exception e2){}
            sequencer = null;
        } 
        if(sequencer == null)
                System.err.println("Sounds were turned off for this effect: [ops] "+theme+">"+effect+">"+volume+" ");

        

    }
    
    @Override
    public String toString(){
        return "[MIDI SND MANAGER: "+theme+">"+effect+">"+volume+"]";
    }
}
