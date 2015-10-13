/**
 * This class contains the main loop for the program,
 * and acts as a unit test for MathDemo. 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment0;

import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		boolean running = true;
		while (running)
		{	
			System.out.println("Please input a value for x:");
			double x = input.nextDouble();
			System.out.println("Please input a value for y:");
			double y = input.nextDouble();
			
			MathDemo demo = new MathDemo(x, y);
			System.out.println("Min:\t\t" + demo.min());
			System.out.println("Min:\t\t" + demo.max() + "\n");
			
			System.out.println("Please input a power for `productToPower()`:");
			double pProductToPower = input.nextDouble();
			System.out.println("p = " + pProductToPower);
			System.out.println("ProductToPower:\t\t" + demo.productToPower(pProductToPower) + "\n");
			
			System.out.println("Please input a mod (%) for `modOfPower()`:");
			double pModOfPower = input.nextDouble();
			System.out.println("p = " + pModOfPower);
			System.out.println("ModOfPower:\t\t" + demo.modOfPower(pModOfPower) + "\n");
			
			System.out.println("Please input a sign (+/-) for `signProduct()`:");
			double pSignProduct = input.nextDouble();
			System.out.println("p = " + pSignProduct);
			System.out.println("SignProduct:\t\t" + demo.signProduct(pSignProduct) + "\n");
			
			// Moved this to the end... Made more sense here.
			System.out.println("Would you like to continue? (y/n)");
			String shouldContinue = input.next();
			if (!shouldContinue.equalsIgnoreCase("y"))
			{
				System.out.println("Quitting...");
				running = false;
				// continue; // This is needed if this section is moved back to the top.
			}
		}
		input.close();
	}

}
