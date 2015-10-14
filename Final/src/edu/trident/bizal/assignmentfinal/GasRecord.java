/**
 * This class extends GenericCabRecord to provide
 * interfaces specific to the GAS record.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignmentfinal;

public class GasRecord extends GenericCabRecord 
{
	private double gallons;
	private double price;
	
	private class FareRecordIndices
	{
		public static final int GALLONS = 3;
		public static final int PRICE 	= 4;
	}

	public GasRecord(String[] components) {
		super(components);
		try 
		{
			gallons = Double.parseDouble(components[FareRecordIndices.GALLONS]);
			price = Double.parseDouble(components[FareRecordIndices.PRICE]);
		}
		catch (NumberFormatException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public double getGallons()
	{
		return gallons;
	}
	
	public double getPrice()
	{
		return price;
	}

}
