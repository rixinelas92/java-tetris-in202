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


    public int getMatchid(){
        return matchid;
    }

    public Player getOtherPlayer(int id){
        return pret.getOther(id, p);
    }
    public Player getPlayerWithid(int id){
        return pret.getWith(id, p);
    }
    public Server getOtherServer(int id){
        return seret.getOther(id, s);
    }
    public Server getServerWithid(int id){
        return seret.getWith(id, s);
    }
    public int getOtherScore(int id){
        return scret.getOther(id, pScore);
    }
    public int getScoreWith(int id){
        return scret.getWith(id, pScore);
    }

    public int[] getPlayersIds(){
        int[] ids = new int[2];
        ids[0] = p[0].getPlayerId();
        ids[1] = p[1].getPlayerId();
        return ids;
    }
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
