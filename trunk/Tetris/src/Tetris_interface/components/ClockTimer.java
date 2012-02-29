/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tetris_interface.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author gustavo
 */
public class ClockTimer implements ActionListener {

        Timer timer;
        long time;
        static final int delay = 500;
        JTextField field;

        /**
         * Creates a new clock with parameters defined by default.
         */
        public ClockTimer(JTextField field) {
            this.field = field;
            timer = new Timer(delay, this);
            time = 0;
            timer.start();
        }

        /**
         * Resets the parameters of the clock and updates the time passed.
         */
        public void restart() {
            time = 0;
            timer.restart();
            field.setText(String.format("%1$tM:%1$tS", time, time));
        }
        /**
         * Implements a listener to the clock.
         * @param ae informes that an event arrived. 
         */
        public void actionPerformed(ActionEvent ae) {
            time += delay;
            field.setText(String.format("%1$tM:%1$tS", time, time));
        }

        /**
         * Implements the case pause in the timer. If the game is stopped, it restarts
         * or it stops if it is was running.
         */
        public void togglePause() {
            if (!timer.isRunning()) {
                timer.start();
            } else {
                timer.stop();
            }
        }

        /**
         * Implements the pause in the reference of the time to the screen -timer-
         * and with this, it stops to be updated.
         */
        public void pauseScreen() {
            timer.stop();
        }
}
