 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;


import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.Screen.BorderRetriever;
import tetris.Screen.FilledRetriever;
import tetris.Screen.OutOfScreenBoundsException;

/**
 * This class was designed to manage the position of the boxes in the screen.
 * It supervises the borders of the screen and allows or not, the mouvement of
 * a piece.
 * @author felipeteles
 */
public class Position {
    private short x;
    private short y;
    static Screen.BorderRetriever borderRetriever;
    static FilledRetriever filledRetriever;
    
    /**
    * Setter of the position to the current piece with reference from the last one.
    * @param old defines the coordanates of the old position.
    * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
    * screen is not respected.
    */
    public Position(Position old) throws OutOfScreenBoundsException{
        setX(old.getX());
        setY(old.getY());
        
    }
    /**
    * Setter of the coordenates of the parameters <em>x</em> and <em>y</em>.
    * @param newX defines the next coordenate of the parameter <em>x</em>.
    * @param newY defines the next coordanate of the parameter <em>y</em>.
    * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
    * screen is not respected.
    */
    public Position(int newX,int newY) throws OutOfScreenBoundsException{
        setX((short)newX);
        setY((short)newY);
    }
    /**
    * Setter of the coordenates of the parameters <em>x</em> and <em>y</em>.
    * @param newX defines the next coordenate of the parameter <em>x</em>.
    * @param newY defines the next coordanate of the parameter <em>y</em>.
    * @throws tetris.Screen.OutOfScreenBoundsException when the borders of the 
    * screen is not respected.
    */
    public Position(short newX, short newY) throws OutOfScreenBoundsException{
        setX(newX);
        setY(newY);
    }

    public Position(int newX,int newY,int secure){
        if(secure != 0){
            try{
                setX(newX);
                setY(newY);
            }catch(Exception e){

            }
        }else{
            this.x = (short)newX;
            this.y = (short)newY;
        }

    }
    /* Hidding constructor from user.
     */
    private Position(){
    }
    /**
    * Default setter of the coordanate <em>x</em>.
    * @param newX defines the next coordanate of the parameter <em>x</em>.
    * @throws tetris.Screen.OutOfScreenBoundsException when the border of the 
    * screen is not respected.
    */
    public void setX(int newX) throws OutOfScreenBoundsException{
        setX((short)newX);
    }
    /**
    * Default setter of the coordanate <em>y</em>.
    * @param newY defines the next coordanate of the parameter <em>y</em>.
    * @throws tetris.Screen.OutOfScreenBoundsException when the border of the 
    * screen is not respected.
    */
    public void setY(int newY) throws OutOfScreenBoundsException {
        setY((short)newY);
    }
    /**
    * Default setter of the coordanate <em>x</em>.
    * @param newX defines the next coordanate of the parameter <em>x</em>.
    * @throws tetris.Screen.OutOfScreenBoundsException when the border of the 
    * screen is not respected.
    */
    public void setX(short newX)throws OutOfScreenBoundsException{
        if(newX-1 > borderRetriever.getMaxX() || newX+1 < 0)
            throw new OutOfScreenBoundsException();
        x = newX;
    }
    /**
    * Default setter of the coordanate <em>y</em>.
    * @param newY defines the next coordanate of the parameter <em>y</em>.
    * @throws tetris.Screen.OutOfScreenBoundsException when the border of the 
    * screen is not respected.
    */
    public void setY(short newY)throws OutOfScreenBoundsException{
        if(newY-1 > borderRetriever.getMaxY() || newY+1 < 0)
            throw new OutOfScreenBoundsException();
        y = newY;
    }
    /**
    * Default setter of the coordenates <em>x</em> and <em>y</em>. 
    * @param newX defines the next coordenate of the parameter <em>x</em>.
    * @param newY defines the next coordanates of the parameter <em>y</em>. 
    */
    public void setPosition(short newX,short newY){
       try{
            setX(newX);
            setY(newY);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
    * Default getter of the attribute <em>x</em>
    * @return the coordenate x of the box.
    */
    public short getX(){
        return x;
    }
    /**
    * Default getter of the attribute <em>y</em>
    * @return the coordenate y of the box.
    */
    public short getY(){
        return y;
    }
    /**
    * Default setter of the parameter <em>borderRetriever</em> definig the new 
    * borders.
    * @param borderRetriever defines the next dimension.
    */
    public static void setBorderRetriever(BorderRetriever borderRetriever){
        Position.borderRetriever = borderRetriever;
    }
    /**
    * Default setter of the parameter <em>filledRetriever</em> definig the new 
    * surface with statics box.
    * @param filledRetriever informes if the borders or statics boxes were reached.
    */
    public static void setFilledRetriever(Screen.FilledRetriever filledRetriever){
        Position.filledRetriever = filledRetriever;
    }
    /**
    * Returns true if the coordenates x and y is filled with a static box.
    * @param x defines the coordanate x of the box.
    * @param y defines the coordenate y of the box.
    * @return true if a box is already positioned in the coordenates (x,y).
    */
    static boolean isFilled(int x, int y){
        return filledRetriever.isFilledWithStaticBloc(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(! (obj instanceof Position)){
            return false;
        }
        Position p = (Position)obj;
        if(x != p.getX())
            return false;
        if(y != p.getY())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public String toString(){
        return "["+x+";"+y+"]";
    }
    public static Position getMinCoord(Position[] vector){
        try {
            Position p=new Position(borderRetriever.getMaxX(),borderRetriever.getMaxY());
            for(Position s:vector){
                p.setX(Math.min(p.getX(), s.getX()));
                p.setY(Math.min(p.getY(),s.getY() ));
            }
            return p;
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Position.class.getName()).log(Level.SEVERE, null, ex);
        }
        Position p = new Position();
        p.x = p.y = 0;
        return p;
    }


    public static Position getMaxCoord(Position[] vector){
        try {
            Position p = new Position(0,0);
            for(Position s:vector){
                p.setX(Math.max(p.getX(), s.getX()));
                p.setY(Math.max(p.getY(),s.getY() ));
            }
            return p;
        } catch (OutOfScreenBoundsException ex) {
            Logger.getLogger(Position.class.getName()).log(Level.SEVERE, null, ex);
        }
        Position p = new Position();
        p.x = p.y = 0;
        return p;
    }
}
