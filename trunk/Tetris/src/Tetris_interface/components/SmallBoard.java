/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris_interface.components;

import Tetris_interface.Interface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tetris.Screen;

/**
 *
 * @author gustavo
 */
public class SmallBoard extends JPanel {

    int[] isFilled;
    Interface container;
    int pxlsize = 6;
    JLabel boardLabels[][];

    public SmallBoard(int[] initialImg, Interface container) {
        this.container = container;
        setSize(Screen.SIZE_X * pxlsize, Screen.SIZE_Y * pxlsize);
        boardLabels = new JLabel[Screen.SIZE_X][Screen.SIZE_Y];
        setBackground(Interface.COLOR_semiopaque);
        GridLayout layout = new GridLayout(Screen.SIZE_Y,Screen.SIZE_X,0,0);
        setLayout(layout);
        isFilled = new int[Screen.SIZE_X];
        for (int i = 0; i < Screen.SIZE_X; i++) {
            isFilled[i] = 0;
            for (int j = 0; j < Screen.SIZE_Y; j++) {
                boardLabels[i][j] = new JLabel(new ImageIcon(getClass().getResource("../imgs/small6.png")));
                boardLabels[i][j].setSize(pxlsize, pxlsize);
                boardLabels[i][j].setMaximumSize(new Dimension(pxlsize, pxlsize));
                boardLabels[i][j].setMinimumSize(new Dimension(pxlsize, pxlsize));

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


    public void updateBoardDescription(int newFilled[]) {
        for (int i = 0; i < Screen.SIZE_X; i++) {
            int diff = this.isFilled[i] ^ newFilled[i];
            if (diff == 0) {
                continue;
            }
            for (int j = 0; j < Screen.SIZE_Y; j++) {
                if ((diff & (1 << j)) > 0) {
                    if ((newFilled[i] & (1 << j)) > 0) {
                        boardLabels[i][j].setVisible(true);
                    } else {
                        boardLabels[i][j].setVisible(false);
                    }
                }
            }
            isFilled[i] = newFilled[i];

        }

    }
}
