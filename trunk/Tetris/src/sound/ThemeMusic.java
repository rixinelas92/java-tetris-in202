/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;



/**
 *
 * @author gustavo
 */
public class ThemeMusic implements MusicI {
    int volume;
    public void prepare() {
        System.out.println("Preparing");
    }
    public void play() {
        System.out.println("Playing");
    }
    public void stop() {
        System.out.println("Stoping");
    }
    public void setLoop(boolean shouldLoop) {
        System.out.println("Loooppp");
    }
    public void setVolume(int v) throws SoundPlaybackException {
        if(v > 100 || v < 0)
            throw new SoundPlaybackException();
        volume = v;
        System.out.println("v="+v);
    }
    public int getVolume() {
        return volume;
        
    }
}

