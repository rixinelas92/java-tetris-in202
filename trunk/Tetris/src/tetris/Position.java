 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;


import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.Screen.FilledRetriever;
import tetris.Screen.OutOfScreenBoundsException;

/**
 *
 * @author felipeteles
 */
public class Position {
    private short x;
    private short y;
    static Screen.BorderRetriever borderRetriever;
    static FilledRetriever filledRetriever;

    public Position(Position old) throws OutOfScreenBoundsException{
        setX(old.getX());
        setY(old.getY());
        
    }
    public Position(int newX,int newY) throws OutOfScreenBoundsException{
        setX((short)newX);
        setY((short)newY);
    }
    public Position(short newX, short newY) throws OutOfScreenBoundsException{
        setX(newX);
        setY(newY);
    }
    private Position(){
    }
    public void setX(int newX) throws OutOfScreenBoundsException{
        setX((short)newX);
    }
    public void setY(int newY) throws OutOfScreenBoundsException {
        setY((short)newY);
    }
    public void setX(short newX)throws OutOfScreenBoundsException{
        if(newX-1 > borderRetriever.getMaxX() || newX+1 < 0)
            throw new OutOfScreenBoundsException();
        x = newX;
    }
    public void setY(short newY)throws OutOfScreenBoundsException{
        if(newY-1 > borderRetriever.getMaxY() || newY+1 < 0)
            throw new OutOfScreenBoundsException();
        y = newY;
    }
    public void setPosition(short newX,short newY){
       try{
            setX(newX);
            setY(newY);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public short getX(){
        return x;//mudar
    }
    public short getY(){
        return y;//mudar
    }
    static public void setBorderRetriever(Screen.BorderRetriever borderRetriever){
        Position.borderRetriever = borderRetriever;
    }
    static public void setFilledRetriever(Screen.FilledRetriever filledRetriever){
        Position.filledRetriever = filledRetriever;
    }

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

}
