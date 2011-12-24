

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

    static private Position middle;

    static public Position getMiddlePosition(){
        if(middle != null)
            return middle;
        try {
            middle = new Position((short) SIZE_X/2 - 2, (short) SIZE_Y-1);
            System.out.println(" >>"+middle.getX()+" ; "+middle.getY());
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return middle;
    }
    public Screen(){ //define o tamanho fixo da tela
        grid = new Box[SIZE_X][SIZE_Y];
        for(int i = 0;i<grid.length;i++)
            for(int j = 0;j<grid[i].length;j++)
                grid[i][j] = new Box();
        
    }
    public Screen(Screen s){
        grid = new Box[SIZE_X][SIZE_Y];
        for(int i = 0;i<SIZE_X;i++){
            for(int j = 0;j<SIZE_Y;j++){
                grid[i][j] = new Box(s.grid[i][j]);
            }
        }
    }

    public void joinPiece(int x, int y, Color color){
        grid[x][y].setFull(true);
        grid[x][y].setColor(color);
    }
    /**
     * Retorns -1 if no line were affected and i >= 0 if the i-ary line
     * were afected. (starting from 0);
     * @return number of line affected
     */
    public short checkLine(){
        for(int i = 0;i<SIZE_Y;i++){
            int j;
            for(j = 0;j<SIZE_X;j++){
                if(!grid[j][i].isFull())
                    break;
            }
            if(j == SIZE_X) // all lines filled
                return (short)i;
        }
        return -1;
    }
    /**
     * Remove Line
     * @return
     */
    public void removeLine(int line){
        for(int i = line+1;i<SIZE_Y;i++){
            int j;
            for(j = 0;j<SIZE_X;j++){
                if(grid[i][j].isFull())
                    break;
            }
            if(j == SIZE_X) // none line is filled
                break;

            for(j = 0;j<SIZE_X;j++){
                grid[i-1][j].copyFrom(grid[i][j]);
            }
        }
        for(int j = 0;j<SIZE_X;j++)
            grid[SIZE_Y-1][j].setFull(false);
    }

    public boolean checkColisionBord(){
        return false;
    }

    public boolean checkColisionBase(){
        return false;
    }

    public void clearPosition(){

    }

    public Box getBoxAt(short x,short y){
        if(x > SIZE_X  || x < 0)
            return null;
        if(y > SIZE_X  || y < 0)
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