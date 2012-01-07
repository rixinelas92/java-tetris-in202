

package tetris;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipeteles
 */
public class Screen {
    static final public short SIZE_X = 10;
    static final public short SIZE_Y = 13;
    private Box[][] grid;
    private Box nulle = new Box();

    /**                \/ middle position
     *     ( 0,12) ... (3,12) ... ( 9,12)
     *      ...........................
     *     ( 0, 0) ... ...........( 9, 0)
     */
    static private Position middle;
    /**
     * Default getter of the the parameter <em>middle</em> if it's already existes
     * or setter of this parameter on the central/top of the screen.
     * @return middle with the initial position of generation of boxes. 
     */
    static public Position getMiddlePosition(){
        if(middle != null)
            return middle;
        
            middle = new Position((short) SIZE_X/2 - 2, (short) SIZE_Y+1,0);
        
        
        return middle;
    }
    /**
     * Creates a new screen with parameters defined by default.
     */
    public Screen(){ 
        grid = new Box[SIZE_X][SIZE_Y+3];
        for(int i = 0;i<grid.length;i++)
            for(int j = 0;j<grid[i].length;j++)
                grid[i][j] = new Box();
        
    }
    /**
     * Creates a clone of a previous screen.
     * @param s defines de original screen.
     */
    public Screen(Screen s){
        grid = new Box[SIZE_X][SIZE_Y];
        for(int i = 0;i<SIZE_X;i++){
            for(int j = 0;j<SIZE_Y;j++){
                grid[i][j] = new Box(s.grid[i][j]);
            }
        }
    }
    /**
     * 
     * @param x defines the parameter <em>x</em> of a table of pieces.
     * @param y defines the parameter <em>y</em> of a table of pieces.
     * @param color defines the color of the selected piece.
     */
    public void joinPiece(int x, int y, Color color){
        grid[x][y].setFull(true);
        grid[x][y].setColor(color);
    }
    /**
     * Returns -1 if no line were affected and i >= 0 if the i-nary line
     * were afected. (starting from 0).
     * @return number of line affected.
     */
    public short checkLine(){
        for(int i = 0;i<SIZE_Y;i++){
            int j;
            for(j = 0;j<SIZE_X;j++){
               
                if(!grid[j][i].isFull())
                    break;
            }
            if(j == SIZE_X) // all lines filled.
                return (short)i;
        }
        return -1;
    }
    /**
     * Removes the line that are filled.
     * @param line informes the line to be remove.
     */
    public void removeLine(int line){
        for(int i = line+1;i<SIZE_Y;i++){
            int j;
            for(j = 0;j<SIZE_X;j++){
                if(grid[j][i].isFull())
                    break;
            }
            if(j == SIZE_X) // none line is filled
                break;

            for(j = 0;j<SIZE_X;j++){
                grid[j][i-1].copyFrom(grid[j][i]);
                grid[j][i].setFull(false);
            }
        }
     //   for(int j = 0;j<SIZE_X;j++)
      //      grid[j][SIZE_Y-1].setFull(false);
    }
    /**
     * Returns colision in bord of the screen like false.
     * @return false.
     */
    public boolean checkColisionBord(){
        return false;
    }
    /**
     * Returns colision in the bottom edge like false.
     * @return false.
     */
    public boolean checkColisionBase(){
        return false;
    }
    /**
     * 
     */
    public void clearPosition(){
    }
    /**
     * Default getter of boxes.
     * @param x defines the parameter <em>x</em> of the table of box.
     * @param y defines the parameter <em>y</em> of the table of box.
     * @return the box correspondent of the table.
     */
    public Box getBoxAt(short x,short y){
        if(x > SIZE_X  || x < 0)
            return null;
        if(y > SIZE_X+3  || y < 0)
            return null;
        return grid[x][y];
    }
    
    public class BorderRetriever{
        public short getMaxX(){
            return SIZE_X;
        }
        public short getMaxY(){
            return SIZE_Y;
        }
    }
    public class FilledRetriever{
        public boolean isFilledWithStaticBloc(int x, int y){
            if(x >= grid.length || x < 0)
                return true;
            if(y >= grid[x].length || y < 0)
                return true;
            return grid[x][y].isFull();
        }
    }
    static public class OutOfScreenBoundsException extends Exception{
    }
    static public class NotAvailablePlaceForPieceException extends Exception{
        public NotAvailablePlaceForPieceException(String error){
            super(error);
        }
    }
}