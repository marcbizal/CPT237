/**
 * This class provides a generic base for other cab records,
 * it handles storing the date for each record, but does not
 * store the ID, or type as this information held by CabInfo, 
 * and extensions of GenericCabRecord. It also provides
 * static functions for splitting a raw string record,
 * and getting the type and ID of a component array.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignmentfinal;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class GenericCabRecord
{
	private static final String DELIMITERS = ",";
	protected DateTime date;
	
	protected class GenericRecordIndices
	{
		public static final int DATE 	= 0;
		public static final int ID 		= 1;
		public static final int TYPE 	= 2;
	}

	public GenericCabRecord(String[] components)
	{
		try
		{
			DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY/MM/dd");
			date = formatter.parseDateTime(components[GenericRecordIndices.DATE]);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static String[] splitIntoComponents(String record)
	{
		return record.split(DELIMITERS);
	}

	public static String getType(String[] components)
	{
		return components[GenericRecordIndices.TYPE];
	}
	
	public static String getID(String[] components)
	{
		return components[GenericRecordIndices.ID];
	}
}
