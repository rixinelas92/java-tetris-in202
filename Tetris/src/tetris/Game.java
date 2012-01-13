/*
 * Java doc OK, mas função em constantte modificação.
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
import sound.SoundEffect;
import tetris.Screen.BorderRetriever;
import tetris.Screen.NotAvailablePlaceForPieceException;
import tetris.Screen.OutOfScreenBoundsException;
import tetris.util.RandomEnum;

/**
 *
 * @author felipeteles
 */
public class Game extends Controller implements ActionListener {

    private Screen screen;
    private Piece currentPiece;
    private Piece nextPiece;
    private Piece holdPiece;
    private RandomEnum<Piece.ShapeType> randomShape = new RandomEnum<Piece.ShapeType>(Piece.ShapeType.class);
    Timer timer = null;
    int timeBeforeNextPiece = 400;
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    boolean isFinished = false;
    boolean alreadyHolded = false;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;
    int points;
    int level;
    int pieceSize = 19;

    /**
     * It determines the dead line to the game in according to the level.
     * @param level informes the actual level of the game.
     * @return the dead line of the game.
     */
    public int timeBefore(int level) {
        if (level > 12) {
            level = 12;
        }
        return 100 + (13 - level) * (400 / 12);
    }

    /*
     *  * une ligne qui disparaît rapporte 40 points,
     *  * 2 lignes qui sont supprimées rapportent 100 points
     *  * 3 lignes qui sont supprimées rapportent 300 points
     *  * 4 lignes (on ne peut pas plus) rapportent 1200 points.
     */
    /**
     * It determines the game score in according with the level of the game and
     * the number of the lines that were completed.
     * @param numL informes the number of lines completed.
     * @return the partial score to be added to the total value. 
     */
    public int ponctuation(int numL) {
        switch (numL) {
            case 1:
                return 40 * level;
            case 2:
                return 100 * level;
            case 3:
                return 300 * level;
            case 4:
                return 1200 * level;
            default:
                return 0 * level;
        }
    }

    /**
     * The score that are obtained by passing the level.
     * @param level informes the actual level of the game.
     * @return the partial score to be added to the total value. 
     */
    public int pointsToLevel(int level) {
        return level * level * 160;
    }

