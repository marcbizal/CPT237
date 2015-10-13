/**
 * This class creates an abstract interface for the Cab object,
 * and provides Swing components to test each function of it.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment3;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

interface Action {
	void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea);
}

abstract public class BasicUI extends JFrame implements ActionListener
{
	
	protected static final String MAINT_NEEDED = "Maintenance Needed!";
	protected static final String NO_MAINT_NEEDED = "Cab is good to go!";
	
    protected static final long serialVersionUID = 1L;
    
    protected ExtendedCab cab = null;
    
    // Since these parallel lists are now managed
    // directly by the program, they shouldn't be
    // a problem.
    
    protected List<String> actionNames = new ArrayList<String>();
    protected List<boolean[]> enabledFields = new ArrayList<boolean[]>();
    protected List<Action> actions = new ArrayList<Action>();
    
    protected JPanel mainPanel;
    
    protected JComboBox<String> actionSelect;
    protected int currentAction;
    
    protected JTextField primaryField;
    protected JTextField secondaryField;
    
	protected JButton okBtn;

	protected JLabel needsMaintenanceLbl;
	protected JButton overrideBtn;
	
	protected JTextArea textArea;
	
	// This function initially sets both fields to be visible,
	// then it iterates through all of the actions to check if
	// the fields are needed, if none of the actions require that
	// field, then the field is hidden.
	
	protected void disableUnneededFields()
	{
        boolean[] enabled = { false, false };
        
        for (int i = 0; i < enabledFields.size(); i++) {
			enabled[0] = enabled[0] || enabledFields.get(i)[0];
			enabled[1] = enabled[1] || enabledFields.get(i)[1];
		}
        
        primaryField.setVisible(enabled[0]);
        secondaryField.setVisible(enabled[1]);
	}
	
	protected boolean fieldNotEmpty(JTextField field)
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
	
	// This class is abstracted because extensions of this
	// class must set up actions before the interface is
	// created.
	abstract void setupActions();
	
	public BasicUI(ExtendedCab cab_) 
	{
		cab = cab_;

		setupActions();
		
		// Set up the JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));
        
        // Interface elements are grouped into panels to keep things organized.
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));
        
        // START ACTION PANEL
        JPanel actionPanel = new JPanel();
        actionSelect = new JComboBox(actionNames.toArray());
        primaryField 	= new JTextField(10);
        secondaryField 	= new JTextField(10);
        okBtn 			= new JButton("OK");

        disableUnneededFields();
        
        actionSelect.addActionListener(this);
        okBtn.addActionListener(this);
        
        actionPanel.add(actionSelect);
        actionPanel.add(primaryField);
        actionPanel.add(secondaryField);
        actionPanel.add(okBtn);
        mainPanel.add(actionPanel);
        // END ACTION PANEL
        
		// START MAINTENANCE PANEL
		JPanel maintenancePanel = new JPanel();
		
		needsMaintenanceLbl		= new JLabel(NO_MAINT_NEEDED);
		overrideBtn 			= new JButton("Override");
		
		overrideBtn.setEnabled(false);
		overrideBtn.addActionListener(this);
		
		maintenancePanel.add(needsMaintenanceLbl);
		maintenancePanel.add(overrideBtn);
		
		mainPanel.add(maintenancePanel);
		// END MAINTENANCE PANEL
       
        add(mainPanel);
        
		// Finally the JTextArea for reporting output.
		textArea = new JTextArea(10, 28);
		textArea.setEditable(false);
		add(new JScrollPane(textArea));
		
		pack();
		setLocationByPlatform(true);
		
		// Show the window.
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		// Find out which operation is currently selected		
		if (source == actionSelect) 
		{
			String actionName = (String)actionSelect.getSelectedItem();
			currentAction = actionNames.indexOf(actionName);

			primaryField.setEditable(enabledFields.get(currentAction)[0]);
			primaryField.setText("");
			secondaryField.setEditable(enabledFields.get(currentAction)[1]);
			secondaryField.setText("");
		}
		
		if (source == okBtn)
		{
			if ((enabledFields.get(currentAction)[0] == fieldNotEmpty(primaryField)) &&
				(enabledFields.get(currentAction)[1] == fieldNotEmpty(secondaryField)))
			{
				actions.get(currentAction).fire(cab, primaryField.getText(), secondaryField.getText(), textArea);
			}
			else
			{
				textArea.append("[!!] One or both fields is empty!\n");
			}
		}
		
		if (source == overrideBtn)
		{
			cab.overrideServiceNeeded();
			overrideBtn.setEnabled(false);
			textArea.append("Service overrided for one trip!.\n");
		}
	}
}
