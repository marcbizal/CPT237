/**
 * This class extends GenericCabRecord to provide
 * interfaces specific to the FARE record.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignmentfinal;

public class FareRecord extends GenericCabRecord 
{
	private double miles;
	private double fare;
	
	private class FareRecordIndices
	{
		public static final int MILES 	= 3;
		public static final int FARE 	= 4;
	}

	public FareRecord(String[] components) {
		super(components);
		try 
		{
			miles = Double.parseDouble(components[FareRecordIndices.MILES]);
			fare = Double.parseDouble(components[FareRecordIndices.FARE]);
		}
		catch (NumberFormatException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public double getMiles()
	{
		return miles;
	}
	
	public double getFare()
	{
		return fare;
	}

}
