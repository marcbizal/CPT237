/**
 * This class serves to control the MultiCabUI,
 * initialize cabs, and perform other logic.
 * 
 * It is also responsible to finding the proper cab,
 * and loading into the UI.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

interface CabLoader 
{
	void loadCab(String cabName);
}

public class Controller implements CabLoader
{
	
		private ArrayList<ExtendedCab> cabs = new ArrayList<ExtendedCab>();
		private MultiCabUI ui;
		
		private Random randGen = new Random();
		public double randDouble(double min, double max) 
		{
		    return min + (max - min) * randGen.nextDouble();
		}
		

		public Controller()
		{
			String cabNames[] = {"Bob", "Larry", "Carl", "Steve", "Thomas", "Brady"};
			
			// For the sake of this program, MPG and tankSize are randomly generated.
			for (String cabName : cabNames)
			{
				cabs.add(new ExtendedCab(cabName, randDouble(12, 16), randDouble(20, 30)));
			}
			
			ui = new MultiCabUI(cabNames, this);
			ui.setCab(cabs.get(0));
		}
		
		public void loadCab(String cabName)
		{
			for (ExtendedCab cab : cabs) {
				if (cab.getName() == cabName)
				{
					ui.setCab(cab);
					break;
				}
			}
		}
		
}
