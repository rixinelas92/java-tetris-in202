/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.*;
import Tetris_interface.AbsoluteConstraints;
import Tetris_interface.AbsoluteLayout;
import tetris.Screen.NotAvailablePlaceForPieceException;
import tetris.Screen.OutOfScreenBoundsException;

/**
 * This class was designed to control...
 * @author gustavo
 */
public abstract class Controller implements KeyListener, MouseMotionListener, MouseListener {
    protected int keyPause,keyGoLeft,keyGoRight,keyGoDown,keyRotate;
    private boolean mouseController;

   public Controller(){
        keyPause=KeyEvent.VK_P;
        keyGoLeft=KeyEvent.VK_LEFT;
        keyGoRight=KeyEvent.VK_RIGHT;
        keyGoDown=KeyEvent.VK_DOWN;
        keyRotate=KeyEvent.VK_UP;   
    }
    /**
    * Default setter to the class to the attributes <em>keyGoLeft</em> 
    * em>keyGoRight</em> <em>keyGoDown</em> <em>keyRotate</em>.
    * @param newLeft defines the action 'go left' in according with the user event.
    * @param newRight defines the action 'go right' in according with the user event.
    * @param newDown defines the action 'go down' in according with the user event.
    * @param newRotate defines the action 'rotate' in according with the user event.
    */
    public void setControllers(int newLeft, int newRight, int newDown, int newRotate){
        keyGoLeft=newLeft;
        keyGoRight=newRight;
        keyGoDown=newDown;
        keyRotate=newRotate;
    }
    /**
     * It returns the state of the mouse, if its static or moving.
     * @return true if it is moving or false if it's static. 
     */
    public boolean isMouseController() {
        return mouseController;
    }
    /**
     * Default setter of the parameter <em>mouseControler</em> in according with
     * the new condition.
     * @param newMouseController defines the new state of the mouse. 
     */
    public void setMouseController(boolean newMouseController) {
        mouseController = newMouseController;
    }
     /**
     * Classes to be overwrite, that are used but not implemented in this class
     * Controller.java.
     */
    public abstract void rotate();
    public abstract void goDown() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void goToBottom() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void goLeft()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void goRight()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void stopToggle()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    protected abstract void goToX()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    
    /**
     * Listeners functions, they check the action that must be executed in according with the key 
     * pressed by the user.
     * @param e verifies if there is an user action.
     */
    public void keyPressed(KeyEvent e) {
        int keyUsed = e.getKeyCode();
        
        try{
            if (keyUsed==keyPause) stopToggle();
        } catch(Exception ex){
            System.out.println("Cant "+e.getKeyChar());
        }
        try{
            if (keyUsed==keyGoLeft) goLeft();
        } catch(Exception ex){
            System.out.println("Cant "+e.getKeyChar());
        }
        try{
            if (keyUsed==keyGoRight) goRight();
        } catch(Exception ex){
            System.out.println("Cant "+e.getKeyChar());
        }
        try{
            if (keyUsed==keyGoDown) goToBottom();
        } catch(Exception ex){
            System.out.println("Cant "+e.getKeyChar());
        }
        try{
            if (keyUsed==keyRotate) rotate();
        } catch(Exception ex){
            System.out.println("Cant "+e.getKeyChar());
        }
        Main.terminateControllerAction();
         
    }
    /**
     * It determines the movement of translation that will be executed in 
     * accoding with the action produced by the user.
     * @param e verifies if there is an user action.
     */
    public void mouseMoved(MouseEvent e) {
        if(mouseController){
            try{
                goToX();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * It determines the movement 
     * @param e 
     */
    public void mouseClicked(MouseEvent e) {
        if(mouseController){
            rotate();
        }
    }  
    //not used
    public abstract void keyTyped(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void mouseDragged(MouseEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);
    public abstract void mouseExited(MouseEvent e);
}
