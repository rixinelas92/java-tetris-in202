/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;


import java.util.Vector;
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

    public enum ShapeType{
        Z,S,I,T,O,Li,L,None
    };

    public Piece(ShapeType s,short square, Color c,Position p){
        currentRotation = 1;
        currentSquare = square;
        currentShape = s;
        color = c;
        position = new Position(p);
    }

    public void setPosition(Position newPos){
        this.position = new Position(newPos);
    }
    private void setShape(ShapeType newShape){
        this.currentShape = newShape;

    }
    public Color getColor(Color newColor){
        return Color.BLUE;
    }
    public Position getPosition(){
        return position;
    }
    @Deprecated
    public Vector<Position> getAllPosition(){
        return null;
    }
    public void rotation(){
        currentRotation+=1;
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

