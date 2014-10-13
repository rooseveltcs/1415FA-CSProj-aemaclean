import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;


public  class Window extends JPanel implements MouseMotionListener,KeyListener,ActionListener
{
    
    //vars
private Timer _timer;
int mouseX = 0;
int mouseY = 0; 
int score = 0;
double goVol = 0;
int total = 0;
double time= 0;
boolean start = false;
int startMass = 1;
int objectNumber = 1;
int objectMassMax = 1;
boolean playerExists = false;
Color backgroundColor = new Color(1, 2, 3);
Color objectsColor = new Color(1, 2, 3);
Color playerColor =new Color(1, 2, 3);
double difficultyInt = 0;
float hue = (float)0.01;
boolean randomC = false;
boolean realistic= false;




JPanel mainPannel = new JPanel();
JPanel numberPannel = new JPanel();
JPanel varPannel = new JPanel();
JPanel massPannel = new JPanel();
JPanel colorPanel = new JPanel();
JPanel playerCPanel = new JPanel();
JPanel objectCPanel = new JPanel();
JPanel backgroundCPanel = new JPanel();

JSlider numberSlider = new JSlider(JSlider.HORIZONTAL, 10, 200, 80);
JSlider varSlider = new JSlider(JSlider.HORIZONTAL, 0, 75, 5);
JSlider massSlider = new JSlider(JSlider.HORIZONTAL, 5, 150, 5);
JButton beginButton = new JButton("Begin");
JLabel difficulty =new JLabel(Integer.toString(varSlider.getValue()));

String[] playerChoices = { "Blue","Red", "Green","Pink","Yellow","White"};
JComboBox<String> playerDD = new JComboBox<String>(playerChoices);
   
String[] objectChoices = { "White","Light Grey", "Dark Grey","Black","Realistic","Random"};
JComboBox<String> objectDD = new JComboBox<String>(objectChoices);

String[] backgroundChoices = { "Black","White", "Blue","Dark Grey"};
JComboBox<String> backgroundDD = new JComboBox<String>(backgroundChoices);

private ArrayList<Object> ObjectPast = new ArrayList<Object>();
private ArrayList<Object> ObjectNow = new ArrayList<Object>();

