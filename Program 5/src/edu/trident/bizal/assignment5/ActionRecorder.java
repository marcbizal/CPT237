/**
 * This is a singleton class with the responsibility to log
 * various sorts of actions to an action record file for book keeping.
 * 
 * RECORD FORMAT:
 * date,cabId,recordType,amount1,amount2
 * 
 * @author Marcus Bizal
 */


package edu.trident.bizal.assignment5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ActionRecorder 
{
	public enum RecordType
	{
		FARE,
		GAS,
		SERVICE,
	}
	private static ActionRecorder actionRecorder = new ActionRecorder();
	private static final String RECORD_FILE = "actionRecords.csv";
	
	private ActionRecorder() {}
	
	public static ActionRecorder getInstance()
	{
		return actionRecorder;
	}
	
	public void writeRecord(String cabId, RecordType type, double value1, double value2)
	{
		try {
			File file = new File(RECORD_FILE);
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter(file, true));
			
			String dateString = new SimpleDateFormat("YYYY/MM/dd").format(new Date());
			pw.printf("%s,%s,%s,%.2f,%.2f\n", dateString, cabId, type.name(), value1, value2);
			
			pw.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
