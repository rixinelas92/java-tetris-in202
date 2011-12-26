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
/**
 *
 * @author gustavo
 */
public abstract class Controller extends JFrame implements KeyListener, MouseMotionListener, MouseListener {
    private int keyPause,keyGoLeft,keyGoRight,keyGoDown,keyRotate;
    private boolean mouseController;

   public Controller(){
        //test
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
        //usefull
        keyPause= KeyEvent.VK_P;
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

    public abstract void goToBottom();
    public abstract void goLeft();
    public abstract void goRight();
    public abstract void stop();
    protected abstract void goToX();

    //test function
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller frame = new Game();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
     
    //Listeners functions
    public void keyPressed(KeyEvent e) {
        int keyUsed = e.getKeyCode();
        
        System.out.println("check");
        if (keyUsed==keyPause) stop();
        if (keyUsed==keyGoLeft) goLeft();
        if (keyUsed==keyGoRight) goRight();
        if (keyUsed==keyGoDown) goToBottom();
        if (keyUsed==keyRotate) rotate();
         
    }
    public void mouseMoved(MouseEvent e) {
        if(mouseController){
            goToX();
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
