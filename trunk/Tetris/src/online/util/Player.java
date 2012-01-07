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
public class Player {

    PlayerState actState;
    final String name;
    Server s;
    final int playerid;
    Match m;

    public static enum PlayerState{
        ONLINE,PLAYING,OFFLINE
    }

    public Player(String name,Server s,int playerid){
        name = validName(name);
        if(name == null)
            name = "Unnamed";
        this.name = name;
        actState = PlayerState.ONLINE;
        this.s = s;
        this.playerid = playerid;
    }

    public void setState(PlayerState state){
        actState = state;
    }

    public PlayerState getState(){
        return actState;
    }

    public String getName(){
        return name;
    }

    public static String validName(String str){
        if(str == null)
            return str;
        str = str.trim();
        str = str.split("//s")[0];
        if(str.length() == 0)
            return null;
        return str;
    }
    public static boolean validateName(String str){
        if(str == null)
            return false;
        str = str.trim();
        str = str.split("//s")[0];
        if(str.length() == 0)
            return false;
        return true;
    }

    public Server getServer(){
        return s;
    }
    public int getPlayerId(){
        return playerid;
    }

    public Match getMatch(){
        return m;
    }
    public void setMatch(Match m){
        this.m = m;
    }

    public String toString(){
        return name+" - "+actState;
    }
}
