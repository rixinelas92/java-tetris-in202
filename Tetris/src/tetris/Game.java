/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import tetris.Screen.BorderRetriever;
import tetris.Screen.OutOfScreenBoundsException;

/**
 *
 * @author felipeteles
 */
public class Game implements Controller,ActionListener{
    private Screen screen;
    private Piece currentPiece;
    private Piece nextPiece;

    private RandomEnum<Piece.ShapeType> randomShape;


    Timer timer;
    int timeBeforeNextPiece;
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

    public void initGame(){
        screen = new Screen();
        Position.setBorderRetriever(screen.new BorderRetriever());
        try {
            currentPiece = new Piece(randomShape.random(), (short) 1, new Position(1, 1));
            nextPiece = new Piece(randomShape.random(), (short) 1, new Position(1, 1));
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer = new Timer(timeBeforeNextPiece,this);
        timer.start();
    }

    public Position[] getCurrentPiecePositions(){
        return currentPiece.getAllPosition();
    }
    public void rotate() {
        currentPiece.rotation();
    }

    public void goToBottom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void goLeft() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void goRight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actionPerformed(ActionEvent ae) {
        if(isFallingFinished){
            try {
                isFallingFinished = false;
                currentPiece = nextPiece;
                nextPiece = new Piece(randomShape.random(), (short) 1, new Position(1, 1));
            } catch (OutOfScreenBoundsException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            int colision = 0;
            int lineC;
            while(true){
                lineC = screen.checkLine();
                if(lineC == -1)
                    break;
                screen.removeLine(lineC);
                colision++;
            }
            System.out.println("HEY! WE HAVE "+colision+" LINES!");
        }
    }

    private static class RandomEnum<E extends Enum> {

        private static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }
        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
    

}