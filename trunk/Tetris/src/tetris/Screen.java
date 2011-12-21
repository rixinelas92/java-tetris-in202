

package tetris;

/**
 *
 * @author felipeteles
 */
public class Screen {
    static final private short sizeX = 10;
    static final private short sizeY = 30;
    private Box[][] grid;

    public Screen(){ //define o tamanho fixo da tela
        grid = new Box[sizeX][sizeY];
    }
    public Screen(Screen s){
        grid = new Box[sizeX][sizeY];
        for(int i = 0;i<sizeX;i++){
            for(int j = 0;j<sizeY;j++){
                grid[i][j] = new Box(s.grid[i][j]);
            }
        }
    }

    /**
     * Retorns -1 if no line were affected and i >= 0 if the i-ary line
     * were afected. (starting from 0);
     * @return number of line affected
     */
    public short checkLine(){
        for(int i = 0;i<sizeY;i++){
            int j;
            for(j = 0;j<sizeX;j++){
                if(!grid[i][j].isFull())
                    break;
            }
            if(j == sizeX) // all lines filled
                return (short)i;
        }
        return -1;
    }

    public boolean checkColisionBord(){
        return false;
    }

    public boolean checkColisionBase(){
        return false;
    }

    public void clearPosition(){

    }//retorna o box correspondente ao original

    public Box getBoxAt(short x,short y){
        if(x > sizeX  || x < 0)
            return null;
        if(y > sizeX  || y < 0)
            return null;
        return grid[x][y];
    }
    
    public class BorderRetriever{
        public short getMaxX(){
            return sizeX;
        }
        public short getMaxY(){
            return sizeY;
        }
    }
    static public class OutOfScreenBoundsException extends Exception{

    }
}