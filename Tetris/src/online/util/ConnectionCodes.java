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
        CREATECLIENT,PLIST,MATCHREQ,MATCHACCEPT,GAMEPOINT,GAMEOVER, MATCHDECLINE, ALIVE, BOARD
    }
    public static enum ServerQueryCodes{
        PLIST,MATCHREQ,MATCHSTART,MATCHDECLINED,GAMEPUNN,ENDGAME,ERROR, ALIVE, BOARD
    }


}
