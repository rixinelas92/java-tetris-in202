/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Shape;
import java.util.Vector;
/**
 *
 * @author felipeteles
 */
public class Piece {
    private short[][][][] shape;//lista de peças, rotaçoes,cada quadrado,coordenadas
    private short currentShape,currentRotation,currentSquare,currentAxis;//indices da tabela aciam
    private Color color;
    private Position position;

    public Piece(){

    }

    public void setPosition(Position newPos){
    }
    private void setShape(Shape newShape){
    }
    public Color getColor(Color newColor){
        return new Color();
    }
    public Position getPosition(){
        return position;
    }
    public Vector<Position> getAllPosition(){
        return null;
    }
    public void rotation(){

    }
    
}