    public Window() {
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(this);
        
        
       
      
       
       
       beginButton.setActionCommand("begin");
       beginButton.addActionListener(this);
        
        numberSlider.setMinorTickSpacing(5);
        numberSlider.setMajorTickSpacing(20);
        numberSlider.setPaintTicks(true);
        numberSlider.setPaintLabels(true);
        numberSlider.setLabelTable(numberSlider.createStandardLabels(10));
        numberSlider.setPreferredSize(new Dimension(500, 50));
        numberSlider.setBackground(Color.WHITE);
        
        varSlider.setMinorTickSpacing(1);
        varSlider.setMajorTickSpacing(5);
        varSlider.setPaintTicks(true);
        varSlider.setPaintLabels(true);
        varSlider.setLabelTable(varSlider.createStandardLabels(5));
        varSlider.setPreferredSize(new Dimension(500, 50));
        varSlider.setBackground(Color.WHITE);
        
        massSlider.setMinorTickSpacing(5);
        massSlider.setMajorTickSpacing(1);
        massSlider.setPaintTicks(true);
        massSlider.setPaintLabels(true);
        massSlider.setLabelTable(numberSlider.createStandardLabels(5));
        massSlider.setPreferredSize(new Dimension(800, 50));
        massSlider.setBackground(Color.WHITE);
        
     mainPannel.setBounds(200, 200, 500, 500);
        numberPannel.setLayout(new GridLayout(2, 1, 0, 0));
        varPannel.setLayout(new GridLayout(2, 1, 0, 0));
        massPannel.setLayout(new GridLayout(2, 1, 0, 0));
       
     
        numberPannel.add(new JLabel("Number of Objects"));
        numberPannel.add(numberSlider);
        
        varPannel.add(new JLabel("Maximum mass of objects"));
        varPannel.add(varSlider);
        
        massPannel.add(new JLabel("Starting Player Mass"));
        massPannel.add(massSlider);
        
        colorPanel.add(playerCPanel);
        colorPanel.add(objectCPanel);
        colorPanel.add(backgroundCPanel);
        colorPanel.setLayout(new GridLayout(1, 3, 0, 0));
        colorPanel.setBackground(Color.WHITE);
        
        playerCPanel.add(new JLabel("Player Color:"));
        playerCPanel.add(playerDD);
        playerCPanel.setBackground(Color.WHITE);
        
        objectCPanel.add(new JLabel("Other Asteroid Color:"));
        objectCPanel.add(objectDD);
        objectCPanel.setBackground(Color.WHITE);
        
        backgroundCPanel.add(new JLabel("Background Color:"));
        backgroundCPanel.add(backgroundDD);
        backgroundCPanel.setBackground(Color.WHITE);
        
        mainPannel.add(numberPannel);
        mainPannel.add(varPannel);
        mainPannel.add(massPannel);
        mainPannel.add(colorPanel);
        mainPannel.add(difficulty);
        mainPannel.add(beginButton);
      
         
          
         mainPannel.setLayout(new GridLayout(6, 2, 100, 30));
         mainPannel.setBackground(Color.WHITE);
         varPannel.setBackground(Color.WHITE);
         massPannel.setBackground(Color.WHITE);
         numberPannel.setBackground(Color.WHITE);
         
         setBackground(Color.WHITE);
         this.add(mainPannel);
       
       
       addMouseMotionListener(this);
      
        _timer = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                paintInterval();
            }

        });
        _timer.start();
        
              
          }
    private void paintInterval() {
        //maths 
        if(start){
        	
        	
        	
        	
       playerExists= false;
    	total = 0;
        time=time+0.025;
    	
        for (Object s1 : ObjectNow) {
            for (Object s2 : ObjectNow) {
          if (s1 == s2){
              continue;
          }
          int xdistance = s2.getX()-s1.getX();
          int ydistance =  s2.getY()-s1.getY();
          
          double radius = Math.sqrt((xdistance*xdistance)+(ydistance*ydistance)); 
          if (radius < (s1.getDiameter()/2)+(s2.getDiameter()/2)){
             if(s1.getMass()>=s2.getMass()){
            	 int totalMass = s1.getMass()+s2.getMass();
            	 //average the colors based on each objects mass
                int red = (int)(s1.getColor().getRed()*((double)s1.getMass()/(double)totalMass) + s2.getColor().getRed()*((double)s2.getMass()/(double)totalMass));
                int green = (int)(s1.getColor().getGreen()*((double)s1.getMass()/(double)totalMass) + s2.getColor().getGreen()*((double)s2.getMass()/(double)totalMass));
                int blue = (int)(s1.getColor().getBlue()*((double)s1.getMass()/(double)totalMass) + s2.getColor().getBlue()*((double)s2.getMass()/(double)totalMass));
               s1.setColor(new Color(red,green,blue));
                //average velocity based on mass of objects
                 s1.setxVelocity((s1.getxVelocity()*((double)s1.getMass()/(double)totalMass))+(s2.getxVelocity()*((double)s2.getMass()/(double)totalMass)));
                 s1.setyVelocity((s1.getyVelocity()*((double)s1.getMass()/(double)totalMass))+(s2.getyVelocity()*((double)s2.getMass()/(double)totalMass)));
         
                  s1.setMass(totalMass);
                 ObjectNow.remove(s2);
                 return;
                 
             }
                 
          }
          int mass = s2.getMass();
          double vol = (double)((mass/(radius*radius))/3); 
          double volP = (vol/radius);
          double xvol = xdistance*volP;
          double yvol = ydistance*volP;
                  
          s1.setxVelocity(s1.getxVelocity()+xvol);
          s1.setyVelocity(s1.getyVelocity()+yvol);
           
         
        }
           s1.testBorder(this.getWidth(), this.getHeight());
            
            if(s1.getType()){
            	s1.setColor(playerColor);
               playerExists = true;
            	int xdistance = mouseX-s1.getX();
                int ydistance =  mouseY-s1.getY();
                
                
                double radius = Math.sqrt((xdistance*xdistance)+(ydistance*ydistance)); 
                if(radius>0){
                double radiusMult = goVol/radius;
                double xvol = xdistance*radiusMult;
                double yvol = ydistance*radiusMult;
                s1.setxVelocity(s1.getxVelocity()+xvol);
                s1.setyVelocity(s1.getyVelocity()+yvol);
                }
                score=s1.getMass();
                
                if(s1.getxVelocity()>10){
                    s1.setxVelocity(10);
              }
               if(s1.getxVelocity()<-10){
                    s1.setxVelocity(-10);
                }
               if(s1.getyVelocity()>10){
                    s1.setyVelocity(10);
                }
                if(s1.getyVelocity()<-10){
                   s1.setyVelocity(-10);
                }

            
            }
            total = total + s1.getMass();
   
        }
        
          for (Object s : ObjectNow) {
            s.tick();
        }
         
    }
        difficultyInt=(varSlider.getValue()*numberSlider.getValue())/massSlider.getValue();
         this.repaint();
    }
    
    public void paint(Graphics g) {
        difficulty.setText("Difficulty: "+Integer.toString((int)difficultyInt)+" / 3000 (over 500 is probobly imbosible)");
    	difficulty.repaint();
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
     if(!start){
        int playerDiameter = (int)((Math.sqrt((massSlider.getValue())/3.14)*4)); 
        g2d.drawOval(100-(playerDiameter/2), 370-(playerDiameter/2), playerDiameter, playerDiameter);
        int objectDiameter = (int)(Math.sqrt((varSlider.getValue())/3.14)*4)+3;
        g2d.drawOval(100-(objectDiameter/2), 200-(objectDiameter/2), objectDiameter, objectDiameter);
     }
        if(start){
        	
        setBackground(backgroundColor);
        
        
           for (Object s : ObjectNow) {
            s.paint(g2d);
        }
         Font font = new Font("Verdana", Font.BOLD, 20);
            g2d.setFont(font);
            g2d.setColor(playerColor);
            g2d.drawString("Score: " +(score-startMass)+"/"+(total-startMass), 5, 5 + font.getSize());
              g2d.drawString("Time: " + Math.round(time), 1060, 5 + font.getSize());
              
              if(!playerExists){
            	  g2d.drawString("Game Over [esc] to restart ", 500, 5 + font.getSize()); 
              }
              if(((score+1)/(total+1))==1){
            	  g2d.drawString("You Won [esc] to restart ", 500, 5 + font.getSize()); 
              }
        }   
    }
    
   @Override
    public void mouseMoved(MouseEvent e) {
       mouseX = e.getX();
       mouseY = e.getY();
       
       
	 }
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

    @Override
    public void keyTyped(KeyEvent e) {
         
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
             case KeyEvent.VK_SPACE:
                
             //    p1.setVisible(false);
                 
                if(goVol<0.05){
                goVol=goVol+0.01;
                }
                break; 
                 
             case KeyEvent.VK_ESCAPE:
                 
               ObjectNow.clear();
               start = false;
              mainPannel.setVisible(true);
              setBackground(Color.WHITE);
               time = 0;
               
                 break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      switch (e.getKeyCode()) {
             case KeyEvent.VK_SPACE:
                
                goVol=0;
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getActionCommand()=="begin"){
           start = true;
           mainPannel.setVisible(false);
           startMass = massSlider.getValue();
           objectMassMax = varSlider.getValue();
           objectNumber = numberSlider.getValue();
           
           if(playerDD.getItemAt(playerDD.getSelectedIndex())=="Red"){
               playerColor = Color.RED;
           }
           if(playerDD.getItemAt(playerDD.getSelectedIndex())=="Blue"){
               playerColor = Color.CYAN;
           }
           if(playerDD.getItemAt(playerDD.getSelectedIndex())=="Green"){
               playerColor = Color.GREEN;
           }
           if(playerDD.getItemAt(playerDD.getSelectedIndex())=="Pink"){
               playerColor = Color.PINK;
           }
           if(playerDD.getItemAt(playerDD.getSelectedIndex())=="Yellow"){
               playerColor = Color.YELLOW;
           }
           if(playerDD.getItemAt(playerDD.getSelectedIndex())=="White"){
               playerColor = Color.WHITE;
           }
           
           
           if(objectDD.getItemAt(objectDD.getSelectedIndex())=="White"){
               objectsColor = Color.WHITE;
           }
           if(objectDD.getItemAt(objectDD.getSelectedIndex())=="Light Grey"){
               objectsColor = Color.LIGHT_GRAY;
           }
           if(objectDD.getItemAt(objectDD.getSelectedIndex())=="Dark Grey"){
               objectsColor = Color.DARK_GRAY;
           }
           if(objectDD.getItemAt(objectDD.getSelectedIndex())=="Black"){
               objectsColor = Color.BLACK;
           }
           if(objectDD.getItemAt(objectDD.getSelectedIndex())=="Random"){
               randomC = true;
           }
           if(objectDD.getItemAt(objectDD.getSelectedIndex())=="Realistic"){
               objectsColor = new Color(184,129,59);
               realistic  = true;
           }
           
           if(backgroundDD.getItemAt(backgroundDD.getSelectedIndex())=="Black"){
               backgroundColor = Color.BLACK;
           }
            if(backgroundDD.getItemAt(backgroundDD.getSelectedIndex())=="White"){
               backgroundColor = Color.WHITE;
           }
             if(backgroundDD.getItemAt(backgroundDD.getSelectedIndex())=="Blue"){
               backgroundColor = Color.BLUE;
           }
             if(backgroundDD.getItemAt(backgroundDD.getSelectedIndex())=="Dark Grey"){
               backgroundColor = Color.DARK_GRAY;
           } 
             
           
           
           
           for(int i = 1 ; i<objectNumber; i++){
        	   if(randomC){
        		   ObjectNow.add(new Object( (int)(Math.random()*1000),(int)(Math.random()*732),(int)(Math.random()*objectMassMax)+1,0,0,false,new Color((int)(Math.random()*255),(int)( Math.random()*255),(int)(Math.random()*255))));  
        	   }
        	   if(realistic){
        		   ObjectNow.add(new Object( (int)(Math.random()*1000),(int)(Math.random()*732),(int)(Math.random()*objectMassMax)+1,0,0,false,new Color((int)(Math.random()*80)+145,(int)( Math.random()*80)+80,(int)(Math.random()*80)+15)));
        	   }
        	   else{
        		
        		  ObjectNow.add(new Object( (int)(Math.random()*1000),(int)(Math.random()*732),(int)(Math.random()*objectMassMax)+1,0,0,false,objectsColor));  
        	   }
          
       } 
       ObjectNow.add(new Object( 1100,450,startMass,0,0,true,playerColor));
           
       }
    }
    
 

    
}

