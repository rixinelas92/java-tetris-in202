/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;

/**
 *
 * @author felipeteles
 */
public class Box {
    private boolean full;
    private Color color;
    
    public Box(){
    };//começa tudo em branco e full=false.
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    static Color getEmptyColor(){
        return Color.WHITE;
    }


}
