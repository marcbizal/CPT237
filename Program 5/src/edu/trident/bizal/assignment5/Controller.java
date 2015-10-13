/**
 * This class serves to control the MultiCabUI,
 * initialize cabs, and perform other logic.
 * 
 * It is also responsible to finding the proper cab,
 * and loading into the UI.
 * 
 * UPDATE 09/30/15:
 * Controller now loads cabs from a csv file.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

interface CabLoader 
{
	void loadCab(String cabName);
}

public class Controller implements CabLoader
{
	
		private static final String CAB_FILE = "cabs.csv";
		private ArrayList<ExtendedCab> cabs = new ArrayList<ExtendedCab>();
		private MultiCabUI ui;
		
		private Random randGen = new Random();
		public double randDouble(double min, double max) 
		{
		    return min + (max - min) * randGen.nextDouble();
		}
		
		private void loadCabsFromFile(String filename)
		{
			BufferedReader br = null;
			String line = "";
			try 
			{
				br = new BufferedReader(new FileReader(filename));
				while ((line = br.readLine()) != null)
				{
					String components[] = line.split(",");
					if (components.length == 4)
					{
						String cabId = components[0];
						double mpg = Double.parseDouble(components[1]);
						double serviceCost = Double.parseDouble(components[2]);
						double tankSize = Double.parseDouble(components[3]);
						
						cabs.add(new ExtendedCab(cabId, mpg, serviceCost, tankSize));
					}
					else
					{
						JOptionPane.showMessageDialog(
								null, 
								"Invalid entry in " + filename + ":\n" + line, 
								"Warning!",
		                        JOptionPane.WARNING_MESSAGE
								);
					}
				}
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}
		
		private String[] getCabNames()
		{
			ArrayList<String> cabNames = new ArrayList<String>();
			
			for (ExtendedCab cab : cabs) {
				cabNames.add(cab.getName());
			}
			
			return cabNames.toArray(new String[cabNames.size()]);
		}
		
		public Controller()
		{
			loadCabsFromFile(CAB_FILE);
			
			ui = new MultiCabUI(getCabNames(), this);
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
