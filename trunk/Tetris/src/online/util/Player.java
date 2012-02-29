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
    /** 
     * Defines the status of a player.
     */
    public static enum PlayerState{
        ONLINE,PLAYING,OFFLINE
    }
    /**
     * Default setter of the parameters of the class player.
     * @param name informes de name of the player.
     * @param s informes the information of the server.
     * @param playerid identifies the player.
     */
    public Player(String name,Server s,int playerid){
        name = validName(name);
        if(name == null)
            name = "Unnamed";
        this.name = name;
        actState = PlayerState.ONLINE;
        this.s = s;
        this.playerid = playerid;
    }
    /**
     * Default setter of the parameter <em>actState</em>.
     * @param state informes the current state of the game.
     */
    public void setState(PlayerState state){
        actState = state;
    }
    /**
     * Default getter of the parameter <em>actState</em>.
     * @return the current state of the player.
     */
    public PlayerState getState(){
        return actState;
    }
    /**
     * Default getter of the parameter <em>name</em>.
     * @return the name of the player.
     */
    public String getName(){
        return name;
    }
    /**
     * Checks the validity of a name.
     * @param str name of reference.
     * @return a treated name.
     */
    public static String validName(String str){
        if(str == null)
            return str;
        str = str.trim();
        str = str.split("//s")[0];
        if(str.length() == 0)
            return null;
        return str;
    }
    /**
     * Executes the trial of names provided.
     * @param str the name of reference.
     * @return true in case of valid name and false in otherwise.
     */
    public static boolean validateName(String str){
        if(str == null)
            return false;
        str = str.trim();
        str = str.split("//s")[0];
        if(str.length() == 0)
            return false;
        return true;
    }
    /**
     * Default getter of the parameter <em>s</em>.
     * @return the information about the server.
     */
    public Server getServer(){
        return s;
    }
    /**
     * Default getter of the parameter <em>id</em>.
     * @return the identity of the player.
     */
    public int getPlayerId(){
        return playerid;
    }
    /**
     * Default getter of the parameter <em>m</em>.
     * @return the data about the match.
     */
    public Match getMatch(){
        return m;
    }
    /**
     * Default setter of the parameter <em>m</em>.
     * @return the parameter m modified.
     */
    public void setMatch(Match m){
        this.m = m;
    }
    /**
     * Adpts the datas to a convenient string.
     * @return the string formed by the agglutination of datas.
     */
    public String toString(){
        return name+" - "+actState;
    }
    @Override
    public int hashCode() {
        return playerid%97;
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Player))
            return false;
        if(((Player)obj).playerid == playerid)
            return true;
        return false;
    }
}
