package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import sound.SoundEffectWrapper;
import sound.SoundManager;
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
    private Piece currentPiece, nextPiece, holdPiece, shadowPiece;
    private RandomEnum<Piece.ShapeType> randomShape = new RandomEnum<Piece.ShapeType>(Piece.ShapeType.class);
    Timer timer = null;
    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    boolean isFinished = false;
    boolean alreadyHolded = false;
    int points;
    int level;
    int pieceSize = 19;
    SoundEffectWrapper fallSom, eraseSom, gameoverSom, pauseSom;

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
        Main.getInstance().restart1pScreen();
        Position.setBorderRetriever(screen.new BorderRetriever());
        Position.setFilledRetriever(screen.new FilledRetriever());
        try {
            Piece.ShapeType cs = randomShape.randomExceptLast();
            Piece.ShapeType ns = randomShape.randomExceptLast();
            nextPiece = new Piece(cs, Screen.getMiddlePosition());
            Main.getInstance().setNewFirstPiece();
            currentPiece = nextPiece;
            nextPiece = new Piece(ns, Screen.getMiddlePosition());
            shadowPiece = new Piece(currentPiece.getShapeType(), currentPiece.getPosition());
            shadowPiece.setRotation(currentPiece.getRotation());


            Main.getInstance().setNewPiece();
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        holdPiece = null;
        level = Main.getInstance().getInitialLevelMain();
        points = pointsToLevel(level);
        Main.getInstance().setListeners(this);
        int pointsToNextLevel = pointsToLevel(level+1);
        Main.getInstance().setPointsAndLevel(points, level, points, pointsToNextLevel);
        if (timer == null) {
            timer = new Timer(timeBefore(level), this);
        }
        timer.setDelay(timeBefore(level));
        isPaused = false;
        isFinished = false;
        isFallingFinished = false;
        alreadyHolded = false;
        timer.start();
        isStarted = true;
    }
    /**
     * Default getter of the positons of the piece.
     * @return the positions.
     */
    public Position[] getCurrentPiecePositions() {
        return currentPiece.getAllPosition();
    }
    /**
     * Default getter of the positons of the shadow piece.
     * @return the positions of the shadow piece.
     */
    public Position[] getShadowPiecePositions() {
        try {
            shadowPiece.setShape(currentPiece.getShapeType());
            shadowPiece.setPosition(currentPiece.getPosition());
            shadowPiece.setRotation(currentPiece.getRotation());
        } catch (Exception ev) {
            //always is possible
        }
        goToBottomShadow();
        return shadowPiece.getAllPosition();
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
            for (int i = 0; i < 2; i++) {
                try {
                    short x = currentPiece.getX();
                    x -= i + 1;
                    currentPiece.setXandRotate(x);
                    break;
                } catch (Exception ex1) {
                }
                try {
                    short x = currentPiece.getX();
                    x += i + 1;
                    currentPiece.setXandRotate(x);
                    break;
                } catch (Exception ex1) {
                }
            }
        }
    }
    /**
     * Manages the actions introduced by the mouse.
     * @param e informes the event of click.
     */
    public void mouseClicked(MouseEvent e) {
        if (isPaused) {
            return;
        }
        if (isFallingFinished) {
            return;
        }
        super.mouseClicked(e);
    }
    /**
     * Manages the actions introduced by the mouse.
     * @param e informes that the mouse was mouved.
     */
    public void mouseMoved(MouseEvent e) {
        if (isPaused) {
            return;
        }
        if (isFallingFinished) {
            return;
        }
        super.mouseMoved(e);
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
     * Executes the direct downward movement showing, with the shadow, where the
     * piece will be fixed.
     */
    public void goToBottomShadow() {
        while (true) {
            try {
                goDownShadow();
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
     * Executes the downward movement in steps, decrementing the coordinate y 
     * and refreshing the new position of the shadow.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen is not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void goDownShadow() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        int x = shadowPiece.getX();
        int y = shadowPiece.getY();
        y--;
        shadowPiece.setPosition(new Position(x, y));
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
        Main.getInstance().togglePause();
    }
    /**
     * Stops the execution of the inversor.
     */
    public void stopToggleVariable() {
        isPaused = !isPaused;
        if (pauseSom != null) {
            pauseSom.play();
        }
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
    /**
     * 
     * @param newX updates the value of x.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen is not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    synchronized protected void goToX(int newX) throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        int x = -2 + newX / pieceSize;
        while (true) {
            Position p = currentPiece.getPosition();
            if(x == p.getX())
                break;
            try {
                if(x  < p.getX()){
                    goLeft();
                }else{
                    goRight();
                }
            } catch (Exception e) {
                break;
            }
        }
    }
    /**
     * Implements the listeners of the game.
     * @param ae informes the action to be executed.
     */
    public void actionPerformed(ActionEvent ae) {
        if (isPaused) {
            return;
        }
        if (isFallingFinished) {
            try {
                alreadyHolded = false;
                isFallingFinished = false;
                currentPiece = nextPiece;
                short xx = currentPiece.getX();
                short yy = currentPiece.getY();
                currentPiece.setX(xx);
                currentPiece.setY(yy);
                nextPiece = new Piece(randomShape.randomExceptLast(), Screen.getMiddlePosition());
                shadowPiece.setPosition(currentPiece.getPosition());
                shadowPiece.setRotation(currentPiece.getRotation());
                shadowPiece.setShape(currentPiece.getShapeType());
                Main.getInstance().setNewPiece();

            } catch (NotAvailablePlaceForPieceException ex) {
                Main.getInstance().pauseGame();
                Main.getInstance().showGameOverAndReturnToNewGame();
                if (gameoverSom != null) {
                    gameoverSom.play();
                }
                return;
            } catch (OutOfScreenBoundsException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                currentPiece.setY((short) (currentPiece.getY() - 1));
            } catch (OutOfScreenBoundsException ex) {
                isFallingFinished = true;
                if (fallSom != null) {
                    fallSom.play();
                }
            } catch (NotAvailablePlaceForPieceException ex) {
                isFallingFinished = true;
                if (fallSom != null) {
                    fallSom.play();
                }
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
                Main.getInstance().pauseGame();
                Main.getInstance().showGameOverAndReturnToNewGame();
                if (gameoverSom != null) {
                    gameoverSom.play();
                }
                isStarted = false;
                return;
            }
            Main.getInstance().updatePiecesPositions();
            int numLinesFull = 0;
            int lineC;
            while (true) {
                lineC = screen.checkLine();
                if (lineC == -1) {
                    break;
                }
                screen.removeLine(lineC);
                Main.getInstance().callScreenRemoveLine(lineC);
                numLinesFull++;
                Main.getInstance().sendGamePoint();
            }
            if (numLinesFull != 0 && eraseSom != null) {
                    eraseSom.play();
            }
            int p = ponctuation(numLinesFull);
            points += p;
            if (points > pointsToLevel(level + 1)) {
                level++;
            }
            int pointsToThisLevel = pointsToLevel(level);
            int pointsToNextLevel = pointsToLevel(level + 1);
            timer.setDelay(timeBefore(level));
            Main.getInstance().setPointsAndLevel(points, level, pointsToThisLevel, pointsToNextLevel);
        }
    }
    /**
     * It holds a piece when required by the user.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen is not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void hold() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException {
        Piece auxiliar = new Piece(randomShape.randomExceptLast(), Screen.getMiddlePosition());
        if (holdPiece != null) {
            if (!alreadyHolded) {
                try {
                    Position[] aux = holdPiece.getAllPosition();
                    boolean flag = true;

                    for(int i = 0;i<4;i++){
                        if(screen.getBoxAt(aux[i].getX(), aux[i].getY()) != null)
                            if(flag && screen.getBoxAt(aux[i].getX(), aux[i].getY()).isFull())
                                flag = false;
                    }


                    if(flag){
                        alreadyHolded = true;
                 /*   if (!screen.getBoxAt(aux[0].getX(), aux[0].getY()).isFull() &&

                            !screen.getBoxAt(aux[1].getX(), aux[1].getY()).isFull() &&
                            !screen.getBoxAt(aux[2].getX(), aux[2].getY()).isFull() &&
                            !screen.getBoxAt(aux[3].getX(), aux[3].getY()).isFull()) {
                    */
                        auxiliar.setRotation(currentPiece.getRotation());
                        auxiliar.setShape(currentPiece.getShapeType());
                        currentPiece.setRotation(holdPiece.getRotation());
                        currentPiece.setShape(holdPiece.getShapeType());
                        currentPiece.setPosition(Screen.getMiddlePosition());
                        holdPiece.setRotation(auxiliar.getRotation());
                        holdPiece.setShape(auxiliar.getShapeType());

                        Main.getInstance().setHold();
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
                Main.getInstance().setFirstHold();
                shadowPiece.setPosition(currentPiece.getPosition());
                shadowPiece.setRotation(currentPiece.getRotation());
                shadowPiece.setShape(currentPiece.getShapeType());

                Main.getInstance().setNewPiece();
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
    /**
     * Implements the pause of the game.
     */
    void pauseGame() {
        isPaused = true;
    }
    /**
     * It returns informations compacted (masked) to be send in the network. 
     * @return the result of the simplification.
     */
    public int[] getGameMask() {
        if (!isStarted) {
            return new int[0];
        }
        int[] mask = new int[Screen.SIZE_X];
        for (int i = 0; i < Screen.SIZE_X; i++) {
            for (int j = 0; j < Screen.SIZE_Y; j++) {
                try {
                    mask[i] |= (screen.getBoxAt((short) i, (short) j).isFull()) ? (1 << j) : 0;
                } catch (Exception e) {
                }
            }
        }
        Position[] pos = currentPiece.getAllPosition();
        for (Position p : pos) {
            mask[p.getX()] |= (1 << p.getY());
        }
        return mask;
    }
    /**
     * Builds the game from the mask passed.
     * @param mask defines the description of the mask.
     * @return the result of the building.
     */
    static public boolean[][] getGameDescWithMask(Integer[] mask) {
        boolean[][] desc = new boolean[Screen.SIZE_X][Screen.SIZE_Y];
        for (int i = 0; i < Screen.SIZE_X; i++) {
            for (int j = 0; j < Screen.SIZE_Y; j++) {
                desc[i][j] = (mask[i] & (1 << j)) > 0;
            }
        }
        return desc;
    }
    /**
     * Implements the punition when the opponent makes points.
     */
    void punnition() {
        goToBottom();
    }
    /**
     * Class to implement the functions listener.
     */
    public class GameViewReadyListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            initGame();
        }
    }
    /**
     * Class to apply the sound effects.
     */
    public void setSomTheme(int somTheme) {

        switch(somTheme){
            case 0:
                SoundManager.setTheme(SoundManager.soundTheme.CLASSIC);
                break;
            case 1:
                SoundManager.setTheme(SoundManager.soundTheme.MARIO);
                break;
            case 2:
                SoundManager.setTheme(SoundManager.soundTheme.PACMAN);
                break;
            case 3:
                SoundManager.setTheme(SoundManager.soundTheme.STARWARS);
                break;

        }
        if (somTheme == 0) {
            fallSom = SoundEffectWrapper.CFALL;
            eraseSom = SoundEffectWrapper.CERASE;
            gameoverSom = null;
            pauseSom = null;
        }
        if (somTheme == 1) {
            fallSom = SoundEffectWrapper.MFALL;
            eraseSom = SoundEffectWrapper.MERASE;
            gameoverSom = SoundEffectWrapper.MGAMEOVER;
            pauseSom = SoundEffectWrapper.MPAUSE;
        }
        if (somTheme == 2) {
            fallSom = SoundEffectWrapper.PFALL;
            eraseSom = SoundEffectWrapper.PERASE;
            gameoverSom = SoundEffectWrapper.PGAMEOVER;
            pauseSom = SoundEffectWrapper.PPAUSE;
        }
        if (somTheme == 3) {
            fallSom = SoundEffectWrapper.SFALL;
            eraseSom = SoundEffectWrapper.SERASE;
            gameoverSom = null;
            pauseSom = null;
        }
        if (somTheme == 4) {
            fallSom = null;
            eraseSom = null;
            gameoverSom = null;
            pauseSom = null;
        }        
    }
}
