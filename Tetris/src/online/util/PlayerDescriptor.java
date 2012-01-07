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

    public PlayerDescriptor(String name, PlayerState state, int id) {
        this.name = name;
        this.state = state;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return id;
    }

    

}
