/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.util;

import junit.framework.TestCase;
import tetris.Piece;
import tetris.Piece.ShapeType;

/**
 *
 * @author gustavo
 */
public class RandomEnumTest extends TestCase {
    
    public RandomEnumTest(String testName) {
        super(testName);
    
        
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of random method, of class RandomEnum.
     */
    public void testRandom() {
        System.out.println("random");
        RandomEnum instance = new RandomEnum<Piece.ShapeType>(Piece.ShapeType.class);
        final int MaxTests = 100;
        for(ShapeType st: ShapeType.values()){
            Enum re;
            int cont = 0;
            do{
                re = instance.random();
                assertTrue("RandomEnum returned an instance of a different class",re instanceof ShapeType );
            }while(!st.equals(re) && cont++ < MaxTests); 
            assertFalse("Reached max limit of tryals, there is a possibility that something is wrong", cont == MaxTests);   
        }
    }

    /**
     * Test of randomExceptLast method, of class RandomEnum.
     */
    public void testRandomExceptLast() {
        System.out.println("randomExceptLast");
        RandomEnum instance = new RandomEnum<Piece.ShapeType>(Piece.ShapeType.class);
        final int MaxTests = 100;
        for(ShapeType st: ShapeType.values()){
            if(st.equals(ShapeType.None))
                continue;
            Enum re;
            int cont = 0;
            do{
                re = instance.randomExceptLast();
                
                assertTrue("RandomEnum returned an instance of a different class",re instanceof ShapeType );
                assertFalse("Random Enum returned a shape that is none",re.equals(ShapeType.None));
            }while(!st.equals(re) && cont++ < MaxTests); 
            assertFalse("Reached max limit of tryals, there is a possibility that something is wrong", cont == MaxTests);   
        }
    }
}
