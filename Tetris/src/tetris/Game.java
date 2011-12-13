/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author felipeteles
 */
public class Game implements Controller{
    private Screen screen;
    private Piece currentPiece;
    private Controller controls;


    Timer timer;
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;

    boolean isFilled(short x, short y){
        Position[] piece = currentPiece.getAllPosition();
        for(Position p: piece){
            if(p.getX() == x && p.getY() == y)
                return true;
        }
        Box box = screen.getBoxAt(x, y);
        if(box == null)
            return true;
        if(box.isFull())
           return true; 
        return false;
    }

    Color getColor(short x, short y){
        Position[] piece = currentPiece.getAllPosition();
        for(Position p: piece){
            if(p.getX() == x && p.getY() == y)
                return currentPiece.getColor();
        }
        Box box = screen.getBoxAt(x, y);
        if(box == null)
            return Box.getEmptyColor();
        if(box.isFull())
           return box.getColor();
        return Box.getEmptyColor();
    }

    public void rotate() {
        Position[] piece = currentPiece.getAllPosition();
        for(Position p: piece){
            for (int i = 0; i < 4; ++i) {
          //  currentPiece.setX(i, y(i));
          //  currentPiece.setY(i, -x(i));
        }
      //  return currentPiece;
                   
        }
    }

    public void goToBottom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void goLeft() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void goLight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    

}


/*
   public Shape rotateLeft()
    {
        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

*/