// Console application that asks for a number between 1 and 100
// and then prints factors for that number.
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;

public class Factors {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String ans = "w";

		while (true) {
			int decition = promptAction(br);
			if (decition == 3) {
				return;
			}
			if ( decition == 1) {
				int n = prompt(br);
				if (n != 0) {
					System.out.println(factors(n));
				}
				System.out.println();

			}

			if (decition == 2) {
				ArrayList<Integer> factorsList2 = new ArrayList<Integer>();
				ArrayList<Integer> factorsList1 = new ArrayList();
				int GDF = -5;
				int n = prompt(br);
				if (n != 0) {
					factorsList1 = factors(n);
				}

				n = prompt(br);
				if (n != 0) {
					factorsList2 = factors(n);
				}

				for (Integer i : factorsList1) {
					if (factorsList2.contains(i) && i > GDF) {
						GDF = i;
					}
				}
				if (GDF != -5) {
					System.out.println(GDF);
				} else {
					System.out.println("one number is not a integer");
				}
				System.out.println();
			}

		}

	}
	//prompts user for a number 1 to 100 prints error messages and returns 0 if not
	//otherwise it returns the number
	public static int prompt(BufferedReader br) throws IOException {

		System.out.print("Enter number 1 to 1OO:  ");
		String s;
		int num = 1;
		s = br.readLine();

		try {
			num = Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			System.out.println("thats not a integer");
			return 0;
		}
		if (num > 100) {
			System.out.println("number too big");
			return 0;
		}
		if (num < 1) {
			System.out.println("number too small");
			return 0;
		}

		return num;
	}
	//returns a list of factors for any integer in a list from least to greatest 
	public static ArrayList factors(int num) {
		ArrayList l = new ArrayList();

		for (int i = 1; i < num + 1; i++) {

			if (num % i == 0) {

				l.add(i);

			}
		}
		return l;
	}
	public static int promptAction(BufferedReader br) throws IOException {
		System.out.println("(f) to get factors");
		System.out.println("(g) to get greatest common factor");
		System.out.println("(q) to quit");
		String ans = br.readLine();
		if (ans.equals("f")){
			return 1;
		}
		if (ans.equals("g")){
			return 2;
		}
		if (ans.equals("q")){
			return 3;
		}
		else{
			return 0;
		}
		
		
	}
}



