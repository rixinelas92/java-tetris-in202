

package tetris;

/**
 *
 * @author felipeteles
 */
public class Screen {
    private short sizeX;
    private short sizeY;
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

    public class BorderRetriever{
        public short getMaxX(){
            return sizeX;
        }
        public short getMaxY(){
            return sizeY;
        }
    }
}