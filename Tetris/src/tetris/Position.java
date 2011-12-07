 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author felipeteles
 */
class Position {
    private short x;
    private short y;
    static Screen.BorderRetriever borderRetriever;

    public Position(Position old){
        try{
            setX(old.getX());
            setY(old.getY());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Position(int newX,int newY){
        try{
            setX((short)newX);
            setX((short)newX);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Position(short newX, short newY){
        try{
            setX(newX);
            setX(newX);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setX(short newX)throws Exception{
        if(newX > borderRetriever.getMaxX() || newX < 0)
            throw new Exception("Tried to set X to a place outside the border");
        x = newX;
    }
    public void setY(short newY)throws Exception{
        if(newY > borderRetriever.getMaxY() || newY < 0)
            throw new Exception("Tried to set Y to a place outside the border");
        y = newY;
    }
    public void setPosition(short newX,short newY){
       try{
            setX(newX);
            setX(newX);
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

}
