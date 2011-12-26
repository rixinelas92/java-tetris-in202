/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package online.util;

/**
 *
 * @author gustavo
 */
public class ConnectionCodes {
    public static enum PlayerQueryCodes{
        CREATECLIENT,PLIST,MATCHREQ,MATCHACCEPT,GAMEPOINT,GAMEOVER
    }
    public static enum ServerQueryCodes{
        PLIST,MATCHREQ,MATCHSTART, GAMEPUNN,ENDGAME,ERROR
    }

}
