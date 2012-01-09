/**
 * Java doc OK
 */
package tetris;

import java.awt.Color;
import tetris.Piece.ShapeType;

/**
 * This class definies the the basic structure for each box of the screen.
 * @author felipeteles.
 */
public class Box {

    private boolean full;
    private Color color;
    /**
     * Creates a new box with attributes color and full setted.
     */
    public Box(){
        this.full = false;
        this.color = Piece.shapeColors[ShapeType.None.ordinal()];
    }
    /** 
     * Creates a new box.
     * @param full defines the condition of the box.
     * @param color defines the color of the box.
     */
    public Box(boolean full, Color color){
        this.full = full;
        this.color = color;
    }
    /**
     * Creates a new box using another one.
     * @param b identifies the box to be copied.
     */
    public Box(Box b){
        this.color=b.color;
        this.full = b.full;
    }
    /**
     * Default getter of the attribute <em>color</em>.
     * @return the color of the box.
     */
    public Color getColor() {
        return color;
    }
    /**
     * Default setter of the attribute <em>color</em>.
     * @param color identifies the color of the box to be setted.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Reader of the condition of the attribute full.
     * @return full true if is full and false if empty.
     */
    public boolean isFull() {
        return full;
    }
    /**
     * Setter of the attribute full.
     * @param full identifies the condition of the box.
     */
    public void setFull(boolean full) {
        this.full = full;
    }
    /**
     * Default getter of the attributes <em>emptyColor</em>.
     * @return color for empty boxes.
     */
    public static Color getEmptyColor(){
        return Color.WHITE;
    }
    /**
     * Clones the parameters into the current object.   
     * @param b identifies the box to be copied.
     */
    public void copyFrom(Box b){
        this.color = b.color;
        this.full = b.full;
    }

}
