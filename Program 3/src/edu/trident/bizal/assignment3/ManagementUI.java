/**
 * This class extends BasicUI and adds actions specific to 
 * what our management may have to do on a daily basis.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment3;

import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ManagementUI extends BasicUI implements MaintenanceListener {
	
	public ManagementUI(ExtendedCab cab_) {
		super(cab_);
		cab_.addMaintenanceListener(this);
		setTitle("Management Console");
	}

	@Override
	public void maintenanceNeeded() {
		needsMaintenanceLbl.setText(MAINT_NEEDED);
		overrideBtn.setEnabled(true);
	}

	@Override
	public void maintenancePerformed() {
		needsMaintenanceLbl.setText(NO_MAINT_NEEDED);
		overrideBtn.setEnabled(false);
	}

	@Override
	void setupActions() {
		addAction("Report Gross Earnings", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.append("$" + cab.getGrossEarnings() + " gross earned since last reset.\n");
			}
		});
		
		addAction("Report Miles Since Service", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.append(cab.getMilesSinceService() + " miles driven since last service.\n");
			}
		});
		
		addAction("Report Net Earnings", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.append("$" + cab.getNetEarnings() + " net earned since last reset.\n");
			}
		});
		
		addAction("Reset", false, false, new Action() {
			public void fire(ExtendedCab cab, String primaryField, String secondaryField, JTextArea textArea)
			{
				textArea.setText("");
				cab.reset();
			}
		});
	}
}
