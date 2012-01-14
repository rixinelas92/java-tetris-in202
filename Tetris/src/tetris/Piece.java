/*
 * Java doc 
 */

package tetris;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.Screen.NotAvailablePlaceForPieceException;
import tetris.Screen.OutOfScreenBoundsException;


/**
 *
 * @author felipeteles
 */
public class Piece {

    private short currentRotation;
    private ShapeType currentShape;
    private Position position;

    static public enum ShapeType{
        Z,S,I,T,O,Li,L,None
    };

    public static final Color[]  shapeColors = {Color.PINK, Color.MAGENTA, Color.BLUE, Color.CYAN,Color.YELLOW,Color.GREEN,Color.ORANGE,Box.getEmptyColor()};
    public static final String[] shapeCNames = {"Purple",   "Red",         "Blue",     "Ciano",   "Yellow",    "Green",    "Brown",     "close"};
    /**
     * Creates a new piece with the parameters <em>currentRotation</em> <em>currentShape</em> 
     * <em>position</em> setted.
     * @param s informes the type of the piece.
     * @param p informes the position of the piece that will be created.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen are not respected and the piece cannot be created. 
     */
    public Piece(ShapeType s, Position p) throws OutOfScreenBoundsException{
        currentRotation = 1;
        currentShape = s;
        position = new Position(p);
    }
    /**
     * Default getter of the coordinate y of the position selected.
     * @return the coordinate y.
     */
    public short getY(){
        return position.getY();
    }
    /**
     * Default getter of the coordinate x of the position selected.
     * @return the coordinate x.
     */
    public short getX(){
        return position.getX();
    }
    /**
     * Default setter of the coordenate y if the operation is possible.
     * @param newY defines the new coordinate y.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen are not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void setY(short newY) throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException{
        int tyleN = currentShape.ordinal();
        int x,y;
        for(int i = 0;i<4;i++){
            x = position.getX() + shape[tyleN][currentRotation][i][0];
            y = newY - shape[tyleN][currentRotation][i][1];
            if(Position.isFilled(x, y))
                throw new Screen.NotAvailablePlaceForPieceException("("+x+","+y+") -- "+newY +";"+shape[tyleN][currentRotation][i][1]);
        }
        position.setY(newY);
    }
    /**
     * Default setter of the coordenate x if the operation is possible.
     * @param newX defines the new coordinate x.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen are not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    
    public void setX(short newX) throws NotAvailablePlaceForPieceException, OutOfScreenBoundsException{
        int tyleN = currentShape.ordinal();
        int x,y;
        for(int i = 0;i<4;i++){
            x = newX + shape[tyleN][currentRotation][i][0];
            y = position.getY() - shape[tyleN][currentRotation][i][1];
            if(Position.isFilled(x, y))
                throw new Screen.NotAvailablePlaceForPieceException("("+x+","+y+")");
        }
        position.setX(newX);
    }

    public void setXandRotate(short newX) throws NotAvailablePlaceForPieceException, OutOfScreenBoundsException{
        int tyleN = currentShape.ordinal();
        int x,y;
        short rot = (short) (currentRotation + 1);
        rot%=4;
        for(int i = 0;i<4;i++){
            x = newX + shape[tyleN][rot][i][0];
            y = position.getY() - shape[tyleN][rot][i][1];
            if(Position.isFilled(x, y))
                throw new Screen.NotAvailablePlaceForPieceException("("+x+","+y+")");
        }
        currentRotation = rot;
        position.setX(newX);

    }


     /**
     * Default setter of the position of the piece with a updated values of x and y. 
     * @param newPos informes the updated position.
     * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
     * screen are not respected. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit with static pieces.
     */
    public void setPosition(Position newPos) throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException{
        int tyleN = currentShape.ordinal();
        int x,y;
        for(int i = 0;i<4;i++){
            x = newPos.getX() + shape[tyleN][currentRotation][i][0];
            y = newPos.getY() - shape[tyleN][currentRotation][i][1];
            if(Position.isFilled(x, y))
                throw new Screen.NotAvailablePlaceForPieceException("("+x+","+y+")");
        }
        position = newPos;
    }
    /**
     * Default setter of the parameter <em>currentShape</em>.
     * @param newShape informes the new type shape.
     */
    public void setShape(ShapeType newShape){
        this.currentShape = newShape;
    }
    /**
     * Default getter of the color correspondent to the current shape.
     * @return the color in according to the type of shape and the table order
     * of colors.
     */
    public Color getColor(){
        return shapeColors[currentShape.ordinal()];
    }
    /**
     * Default getter of the color correspondent to the current shape.
     * @return the name of color in according to the type of shape and the 
     * table order of names of colors.
     */    
    public String getColorName(){
        return shapeCNames[currentShape.ordinal()];
    }
    /**
     * Informes the position of each box of the piece, considering its shape and
     * rotation.
     * @return the position of each box.
     */
    public Position[] getAllPosition(){
        Position[] pos = new Position[4];
        int tyleN = currentShape.ordinal();
        try {
            for(int i = 0;i<4;i++)
                pos[i] = new Position(position.getX() + shape[tyleN][currentRotation][i][0],
                                      position.getY() - shape[tyleN][currentRotation][i][1]);
        } catch (OutOfScreenBoundsException ex) {
          Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pos;
    }
    
    public Position getPosition(){
        return position;
    }
    public ShapeType getShapeType(){
        return currentShape;
    }
    public short getRotation(){
        return currentRotation;
    }
    public void setRotation(short newRotation){
        currentRotation=newRotation;
    }
    
    /**
     * Implements the rotation of the piece if it is possible, accessing the table
     * with relative positions and updating the new configuration of the piece.
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit that does not allow the movement of rotation.
     */
    public void rotation() throws NotAvailablePlaceForPieceException{
        int tyleN = currentShape.ordinal();
        int x,y;
        short rot = (short) (currentRotation + 1);
        rot%=4;
        for(int i = 0;i<4;i++){
            x = this.position.getX() + shape[tyleN][rot][i][0];
            y = this.position.getY() - shape[tyleN][rot][i][1];
            if(Position.isFilled(x, y))
                throw new Screen.NotAvailablePlaceForPieceException("("+x+","+y+")");
        }
        currentRotation=rot;
    }
    /**
     * Implements the rotation of the piece in reversed diretion if it is possible, 
     * accessing the table with relative positions and updating the new configuration 
     * of the piece. 
     * @throws tetris.Screen.NotAvailablePlaceForPieceException when there is a 
     * conflit that does not allow the movement of rotation.
     */
    public void rotationInversed() throws NotAvailablePlaceForPieceException{
        int tyleN = currentShape.ordinal();
        int x,y;
        short rot = (short) (currentRotation + 3);
        rot%=4;

        for(int i = 0;i<4;i++){
            x = this.position.getX() + shape[tyleN][rot][i][0];
            y = this.position.getY() - shape[tyleN][rot][i][1];
            if(Position.isFilled(x, y))
                throw new Screen.NotAvailablePlaceForPieceException("("+x+","+y+")");
        }
        currentRotation=rot;
    }
    static char[][] model = new char[4][4];
    /**
     * Test fonction to test types.
     * @param s is the shape type.
     * @param rot is the current rotation.
     */
    synchronized public static void printTyle(ShapeType s,int rot){
        for(int a = 0;a<4;a++){
            for(int b = 0;b<4;b++)
                model[a][b] = ' ';
        }
        int tyleN = s.ordinal();
        for(int i = 0;i<4;i++){
            model[shape[tyleN][rot][i][0]+1]
                 [shape[tyleN][rot][i][1]+1]= '*';
        }
        for(int a = 0;a<4;a++){
            for(int b = 0;b<4;b++)
                System.out.print(model[a][b]);
            System.out.println();
        }
    }

    /**
     * This class is designed to informe the features of the piece (shape, rotation
     * block and the relative coordinates of each block
     */
    static private short[][][][] shape = { // [Shape][rotation][block][x,y]
        {
            { { 1, 0 }, { 1, 1 }, { 0, 1 }, { 2, 0 } }, // ... .x. ... .x.
            { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 } }, // xx. xx. xx. xx.  Z
            { { 1, 0 }, { 1, 1 }, { 0, 1 }, { 2, 0 } }, // .xx x.. .xx x..
            { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 } }  //

        },

