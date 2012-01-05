/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

/**
 *
 * @author tacilo
 */
//This class have was designed to simplify the work with color in substitution
//to the base class provided by java. This class just generates just the necessaire
//strings to the code.
/**
 * This class was designed in replacement of the class Color.
 * @author Tacilo
 */
public class Color {
    private String color;
    /**
     * Default setter of the attribute <em>color</em>
     * @param color defines the color.
     */
    public Color(String color) {
        this.color = color;
    }
    /**
     * Setter of the color in according to the reference.
     * @param n selects the color to be setted. 
     */
    public Color(int n) {
        if(n==1){
        this.color = "Blue";
    }
        if(n==2){
        this.color = "Brown";
    }
        if(n==3){
        this.color = "Ciano";
    }
        if(n==4){
        this.color = "Green";
    }
        if(n==5){
        this.color = "Purple";
    }
        if(n==6){
        this.color = "Red";
    }
        if(n==7){
        this.color = "Yellow";
    }
}
    /**
     * Default getter of the attribute <em>color<em/>
     * @return color of the object.
     */
    public String getColor() {
        return color;
    }   
}  
    
           

    

