 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import tetris.Screen.OutOfScreenBoundsException;

/**
 *
 * @author felipeteles
 */
class Position {
    private short x;
    private short y;
    static Screen.BorderRetriever borderRetriever;

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
    public void setX(short newX)throws OutOfScreenBoundsException{
        if(newX > borderRetriever.getMaxX() || newX < 0)
            throw new OutOfScreenBoundsException();
        x = newX;
    }
    public void setY(short newY)throws OutOfScreenBoundsException{
        if(newY > borderRetriever.getMaxY() || newY < 0)
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

    public String toString(){
        return "["+x+";"+y+"]";
    }


}
