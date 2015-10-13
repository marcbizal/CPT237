/**
 * This class creates an interface for the Cab object,
 * and provides elements to test each function of it.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CabInterface extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;

    static final int WIDTH = 360;
    static final int HEIGHT = 390;
    
    Cab cab = null;
    
    JLabel tripLabel;
    JLabel gasLabel;
    
    JTextField tripMilesField;
    JTextField addGasField;
    
	JButton recordBtn;
	JButton addGasBtn;
	JButton gasAvailableBtn;
	JButton milesAvailableBtn;
	JButton milesDrivenBtn;
	JButton grossEarningsBtn;
	JButton resetBtn;
	
	private JTextArea textArea;
	
	private boolean fieldNotEmpty(JTextField field)
	{
		return !field.getText().equals("");
	}
	
	public CabInterface(Cab cab_) 
	{
		cab = cab_;
		
		// Set up the JFrame
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        setLayout(layout);
        
        // Interface elements are grouped into panels to keep things organized.
        
        // Start Trip Panel
        JPanel tripPanel = new JPanel();
        tripLabel 		= new JLabel("Trip:");
        tripMilesField 	= new JTextField(10);
        recordBtn 		= new JButton("Record Trip");
        
        recordBtn.addActionListener(this);
        
        tripPanel.add(tripLabel);
        tripPanel.add(tripMilesField);
        tripPanel.add(recordBtn);
        
        add(tripPanel);
        // End Trip Panel
        
        // Start Gas Panel
        JPanel gasPanel = new JPanel();
        gasLabel 	= new JLabel("Gas:");
        addGasField = new JTextField(10);
        addGasBtn 	= new JButton("Add Gas");
        
        addGasBtn.addActionListener(this);
        
        gasPanel.add(gasLabel);
        gasPanel.add(addGasField);
        gasPanel.add(addGasBtn);
        
        add(gasPanel);
        // End Gas Panel
        
        // Start Reports Panel
        JPanel reportsPanel = new JPanel();
        reportsPanel.setLayout(new GridLayout(2, 2));
    	gasAvailableBtn 	= new JButton("Get Gas Available");
    	milesAvailableBtn 	= new JButton("Get Miles Available");
    	milesDrivenBtn 		= new JButton("Get Miles Driven");
    	grossEarningsBtn 	= new JButton("Get Gross Earnings");
    	
    	gasAvailableBtn.addActionListener(this);
    	milesAvailableBtn.addActionListener(this);
    	milesDrivenBtn.addActionListener(this);
    	grossEarningsBtn.addActionListener(this);
    	
    	reportsPanel.add(gasAvailableBtn);
    	reportsPanel.add(milesAvailableBtn);
    	reportsPanel.add(milesDrivenBtn);
    	reportsPanel.add(grossEarningsBtn);
    	
    	add(reportsPanel);
    	// End Reports Panel
        
    	// Reset Button
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
		add(resetBtn);
		
		// Finally the JTextArea for reporting output.
		textArea = new JTextArea(10, 28);
		textArea.setEditable(false);
		add(new JScrollPane(textArea));
		
		// Show the window.
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		if (source == recordBtn && fieldNotEmpty(tripMilesField)) {
			try 
			{
				int miles = Integer.parseInt(tripMilesField.getText());
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
		}
		else if (source == addGasBtn && fieldNotEmpty(addGasField))
		{
			try 
			{
				double gasToAdd = Double.parseDouble(addGasField.getText());
				double gasAvailable = cab.addGas(gasToAdd);
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
							+ "Please input only number values for the amount of gas to add!");
					
				}
			}
		}
		else if (source == gasAvailableBtn)
		{
			textArea.append(cab.getGas() + " gallons available.\n");
		}
		else if (source == milesAvailableBtn)
		{
			textArea.append("The cab can drive " + cab.getMilesAvailable() + " miles on currently available gas.\n");
		}
		else if (source == milesDrivenBtn)
		{
			textArea.append(cab.getMilesSinceReset() + " miles driven since last reset.\n");
		}
		else if (source == grossEarningsBtn)
		{
			textArea.append("$" + cab.getGrossEarnings() + " earned since last reset.\n");
		}
		else if (source == resetBtn)
		{
			cab.reset();
			tripMilesField.setText("");
			addGasField.setText("");
			textArea.setText("");
		}
		
	}
}
