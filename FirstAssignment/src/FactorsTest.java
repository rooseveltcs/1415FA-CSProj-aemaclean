import org.junit.Test;
import org.junit.Assert;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.*;
import java.io.IOException;

public class FactorsTest {
	@Test 
	public void factorsOfEven() {
		Assert.assertTrue(Factors.factors(20).size()==6);
		Assert.assertTrue(Factors.factors(20).contains(20));
		Assert.assertTrue(Factors.factors(20).contains(1));


	}
	@Test 
	public void factorsOfOdd() {
		Assert.assertTrue(Factors.factors(21).size()==4);
		Assert.assertTrue(Factors.factors(21).contains(7));
		Assert.assertTrue(Factors.factors(21).contains(1));


	}
	@Test 
	public void factorsOfPrime() {
		Assert.assertTrue(Factors.factors(17).size()==2);
		Assert.assertTrue(Factors.factors(17).contains(17));
		Assert.assertTrue(Factors.factors(17).contains(1));


	}
	
	@Test
	public void inputIsNumber() throws IOException{
		StringReader sr1 = new StringReader("notnumber");
		BufferedReader br = new BufferedReader(sr1);
		Assert.assertTrue(Factors.prompt(br) ==0);
	}
	@Test
	public void inputIsInt() throws IOException{
		StringReader sr1 = new StringReader("3.12");
		BufferedReader br = new BufferedReader(sr1);
		Assert.assertTrue(Factors.prompt(br) ==0);
	}
	@Test
	public void inputInBounds() throws IOException{
		BufferedReader br = new BufferedReader(new StringReader("101"));
		Assert.assertTrue(Factors.prompt(br) ==0);
		br = new BufferedReader(new StringReader("100"));
		Assert.assertTrue(Factors.prompt(br) ==100);
		br = new BufferedReader(new StringReader("0"));
		Assert.assertTrue(Factors.prompt(br) ==0);
		br = new BufferedReader(new StringReader("1"));
		Assert.assertTrue(Factors.prompt(br) ==1);
		
	}
	@Test
	public void actionInput() throws IOException{
		BufferedReader br = new BufferedReader(new StringReader("f"));
		Assert.assertTrue(Factors.promptAction(br)==1);
		 br = new BufferedReader(new StringReader("q"));
		Assert.assertTrue(Factors.promptAction(br)==3);
		 br = new BufferedReader(new StringReader("assdasdfsddfgjghvc"));
		Assert.assertTrue(Factors.promptAction(br)==0);
		 br = new BufferedReader(new StringReader("g"));
		Assert.assertTrue(Factors.promptAction(br)==2);
		
	


	}
}
