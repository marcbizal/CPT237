/**
 * This class serves as a UI to control multiple ExtendedCabs,
 * providing an interface to take a trip, add gas. service, and reset.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment4;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

interface Action {
	void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextField output);
}

public class MultiCabUI implements ActionListener {
	
	private JTextField gasAvailOut = new JTextField(12);
	private JTextField milesAvailOut = new JTextField(12);
	private JTextField milesSinceServiceOut = new JTextField(12);
	private JTextField milesSinceResetOut = new JTextField(12);
	private JTextField grossSinceResetOut = new JTextField(12);
	private JTextField netSinceResetOut = new JTextField(12);

    private List<String> actionNames = new ArrayList<String>();
    private List<boolean[]> enabledFields = new ArrayList<boolean[]>();
    private List<Action> actions = new ArrayList<Action>();
    
    private JComboBox<String> actionSelect;
    private int currentAction;
    
    private JTextField primaryField = new JTextField(12);
    private JTextField secondaryField = new JTextField(12);
    private JTextField output = new JTextField(12);
    
	private JButton okBtn = new JButton("OK");
    private JButton refreshBtn = new JButton("Refresh");
	
    private JComboBox<String> cabCombo;
    private CabLoader cabLoader;
	private ExtendedCab cab;
	
	public MultiCabUI(String[] cabNames, CabLoader cabLoader_)
	{
		cabLoader = cabLoader_;
		
        JLabel gasAvail = new JLabel("Gas Available");
        JLabel milesAvail = new JLabel("Miles Available");
        JLabel milesSinceService = new JLabel("Miles since service");
        JLabel milesSinceReset = new JLabel("Miles since reset");
        JLabel grossSinceReset = new JLabel("Gross earnings since reset");
        JLabel netSinceReset = new JLabel("Net earnings since reset");
        JFrame frame = new JFrame("Acme Cabs");
        JPanel pane1 = new JPanel();
        JPanel cabControlPanel = new JPanel();
        Container contentPane = frame.getContentPane();

        cabCombo = new JComboBox<String>(cabNames);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Cab Info"));
        statusPanel.setLayout(new GridLayout(0,2,5,5));
        statusPanel.add(cabCombo);
        statusPanel.add(refreshBtn);

        // disable all of the output fields
        // western panel
        gasAvailOut.setEnabled(false);
        milesAvailOut.setEnabled(false);
        milesSinceServiceOut.setEnabled(false);
        milesSinceResetOut.setEnabled(false);
        grossSinceResetOut.setEnabled(false);
        netSinceResetOut.setEnabled(false);

        
        // add the labels and output fields in pairs
        statusPanel.add(gasAvail);
        statusPanel.add(gasAvailOut);

        statusPanel.add(milesAvail);
        statusPanel.add(milesAvailOut);
        
        statusPanel.add(milesSinceService);
        statusPanel.add(milesSinceServiceOut);
        
        statusPanel.add(milesSinceReset);
        statusPanel.add(milesSinceResetOut);
        
        statusPanel.add(grossSinceReset);
        statusPanel.add(grossSinceResetOut);
        
        statusPanel.add(netSinceReset);
        statusPanel.add(netSinceResetOut);
        
        cabCombo.addActionListener(this);
        refreshBtn.addActionListener(this);
        
        // construct the second panel, place it in a secondary panel to
        // allow better sized fields
        contentPane.add(statusPanel, BorderLayout.WEST);
        JPanel p1 = new JPanel(); // dummy panel
        contentPane.add(p1, BorderLayout.EAST);
        p1.setBorder(BorderFactory.createTitledBorder("Cab Control"));
        p1.add(cabControlPanel);
        
        setupActions();

        cabControlPanel.setLayout(new GridLayout(0,1,5,5));
        actionSelect = new JComboBox(actionNames.toArray());
        
        secondaryField.setEnabled(false);
        output.setEnabled(false);
        
        actionSelect.addActionListener(this);
        okBtn.addActionListener(this);
        
        cabControlPanel.add(actionSelect);
        cabControlPanel.add(primaryField);
        cabControlPanel.add(secondaryField);
        cabControlPanel.add(output);
        
        JSeparator js = new JSeparator();
        js.setVisible(false);
        cabControlPanel.add(js);
        cabControlPanel.add(okBtn);

        // contentPane.add(cabControlPanel, BorderLayout.EAST);
        
        frame.pack();
        frame.setVisible(true);
	}
	
	public void refresh()
	{
		gasAvailOut.setText(Double.toString(cab.getGas()));
		milesAvailOut.setText(Double.toString(cab.getMilesAvailable()));
		milesSinceServiceOut.setText(Double.toString(cab.getMilesSinceService()));
		milesSinceResetOut.setText(Double.toString(cab.getMilesSinceReset()));
		grossSinceResetOut.setText(Double.toString(cab.getGrossEarnings()));
		netSinceResetOut.setText(Double.toString(cab.getNetEarnings()));
	}
	
	public void setCab(ExtendedCab cab_)
	{
		cab = cab_;
	}
	
	private boolean fieldNotEmpty(JTextField field)
	{
		return !field.getText().equals("");
	}
	
	public void addAction(String name, 
			boolean field1Enabled,
			boolean field2Enabled,
			Action action)
	{
		actionNames.add(name);
		enabledFields.add(new boolean[] {field1Enabled, field2Enabled});
		actions.add(action);
	}
	
	private void setupActions()
	{
		addAction("Record Trip", true, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextField output)
			{
				try 
				{
					int miles = Integer.parseInt(primaryField);
					double fare = cab.recordTrip(miles);
					if (fare != -1)
					{
						output.setText(Double.toString(fare));
					}
					else
					{
						JOptionPane.showMessageDialog(
								null, 
								"You don't have enough gas to complete the trip,\n" +
								"or your cab needs to be serviced!\n", 
								"Error!",
		                        JOptionPane.ERROR_MESSAGE
	                        );
					}
				}
				catch(Exception e)
				{
					if (e instanceof NumberFormatException)
					{
						JOptionPane.showMessageDialog(
								null, 
								e.toString() + "\n" + 
								"Please input only INTEGER values for trip recording!", 
								"Error!",
		                        JOptionPane.ERROR_MESSAGE
	                        );
					}
				}
			}
		});
		
		addAction("Add Gas", true, true, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextField output)
			{
				try 
				{
					double gasToAdd = Double.parseDouble(primaryField);
					double costPerGallon = Double.parseDouble(secondaryField);
					double gasAvailable = cab.addGas(gasToAdd, costPerGallon);
					if (gasAvailable != -1)
					{
						output.setText(Double.toString(gasAvailable));
					}
					else
					{
						JOptionPane.showMessageDialog(
								null, 
								"Can't add " + gasToAdd + " more gallons to the tank.\n" +
								"It already has " + cab.getGas() + " gallons in it.\n" +
								"Please add " + cab.getGasToFill() + " gallons to fill the tank.\n", 
								"Error!",
		                        JOptionPane.ERROR_MESSAGE
	                        );
					}
				}
				catch(Exception e)
				{
					if (e instanceof NumberFormatException)
					{
						JOptionPane.showMessageDialog(
								null, 
								e.toString() + "\n" +
								"Please input only number values for the amount of gas to add and cost!", 
								"Error!",
		                        JOptionPane.ERROR_MESSAGE
	                        );
						
					}
				}
			}
		});
		
		addAction("Service", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextField output)
			{
	    		cab.serviceCab();
	    		output.setText("Serviced.");
			}
		});
		
		addAction("Reset", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextField output)
			{
	    		cab.reset();
	    		output.setText("Reset.");
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == refreshBtn || source == cabCombo)
		{
			cabLoader.loadCab((String)cabCombo.getSelectedItem());
			refresh();
		}
		
		// Find out which operation is currently selected		
		if (source == actionSelect) 
		{
			String actionName = (String)actionSelect.getSelectedItem();
			currentAction = actionNames.indexOf(actionName);

			primaryField.setEnabled(enabledFields.get(currentAction)[0]);
			primaryField.setText("");
			secondaryField.setEnabled(enabledFields.get(currentAction)[1]);
			secondaryField.setText("");
			output.setText("");
		}
		
		if (source == okBtn)
		{
			if ((enabledFields.get(currentAction)[0] == fieldNotEmpty(primaryField)) &&
				(enabledFields.get(currentAction)[1] == fieldNotEmpty(secondaryField)))
			{
				actions.get(currentAction).fire(cab, primaryField.getText(), secondaryField.getText(), output);
			}
			else
			{
				JOptionPane.showMessageDialog(
							null, 
							"One or both fields is empty!", 
							"Error!",
	                        JOptionPane.ERROR_MESSAGE
                        );
			}
		}
	}
}
