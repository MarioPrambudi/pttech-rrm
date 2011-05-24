package com.djavafactory.pttech.rrm.reports;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.Report;
import com.djavafactory.pttech.rrm.util.DateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

	/*
	 * calculation method
	 */
    
    /**
	 * Get fee value from configuration
	 * @param none
	 * @return BigDecimal - Fee 
	 */
    public static BigDecimal getReportFee() {    	 
    	 String reportfee =  Configuration.getReportConfigValue(Constants.REPORT_CONFIG_FEE);
    	 BigDecimal decimalFee = new BigDecimal(reportfee); 
    	 return decimalFee;
    }

    /**
	 * Get celcom commission value from configuration
	 * @param none
	 * @return BigDecimal - Celcom commission in BigDecimal
	 */ 
    public static BigDecimal getReportCelComm() {	      
    	 String reportCelComm = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_CELCOMM);
    	 BigDecimal decimalCelComm = new BigDecimal(reportCelComm); 
    	 return decimalCelComm;    	 
    }
    
    /**
	 * Get Tng commission value from configuration
	 * @param none
	 * @return BigDecimal - Tng commission commission in BigDecimal
	 */ 
    public static BigDecimal getReportTngComm() {	      
     	 String reportTngComm = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_TNGCOMM);
     	 BigDecimal decimalTngComm= new BigDecimal(reportTngComm); 
     	 return decimalTngComm; 
    }
    
    /**
	 * Get totalChargeToCustomer (reload amount + fee)
	 * @param reloadAmount BigDecimal
	 * @return BigDecimal - TotalChargeToCustomer
	 */ 
    public static BigDecimal getTotalChargeToCustomer(BigDecimal reloadAmount) {	
     	BigDecimal fee = getReportFee();
     	BigDecimal sumForReloadAmountAndFee = fee.add(reloadAmount);
     	 
     	 return sumForReloadAmountAndFee; 
    }
    
    /**
	 * Get commission amount deducted by SOF (fee x celcomm)
	 * @param none
	 * @return BigDecimal - Commission Amount
	 */ 
    public static BigDecimal getCommAmountDeductedBySOF() {	           	     	 
     	 BigDecimal celComm = getReportCelComm(); 
     	 BigDecimal fee = getReportFee();
     	 BigDecimal commAmount = fee.multiply(celComm);
     	 
     	 return commAmount; 
    }    
    
    /**
	 * Get net payment TnG
	 * @param totalChargeToCust BigDecimal
	 * @return BigDecimal - TotalChargeToCustomer
	 */ 
    public static BigDecimal getNetPaymentToTnG(BigDecimal totalChargeToCust, BigDecimal commAmountSof) {	
     	BigDecimal netPayment = totalChargeToCust.subtract(commAmountSof);
     	return netPayment; 
    }
    
    /**
	 * Get sumTotalChargeToCustomer (total reload amount + total fee)
	 * @param totalreloadAmount BigDecimal
	 * @param totalFee BigDecimal
	 * @return BigDecimal - sumTotalChargeToCustomer
	 */ 
    public static BigDecimal getSumTotalChargeToCustomer(BigDecimal totalReloadAmount, BigDecimal totalFee) {	
     	BigDecimal sumTotalChargeToCustomer = totalReloadAmount.add(totalFee);
     	 return sumTotalChargeToCustomer; 
    }
    
    /**
	 * Get SumCommissionAmountDeductedBySof (CommAmountDeductedBySOF x totalReloadQty)
	 * @param totalReloadQty long
	 * @return BigDecimal - SumCommissionAmountDeductedBySof
	 */ 
    public static BigDecimal getSumCommissionAmountDeductedBySof(long totalReloadQty) {	
     	BigDecimal sumCommissionAmountDeductedBySof = getCommAmountDeductedBySOF().multiply(new BigDecimal(totalReloadQty));
     	 return sumCommissionAmountDeductedBySof; 
    }
    
    /**
	 * Get SumNetPaymentToTng (sumTotalChargeToCustomer - sumCommissionAmountDeductedBySof)
	 * @param sumTotalChargeToCustomer BigDecimal
	 * @param sumCommissionAmountDeductedBySof BigDecimal
	 * @return BigDecimal - SumNetPaymentToTng
	 */
    public static BigDecimal getSumNetPaymentToTng(BigDecimal sumTotalChargeToCustomer, BigDecimal sumCommissionAmountDeductedBySof) {	
     	BigDecimal sumNetPaymentToTng = sumTotalChargeToCustomer.subtract(sumCommissionAmountDeductedBySof);
     	 return sumNetPaymentToTng; 
    }
       
    /**
	 * Get totalFees (ReportFee x totalReloadQty)
	 * @param totalReloadQty long
	 * @return BigDecimal - totalFees 
	 */ 
    public static BigDecimal getTotalFee(long totalReloadQty) {	
     	BigDecimal totalFees= getReportFee().multiply(new BigDecimal(totalReloadQty));
     	 return totalFees; 
    } 
    
    /**
	 * Get get initial date value
	 * @param none
	 * @return Date - 01/MM/yyyy
	 */
    public static Date getInitDateMin()
    {
	  	Calendar cal = Calendar.getInstance();
	  	Calendar calNow = Calendar.getInstance();
	    int date = 1;
	    cal.set(calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH), date);
	    return cal.getTime();
    }
    
	/**
	 * Get getTotalPaymentToTNG (totalReloadAmount + totalFee - sumCommAmountDeductedBySOF)
	 * @param totalReloadQty long
	 * @param totalReloadAmount BigDecimal 
	 * @return BigDecimal - TotalPaymentToTNG
	 */    
    public static BigDecimal getTotalPaymentToTNG(long totalReloadQty, BigDecimal totalReloadAmount)
    {
    	BigDecimal sumCommissionAmountDeductedBySof= getSumCommissionAmountDeductedBySof(totalReloadQty);
        BigDecimal totalFee = getTotalFee(totalReloadQty);
    	BigDecimal totalPaymentToTNG =  totalReloadAmount.add(totalFee).subtract(sumCommissionAmountDeductedBySof);
    	
    	return totalPaymentToTNG;
    	
    }
    
    /**
	 * Get settlementNetPaymentToTng (totalPaymentToTNG- totalCancellation)
	 * @param totalPaymentToTNG BigDecimal
	 * @param totalCancellation BigDecimal
	 * @return BigDecimal - settlementNetPaymentToTng
	 */
    public static BigDecimal getSettlementNetPaymentToTng(BigDecimal totalPaymentToTNG, BigDecimal totalCancellation) {	
     	BigDecimal settlementNetPaymentToTng = totalPaymentToTNG.subtract(totalCancellation);
     	 return settlementNetPaymentToTng; 
    }
	/*
	 * end calculation methods
   	*/
    
    /**
	 * To copy reload request list to report list
	 * @param listReloadRequest A list of reload request
	 * @return List<Report> - list of report
	 */
    public static List<Report> copyReloadRequestToReport(List<ReloadRequest> listReloadRequest) {
    	Iterator it = listReloadRequest.iterator();
        List <Report> listReport = new ArrayList<Report>();
        while(it.hasNext()) {
            Report report;
            try {
	            report = new Report();
	            ReloadRequest reloadrequest = (ReloadRequest)it.next();
	            BeanUtils.copyProperties(report, reloadrequest);
	            if(reloadrequest.getModifiedTime() != null)
	            {
	            	report.setModifiedDate(reloadrequest.getModifiedTime());
	            }
	            if(reloadrequest.getTotalCancellationAmt() != null)
	            {
	            	report.setTotalCancellationRm(reloadrequest.getTotalCancellationAmt());
	            }
	            listReport.add(report);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return listReport;   	
    }
    
    /**
	 * get list of all status
	 * @param none
	 * @return List<String> - list of status
	 */
	  public static List<String> getListAllStatus(){
		  List<String> listStatus = new ArrayList<String>();
		  listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
		  listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());			
		  listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());			
		  listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
		  listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase());
		  listStatus.add(Constants.RELOAD_STATUS_PENDING.toLowerCase());
		  return listStatus;		
	  }
	  
	/**
	 * get list of all status
	 * @param none
	 * @return List<String> - list of status
	 */
	  public static List<String> getListAllStatusNotPending(){
		  List<String> listStatus = new ArrayList<String>();
		  listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
		  listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());			
		  listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());			
		  listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
		  listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase());
		  return listStatus;		
	  }
		  
	/**
	 * get list of all cancellation status
	 * @param none
	 * @return List<String> - list cancellation status
	 */
	  public static List<String> getListAllCancel(){
		  List<String> listStatus = new ArrayList<String>();
		  listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
		  listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
		  listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
		  return listStatus;
	  }
	  
    /**
	 * get list of all success status
	 * @param none
	 * @return List<String> - list of success status
	 */
	  public static List<String> getListAllSuccess(){
		  List<String> listStatus = new ArrayList<String>();
		  listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
		  return listStatus;
	  }
	  
	/**
	 * get the formated start date for finder
	 * @param dateMinStr String 
	 * @return Date - dateMin
	 */
	  public static Date getDateMin(String dateMinStr) throws ParseException{
		  Date dateMin = null;
		  if("null".equals(dateMinStr))
		  {
		    dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		  }
		  else
		  {
			dateMin = DateUtil.convertStringToDate(dateMinStr);
		  }  	
		  return dateMin;	  
	  }
	  
	/**
	 * get the formated end date for finder
	 * @param dateMin Date
	 * @param dateMaxStr String 
	 * @return Date - dateMax
	 */ 
	  public static Date getDateMax(Date dateMin, String dateMaxStr) throws ParseException{
		  Date dateMax = null;
		  if("null".equals(dateMaxStr))
		  {
			 dateMax = DateUtil.add(dateMin, 5, 1);
		  }
		  else
		  {
			dateMax = DateUtil.convertStringToDate(dateMaxStr);
		  }  	
		  return dateMax;	  
	  }
	  	  
    /**
	 * get the formated start date for summary report finder
	 * @param dateMinStr String 
	 * @return Date - dateMin
	 */  
	  public static Date getSummaryDateMin(String dateMinStr) throws ParseException{
		  Date dateMin = null;
		  if("null".equals(dateMinStr))
		  {
		    dateMin = getInitDateMin();
		  }
		  else
		  {
			dateMin = DateUtil.convertStringToDate(dateMinStr);
		  }  	
		  return dateMin;	  
	  }
	  
	/**
	 * get the formated end date for summary report finder
	 * @param dateMin Date
	 * @param dateMaxStr String 
	 * @return Date - dateMax
	 */
	  public static Date getSummaryDateMax(Date dateMin, String dateMaxStr) throws ParseException{
		  Date dateMax = null;
		  if("null".equals(dateMaxStr))
		  {
			dateMax = DateUtil.add(dateMin, 2, 1);
		  }
		  else
		  {
			dateMax = DateUtil.convertStringToDate(dateMaxStr);
		  }  	
		  return dateMax;	  
	  }
	  
	/**
	 * get total number of report based on requested date between and status
	 * @param String dateMinStr
	 * @param String dateMaxStr 
	 * @param listStatus List<String>  
	 * @return long - total number of report
	 */
	  public static long getTotalReport(String dateMinStr, String dateMaxStr, List<String> listStatus) throws ParseException
	  {
		  Date dateMin = getDateMin(dateMinStr);
		  Date dateMax = getDateMax(dateMin, dateMaxStr);	
		  long totalReport = ReloadRequest.totalReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
		  return totalReport;
	  }
	  
	/**
	 * get total number of summary report based on requested date between and status
	 * @param String dateMinStr
	 * @param String dateMaxStr 
	 * @param listStatus List<String>  
	 * @return long - total number of summary report
	 */
	  public static long getTotalSummaryReport(String dateMinStr, String dateMaxStr, List<String> listStatus) throws ParseException
	  {
		  Date dateMin = getSummaryDateMin(dateMinStr);
		  Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);
		  Date dateMaxSearch = null;
		  List<Report> listReport = new ArrayList<Report>();
	      List<Report> listCompleteReport = new ArrayList<Report>();	      
	      
	      while(dateMin.before(dateMax))
	      {	   		
	    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		
	    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);    		
	    		if (listReloadRequest != null && listReloadRequest.size()>0)
	    		{
	    			listReport = copyReloadRequestToReport(listReloadRequest);  
	        		Iterator it = listReport.iterator();
	        		Report reportSummary = new Report();
	                while(it.hasNext()) { 
	                    try {
	                      reportSummary = (Report)it.next();
	                      reportSummary.setSofRequestedDatetime(reportSummary.getRequestedTime());
	                      reportSummary.setTotalAmountRequestRm(reportSummary.getReloadAmount());
	                      reportSummary.setTotalFees(getTotalFee(reportSummary.getTotalReloadQty()));
	                      reportSummary.setSumTotalChargeToCustomer(getSumTotalChargeToCustomer(reportSummary.getTotalAmountRequestRm(), reportSummary.getTotalFees()));
	                      reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalReloadQty()));
	                      reportSummary.setSumNetPaymentToTng(getSumNetPaymentToTng(reportSummary.getSumTotalChargeToCustomer(), reportSummary.getSumCommissionAmountDeductedBySof()));                  
	                    } catch (Exception e) {
	                        e.printStackTrace();  
	                    }
	                }
	                listCompleteReport.add(reportSummary);    		
	    		}  		
	    		dateMin = dateMaxSearch;
	      }
	      return listCompleteReport.size();
	  }
	  
	/**
	 * rearrange report list for paging
	 * @param String dateMinStr
	 * @param String dateMaxStr 
	 * @param listStatus List<String>  
	 * @return long - total number of summary report
	 */
	  public static List<Report> formatSummaryList(List<Report> listReport, int first, int size)
	  {
		  if(first > -1 && size > 0) 
		  { 	
	    		 List<Report> listReportPage= new ArrayList<Report>();
	    		 int i = 0;
	    		 while(i<size)
	    		 {
	    			if (first<listReport.size())
	    			{
	    				listReportPage.add(listReport.get(first));
	    				first++; 				
	    			}
	    			i++;
	    		  }
	    		 return listReportPage;
		  }
		  return listReport;
	  }
    /*
	 * TNG Report
	 */
   	/**
     * get result for report dailyDetailsRequestReloadFrmCelcomReport/TG0001-Report
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getDailyDetailsRequestReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllStatus();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus, first, size).getResultList();
        listReport = copyReloadRequestToReport(listReloadRequest);
		Iterator it = listReport.iterator();

		BigDecimal totalFee= new BigDecimal("0.00");
		BigDecimal totalAmountRequest = new BigDecimal("0.00");
		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");
		
        while(it.hasNext())
		{        
            try {
            	Report report = (Report)it.next();
            	//manually set value into report fields
            	report.setSofRequestedDatetime(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	listCompleteReport.add(report);
            	//sum
            	totalFee = totalFee.add(report.getFees());
            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
            	sumTotalChargeCust = sumTotalChargeCust.add(report.getTotalChargeToCustomer());
            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        
        if(!listCompleteReport.isEmpty())
        {
        	 //the add the sum to the end of the report list
            Report reportSum = new Report();
            reportSum.setFees(totalFee);
            reportSum.setReloadAmount(totalAmountRequest);
            reportSum.setTotalChargeToCustomer(sumTotalChargeCust);
            reportSum.setCommissionAmountDeductedBySof(totalCommSofDeduct);
            reportSum.setNetPaymentToTng(totalNetPaymentTng);
            listCompleteReport.add(reportSum);       	
        }
      return listCompleteReport;
    }
    
    
    /**
     * get result for report dailyDetailedReloadFrmCelcomReport/TG0003
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getDailyDetailedReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllSuccess();    	
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);		
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus, first, size).getResultList();
        listReport = copyReloadRequestToReport(listReloadRequest);        
		Iterator it = listReport.iterator();
		
		BigDecimal totalFee= new BigDecimal("0.00");
		BigDecimal totalAmountRequest = new BigDecimal("0.00");
		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");

        while(it.hasNext())
		{        
            try {

            	Report report = (Report)it.next();
            	//manually set value into report fields
            	report.setReloadDateTime(report.getModifiedDate());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	listCompleteReport.add(report);            	
            	//sum
            	totalFee = totalFee.add(report.getFees());
            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
            	sumTotalChargeCust = sumTotalChargeCust.add(report.getTotalChargeToCustomer());
            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());       	
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if(!listCompleteReport.isEmpty())
        {
	        //the add the sum to the end of the report list
	        Report reportSum = new Report();
	        reportSum.setFees(totalFee);
	        reportSum.setReloadAmount(totalAmountRequest);
	        reportSum.setTotalChargeToCustomer(sumTotalChargeCust);
	        reportSum.setCommissionAmountDeductedBySof(totalCommSofDeduct);
	        reportSum.setNetPaymentToTng(totalNetPaymentTng);
	        listCompleteReport.add(reportSum);
        }
        return listCompleteReport;
    }
    
    /**
     * get result for report dailyDetailsCancellationReloadReqFrmCelcomReport/TG0005 
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception  
     */
    public static List<Report> getDailyDetailsCancellationReloadReqFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllCancel();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	 			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus, first, size).getResultList();
        listReport = copyReloadRequestToReport(listReloadRequest);
		Iterator it = listReport.iterator();

		BigDecimal totalFee= new BigDecimal("0.00");
		BigDecimal sumTotalChargeCust = new BigDecimal("0.00");
		BigDecimal totalCommSofDeduct = new BigDecimal("0.00");
		BigDecimal totalRefundCust = new BigDecimal("0.00");
		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");
		
        while(it.hasNext())
		{        
            try {

            	Report report = (Report)it.next();
            	//manually set value into report fields
            	report.setSofRequestedDatetime(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	report.setAmountRefundedToCustomer(report.getReloadAmount());
            	report.setDateTimeRefundedCustomer(report.getModifiedDate());
            	if(Constants.RELOAD_REQUEST_FAILED.equals(report.getStatus()))
        		{
            		report.setCancellationStatus(Constants.REPORT_RELOAD_REQUEST_FAILED);
        		}
            	if(Constants.RELOAD_REQUEST_EXPIRED.equals(report.getStatus()))
        		{
            		report.setCancellationStatus(Constants.REPORT_RELOAD_REQUEST_EXPIRED);
        		}
            	if(Constants.RELOAD_REQUEST_MANUALCANCEL.equals(report.getStatus()))
        		{
            		report.setCancellationStatus(Constants.REPORT_RELOAD_REQUEST_CANCELLED);
        		}
            	listCompleteReport.add(report);            	
            	//sum
            	totalFee = totalFee.add(report.getFees());
            	sumTotalChargeCust = sumTotalChargeCust.add(report.getTotalChargeToCustomer());
            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());            	
            	totalRefundCust = totalRefundCust.add(report.getAmountRefundedToCustomer());         	      	           	
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if(!listCompleteReport.isEmpty())
        {
	        //the add the sum to the end of the report list
	        Report reportSum = new Report();
	        reportSum.setFees(totalFee);
	        reportSum.setAmountRefundedToCustomer(totalRefundCust);
	        reportSum.setTotalChargeToCustomer(sumTotalChargeCust);
	        reportSum.setCommissionAmountDeductedBySof(totalCommSofDeduct);
	        reportSum.setNetPaymentToTng(totalNetPaymentTng);
	        listCompleteReport.add(reportSum);
        }
        return listCompleteReport;
    }
    

    /**
     * get result for report dailySettlementReloadReqFrmCelcomReport/TG0007 
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
  public static List<Report> getDailySettlementReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  List<String> listStatus = getListAllStatusNotPending();
	  List<Report> listReportPage = new ArrayList<Report>();
	  Date dateMin = getSummaryDateMin(dateMinStr);
	  Date dateMax = getDateMax(dateMin, dateMaxStr);		
	  Date dateMaxSearch = null;	  
	  BigDecimal sumGrossPaymentToTNG = new BigDecimal("0.00");
	  BigDecimal sumCancellationAmt = new BigDecimal("0.00");
	  BigDecimal sumAmtCreditedToTNG = new BigDecimal("0.00");
	  Long sumReloadQty = new Long(0);

	  while(dateMin.before(dateMax))
	  {	   		   		
		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
		
		if (listReloadRequest != null && listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
			Iterator it = listReport.iterator();
			Report reportSummary = new Report();
	        while(it.hasNext()) {        
	            try {
	              reportSummary = (Report)it.next();
				  reportSummary.setTransactionDate(reportSummary.getModifiedDate());
				  reportSummary.setGrossPaymentToTngRm(getTotalPaymentToTNG(reportSummary.getTotalReloadQty(), reportSummary.getReloadAmount()));		         		          
		          reportSummary.setAmountCreditedToTngRm(getSettlementNetPaymentToTng(reportSummary.getGrossPaymentToTngRm(), reportSummary.getTotalCancellationRm()));
		          //reportSummary.setDateCreditedToTngAccount(); Blank
		        } catch (Exception e) {
	                e.printStackTrace();  
	            }
	        }
	        listCompleteReport.add(reportSummary);    		
		}  		
		dateMin = dateMaxSearch;
	  }

		listReportPage = formatSummaryList(listCompleteReport, first, size);
		Iterator it = listReportPage.iterator();
	  	while(it.hasNext()){
	  		Report reportSummary = new Report();
	  		reportSummary = (Report)it.next();
	  		if (reportSummary.getTotalCancellationRm() != null && reportSummary.getTotalCancellationRm() != new BigDecimal("0.00"))
	      	{
	  			sumCancellationAmt = sumCancellationAmt.add(reportSummary.getTotalCancellationRm());
	      	}
	      	sumGrossPaymentToTNG = sumGrossPaymentToTNG.add(reportSummary.getGrossPaymentToTngRm());
	      	sumAmtCreditedToTNG = sumAmtCreditedToTNG.add(reportSummary.getAmountCreditedToTngRm());
	      	sumReloadQty = sumReloadQty + reportSummary.getTotalReloadQty();
	  	}
	  	if(!listReportPage.isEmpty())
		   	 {
		    	//the add the sum to the end of the report list
		        Report reportSum = new Report();
		        reportSum.setGrossPaymentToTngRm(sumGrossPaymentToTNG);
		        reportSum.setAmountCreditedToTngRm(sumAmtCreditedToTNG);
		        reportSum.setTotalCancellationRm(sumCancellationAmt);
		        reportSum.setTotalReloadQty(sumReloadQty);
		        listReportPage.add(reportSum);
		   	 }	  	
	  	return listReportPage;
  }

  	//Tng Summary Reports
    /**
     * get result for report summaryRequestReloadFrmCelcomReport/TG0002
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getSummaryRequestReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
        List<Report> listReportPage = new ArrayList<Report>();
    	List<String> listStatus = getListAllStatus();
     	
    	Date dateMin = getSummaryDateMin(dateMinStr);
    	Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
    	Date dateMaxSearch = null;
    	
		BigDecimal sumFee= new BigDecimal("0.00");
		BigDecimal sumAmountRequest = new BigDecimal("0.00");
		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
		BigDecimal sumCommSofDeduct= new BigDecimal("0.00");
		BigDecimal sumNetPaymentTng= new BigDecimal("0.00");
		Long sumReloadQty = new Long(0);
		
    	while(dateMin.before(dateMax))
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
    		
    		if (listReloadRequest != null && listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);  
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) { 
                    try {
                      reportSummary = (Report)it.next();
                      reportSummary.setSofRequestedDate(reportSummary.getRequestedTime());
                      reportSummary.setTotalAmountRequestRm(reportSummary.getReloadAmount());
                      reportSummary.setTotalFees(getTotalFee(reportSummary.getTotalReloadQty()));
                      reportSummary.setSumTotalChargeToCustomer(getSumTotalChargeToCustomer(reportSummary.getTotalAmountRequestRm(), reportSummary.getTotalFees()));
                      reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalReloadQty()));
                      reportSummary.setSumNetPaymentToTng(getSumNetPaymentToTng(reportSummary.getSumTotalChargeToCustomer(), reportSummary.getSumCommissionAmountDeductedBySof()));                  
                    } catch (Exception e) {
                        e.printStackTrace();  
                    }
                }
                listCompleteReport.add(reportSummary);    		
    		}  		
    		dateMin = dateMaxSearch;
    	}	
    	
    	listReportPage = formatSummaryList(listCompleteReport, first, size);
    	Iterator it = listReportPage.iterator();
    	while(it.hasNext()){
    		Report reportSummary = new Report();
    		reportSummary = (Report)it.next();
    		//sum
        	sumFee = sumFee.add(reportSummary.getTotalFees());
        	sumAmountRequest = sumAmountRequest.add(reportSummary.getTotalAmountRequestRm());
        	sumTotalChargeCust = sumTotalChargeCust.add(reportSummary.getSumTotalChargeToCustomer());
        	sumCommSofDeduct = sumCommSofDeduct.add(reportSummary.getSumCommissionAmountDeductedBySof());
        	sumNetPaymentTng = sumNetPaymentTng.add(reportSummary.getSumNetPaymentToTng());   
        	sumReloadQty = sumReloadQty + reportSummary.getTotalReloadQty();
    	}

    	 if(!listReportPage.isEmpty())
    	 {
    		//the add the sum to the end of the report list
	        Report reportSum = new Report();
	        reportSum.setTotalFees(sumFee);
	        reportSum.setTotalAmountRequestRm(sumAmountRequest);
	        reportSum.setSumTotalChargeToCustomer(sumTotalChargeCust);
	        reportSum.setSumCommissionAmountDeductedBySof(sumCommSofDeduct);
	        reportSum.setSumNetPaymentToTng(sumNetPaymentTng);
	        reportSum.setTotalReloadQty(sumReloadQty);
	        listReportPage.add(reportSum);
    	 }
    	return listReportPage;
    }


    /**
     * get result for report summaryReloadFrmCelcomReport/TG0004
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getSummaryReloadReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = getListAllSuccess();
    	List<Report> listReportPage = new ArrayList<Report>();
    	Date dateMin = getSummaryDateMin(dateMinStr);
    	Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
    	Date dateMaxSearch = null;
    	
    	BigDecimal sumFee= new BigDecimal("0.00");
		BigDecimal sumReloadAmount = new BigDecimal("0.00");
		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
		BigDecimal sumCommSofDeduct= new BigDecimal("0.00");
		BigDecimal sumNetPaymentTng= new BigDecimal("0.00");
		
    	while(dateMin.before(dateMax))
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
    		
    		if (listReloadRequest != null && listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      reportSummary.setReloadDate(reportSummary.getModifiedDate());
	                  reportSummary.setTotalReloadAmountRm(reportSummary.getReloadAmount());
	                  reportSummary.setTotalFees(getTotalFee(reportSummary.getTotalReloadQty()));
	                  reportSummary.setSumTotalChargeToCustomer(getSumTotalChargeToCustomer(reportSummary.getTotalReloadAmountRm(), reportSummary.getTotalFees()));
	                  reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalReloadQty()));
	                  reportSummary.setSumNetPaymentToTng(getSumNetPaymentToTng(reportSummary.getSumTotalChargeToCustomer(), reportSummary.getSumCommissionAmountDeductedBySof()));                    
	                } catch (Exception e) {
                        e.printStackTrace();  
                    }
                }
                listCompleteReport.add(reportSummary);    		
    		}  		
    		dateMin = dateMaxSearch;
    	}
    	listReportPage = formatSummaryList(listCompleteReport, first, size);
    	Iterator it = listReportPage.iterator();
    	while(it.hasNext()){
    		Report reportSummary = new Report();
    		reportSummary = (Report)it.next();
    		//sum
        	sumFee = sumFee.add(reportSummary.getTotalFees());
        	sumReloadAmount = sumReloadAmount.add(reportSummary.getTotalReloadAmountRm());
        	sumTotalChargeCust = sumTotalChargeCust.add(reportSummary.getSumTotalChargeToCustomer());
        	sumCommSofDeduct = sumCommSofDeduct.add(reportSummary.getSumCommissionAmountDeductedBySof());
        	sumNetPaymentTng = sumNetPaymentTng.add(reportSummary.getSumNetPaymentToTng());   
    	}

    	if(!listReportPage.isEmpty())
	   	 {
	        //the add the sum to the end of the report list
	        Report reportSum = new Report();
	        reportSum.setTotalFees(sumFee);
	        reportSum.setTotalReloadAmountRm(sumReloadAmount);
	        reportSum.setSumTotalChargeToCustomer(sumTotalChargeCust);
	        reportSum.setSumCommissionAmountDeductedBySof(sumCommSofDeduct);
	        reportSum.setSumNetPaymentToTng(sumNetPaymentTng);
	        listReportPage.add(reportSum);
	   	 }
 
    	return listReportPage;
    }
   
    
    /**
     * get result for report summaryCancellationReloadReqFrmCelcomReport/TG0006
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getSummaryCancellationReloadReqFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = getListAllCancel();
    	List<Report> listReportPage = new ArrayList<Report>();
    	Date dateMin = getSummaryDateMin(dateMinStr);
    	Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
    	Date dateMaxSearch = null; 
    	
    	BigDecimal sumFee= new BigDecimal("0.00");
		BigDecimal sumAmountCancel = new BigDecimal("0.00");
		BigDecimal sumCommSofDeduct= new BigDecimal("0.00");
		BigDecimal sumRefundToCust= new BigDecimal("0.00");
		Long sumCancelQty = new Long(0);

    	while(dateMin.before(dateMax))	   		
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		  		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
    		
    		if (listReloadRequest != null && listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      // summary calculation
	            	  reportSummary.setDateCancelRequest(reportSummary.getModifiedDate());
	                  reportSummary.setReloadDate(reportSummary.getRequestedTime());
	                  reportSummary.setTotalCancellationQty(reportSummary.getTotalReloadQty());
	                  reportSummary.setTotalAmountCancelledRm(reportSummary.getReloadAmount());
	                  reportSummary.setTotalFees(getTotalFee(reportSummary.getTotalCancellationQty()));
	                  reportSummary.setTotalRefundToCustomerRm(reportSummary.getTotalAmountCancelledRm());
	                  reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalCancellationQty()));                   
                    } catch (Exception e) {
                        e.printStackTrace();  
                    }
                }
                listCompleteReport.add(reportSummary);    		
    		}  		
    		dateMin = dateMaxSearch;
    	}
    	listReportPage = formatSummaryList(listCompleteReport, first, size);
    	Iterator it = listReportPage.iterator();
    	while(it.hasNext()){
    		Report reportSummary = new Report();
    		reportSummary = (Report)it.next();
    		//sum
        	sumFee = sumFee.add(reportSummary.getTotalFees());
        	sumAmountCancel = sumAmountCancel.add(reportSummary.getTotalAmountCancelledRm());
        	sumRefundToCust = sumRefundToCust.add(reportSummary.getTotalRefundToCustomerRm());
        	sumCommSofDeduct = sumCommSofDeduct.add(reportSummary.getSumCommissionAmountDeductedBySof());
        	sumCancelQty = sumCancelQty + reportSummary.getTotalCancellationQty();
    	}
    	if(!listReportPage.isEmpty())
	   	 {
	    	//the add the sum to the end of the report list
	        Report reportSum = new Report();
	        reportSum.setTotalFees(sumFee);
	        reportSum.setTotalRefundToCustomerRm(sumRefundToCust);
	        reportSum.setTotalAmountCancelledRm(sumAmountCancel);
	        reportSum.setSumCommissionAmountDeductedBySof(sumCommSofDeduct);
	        reportSum.setTotalCancellationQty(sumCancelQty);
	        listReportPage.add(reportSum);
	   	 }
    	return listReportPage;
   	 
    }
  
    /** 
     * get result for report summarySettlementReloadReqFrmCelcomReport/TG0008
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getSummarySettlementReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  List<String> listStatus = getListAllStatusNotPending();
	  List<Report> listReportPage = new ArrayList<Report>();
	  Date dateMin = getSummaryDateMin(dateMinStr);
	  Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
	  Date dateMaxSearch = null;
	  
	  BigDecimal sumPaymentToTNG = new BigDecimal("0.00");
	  BigDecimal sumCancellationAmt = new BigDecimal("0.00");
	  BigDecimal sumNetPaymentToTNG = new BigDecimal("0.00");
	  Long sumReloadQty = new Long(0);


	  while(dateMin.before(dateMax))
	  {	   		   		
		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
		
		if (listReloadRequest != null && listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
			Iterator it = listReport.iterator();
			Report reportSummary = new Report();
	        while(it.hasNext()) {        
	            try {
	              reportSummary = (Report)it.next();
				  reportSummary.setTransactionDate(reportSummary.getModifiedDate());
				  reportSummary.setTotalPaymentToTngRm(getTotalPaymentToTNG(reportSummary.getTotalReloadQty(), reportSummary.getReloadAmount()));		         		          
		          reportSummary.setNetPaymentToTng(getSettlementNetPaymentToTng(reportSummary.getTotalPaymentToTngRm(), reportSummary.getTotalCancellationRm()));
		          //reportSummary.setDateCreditedToTngAccount(); Blank
		        } catch (Exception e) {
	                e.printStackTrace();  
	            }
	        }
	        listCompleteReport.add(reportSummary);    		
		}  		
		dateMin = dateMaxSearch;
	  }

		listReportPage = formatSummaryList(listCompleteReport, first, size);
		Iterator it = listReportPage.iterator();
	  	while(it.hasNext()){
	  		Report reportSummary = new Report();
	  		reportSummary = (Report)it.next();
	  		if (reportSummary.getTotalCancellationRm() != null && reportSummary.getTotalCancellationRm() != new BigDecimal("0.00"))
	      	{
	  			sumCancellationAmt = sumCancellationAmt.add(reportSummary.getTotalCancellationRm());
	      	}
	      	sumPaymentToTNG = sumPaymentToTNG.add(reportSummary.getTotalPaymentToTngRm());
	      	sumNetPaymentToTNG = sumNetPaymentToTNG.add(reportSummary.getNetPaymentToTng());
	      	sumReloadQty = sumReloadQty + reportSummary.getTotalReloadQty();
	  	}
	  	if(!listReportPage.isEmpty())
		   	 {
		    	//the add the sum to the end of the report list
		        Report reportSum = new Report();
		        reportSum.setTotalPaymentToTngRm(sumPaymentToTNG);
		        reportSum.setNetPaymentToTng(sumNetPaymentToTNG);
		        reportSum.setTotalCancellationRm(sumCancellationAmt);
		        reportSum.setTotalReloadQty(sumReloadQty);
		        listReportPage.add(reportSum);
		   	 }
	  	
	  	return listReportPage;
 	 
  }
    


    /*
	 * End of TNG Report
	 */

    
    
// <<<<<<<<<<<<<<<<<<<<<<<<< CELCOM REPORT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    /** 
     * TO DO
     * get result for report dailyTransactionDetailsReport/CE0001
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionDetailsReport(int first, int size) throws Exception {
    	return getDailyTransactionDetailsReport(null, null, first, size);
    }
    
    /** 
     * TO DO
     * get result for report dailyTransactionDetailsByRangeOfDatesReport/CE0002
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllStatus();
      	Date dateMin = null;
    	Date dateMax = null;
    	
    	if(dateMinStr != null && dateMaxStr != null) {System.out.println("<<<<<<<<<<<<< date tidak null");
      	  	dateMin = getDateMin(dateMinStr);
	    	dateMax = getDateMax(dateMin, dateMaxStr);
    	}
      	
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByParamCelcom(dateMin, dateMax, listStatus, first, size);
    	listReport = copyReloadRequestToReport(listReloadRequest);

//		BigDecimal totalFee= new BigDecimal("0.00");
//		BigDecimal totalAmountRequest = new BigDecimal("0.00");
//		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
//		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
//		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");
    	
		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
				
//				report.setDate();
//				report.setTime();
//				report.setCmmpTrxId(); 		// Long
//				report.setTngTrxId(); 		// Long
//				report.setAircashAccNo(); 	// Long
//				report.setMobileNo(); 		// Long
//				report.setTngMfgNo(); 		// Long
				
            	report.setFees(getReportFee());
            	report.setGrossAmount(report.getReloadAmount().add(report.getFees())); 			// reloadAmount + fee
            	report.setTngFee(new BigDecimal(0.45)); 				// RM 0.45
            	report.setPrintisFee(new BigDecimal(0.30)); 			// RM 0.30
            	report.setCelcomMobileFee(new BigDecimal(0.00)); 		// RM 0.00
            	report.setCmmFee(new BigDecimal(0.25)); 				// RM 0.25
            	report.setTotalFees(new BigDecimal(1)); 				// RM 1
            	report.setAmountDueTng(report.getReloadAmount().add(report.getTngFee()));	// reloadAmount + tng Fee
            	report.setAmountDuePrintis(new BigDecimal(0.30)); 		// RM 0.30
            	report.setAmountDueCelcomMobile(new BigDecimal(0.00)); 	// RM 0.00
            	report.setAmountDueCmm(new BigDecimal(0.30)); 			// RM 0.25
            	
            	listCompleteReport.add(report);
//            	//sum
//            	totalFee = totalFee.add(report.getFees());
//            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
//            	sumTotalChargeCust = sumTotalChargeCust.add(report.getTotalChargeToCustomer());
//            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
//            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
            } catch (Exception e) {
                e.printStackTrace();
            }
		}

//      if(!listCompleteReport.isEmpty()) {
//      	 //the add the sum to the end of the report list
//          Report reportSum = new Report();
//          reportSum.setFees(totalFee);
//          reportSum.setReloadAmount(totalAmountRequest);
//          reportSum.setTotalChargeToCustomer(sumTotalChargeCust);
//          reportSum.setCommissionAmountDeductedBySof(totalCommSofDeduct);
//          reportSum.setNetPaymentToTng(totalNetPaymentTng);
//          listCompleteReport.add(reportSum);       	
//      }		          
        return listCompleteReport;
    }
    
        
    /** 
     * TO DO
     * get result for report dailyTransactionFeeDetailsReport/CE0003
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionFeeDetailsReport() throws Exception {
    	return getDailyTransactionFeeDetailsReport(null, null, -1, -1);
    }
    
    /** 
     * TO DO
     * get result for report dailyTransactionFeeDetailsByRangeOfDatesReport/CE0004
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionFeeDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllStatus();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);
      	
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByParamCelcom(dateMin, dateMax, listStatus, first, size);
    	listReport = copyReloadRequestToReport(listReloadRequest);

//		BigDecimal totalFee= new BigDecimal("0.00");
//		BigDecimal totalAmountRequest = new BigDecimal("0.00");
//		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
//		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
//		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");
    	
		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
				
//				report.setSofRequestedDatetime(report.getRequestedTime());
//            	report.setFees(getReportFee());
//            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
//            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
//            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
//            	listCompleteReport.add(report);
//            	//sum
//            	totalFee = totalFee.add(report.getFees());
//            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
//            	sumTotalChargeCust = sumTotalChargeCust.add(report.getTotalChargeToCustomer());
//            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
//            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
            	listCompleteReport.add(report);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}

//      if(!listCompleteReport.isEmpty()) {
//      	 //the add the sum to the end of the report list
//          Report reportSum = new Report();
//          reportSum.setFees(totalFee);
//          reportSum.setReloadAmount(totalAmountRequest);
//          reportSum.setTotalChargeToCustomer(sumTotalChargeCust);
//          reportSum.setCommissionAmountDeductedBySof(totalCommSofDeduct);
//          reportSum.setNetPaymentToTng(totalNetPaymentTng);
//          listCompleteReport.add(reportSum);       	
//      }		          
        return listCompleteReport;
    }
    
    /** 
     * TODO
     * get result for report summaryDailyTransactionDetailsReport/CE0005
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryDailyTransactionDetailsReport(int first, int size) throws Exception {
    	return getSummaryDailyTransactionDetailsReport(null, null, first, size);
    }
    
    /** 
     * TODO
     * get result for report ummaryDailyTransactionDetailsReportByRangeOfDatesReport/CE0006
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryDailyTransactionDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllStatus();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);
      	
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByParamCelcom(dateMin, dateMax, listStatus, first, size);
    	listReport = copyReloadRequestToReport(listReloadRequest);

//		BigDecimal totalFee= new BigDecimal("0.00");
//		BigDecimal totalAmountRequest = new BigDecimal("0.00");
//		BigDecimal sumTotalChargeCust= new BigDecimal("0.00");
//		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
//		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");
    	
		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
				
//				report.setSofRequestedDatetime(report.getRequestedTime());
//            	report.setFees(getReportFee());
//            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
//            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
//            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
//            	listCompleteReport.add(report);
//            	//sum
//            	totalFee = totalFee.add(report.getFees());
//            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
//            	sumTotalChargeCust = sumTotalChargeCust.add(report.getTotalChargeToCustomer());
//            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
//            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
            	listCompleteReport.add(report);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}

//      if(!listCompleteReport.isEmpty()) {
//      	 //the add the sum to the end of the report list
//          Report reportSum = new Report();
//          reportSum.setFees(totalFee);
//          reportSum.setReloadAmount(totalAmountRequest);
//          reportSum.setTotalChargeToCustomer(sumTotalChargeCust);
//          reportSum.setCommissionAmountDeductedBySof(totalCommSofDeduct);
//          reportSum.setNetPaymentToTng(totalNetPaymentTng);
//          listCompleteReport.add(reportSum);       	
//      }		          
        return listCompleteReport;
    }
}
