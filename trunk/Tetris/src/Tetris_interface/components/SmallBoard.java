/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris_interface.components;

import Tetris_interface.Interface;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import tetris.Screen;

/**
 *
 * @author gustavo
 */
/**
 * Class implemented to provide interface when the game is running in mode
 * two player, allowing to know the mouvements of the opponent.
 */
public class SmallBoard extends JPanel {

    int[] isFilled;
    Interface container;
    int pxlsize = 6;
    JLabel boardLabels[][];
    
    /**
     * Creates a new small board with parameters by default.
     */
    public SmallBoard(int[] initialImg, Interface container) {
        this.container = container;
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        setSize(Screen.SIZE_X * pxlsize, Screen.SIZE_Y * pxlsize);
        boardLabels = new JLabel[Screen.SIZE_X][Screen.SIZE_Y];
        setBackground(Interface.COLOR_semiopaque);
        setDoubleBuffered(true); 
        GridLayout layout = new GridLayout(Screen.SIZE_Y,Screen.SIZE_X,0,0);
        setLayout(layout);
        isFilled = new int[Screen.SIZE_X];
        ImageIcon img = new ImageIcon(container.getClass().getResource("imgs/small6.png"));
        for (int i = 0; i < Screen.SIZE_X; i++) {
            isFilled[i] = 0;
            for (int j = 0; j < Screen.SIZE_Y; j++) {
                
                boardLabels[i][j] = new JLabel(img);
                boardLabels[i][j].setSize(pxlsize, pxlsize);
                boardLabels[i][j].setMaximumSize(new Dimension(pxlsize, pxlsize));
                boardLabels[i][j].setMinimumSize(new Dimension(pxlsize, pxlsize));
                boardLabels[i][j].setOpaque(true);
                boardLabels[i][j].setVisible(false);
                add(boardLabels[i][j]);
            }
        }
        for (int i = Screen.SIZE_Y -1; i >= 0; i--) {
            for (int j = 0; j < Screen.SIZE_X; j++) {
                add(boardLabels[j][i]);
            }
        }
        updateBoardDescription(initialImg);
    }
    /**
     * Updates the interface.
     */
    public void updateBoardDescription(int newFilled[]) {
        boolean changed = false;
        for (int i = 0; i < Screen.SIZE_X; i++) {
            int diff = this.isFilled[i] ^ newFilled[i];
            if (diff == 0) {
                continue;
            }
            changed = true;
            for (int j = 0; j < Screen.SIZE_Y; j++) {
                if ((diff & (1 << j)) > 0) {
                    if ((newFilled[i] & (1 << j)) > 0) {
                        boardLabels[i][j].setVisible(true);
                    } else {
                        boardLabels[i][j].setVisible(false);
                        paintImmediately(boardLabels[i][j].getBounds());
                    }
                }
            }
            isFilled[i] = newFilled[i];
        }
        if(changed){
            paintImmediately(getBounds());
        }
    }
}
