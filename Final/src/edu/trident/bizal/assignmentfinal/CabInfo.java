/**
 * This class holds and processes record information about a single cab,
 * and provides interfaces to access this information.
 * 
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignmentfinal;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class CabInfo
{
	private ArrayList<FareRecord> fareRecords = new ArrayList<FareRecord>();
	private ArrayList<ServiceRecord> serviceRecords = new ArrayList<ServiceRecord>();
	private ArrayList<GasRecord> gasRecords = new ArrayList<GasRecord>();

	private String id = "";
	
	DateTime firstDate = null;
	DateTime lastDate = null;
	
	double grossEarnings = 0.0;
	
	double totalGasCost = 0.0;
	double totalServiceCost = 0.0;
	
	double totalMiles = 0.0;
	
	private ArrayList<Integer> daysBetweenServices = new ArrayList<Integer>();
	int maxService = 0;
	
	public CabInfo(String id_)
	{
		id = id_;
	}
	
	public String getID()
	{
		return id;
	}
	
	public DateTime getFirstDate()
	{
		return firstDate;
	}
	
	public DateTime getLastDate()
	{
		return lastDate;
	}
	
	public double getGrossEarnings()
	{
		return grossEarnings;
	}
	
	public double getTotalGasCost()
	{
		return totalGasCost;
	}
	
	public double getTotalServiceCost()
	{
		return totalServiceCost;
	}
	
	public double getNetTotal()
	{
		return getGrossEarnings() - getTotalGasCost() - getTotalServiceCost();
	}
	
	public double getTotalMiles()
	{
		return totalMiles;
	}
	
	public int getTotalServices()
	{
		return serviceRecords.size();
	}
	
	public int getMaxDaysBetweenService()
	{
		return maxService;
	}
	
	public ArrayList<Integer> getDaysBetweenService()
	{
		return daysBetweenServices;
	}
	
	private void checkDate(GenericCabRecord record)
	{
		if (firstDate == null || record.date.isBefore(firstDate)) firstDate = record.date;
		if (lastDate == null || record.date.isAfter(lastDate)) lastDate = record.date;
	}
	
	public void addFareRecord(FareRecord record)
	{
		checkDate(record);
		fareRecords.add(record);
		
		grossEarnings += record.getFare();
		totalMiles += record.getMiles();
	}
	
	public void addServiceRecord(ServiceRecord record)
	{
		checkDate(record);
		serviceRecords.add(record);
		
		totalServiceCost += record.getCostOfService();
		
		if (serviceRecords.size() > 1)
		{
			DateTime lastService = serviceRecords.get(serviceRecords.size() - 2).date;
			
			// System.out.println(lastService.toString("YYYY/MM/dd") + "-" + record.date.toString("YYYY/MM/dd"));
			
			int days = Days.daysBetween(lastService, record.date).getDays();
			daysBetweenServices.add(days);
			
			if (days > maxService) maxService = days;
		}
	}
	
	public void addGasRecord(GasRecord record)
	{
		checkDate(record);
		gasRecords.add(record);
		
		totalGasCost += record.getPrice();
	}

}
