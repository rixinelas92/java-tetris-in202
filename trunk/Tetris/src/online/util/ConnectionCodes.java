/* Java doc OK.
 * 
 */


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package online.util;

/**
 *
 * @author gustavo
 */
 /**
 * This class has the responsabilite of define the status/massages that are change 
 * between the players to the server.
 */
public class ConnectionCodes {
    /**
     * This class defines the messages/requests that can be done by the players 
     * to the server in order to configure the game.
     */
    public static enum PlayerQueryCodes{
        CREATECLIENT,PLIST,MATCHREQ,MATCHACCEPT,GAMEPOINT,GAMEOVER, MATCHDECLINE, ALIVE, BOARD
    }
    /**
     * This class defines the messages/requests that can be done by the server 
     * to the players in order to configure the game.
     */
    public static enum ServerQueryCodes{
        PLIST,MATCHREQ,MATCHSTART,MATCHDECLINED,GAMEPUNN,ENDGAME,ERROR, ALIVE, BOARD
    }


}
