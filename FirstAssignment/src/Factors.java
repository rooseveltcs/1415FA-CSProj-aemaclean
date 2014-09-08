
// Console application that asks for a number between 1 and 100
// and then prints factors for that number.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;

public class Factors {
	public static void main(String[] args) throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //yay yay
         while(true){
        System.out.print("Enter number 1 to 1OO:  ");
        String s; 
       boolean works=true;
        int num=1;
        boolean first=true;
        s=br.readLine();
       
     
       if(s.toLowerCase().equals("quit")){
        	return;
        }
   //     System.out.println(s);
        try{
            num = Integer.parseInt(s);
        }catch(NumberFormatException nfe){
            System.out.println("thats not a integer");
            works=false;
        }
        if (num>100){
           System.out.println("number too big"); 
           works=false;
        }
        if (num<1){
           System.out.println("number too small");
           works=false;
        }
       
  if(works){
        System.out.print("Factors: ");
        for(int i = 1; i<num+1;i++){
            
            if (num%i == 0){
              if(!first){
                System.out.print(", ");  
              }
              System.out.print(i);
              first=false;
            }
        }
      System.out.println(" ") ;
  }
   
    }
	}
}


