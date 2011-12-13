/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.Screen.OutOfScreenBoundsException;
/**
 *
 * @author felipeteles
 */
public class Piece {
    static private short[][][][] shape = { // [Shape][rotation][block][x,y]
        {
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { 1, -1 } }, // ... .x. ... .x.
            { {-1, -1 },  {-1, 0 },   {  0, 0 },  {  0, 1 } }, // xx. xx. xx. xx.  Z
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { 1, -1 } }, // .xx x.. .xx x..
            { {-1, -1 },  {-1, 0 },   {  0, 0 },  {  0, 1 } }  //

        },

        {
            { { 1,  0 },  { 0, 0 },   { 0, -1 },   {-1,-1 } },// ... x.. ... X..
            { { -1, 1 },  {-1, 0 },   {  0, 0 },  {  0,-1 } },// .xx xx. .XX xx. S
            { { 1,  0 },  { 0, 0 },   { 0, -1 },   {-1,-1 } },// xx. .x. XX. .x.
            { { -1, 1 },  {-1, 0 },   {  0, 0 },  {  0,-1 } }//

        },

        {
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } }, // .x.. .... .x.. ....
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 2, 0 } }, // .x.. xxxx .x.. xxxx I
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } }, // .x.. .... .x.. ....
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 2, 0 } }  // .x.. .... .x.. ....

        },

        {
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } }, // .x. .x. ... .x.
            { {  0, -1},  { 0, 0 },   { 1, 0 },   { 0, 1 } }, // xxx .xx xxx xx. T
            { {  0, -1},  { 0, 0 },   { 1, 0 },   {-1, 0 } }, // ... .x. .x. .x.
            { {  0, -1},  { 0, 0 },   {-1, 0 },   { 0, 1 } }

        },

        {
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } }, // .xx .xx .xx .xx
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } }, // .xx .xx .xx .xx O
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } }, // ... ... ... ...
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } }

        },
        {
            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } }, // .x. x.. .xx ...
            { { 0, 0 },   {-1, 0 },   {-1, 1 },   { 1, 0 } }, // .x. xxx .x. xxx Li
            { { 1, 1 },   { 0, -1},   { 0, 0 },   { 0, 1 } }, // xx. ... .x. ..x
            { { 0, 0 },   {-1, 0 },   { 1,-1 },   { 1, 0 } },

        },
        {
            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }, // .x. ... xx. ..x
            { { 0, 0 },   {-1, 0 },   {-1,-1 },   { 1, 0 } }, // .x. xxx .x. xxx L
            { {-1, 1 },   { 0, -1},   { 0, 0 },   { 0, 1 } }, // .xx x.. .x. ...
            { { 0, 0 },   {-1, 0 },   { 1, 1 },   { 1, 0 } },

        },
        {
            { { 0, 0 },  { 0, 0 },  { 0, 0 },  { 0, 0 } }, // 
            { { 0, 0 },  { 0, 0 },  { 0, 0 },  { 0, 0 } }, // None
            { { 0, 0 },  { 0, 0 },  { 0, 0 },  { 0, 0 } }, // 
            { { 0, 0 },  { 0, 0 },  { 0, 0 },  { 0, 0 } },

        }
    };//lista de peças, rotaçoes,cada quadrado,coordenadas
    private short currentRotation,currentSquare;//indices da tabela aciam
    private ShapeType currentShape;
    private Color color;
    private Position position;

    static public enum ShapeType{
        Z,S,I,T,O,Li,L,None
    };

    Color[] shapeColors = {Color.PINK, Color.MAGENTA, Color.BLUE, Color.CYAN,Color.YELLOW,Color.GREEN,Color.ORANGE,Box.getEmptyColor()};

    public Piece(ShapeType s,short square, Position p) throws OutOfScreenBoundsException{
        currentRotation = 1;
        currentSquare = square;
        currentShape = s;
        color = this.getColor();
        position = new Position(p);
    }

    public void setPosition(Position newPos) throws OutOfScreenBoundsException{
        this.position = new Position(newPos);
    }
    private void setShape(ShapeType newShape){
        this.currentShape = newShape;

    }
    public Color getColor(){
        return shapeColors[currentShape.ordinal()];
    }
    public Position getPosition(){
        return position;
    }
    public Position[] getAllPosition(){
        Position[] pos = new Position[4];
        int tyleN = currentShape.ordinal();
        try {
            for(int i = 0;i<4;i++)
                pos[i] = new Position(position.getX() + shape[tyleN][currentRotation][i][0],
                                      position.getY() + shape[tyleN][currentRotation][i][1]);
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

    public static void main(String[] args){
        char[][] model = new char[4][4];
        for(ShapeType s:ShapeType.values()){
            for(int i = 0;i<4;i++){
               printTyle(s,i);
               System.out.println(" ---- ");
            }
        }
    }
    private static char[][] model = new char[4][4];
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
    
}

