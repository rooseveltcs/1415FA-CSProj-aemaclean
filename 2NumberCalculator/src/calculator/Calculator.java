package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Calculator  extends JFrame{

	 Window window = new Window();

	   public Calculator() {
	        add(window);
	        setTitle("Claculator");
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(250, 200);
	        setLocationRelativeTo(null);
	        setUndecorated(false);
	        setVisible(true);
	        setResizable(false);
	    }
	 
	   
	   public static void main(String[] args) {
	       new Calculator();
	    }
	    
	
   
}







