/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.Random;
import junit.framework.TestCase;

/**
 *
 * @author gustavo
 */
public class ScreenTest extends TestCase {
    
    public ScreenTest(String testName) {
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
     * Test of getMiddlePosition method, of class Screen.
     */
    public void testScreen() {
        Screen s =  new Screen();
        int NTests = 500;
        Random r = new Random(System.currentTimeMillis());
        for(int t = 0;t<NTests;t++){
            s.clean();
            int[][] b = new int[Screen.SIZE_X][Screen.SIZE_Y];
            for(int i = 0;i<Screen.SIZE_X;i++){
                for(int j = 0;j<Screen.SIZE_Y;j++){
                    if(r.nextBoolean()){
                        b[i][j] = r.nextInt(Piece.shapeColors.length);
                        s.joinPiece(i, j, Piece.shapeColors[b[i][j]]);
                    }else{
                        b[i][j] = -1;
                    }
                }
            }

            s = new Screen(s); // checking copy from reference;
            short line = s.checkLine();
            if(line != -1){
                s.removeLine(line);
                System.out.println("[ScreenTest] "+line+" has been removed");
            }
            for(int j = 0,jj = 0;j<Screen.SIZE_Y && jj<Screen.SIZE_Y ;j++, jj++){
                if(j == line)
                    jj++;
                if(jj == Screen.SIZE_Y)
                    break;
                for(int i = 0;i<Screen.SIZE_X;i++){
                    Box box = s.getBoxAt((short)i, (short)j);
                    assertTrue("Box is occupied and not at the same time",box.isFull() == (b[i][jj] >= 0));
                    if(b[i][jj] >= 0)
                        assertTrue("Color doesnt respect construction", box.getColor().equals(Piece.shapeColors[b[i][jj]]));
                }
            }
        }
        
    }


}
