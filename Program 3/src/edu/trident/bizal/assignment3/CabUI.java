/**
 * This class extends BasicUI and adds actions specific to 
 * what our Cab drivers may have to do on a daily basis.
 * This UI disables the override button.
 * 
 * @author Marcus Bizal
 */


package edu.trident.bizal.assignment3;

import javax.swing.JTextArea;

public class CabUI extends BasicUI implements MaintenanceListener {

	public CabUI(ExtendedCab cab_) 
	{
		super(cab_);
		cab_.addMaintenanceListener(this);
		setTitle("Cab Console");
		
		overrideBtn.setVisible(false);
		overrideBtn.setEnabled(false);
	}
	
	@Override
	public void maintenanceNeeded() {
		needsMaintenanceLbl.setText(MAINT_NEEDED);
	}

	@Override
	public void maintenancePerformed() {
		needsMaintenanceLbl.setText(NO_MAINT_NEEDED);
	}

	@Override
	void setupActions() {
		addAction("Record Trip", true, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				try 
				{
					int miles = Integer.parseInt(primaryField);
					double fare = cab.recordTrip(miles);
					if (fare != -1)
					{
						textArea.append("Total fare for this trip is: $" + fare + "!\n");
					}
					else
					{
						textArea.append("You don't have enough gas to complete the trip,\n"
										+ "or your cab needs to be serviced!\n");
					}
				}
				catch(Exception e)
				{
					if (e instanceof NumberFormatException)
					{
						textArea.append("[!!] " + e.toString() + "\n"
								+ "Please input only INTEGER values for trip recording!");
					}
				}
			}
		});
		
		addAction("Add Gas", true, true, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				try 
				{
					double gasToAdd = Double.parseDouble(primaryField);
					double costPerGallon = Double.parseDouble(secondaryField);
					double gasAvailable = cab.addGas(gasToAdd, costPerGallon);
					if (gasAvailable != -1)
					{
						textArea.append("Added " + gasToAdd + " gallons to tank.\n"
									  + "The tank now contains " + gasAvailable + " gallons.\n");
					}
					else
					{
						textArea.append("Can't add " + gasToAdd + " more gallons to the tank.\n"
									  + "It already has " + cab.getGas() + " gallons in it.\n"
									  + "Please add " + cab.getGasToFill() + " gallons to fill the tank.\n");
					}
				}
				catch(Exception e)
				{
					if (e instanceof NumberFormatException)
					{
						textArea.append("[!!] " + e.toString() + "\n"
								+ "Please input only number values for the amount of gas to add and cost!");
						
					}
				}
			}
		});
		
		addAction("Report Gas Available", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.append(cab.getGas() + " gallons available.\n");
			}
		});
		
		addAction("Report Miles Available", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.append("The cab can drive " + cab.getMilesAvailable() + " miles on currently available gas.\n");
			}
		});
		
		addAction("Report Miles Driven", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.append(cab.getMilesSinceReset() + " miles driven since last reset.\n");
			}
		});
		
		addAction("Service", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
	    		cab.serviceCab();
	    		textArea.append("Service completed!\n");
			}
		});
	}
}
