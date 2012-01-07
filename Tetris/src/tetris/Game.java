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
    boolean isFinished = false;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;
    int points;
    int level;



    public int timeBefore(int level){
        return (13-level)*(500/12);
    }

            /*
             *  * une ligne qui disparaît rapporte 40 points,
             *  * 2 lignes qui sont supprimées rapportent 100 points
             *  * 3 lignes qui sont supprimées rapportent 300 points
             *  * 4 lignes (on ne peut pas plus) rapportent 1200 points.
             */
    public int ponctuation(int numL){
        switch(numL){
            case 1: return 40*level;
            case 2: return 100*level;
            case 3: return 300*level;
            case 4: return 1200*level;
            default: return 0*level;
        }
    }

    public int pointsToLevel(int level){

        return level*level*80 ;
    }
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
            Piece.ShapeType cs = randomShape.randomExceptLast();
            Piece.ShapeType ns = randomShape.randomExceptLast();
            nextPiece = new Piece(cs, Screen.getMiddlePosition());
            Main.setNewFirstPiece();
            currentPiece = nextPiece;
            
            nextPiece = new Piece(ns, Screen.getMiddlePosition());
            Main.setNewPiece();
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        level = 1;
        points = 0;
        Main.setListeners(this);
        int pointsToNextLevel = pointsToLevel(level+1);
        Main.setPointsAndLevel(points, level, pointsToNextLevel);
        timer = new Timer(timeBefore(level),this);
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
        try {
            currentPiece.rotation();
        } catch (NotAvailablePlaceForPieceException ex) {
            System.out.println("Cant Rotate");
        }
    }

    public void goToBottom() {
        while(true){
            try{
                goDown();
            }catch(Exception e){
                break;
            }
        }
    }

    public void goDown() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException{
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        y--;
        currentPiece.setPosition(new Position(x,y));
    }
    public void goLeft() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException{
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        x--;
        currentPiece.setPosition(new Position(x,y));
    }

    public void goRight() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException{
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        x++;
        currentPiece.setPosition(new Position(x,y));
    }

    public void stopToggle() {
        isPaused = !isPaused;
    }

    public void actionPerformed(ActionEvent ae) {
        if(isPaused){
            return;
        }
        if(isFallingFinished){
            try {
                isFallingFinished = false;
                currentPiece = nextPiece;
                nextPiece = new Piece(randomShape.randomExceptLast(), Screen.getMiddlePosition());
                Main.setNewPiece();
            } catch (OutOfScreenBoundsException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                currentPiece.setY((short) (currentPiece.getY()-1));
            } catch (OutOfScreenBoundsException ex) {
                System.out.println("Cant Floor");
                isFallingFinished = true;
            } catch (NotAvailablePlaceForPieceException ex) {
                System.out.println("Cant Piece");
                isFallingFinished = true;
            }
            if(isFallingFinished){
                Position[] all = currentPiece.getAllPosition();
                for(Position p: all){
                    Box b = screen.getBoxAt(p.getX(), p.getY());
                    if(b == null){
                        isFinished = true;
                        continue;
                    }
                    b.setFull(true);
                    b.setColor(currentPiece.getColor());
                }
            }
            if(isFinished){
                System.out.println("ACABOU!!!");
                timer.stop();
                return;

            }
            Main.updatePiecesPositions();
            int numLinesFull = 0;
            int lineC;
            while(true){
                lineC = screen.checkLine();
                if(lineC == -1)
                    break;
                screen.removeLine(lineC);
                Main.callScreenRemoveLine(lineC);
                numLinesFull++;
            }
            int p = ponctuation(numLinesFull);
            points+=p;
            if(points>pointsToLevel(level+1)){
                level++;
            }
            int pointsToNextLevel = pointsToLevel(level+1);
            timer.setDelay(timeBefore(level));
            Main.setPointsAndLevel(points, level, pointsToNextLevel);


        }

    }

    @Override
    protected void goToX() {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }


    public class GameViewReadyListener implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            initGame();
        }
    }
    

}