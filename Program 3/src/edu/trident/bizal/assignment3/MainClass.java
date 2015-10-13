/**
 * This class simply contains the main function for the program.
 * It creates both the Cab object, and the Cab Interface, linking the two.
 * 
 * @author Marcus Bizal
 */


package edu.trident.bizal.assignment3;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainClass {

	public static void main(String[] args) {
		try 
		{
			double gas = 	Double.parseDouble((String) JOptionPane.showInputDialog(null, 
			        			"How much gas should the cab contain?\n(22.9 for full tank)",
			        			"Gas in Tank",
			        			JOptionPane.QUESTION_MESSAGE
							));
			
			ExtendedCab myCab = new ExtendedCab(gas);
			CabUI cabInterface = new CabUI(myCab);
			ManagementUI managementInterface = new ManagementUI(myCab);
		}
		catch(Exception e)
		{
			System.out.printf("Error: Please enter a number for gas.");
			System.exit(1);
		}
	}

}
