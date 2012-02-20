/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

/**
 *
 * @author gustavo
 */
public class WavSoundManager extends SoundManager {
    private Clip clip;
    private FloatControl volumeControl;

    
    
    public WavSoundManager(soundEffects se, soundTheme st,int volume){
        super(se,st,volume);
    }
    
    @Override
    public void setVolume(int newVolume) {
        if(volumeControl == null)
            return;
        float volume;
        volume = (float) (volumeControl.getMaximum() - volumeControl.getMinimum());
        volume *= (float) Math.log(2*newVolume) /Math.log(2*100);
        volumeControl.setValue(volume+volumeControl.getMinimum());
    }

    @Override
    public void play() {
        if(volumeControl == null)
            return ;
        if (volumeControl.getValue() != volumeControl.getMinimum()) {
            if (clip.isRunning()) {
                clip.stop();   // Stop the player if it is still running
            }
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();// Start playing

        }    
    }

    @Override
    public void setLoop() {
        if(clip == null)
            return;
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stopSound() {
        if(clip == null)
            return;
        clip.stop();
    }

    @Override
    protected void setUp(soundTheme theme,soundEffects effect,int volume) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getResource("files/"+theme+"_"+effect+".wav");
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volume);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("It was not possible to start the sound");
        //    e.printStackTrace();
        } catch (Exception e) {
            System.err.println("It was not possible to start the sound");
        //    e.printStackTrace();
        }
    }
    
}
