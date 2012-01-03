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
public class Color {
    private String color;
    
    public Color(String color) {
        this.color = color;
    }
    
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
    public String getColor() {
        return color;
    }   
}  
    
           

    

