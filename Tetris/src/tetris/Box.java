/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import tetris.Piece.ShapeType;

/**
 *
 * @author felipeteles
 */
public class Box {
    private boolean full;
    private Color color;
    
    public Box(){
        this.full = false;
        this.color = Piece.shapeColors[ShapeType.None.ordinal()];
    }
    public Box(boolean full, Color color){
        this.full = full;
        this.color = color;
    }
    public Box(Box b){
        this.color=b.color;
        this.full = b.full;
    }
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

    public void copyFrom(Box b){
        this.color = b.color;
        this.full = b.full;
    }

}
