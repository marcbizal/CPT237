/**
 * This class extends the basic Cab class to
 * add improved functionality. The ExtendedCab
 * can now be serviced, and keeps track of costs.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment2;

public class ExtendedCab extends Cab {
	
	private static final double SERVICE_COST = 25.00;
	private double totalCosts;
	private int milesSinceService;
	
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
		double fare = super.recordTrip(miles);
		if (fare != -1)
		{
			milesSinceService += miles;
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
		double gallonsAdded = super.addGas(gallons);
		if (gallonsAdded != -1)
		{
			totalCosts += gallonsAdded * cost;
		}
			
		return gallonsAdded;
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
}
