/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */
public enum SoundEffect {

    THEME("testTheme.wav"), // explosion
    ERASE("eraseLineSound.wav"), // gong
    FALL("fallDownSound.wav");        // bullet
    // Nested class for specifying volume
    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;
    private FloatControl volumeControl;
    static int globalvolume;
    // Constructor to construct each element of the enum with its own sound file.

    SoundEffect(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void setVolume(int newVolume) {
        float volume;
        volume = volumeControl.getMaximum() - volumeControl.getMinimum();
        volume *= (float) newVolume / 100;
        volumeControl.setValue(volumeControl.getMinimum() + volume);
    }

    static public void setGlobalVolume(int newVolume) {
        for (SoundEffect se : SoundEffect.values()) {
            se.setVolume(newVolume);
        }

    }

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

    public void setLoop() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}