    /**
     * Verifies if in the position x and y already occupied or not.   
     * @param x defines the coordinate x of the position. 
     * @param y defines the coordinate y of the position.
     * @return true if the coordinate x and y analyzed is already occupied, or
     * false if it is empty.
     */
    boolean isFilled(short x, short y) {
        Position[] piece = currentPiece.getAllPosition();
        for (Position p : piece) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }
        Box box = screen.getBoxAt(x, y);
        if (box == null) {
            return true;
        }
        if (box.isFull()) {
            return true;
        }
        return false;
    }

    /**
     * Default getter of the color and constructor of the boxes in the screen.
     * @param x informes the coodinate x of the piece.
     * @param y informes the coodinates y of the piece.
     * @return the consistent construction of the box in the screen.
     */
    Color getColor(short x, short y) {
        Position[] piece = currentPiece.getAllPosition();
        for (Position p : piece) {
            if (p.getX() == x && p.getY() == y) {
                return currentPiece.getColor();
            }
        }
        Box box = screen.getBoxAt(x, y);
        if (box == null) {
            return Box.getEmptyColor();
        }
        if (box.isFull()) {
            return box.getColor();
        }
        return Box.getEmptyColor();
    }

    /**
     * This class initialize the game. Giving the basic default configurations
     * of the user screen and determining the genation of new piece.   
     */
    public void initGame() {
        if (timer != null) {
            timer.stop();
        }
        if (screen == null) {
            screen = new Screen();
        } else {
            screen.clean();
        }
        nextPiece = currentPiece = null;
        Main.restart1pScreen();
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
        holdPiece = null;
        level = 1;
        points = 0;
        Main.setListeners(this);
        int pointsToNextLevel = pointsToLevel(level + 1);
        Main.setPointsAndLevel(points, level, pointsToNextLevel);
        if (timer == null) {
            timer = new Timer(timeBefore(level), this);
        }
        timer.setDelay(timeBefore(level));
        isPaused = false;
        isFinished = false;
        isFallingFinished = false;
        alreadyHolded = false;
        timer.start();
    }

    /**
     * Default getter of the positons of the piece.
     * @return the positions.
     */
    public Position[] getCurrentPiecePositions() {
        return currentPiece.getAllPosition();
    }

    /**
     * Default getter of the positions of the next piece.
     * @return the positions.
     */
    public Position[] getNextPiecePositions() {
        return nextPiece.getAllPosition();
    }

    /**
     * Default getter of the positions of the hold piece.
     * @return the positions.
     */
    public Position[] getHoldPiecePositions() {
        return holdPiece.getAllPosition();
    }

    /**
     * Default getter of the parameter color of the current piece. 
     * @return a string with the name. 
     */
    public String getCurrentPieceColorName() {
        return currentPiece.getColorName();
    }

    /**
     * Default getter of the parameter color of the next piece. 
     * @return a string with the name. 
     */
    public String getNextPieceColorName() {
        return nextPiece.getColorName();
    }

    /**
     * Executes rotation of the piece if it is possible.
     */
    public void rotate() {
        try {
            currentPiece.rotation();
        } catch (NotAvailablePlaceForPieceException ex) {
            System.out.println("Cant Rotate");
        }
    }

    /**
     * Executes the direct downward movement when it is possible.
     */
    public void goToBottom() {
        while (true) {
            try {
                goDown();
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * Executes the downward movement in steps, decrementing the coordinate y 
     * and refreshing the new position.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen is not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void goDown() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        y--;
        currentPiece.setPosition(new Position(x, y));
    }

    /**
     * Executes the movement to the left in steps, decrementing the coordinate x 
     * and refreshing the new position.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen is not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void goLeft() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        x--;
        currentPiece.setPosition(new Position(x, y));
    }

    /**
     * Executes the movement to the right in steps, decrementing the coordinate x 
     * and refreshing the new position.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen is not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void goRight() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        x++;
        currentPiece.setPosition(new Position(x, y));
    }

    /**
     * Inversor of condition.
     */
    public void stopToggle() {
        Main.togglePause();
    }

    public void stopToggleVariable() {
        isPaused = !isPaused;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isPaused && keyPause != e.getKeyCode()) {
            return;
        }
        if (isFallingFinished && keyPause != e.getKeyCode()) {
            return;
        }
        super.keyPressed(e);
    }

    synchronized protected void goToX(int newX) throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        if (isFallingFinished) {
            return;
        }
        Position[] vector = currentPiece.getAllPosition();
        int x = -2 + newX / pieceSize;
        //int x = -((Position.getMaxCoord(vector).getX() +Position.getMinCoord(vector).getX())/2) + newX/pieceSize;
        System.out.println(Position.getMaxCoord(vector).getX() + " " + Position.getMinCoord(vector).getX());
        int y = currentPiece.getY();
        try {
            currentPiece.setPosition(new Position(x, y));
        } catch (Exception e) {
            System.out.print("mouse not");
        }

    }

    public void actionPerformed(ActionEvent ae) {
        if (isPaused) {
            return;
        }
        if (isFallingFinished) {
            try {
                isFallingFinished = false;
                currentPiece = nextPiece;
                short xx = currentPiece.getX();
                short yy = currentPiece.getY();
                currentPiece.setX(xx);
                currentPiece.setY(yy);
                nextPiece = new Piece(randomShape.randomExceptLast(), Screen.getMiddlePosition());
                Main.setNewPiece();
                alreadyHolded = false;
            } catch (NotAvailablePlaceForPieceException ex) {
                System.out.println("ACABOU OUTRA VEZ!!!");
                Main.pauseGame();
                Main.showGameOverAndReturnToNewGame();
                return;
            } catch (OutOfScreenBoundsException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                currentPiece.setY((short) (currentPiece.getY() - 1));
            } catch (OutOfScreenBoundsException ex) {
                System.out.println("Cant Floor");
                isFallingFinished = true;
                SoundEffect.FALL.play();
            } catch (NotAvailablePlaceForPieceException ex) {
                System.out.println("Cant Piece");
                isFallingFinished = true;
                SoundEffect.FALL.play();
            }
            if (isFallingFinished) {
                Position[] all = currentPiece.getAllPosition();
                for (Position p : all) {
                    Box b = screen.getBoxAt(p.getX(), p.getY());
                    if (b == null || p.getY() >= Position.borderRetriever.getMaxY()) {
                        isFinished = true;
                        continue;
                    }
                    b.setFull(true);
                    b.setColor(currentPiece.getColor());
                }
            }
            if (isFinished) {
                Main.pauseGame();
                Main.showGameOverAndReturnToNewGame();
                return;
            }
            Main.updatePiecesPositions();
            int numLinesFull = 0;
            int lineC;
            while (true) {
                lineC = screen.checkLine();
                if (lineC == -1) {
                    break;
                }
                screen.removeLine(lineC);
                SoundEffect.ERASE.play();
                Main.callScreenRemoveLine(lineC);
                numLinesFull++;
            }
            int p = ponctuation(numLinesFull);
            points += p;
            if (points > pointsToLevel(level + 1)) {
                level++;
            }
            int pointsToNextLevel = pointsToLevel(level + 1);
            timer.setDelay(timeBefore(level));
            Main.setPointsAndLevel(points, level, pointsToNextLevel);
        }
    }

    public void hold() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        Piece auxiliar = new Piece(randomShape.randomExceptLast(), Screen.getMiddlePosition());
        if (holdPiece != null) {
            if (!alreadyHolded) {
                alreadyHolded = true;
                try {
                    Position[] aux = holdPiece.getAllPosition();
                    if (!screen.getBoxAt(aux[0].getX(), aux[0].getY()).isFull() && !screen.getBoxAt(aux[1].getX(), aux[1].getY()).isFull() && !screen.getBoxAt(aux[2].getX(), aux[2].getY()).isFull() && !screen.getBoxAt(aux[3].getX(), aux[3].getY()).isFull()) {

                        auxiliar.setRotation(currentPiece.getRotation());
                        auxiliar.setShape(currentPiece.getShapeType());
                        currentPiece.setRotation(holdPiece.getRotation());
                        currentPiece.setShape(holdPiece.getShapeType());
                        currentPiece.setPosition(Screen.getMiddlePosition());
                        holdPiece.setRotation(auxiliar.getRotation());
                        holdPiece.setShape(auxiliar.getShapeType());

                        Main.setHold();
                    }
                } catch (Exception en) {
                    en.printStackTrace();
                }
            }
        } else {
            try {
                holdPiece = new Piece(currentPiece.getShapeType(), Screen.getMiddlePosition());
                holdPiece.setRotation(currentPiece.getRotation());
                currentPiece = nextPiece;
                nextPiece = new Piece(randomShape.randomExceptLast(), Screen.getMiddlePosition());
                Main.setFirstHold();
                Main.setNewPiece();
            } catch (Exception en) {
                en.printStackTrace();
            }
        }
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

    void pauseGame() {
        isPaused = true;
    }

    /**
     * Class to implement the fonctions listener.
     */
    public class GameViewReadyListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            initGame();
        }
    }
}