        {
            { { 2, 1 }, { 1, 1 }, { 1, 0 }, { 0,0 } },// ... x.. ... X..
            { { 0, 2 }, { 0, 1 }, { 1, 1 }, { 1,0 } },// .xx xx. .XX xx. S
            { { 2, 1 }, { 1, 1 }, { 1, 0 }, { 0,0 } },// xx. .x. XX. .x.
            { { 0, 2 }, { 0, 1 }, { 1, 1 }, { 1,0 } }//

        },

        {
            { { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 } }, // .x.. .... .x.. ....
            { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 } }, // .x.. xxxx .x.. xxxx I
            { { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 } }, // .x.. .... .x.. ....
            { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 } }  // .x.. .... .x.. ....

        },

        {
            { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 1, 2 } }, // .x. .x. ... .x.
            { { 1, 0 }, { 1, 1 }, { 2, 1 }, { 1, 2 } }, // xxx .xx xxx xx. T
            { { 1, 0 }, { 1, 1 }, { 2, 1 }, { 0, 1 } }, // ... .x. .x. .x.
            { { 1, 0 }, { 1, 1 }, { 0, 1 }, { 1, 2 } }

        },

        {
            { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } }, // .xx .xx .xx .xx
            { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } }, // .xx .xx .xx .xx O
            { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } }, // ... ... ... ...
            { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 } }

        },
        {
            { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // .x. x.. .xx ...
            { { 1, 1 }, { 0, 1 }, { 0, 2 }, { 2, 1 } }, // .x. xxx .x. xxx Li
            { { 2, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // xx. ... .x. ..x
            { { 1, 1 }, { 0, 1 }, { 2, 0 }, { 2, 1 } },

        },
        {
            { { 2, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // .x. ... xx. ..x
            { { 1, 1 }, { 0, 1 }, { 0, 0 }, { 2, 1 } }, // .x. xxx .x. xxx L
            { { 0, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // .xx x.. .x. ...
            { { 1, 1 }, { 0, 1 }, { 2, 2 }, { 2, 1 } },

        },
        {
            { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } }, //
            { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } }, // None
            { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } }, //
            { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } },

        }
    };
}

