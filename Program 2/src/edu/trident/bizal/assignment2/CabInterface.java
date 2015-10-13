/**
 * This class creates an interface for the Cab object,
 * and provides elements to test each function of it.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CabInterface extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    
    private static enum operations {
    	RECORD_TRIP,
    	ADD_GAS,
    	REPORT_GAS_AVAILABLE,
    	REPORT_MILES_AVAILABLE,
    	REPORT_MILES_DRIVEN,
    	REPORT_GROSS_EARNINGS,
    	REPORT_MILES_SINCE_SERVICE,
    	REPORT_NET_EARNINGS,
    	SERVICE,
    	RESET
    };
    
    private static final String[] operationNames = {
    	"Record Trip", 
    	"Add Gas",
    	"Report Gas Available",
    	"Report Miles Available",
    	"Report Miles Driven",
    	"Report Gross Earnings",
    	"Report Miles Since Service", 
    	"Report Net Earnings",
    	"Service",
    	"Reset"
    };
    
    private static final boolean[][] enabledFields = {
    	{ true, false }, 
    	{ true, true }, 
    	{ false, false }, 
    	{ false, false }, 
    	{ false, false }, 
    	{ false, false }, 
    	{ false, false }, 
    	{ false, false }, 
    	{ false, false }, 
    	{ false, false }
    };

    private ExtendedCab cab = null;
    
    private JComboBox<String> operationSelect;
    private int currentOperation;
    
    private JTextField primaryField;
    private JTextField secondaryField;
    
	private JButton okBtn;
	
	private JTextArea textArea;
	
	private boolean fieldNotEmpty(JTextField field)
	{
		return !field.getText().equals("");
	}
	
	public CabInterface(ExtendedCab cab_) 
	{
		cab = cab_;
		
		// Set up the JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,0));
        
        // Interface elements are grouped into panels to keep things organized.
        
        // Start Action Panel
        JPanel actionPanel = new JPanel();
        operationSelect = new JComboBox(operationNames);
        primaryField 	= new JTextField(10);
        secondaryField 	= new JTextField(10);
        okBtn 			= new JButton("OK");
        
        operationSelect.addActionListener(this);
        okBtn.addActionListener(this);
        
        actionPanel.add(operationSelect);
        actionPanel.add(primaryField);
        actionPanel.add(secondaryField);
        actionPanel.add(okBtn);
        
        add(actionPanel);
        // End Action Panel
       
		// Finally the JTextArea for reporting output.
		textArea = new JTextArea(10, 28);
		textArea.setEditable(false);
		add(new JScrollPane(textArea));
		
		this.pack();
		
		// Show the window.
		setVisible(true);
	}
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		// Find out which operation is currently selected		
		if (source == operationSelect) 
		{
			String operationName = (String)operationSelect.getSelectedItem();
			for (int i = 0; i < operationNames.length; i++)
			{
				if (operationName == operationNames[i])
				{
					primaryField.setEditable(enabledFields[i][0]);
					primaryField.setText("");
					secondaryField.setEditable(enabledFields[i][1]);
					secondaryField.setText("");
					currentOperation = i;
				}
			}
		}
		
		if (source == okBtn)
		{
			if ((enabledFields[currentOperation][0] == fieldNotEmpty(primaryField)) &&
				(enabledFields[currentOperation][1] == fieldNotEmpty(secondaryField)))
			{
				switch (operations.values()[currentOperation]) {
					case RECORD_TRIP: 
					{
						try 
						{
							int miles = Integer.parseInt(primaryField.getText());
							double fare = cab.recordTrip(miles);
							if (fare != -1)
							{
								textArea.append("Total fare for this trip is: $" + fare + "!\n");
							}
							else
							{
								textArea.append("You have entered an invalid number of miles,\n"
										+ "or you don't have enough gas to complete the trip!\n");
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
						break;
					}
					case ADD_GAS:
					{
						try 
						{
							double gasToAdd = Double.parseDouble(primaryField.getText());
							double costPerGallon = Double.parseDouble(secondaryField.getText());
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
						break;
					}
					case REPORT_GAS_AVAILABLE:
					{
						textArea.append(cab.getGas() + " gallons available.\n");
						break;
					}
					case REPORT_MILES_AVAILABLE:
					{
						textArea.append("The cab can drive " + cab.getMilesAvailable() + " miles on currently available gas.\n");
						break;
					}
					case REPORT_MILES_DRIVEN:
					{
						textArea.append(cab.getMilesSinceReset() + " miles driven since last reset.\n");
						break;
					}
					case REPORT_GROSS_EARNINGS:
					{
						textArea.append("$" + cab.getGrossEarnings() + " gross earned since last reset.\n");
						break;
					}
					case RESET:
					{
						textArea.setText("");
						cab.reset();
						break;
					}
					case REPORT_MILES_SINCE_SERVICE:
					{
						textArea.append(cab.getMilesSinceService() + " miles driven since last service.\n");
						break;
					}
					case REPORT_NET_EARNINGS:
					{
						textArea.append("$" + cab.getNetEarnings() + " net earned since last reset.\n");
						break;
					}
					case SERVICE:
			    	{
			    		cab.serviceCab();
			    		textArea.append("Service completed!\n");
						break;
			    	}
		            default:
		                break;
				}
			}
			else
			{
				textArea.append("[!!] One or both fields is empty!\n");
			}
		}
	}
}
