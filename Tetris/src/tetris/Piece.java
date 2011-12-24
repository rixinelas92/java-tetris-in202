/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    private short currentRotation,currentSquare;//indices da tabela aciam
    private ShapeType currentShape;
    private Position position;

    static public enum ShapeType{
        Z,S,I,T,O,Li,L,None
    };

    public static final Color[]  shapeColors = {Color.PINK, Color.MAGENTA, Color.BLUE, Color.CYAN,Color.YELLOW,Color.GREEN,Color.ORANGE,Box.getEmptyColor()};
    public static final String[] shapeCNames = {"Purple",   "Red",         "Blue",     "Ciano",   "Yellow",    "Green",    "Brown",     "close"};

    public Piece(ShapeType s,short square, Position p) throws OutOfScreenBoundsException{
        currentRotation = 1;
        currentSquare = square;
        currentShape = s;
        position = new Position(p);
    }

    public short getY(){
        return position.getY();
    }
    public short getX(){
        return position.getX();
    }

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
    public void setShape(ShapeType newShape){
        this.currentShape = newShape;
    }
    public Color getColor(){
        return shapeColors[currentShape.ordinal()];
    }
    public String getColorName(){
        return shapeCNames[currentShape.ordinal()];
    }


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
    public void rotation(){
        currentRotation+=1;
        currentRotation%=4;
    }
    public void rotationInversed(){
        currentRotation+=3;
        currentRotation%=4;
    }

    








    static char[][] model = new char[4][4];
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
    };//lista de peças, rotaçoes,cada quadrado,coordenadas
}

