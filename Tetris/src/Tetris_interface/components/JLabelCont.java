/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris_interface.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author gustavo
 */
public class JLabelCont extends JLabel {

        int x = -1;
        int y = -1;

        public JLabelCont(ImageIcon imageIcon) {
            super(imageIcon);
        }

        /**
         * Default setter of the coordinates of the positon.
         * @param x defines the coordinate x.
         * @param y defines the coordinate y.
         */
        public void setP(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Default getter of the coordinate x.
         * @return the value of the coordinate.
         * @throws Exception if position x is -1.#############################################
         */
        public int getCX() throws Exception {
            if (x == -1) {
                throw new Exception("x null");
            }
            return x;
        }

        /**
         * Default getter of the coordinate y.
         * @return the value of the coordinate.
         * @throws Exception if position y is -1.#############################################
         */
        public int getCY() throws Exception {
            if (y == -1) {
                throw new Exception("y null");
            }
            return y;
        }
}