/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package online.util;

import online.server.Server;

/**
 *
 * @author gustavo
 */

public class Match {
    final int matchid;
    private Player[] p = new Player[2];
    private Integer[] pScore = new Integer[2];
    private Server[] s = new Server[2];
    private caracRet<Player> pret = new caracRet<Player>();
    private caracRet<Integer> scret = new caracRet<Integer>();
    private caracRet<Server>  seret = new caracRet<Server>();

    /**
     * This class checks the validity of the parameters and configures the network
     * if the parameters are valids.
     * @param p1 defines the first player.
     * @param s1 defines the server to the first player.
     * @param p2 defines the second player.
     * @param s2 defines the server to the second player.
     * @param matchid identifies the game.
     */
    public Match(Player p1, Server s1, Player p2, Server s2,int matchid) {
        if(p1 == null)
            throw new NullPointerException("p1");
        if(s1 == null)
            throw new NullPointerException("s1");
        if(p2 == null)
            throw new NullPointerException("p2");
        if(s2 == null)
            throw new NullPointerException("s2");
        
        this.p[0] = p1;
        this.pScore[0] = 0;
        this.s[0] = s1;
        this.p[1] = p2;
        this.pScore[1] = 0;
        this.s[1] = s2;
        this.matchid = matchid;
        p1.setMatch(this);
        p2.setMatch(this);
    }

    /**
     * Default getter of the parameter <em>matchid</em>.
     * @return the identity of the game.
     */
    public int getMatchid(){
        return matchid;
    }
    /**
     * Returns the identity of the opponent in the game.
     * @param id identifies the player of reference.
     * @return the identity of the opponent.
     */
    public Player getOtherPlayer(int id){
        return pret.getOther(id, p);
    }
    /**
     * Returns the information of the player identified just by the id.
     * @param id informs the player of reference.
     * @return the parameters of the player.
     */
    public Player getPlayerWithid(int id){
        return pret.getWith(id, p);
    }
    /**
     * Returns the information of the other server.
     * @param id identifies the server of reference.
     * @return the parameters of the other server.
     */
    public Server getOtherServer(int id){
        return seret.getOther(id, s);
    }
    /**
     * Returns the information of the server identified just by the id.
     * @param id informs the server of reference.
     * @return the parameters of the server.
     */
    public Server getServerWithid(int id){
        return seret.getWith(id, s);
    }
    /**
     * Returns the score of the opponent in the game.
     * @param id identifies the player of reference.
     * @return the score the opponent.
     */
    public int getOtherScore(int id){
        return scret.getOther(id, pScore);
    }
    /**
     * Returns the information about the score of the player identified just by the id.
     * @param id informs the score of the player of reference.
     * @return the score of the player.
     */
    public int getScoreWith(int id){
        return scret.getWith(id, pScore);
    }
    /**
     * Returns the identities of the players in the game.
     * @return the parameters of the players.
     */
    public int[] getPlayersIds(){
        int[] ids = new int[2];
        ids[0] = p[0].getPlayerId();
        ids[1] = p[1].getPlayerId();
        return ids;
    }
    /**
     * Implements the methods necessary to the class Math.
     * @param <T> 
     */
    final class caracRet<T>{
        T getOther(int id,T[] v){
            if(p[0].getPlayerId() == id)
                return v[1];
            if(p[1].getPlayerId() == id)
                return v[0];
            return null;
        }

        T getWith(int id,T[] v){
            if(p[1].getPlayerId() == id)
                return v[1];
            if(p[0].getPlayerId() == id)
                return v[0];
            return null;
        }
    }
}
