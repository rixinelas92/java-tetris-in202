/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.util;

import java.util.Random;

/**
 *
 * @author gustavo
 */
public class RandomEnum<E extends Enum> {

        private static final Random RND = new Random();
        private final E[] values;
        /**
         * Creates a new RandomEnum setting the parameter values.
         */
        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }
        public E random() {
            return values[RND.nextInt(values.length)];
        }
        public E randomExceptLast() {
            return values[RND.nextInt(values.length-1)];
        }
}