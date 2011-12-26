/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import tetris.Screen.BorderRetriever;
import tetris.Screen.NotAvailablePlaceForPieceException;
import tetris.Screen.OutOfScreenBoundsException;
import tetris.util.RandomEnum;

/**
 *
 * @author felipeteles
 */
public class Game extends Controller implements ActionListener{
    private Screen screen;
    private Piece currentPiece;
    private Piece nextPiece;

    private RandomEnum<Piece.ShapeType> randomShape = new RandomEnum<Piece.ShapeType>(Piece.ShapeType.class);


    Timer timer;
    int timeBeforeNextPiece = 400;
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
        Position.setFilledRetriever(screen.new FilledRetriever());
        try {
            currentPiece = new Piece(randomShape.randomExceptLast(), (short) 1, Screen.getMiddlePosition());
            nextPiece = new Piece(randomShape.randomExceptLast(), (short) 1, Screen.getMiddlePosition());
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer = new Timer(timeBeforeNextPiece,this);
        timer.start();
    }

    public Position[] getCurrentPiecePositions(){
        return currentPiece.getAllPosition();
    }

    public Position[] getNextPiecePositions(){
        return nextPiece.getAllPosition();
    }

    public String getCurrentPieceColorName(){
        return currentPiece.getColorName();
    }
    public String getNextPieceColorName(){
        return nextPiece.getColorName();
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
                nextPiece = new Piece(randomShape.randomExceptLast(), (short) 1, Screen.getMiddlePosition());
                Main.setNewPiece();
            } catch (OutOfScreenBoundsException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                currentPiece.setY((short) (currentPiece.getY()-1));
            } catch (OutOfScreenBoundsException ex) {
                ex.printStackTrace();
                isFallingFinished = true;
            } catch (NotAvailablePlaceForPieceException ex) {
                ex.printStackTrace();
                isFallingFinished = true;
            }
            if(isFallingFinished){
                Position[] all = currentPiece.getAllPosition();
                for(Position p: all){
                    screen.getBoxAt(p.getX(), p.getY()).setFull(true);
                    screen.getBoxAt(p.getX(), p.getY()).setColor(currentPiece.getColor());
                }
            }
            int numLinesFull = 0;
            int lineC;
            while(true){
                lineC = screen.checkLine();
                if(lineC == -1)
                    break;
                screen.removeLine(lineC);
                numLinesFull++;
            }          
            Main.updatePiecesPositions();
            System.out.println("HEY! WE HAVE "+numLinesFull+" LINES!");
        }
    }

    @Override
    protected void goToX() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    

}