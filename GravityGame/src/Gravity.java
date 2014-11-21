


import java.awt.Color;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
 
public class Gravity extends JFrame {
    
    Window1 window = new Window1();

   public Gravity() {
        add(window);
        setTitle("Asteroid Feild Domonator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setVisible(true);
        setResizable(false);
        
    }
 
   
   public static void main(String[] args) {
       new Gravity();
    }
    
}
