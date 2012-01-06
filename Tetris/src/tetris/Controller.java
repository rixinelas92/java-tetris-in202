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
 *
 * @author gustavo
 */
public abstract class Controller implements KeyListener, MouseMotionListener, MouseListener {
    private int keyPause,keyGoLeft,keyGoRight,keyGoDown,keyRotate;
    private boolean mouseController;

   public Controller(){
        //test
       /*
        setSize(100,100);
        JPanel typingArea = new JPanel(new AbsoluteLayout());
        JPanel typingPanel = new JPanel(new AbsoluteLayout());
        typingArea.setFocusable(true);
        typingArea.addKeyListener(this);
        typingArea.addMouseMotionListener(this);
        typingArea.addMouseListener(this);
        getContentPane().setLayout(new AbsoluteLayout());
        getContentPane().add(typingArea, new AbsoluteConstraints(10,10,50,50));
        getContentPane().add(typingPanel, new AbsoluteConstraints(10,30,50,50));
        *  * 
        */
        
        //usefull
        keyPause=KeyEvent.VK_P;
        keyGoLeft=KeyEvent.VK_LEFT;
        keyGoRight=KeyEvent.VK_RIGHT;
        keyGoDown=KeyEvent.VK_DOWN;
        keyRotate=KeyEvent.VK_UP;
       
       
    }
    public void setControllers(int newLeft, int newRight, int newDown, int newRotate){
        keyGoLeft=newLeft;
        keyGoRight=newRight;
        keyGoDown=newDown;
        keyRotate=newRotate;
    }
     public boolean isMouseController() {
        return mouseController;
    }

    public void setMouseController(boolean newMouseController) {
        mouseController = newMouseController;
    }
    //classes to be overwrite   
    public abstract void rotate();
    public abstract void goDown() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void goToBottom() throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void goLeft()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void goRight()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    public abstract void stopToggle()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;
    protected abstract void goToX()throws OutOfScreenBoundsException, NotAvailablePlaceForPieceException;

    //test function
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller frame = new Game();
                /*
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                 * 
                 */
            }
        });
    }
     
    //Listeners functions
    public void keyPressed(KeyEvent e) {
        int keyUsed = e.getKeyCode();
        
        System.out.println("check");
        /*
         * Each action have its own try catch in order to permit the same key to
         * trigger two or more different actions.
         */
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
    public void mouseMoved(MouseEvent e) {
        if(mouseController){
            try{
                goToX();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

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
