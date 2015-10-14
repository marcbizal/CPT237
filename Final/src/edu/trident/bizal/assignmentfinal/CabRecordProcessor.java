/**
 * This class handles the loading and processing of .csv files,
 * as well as the output of a summary of all cabs individually,
 * and all cabs as a collection. 
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignmentfinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class CabRecordProcessor
{
	private Map<String, CabInfo> cabInfoRecords = new TreeMap<String, CabInfo>();

	public CabRecordProcessor() {}

	public static void main(String[] args)
	{
		CabRecordProcessor crp = new CabRecordProcessor();
		crp.loadRecords("actionRecords.csv");
		crp.produceReport("summaryReport.csv");
	}
	
	private double averageOf(ArrayList<Integer> list) {
		Integer sum = 0;
		if(!list.isEmpty()) {
			for (Integer num : list) {
				sum += num;
		    }
		    return sum.doubleValue() / list.size();
		}
		return sum;
	}
	
	private void loadRecords(String filename)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line = "";
			int lineNumber = 1;
			
			String cabID = "";
			CabInfo info = null;
				
			while ((line = br.readLine()) != null)
			{
				String components[] = GenericCabRecord.splitIntoComponents(line);
				
				// Only change cabID and info if the ID has changed.
				if (cabID != GenericCabRecord.getID(components))
				{
					cabID = GenericCabRecord.getID(components);
					info = cabInfoRecords.get(cabID);
					if(info == null)
					{
						info = new CabInfo(cabID);
						cabInfoRecords.put(cabID, info);
					}
				}
				
				String recordType = GenericCabRecord.getType(components);
				switch (recordType)
				{
					case "FARE":
					{
						info.addFareRecord(new FareRecord(components));
						break;
					}
					
					case "SERVICE":
					{
						info.addServiceRecord(new ServiceRecord(components));
						break;
					}
					
					case "GAS":
					{
						info.addGasRecord(new GasRecord(components));
						break;
					}
					
					default:
					{
						System.out.println("Unknown record type " + recordType + " on line " + lineNumber);
					}
				}
				
				lineNumber++;
			}
			
			br.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void produceReport(String filename)
	{
		double totalGrossEarnings = 0.0;
		double totalGasCost = 0.0;
		double totalServiceCost = 0.0;
		double totalNetEarnings = 0.0;
		double totalMilesDriven = 0.0;
		
		ArrayList<Integer> allDaysBetweenService = new ArrayList<Integer>();
		double averageDaysBetweenServices = 0.0;
		
		try {
			File file = new File(filename);
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			pw.println("Cab ID,Start Date,End Date,Gross Earnings,Gas Cost,Service Cost,Net Earnings,Total Miles,Services Performed,Average Service,Max Service");
			for (String key : cabInfoRecords.keySet()) {
			    CabInfo info = cabInfoRecords.get(key);
			    pw.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%.2f,%d\n", 
			    		info.getID(), 
			    		info.firstDate.toString("YYYY/MM/dd"),
			    		info.lastDate.toString("YYYY/MM/dd"),
			    		info.getGrossEarnings(),
			    		info.getTotalGasCost(),
			    		info.getTotalServiceCost(),
			    		info.getNetTotal(),
			    		info.getTotalMiles(),
			    		info.getTotalServices(),
			    		averageOf(info.getDaysBetweenService()),
			    		info.getMaxDaysBetweenService());
			    
			    totalGrossEarnings += info.getGrossEarnings();
			    totalGasCost += info.getTotalGasCost();
			    totalServiceCost += info.getTotalServiceCost();
			    totalNetEarnings += info.getNetTotal();
			    totalMilesDriven += info.getTotalMiles();
			    allDaysBetweenService.addAll(info.getDaysBetweenService());
			}
			
			pw.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		averageDaysBetweenServices = averageOf(allDaysBetweenService);
		System.out.printf("ALL CAB SUMMARY\n"
						+ "---------------\n"
						+ "GROSS EARNINGS: $%.2f\n"
						+ "GAS COSTS: $%.2f\n"
						+ "SERVICE COSTS: $%.2f\n"
						+ "NET EARNINGS: $%.2f\n"
						+ "MILES DRIVEN: %.2f miles\n"
						+ "AVERAGE DAYS BETWEEN SERVICE: %.2f days\n", 
						totalGrossEarnings, 
						totalGasCost, 
						totalServiceCost, 
						totalNetEarnings, 
						totalMilesDriven, 
						averageDaysBetweenServices);
	}
}
