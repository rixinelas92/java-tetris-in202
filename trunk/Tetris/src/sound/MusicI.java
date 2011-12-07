/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sound;

/**
 *
 * @author gustavo
 */
public interface MusicI {

    public void prepare();
    public void play() throws SoundPlaybackException;
    public void stop() throws SoundPlaybackException;
    public void setLoop(boolean shouldLoop);
    public void setVolume(int v) throws SoundPlaybackException;
    public int getVolume() ;

    static public class SoundPlaybackException extends Exception{
        
    }
}
