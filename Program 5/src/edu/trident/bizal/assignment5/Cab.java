/**
 * This class simulates some basic functions of a Cab Car 
 * that might be useful to a cab driver, including 
 * filling the tank, and recording trips, and reporting 
 * various information.
 * 
 * @author Marcus Bizal
 * 
 * TODO: Fix decimal places on output.
 * TODO: Refine interface
 */


package edu.trident.bizal.assignment5;

public class Cab {
	// Named Constants
	protected static final double MPG = 17.8;
	protected static final double TANK_SIZE = 22.9;
	protected static final double BASE_FARE = 2.00;
	protected static final double MILE_FARE = 0.585;
	
	// Member variables
	protected double gasAvailable;
	protected int milesSinceReset;
	protected double grossEarnings;
	protected double mpg = MPG;
	protected double tankSize = TANK_SIZE;
	
	/**
	 * Object constructor
	 */
	
	public Cab()
	{
		gasAvailable = 0;
		reset();
	}
	
	/**
	 * Returns the available gas.
	 */
	
	public double getGas()
	{
		return gasAvailable;
	}
	
	/**
	 * Returns the amount of gas needed to fill the tank.
	 */
	
	public double getGasToFill()
	{
		return tankSize - getGas();
	}
	
	/**
	 * Returns the number of miles driven since the last reset.
	 */
	
	public int getMilesSinceReset()
	{
		return milesSinceReset;
	}
	
	/**
	 * If the number of miles passed to `recordTrip()` is
	 * within range of the cab with gas available, then the
	 * trip is made. Gas will be used in the appropriate amount,
	 * and the total fare will be added to earnings.
	 * 
	 * If the number of miles is out of range, it will return an error: -1
	 */
	
	public double recordTrip(int miles)
	{
		if (getMilesAvailable() > miles)
		{
			milesSinceReset += miles;
			gasAvailable -= miles / mpg;
			
			double totalFare = getFare(miles);
			grossEarnings += totalFare;
			return totalFare;
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * If the number of gallons is less than or equal to
	 * the amount it will take to to fill the tank, add 
	 * gas to the tank.
	 * 
	 * If the number of gallons will overflow the tank,
	 * return an error: -1. I'm leaving it up to the caller
	 * to know how many gallons to put in.
	 */
	
	public double addGas(double gallons)
	{
		if (gallons <= getGasToFill())
		{
			// Add gas to the tank.
			gasAvailable += gallons;
			return gasAvailable;
		}
		else
		{
			// return error
			return -1;
		}
	}
	
	/**
	 * Returns the gross earnings since the last reset.
	 */
	
	public double getGrossEarnings()
	{
		return grossEarnings;
	}
	
	/**
	 * Returns the range of the cab based on the gas available and MPG.
	 */
	
	public double getMilesAvailable()
	{
		return gasAvailable * mpg;
	}
	
	/**
	 * Resets the miles and gross earnings.
	 */
	
	public void reset()
	{
		milesSinceReset = 0;
		grossEarnings = 0;
	}
	
	/**
	 * Returns a fare based on a number of miles.
	 */
	
	public double getFare(int miles)
	{
		return BASE_FARE + MILE_FARE * miles;
	}

}
