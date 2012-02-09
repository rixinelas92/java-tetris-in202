/**
 * Java doc OK
 */

package tetris;

import java.awt.Color;

/**
 *
 * @author felipeteles
 */
public class Screen {
    static final public short SIZE_X = 10;
    static final public short SIZE_Y = 20;


    private Box[][] grid;

    /**                \/ middle position
     *     ( 0,12) ... (3,12) ... ( 9,12)
     *      ...........................
     *     ( 0, 0) ... ...........( 9, 0)
     */
    static private Position middle;
    /**
     * Default getter of the the parameter <em>middle</em> if it's already exists
     * or setter of this parameter on the central/top of the screen.
     * @return middle with the initial position of generation of boxes. 
     */
    static public Position getMiddlePosition(){
        try {
            if (middle == null) {
                middle = new Position((short) SIZE_X / 2 - 2, (short) SIZE_Y + 1, 0);
            }
            return new Position(middle);
        } catch (OutOfScreenBoundsException ex) {
        }
        return middle;
    }
    /**
     * Creates a new screen with parameters size x and size y defined by default.
     */
    public Screen(){ 
        grid = new Box[SIZE_X][SIZE_Y+3];
        for(int i = 0;i<grid.length;i++)
            for(int j = 0;j<grid[i].length;j++)
                grid[i][j] = new Box();
        
    }
    public void clean(){
        for(int i = 0;i<grid.length;i++)
            for(int j = 0;j<grid[i].length;j++)
                grid[i][j] = new Box();
    }
    public void printScreen(){
        for(int j = 0;j<grid[0].length;j++, System.out.println())
        for(int i = 0;i<grid.length;i++)
                System.out.print(grid[i][j].isFull()?'#':' ');
    }
    /**
     * Creates a clone of a previous screen.
     * @param s defines de original screen.
     */
    public Screen(Screen s){
        grid = new Box[SIZE_X][SIZE_Y+3];
        for(int i = 0;i<SIZE_X;i++){
            for(int j = 0;j<SIZE_Y+3;j++){
                grid[i][j] = new Box(s.grid[i][j]);
            }
        }
    }
    /**
     * Creates a box in the coordinate x and y with the parameter <em>color</em>
     * setted.
     * @param x defines the parameter <em>x</em> of the table of box.
     * @param y defines the parameter <em>y</em> of the table of box.
     * @param color defines the color wanted to the current box.
     */
    public void joinPiece(int x, int y, Color color){
        grid[x][y].setFull(true);
        grid[x][y].setColor(color);
    }
    /**
     * Returns -1 if no line were affected and i >= 0 if the i-nary line
     * were affected. (starting from 0).
     * @return number of the line affected.
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
     * Removes the lines that are filled.
     * @param line informs the line to be remove.
     */
    synchronized public void removeLine(int line){
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
    }


    /**
     * Default getter of active boxes.
     * @param x defines the parameter <em>x</em> of the table of box.
     * @param y defines the parameter <em>y</em> of the table of box.
     * @return the box correspondent of the table.
     */
    public Box getBoxAt(short x,short y){
        if(x > SIZE_X  || x < 0)
            return null;
        if(y > SIZE_Y  || y < 0)
            return null;
        return grid[x][y];
    }
    /**
     * Inner class BorderRetriver
     */
    public class BorderRetriever{
        /**
         * Returns the with of the screen, defined by default.
         * @return the value of the with.
         */
        public short getMaxX(){
            return SIZE_X;
        }
        /**
         * Returns the length of the screen, defined by default.
         * @return the value of the length.
         */
        public short getMaxY(){
            return SIZE_Y;
        }
    }
    /**
     * Inner class FilledRetriever
     */
    public class FilledRetriever{
        /**
         * Returns informations about limits of screen. If the coordinates is out of
         * boards, returns true (is full with static boxes) or update the status
         * of the current box to true (full).
         * @param x defines the parameter <em>x</em> of the table of box.
         * @param y defines the parameter <em>y</em> of the table of box.
         * @return true if it is static or return true to the current box.
         */
        public boolean isFilledWithStaticBloc(int x, int y){
            if(x >= grid.length || x < 0)
                return true;
            if(y >= grid[x].length || y < 0)
                return true;
            return grid[x][y].isFull();
        }
    }
    /**
     * Method in according to Java standard
     */
    static public class OutOfScreenBoundsException extends Exception{
    }
    /**
     * Inner class NotAvailablePlaceForPieceException
     */
    static public class NotAvailablePlaceForPieceException extends Exception{
        /**
         * informs the occurence of conflicts among piece and static parts.
         * @param error informs the status.
         */
        public NotAvailablePlaceForPieceException(String error){
            super(error);
        }
    }
}