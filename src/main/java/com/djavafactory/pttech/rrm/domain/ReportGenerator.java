package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.util.DateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Methods to generate reports.
 * User: rainpoh
 * Date: 5/5/11
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportGenerator {

   /**
     * get result for report dailyDetailsRequestReloadFfmCelcomReport
     * @param none
     * @return List A report list
     */
    public static List getDetailsRequestReloadFrmCelcomReport()
    {
    	Date dateMin = getMinDate();
    	Date dateMax = getMaxDate(dateMin);
		
		//get reloadrequest list
    	List listReloadRequest = ReloadRequest.findListReloadRequestsByRequestedTimeBetween(dateMin, dateMax);

        List <Report> listReport = new ArrayList<Report>();
        
        //call method to copy reload request list to report list
        listReport = copyReloadRequestToReport(listReloadRequest);
        
        //declare report list to hold complete report data
        List <Report> listCompleteReport = new ArrayList<Report>();
        
		Iterator it = listReport.iterator();

        while(it.hasNext())
		{        
            try {

            	Report report = (Report)it.next();
            	//manually set value into report fields
            	report.setReloadAmount(getReportFee());
            	listCompleteReport.add(report);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        return listCompleteReport;
    }
    

	/**
	 * To copy reload request list to report list
	 * @param listReloadRequest A list of reload request
	 * @return List Report list
	 */
    public static List copyReloadRequestToReport(List listReloadRequest)
    {
    	Iterator it = listReloadRequest.iterator();
        List <Report> listReport = new ArrayList<Report>();

        while(it.hasNext())
		{

            Report report;
            try {
            	
	            report = new Report();
	            ReloadRequest reloadrequest = (ReloadRequest)it.next();
	            BeanUtils.copyProperties(report, reloadrequest);
	            listReport.add(report);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        return listReport;   	
    }
    
    /**
	 * Get fee value from configuration
	 * @param none
	 * @return BigDecimal Fee in BigDecimal
	 */
    public static BigDecimal getReportFee()
    {    	 
    	 String reportfee =  Configuration.getReportConfigValue(Constants.REPORT_CONFIG_FEE);
    	 BigDecimal decimalFee = new BigDecimal(reportfee); 
    	 return decimalFee;
    }

    /**
	 * Get celcom commission value from configuration
	 * @param none
	 * @return BigDecimal Celcom commission in BigDecimal
	 */ 
    public static BigDecimal getReportCelComm()
    {	      
    	 String reportCelComm = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_CELCOMM);
    	 BigDecimal decimalCelComm = new BigDecimal(reportCelComm); 
    	 return decimalCelComm;    	 
    }
    
    /**
	 * Get Tng commission value from configuration
	 * @param none
	 * @return BigDecimal Tng commission commission in BigDecimal
	 */ 
    public static BigDecimal getReportTngComm()
    {	      
     	 String reportTngComm = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_TNGCOMM);
     	 BigDecimal decimalTngComm= new BigDecimal(reportTngComm); 
     	 return decimalTngComm; 
    }
    
    /**
	 * Get TotalChargeToCustomer (reload amount + fee)
	 * @param reloadAmount 
	 * @return BigDecimal TotalChargeToCustomer
	 */ 
    public static BigDecimal getTotalChargeToCustomer(BigDecimal reloadAmount)
    {	
     	BigDecimal fee = getReportFee();
     	BigDecimal sumForReloadAmountAndFee = fee.add(reloadAmount);
     	 
     	 return sumForReloadAmountAndFee; 
    }
    
    
    /**
	 * get minimum date - for daily report minDate is current date
	 * @param none
	 * @return Date Current date with short format
	 */
    public static Date getMinDate()
    {	
    	Date dateMin = null;      
    	try {	
			dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     	 
     	 return dateMin; 
    }
  
    /**
	 * get max date - for daily report the maxDate 
	 * @param dateMin The minimum date
	 * @return Date dateMin + 1 day
	 */
    public static Date getMaxDate(Date dateMin)
    {	
    	Date dateMax = null;      
    	try {
			dateMax =  DateUtil.add(dateMin, 5, 1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     	 
     	 return dateMax; 
    }
     
}
