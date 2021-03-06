/**
 * This class extends the basic Cab class to
 * add improved functionality. The ExtendedCab
 * can now be serviced, and keeps track of costs.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment3;

import java.util.ArrayList;
import java.util.List;

public class ExtendedCab extends Cab {
	
	private static final double SERVICE_COST = 25.00;
	private static final int MILES_BETWEEN_SERVICE = 500;
	private double totalCosts;
	private int milesSinceService;
	boolean needsService = false;
	boolean isServiceOverriden = false;
	List<MaintenanceListener> listeners = new ArrayList<MaintenanceListener>();
	
	/**
	 * Extended constructor starts the cab with a
	 * specified number of gallons of gas.
	 */

	public ExtendedCab(double gallons) 
	{
		super();
		super.addGas(gallons);
		
		totalCosts = 0;
		milesSinceService = 0;
	}
	
	/**
	 * Returns the total costs for the cab.
	 */
	
	public double getTotalCosts()
	{
		return totalCosts;
	}
	
	/**
	 * Returns the miles since the cab was last serviced.
	 */
	
	public double getMilesSinceService()
	{
		return milesSinceService;
	}
	
	/**
	 * This is an extension of `recordTrip`
	 * simply to make sure the miles since
	 * last service is tracked.
	 */
	
	public double recordTrip(int miles) 
	{
		double fare = -1;
		if (!needsService || isServiceOverriden)
		{
			fare = super.recordTrip(miles);
			if (fare != -1)
			{
				milesSinceService += miles;
			}
			
			if (milesSinceService > MILES_BETWEEN_SERVICE)
			{
				notifyListeners(true);
				needsService = true;
				isServiceOverriden = false;
			}
		}
		return fare;
	}
	
	/**
	 * This is an extension of `addGas` that
	 * now takes a cost arguement as the cost
	 * per gallon of gas. The total cost for
	 * the gas is added the the cab costs.
	 */
	
	public double addGas(double gallons, double cost)
	{
		double gallonsInTank = super.addGas(gallons);
		if (gallonsInTank != -1)
		{
			totalCosts += gallons * cost;
		}
			
		return gallonsInTank;
	}
	
	/**
	 * This method resets the miles
	 * since the last service to zero,
	 * and adds a service cost to the
	 * cab's total costs.
	 */
	
	public void serviceCab()
	{
		milesSinceService = 0;
		totalCosts += SERVICE_COST;
		needsService = false;
		notifyListeners(false);
	}
	
	/**
	 * Returns the net earnings for the cab since the last reset.
	 */
	
	public double getNetEarnings()
	{
		return grossEarnings - totalCosts;
	}
	
	/**
	 * Extended reset to reset totalCosts.
	 * We DO NOT reset milesSinceService.
	 */
	
	public void reset()
	{
		super.reset();
		totalCosts = 0;
	}
	
	public void addMaintenanceListener(MaintenanceListener listener)
	{
		listeners.add(listener);
	}
	
	public void overrideServiceNeeded()
	{
		isServiceOverriden = true;
	}
	
	public void notifyListeners(boolean needed)
	{
		for (int i = 0; i < listeners.size(); i++) {
			MaintenanceListener listener = listeners.get(i);
			if (needed)
			{
				listener.maintenanceNeeded();
			}
			else
			{
				listener.maintenancePerformed();
			}
		}
	}
}
