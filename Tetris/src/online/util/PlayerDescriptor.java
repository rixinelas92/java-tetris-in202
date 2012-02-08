/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package online.util;

import online.util.Player.PlayerState;

/**
 *
 * @author gustavo
 */

public class PlayerDescriptor {
    int id = -1;
    String name;
    PlayerState state;
    /**
     * Creates a new PlayerDescriptor  with the parameters <em>name</em>, 
     * <em>state</em>, <em>id</em> already setted.
     * @param name identifies the name of the player in the game.
     * @param state identifies the status of the game.
     * @param id identifies the match.
     */
    public PlayerDescriptor(String name, PlayerState state, int id) {
        this.name = name;
        this.state = state;
        this.id = id;
    }
    /** 
     * Default getter of the parameter <em>id</em>.
     * @return idetifies the game.
     */
    public int getId() {
        return id;
    }
    /** 
     * Default setter of the parameter <em>id</em>.
     */
    public void setId(int id) {
        this.id = id;
    }
    /** 
     * Default getter of the parameter <em>name</em>.
     * @return player's name in the game.
     */
    public String getName() {
        return name;
    }
    /** 
     * Default setter of the parameter <em>name</em>.
     */
    public void setName(String name) {
        this.name = name;
    }
    /** 
     * Default getter of the parameter <em>state</em>.
     * @return the status of the game.
     */
    public PlayerState getState() {
        return state;
    }
    /** 
     * Default setter of the parameter <em>state</em>.
     */
    public void setState(PlayerState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return id;
    }
    public String toString(){
        return name+" - "+state;
    }
    

}
