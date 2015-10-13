/**
 * This class simply contains the main function for the program.
 * It creates both the Cab object, and the Cab Interface, linking the two.
 * 
 * @author Marcus Bizal
 */


package edu.trident.bizal.assignment1;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cab myCab = new Cab();
		myCab.addGas(22.9); // full tank
		
		CabInterface ui = new CabInterface(myCab);
	}

}
