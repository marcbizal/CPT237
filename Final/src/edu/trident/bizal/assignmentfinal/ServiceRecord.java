/**
 * This class extends GenericCabRecord to provide
 * interfaces specific to the SERVICE record.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignmentfinal;

public class ServiceRecord extends GenericCabRecord
{
	private double milesSinceService;
	private double costOfService;
	
	private class FareRecordIndices
	{
		public static final int MILES_SINCE_SERVICE 	= 3;
		public static final int COST_OF_SERVICE	 		= 4;
	}

	public ServiceRecord(String[] components) {
		super(components);
		try 
		{
			milesSinceService = Double.parseDouble(components[FareRecordIndices.MILES_SINCE_SERVICE]);
			costOfService = Double.parseDouble(components[FareRecordIndices.COST_OF_SERVICE]);
		}
		catch (NumberFormatException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public double getMilesSinceService()
	{
		return milesSinceService;
	}
	
	public double getCostOfService()
	{
		return costOfService;
	}

}
