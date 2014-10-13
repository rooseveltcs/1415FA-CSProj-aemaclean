
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;


public class Object {
    private double x;
    private double y;
    private double xVelocity;
    private double yVelocity; 
    private int mass;
    private boolean type;
    private Color color;
    private int diameter;
    
    
    public Object(int x, int y, int mass,int xvol,int yvol,boolean type,Color color) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.xVelocity = xvol;
        this.yVelocity = yvol;
        this.type = type;
        this.color = color;
    }
    
     public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
    
       public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }
    public int getMass(){
        return mass;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
    public void setMass(int Mass) {
        this.mass = Mass;
    }
    
    public void tick() {
        x += xVelocity;
        y += yVelocity;
         diameter = (int)(Math.sqrt((mass*2)/3.14)*3)+1;
    }
    public boolean getType(){
        return type;
    }
    public int getDiameter(){
        return diameter;
    }
    public void sety(int y) {
        this.y = y;
    }
    public void setx(int x) {
        this.y = y;
    }
    public void setColor(Color c) {
        this.color = c;
    }
    public Color getColor() {
      return color;
    }
     public void testBorder(int xBorder,int yBorder) {
    	 int diameter = (int)(Math.sqrt((mass*2)/3.14)*2)+1;
        if(x>xBorder+(diameter/2)){
           x=0-(diameter/2); 
        } 
        if(x<0-(diameter/2)){
            x=xBorder+(diameter/2);
        }
        if(y>yBorder+(diameter/2)){
           y=0-(diameter/2); 
        } 
        if(y<0-(diameter/2)){
            y=yBorder+(diameter/2);
        }
     }
    
    public void paint(Graphics2D g2d){
    	g2d.setColor(color);
    	
        g2d.drawOval( (int) x-diameter/2, (int) y-diameter/2, (int) diameter,  (int) diameter);
       g2d.fillOval( (int) x-diameter/2, (int) y-diameter/2, (int) diameter,  (int) diameter);
       
       if(type){
    	
    	   g2d.drawOval( (int)( x-diameter/2), (int) (y-diameter/2), (int) diameter,  (int) diameter);
       }
      
    }
    
}


