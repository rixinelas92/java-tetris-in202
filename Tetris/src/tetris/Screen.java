

package tetris;

/**
 *
 * @author felipeteles
 */
public class Screen {
    private short sizeX = 10;
    private short sizeY = 30;
    private Box[][] screen;

    public Screen(){ //define o tamanho fixo da tela
    }

    public short checkLine(){
        short n_line = 0;
        return n_line;
    }//verifica linhas cheias, apaga e retorna numero de linhas feitas

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
        return screen[x][y];
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