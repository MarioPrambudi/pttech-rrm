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
	 * @return BigDecimal Fee in BigDecimal
	 */
    public static BigDecimal getReportFee() {    	 
    	 String reportfee =  Configuration.getReportConfigValue(Constants.REPORT_CONFIG_FEE);
    	 BigDecimal decimalFee = new BigDecimal(reportfee); 
    	 return decimalFee;
    }

    /**
	 * Get celcom commission value from configuration
	 * @param none
	 * @return BigDecimal Celcom commission in BigDecimal
	 */ 
    public static BigDecimal getReportCelComm() {	      
    	 String reportCelComm = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_CELCOMM);
    	 BigDecimal decimalCelComm = new BigDecimal(reportCelComm); 
    	 return decimalCelComm;    	 
    }
    
    /**
	 * Get Tng commission value from configuration
	 * @param none
	 * @return BigDecimal Tng commission commission in BigDecimal
	 */ 
    public static BigDecimal getReportTngComm() {	      
     	 String reportTngComm = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_TNGCOMM);
     	 BigDecimal decimalTngComm= new BigDecimal(reportTngComm); 
     	 return decimalTngComm; 
    }
    
    /**
	 * Get totalChargeToCustomer (reload amount + fee)
	 * @param reloadAmount BigDecimal
	 * @return BigDecimal TotalChargeToCustomer
	 */ 
    public static BigDecimal getTotalChargeToCustomer(BigDecimal reloadAmount) {	
     	BigDecimal fee = getReportFee();
     	BigDecimal sumForReloadAmountAndFee = fee.add(reloadAmount);
     	 
     	 return sumForReloadAmountAndFee; 
    }
    
    /**
	 * Get commission amount deducted by SOF (fee x celcomm)
	 * @param none
	 * @return BigDecimal commAmount
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
	 * @return BigDecimal TotalChargeToCustomer
	 */ 
    public static BigDecimal getNetPaymentToTnG(BigDecimal totalChargeToCust, BigDecimal commAmountSof) {	
     	BigDecimal netPayment = totalChargeToCust.subtract(commAmountSof);
     	return netPayment; 
    }

    
    /**
	 * Get sumTotalChargeToCustomer (total reload amount + total fee)
	 * @param totalreloadAmount BigDecimal
	 * @param totalFee BigDecimal
	 * @return BigDecimal sumTotalChargeToCustomer
	 */ 
    public static BigDecimal getSumTotalChargeToCustomer(BigDecimal totalReloadAmount, BigDecimal totalFee) {	
     	BigDecimal sumTotalChargeToCustomer = totalReloadAmount.add(totalFee);
     	 return sumTotalChargeToCustomer; 
    }
    
    /**
	 * Get SumCommissionAmountDeductedBySof (CommAmountDeductedBySOF x totalReloadQty)
	 * @param totalReloadQty long
	 * @return BigDecimal SumCommissionAmountDeductedBySof
	 */ 
    public static BigDecimal getSumCommissionAmountDeductedBySof(long totalReloadQty) {	
     	BigDecimal sumCommissionAmountDeductedBySof = getCommAmountDeductedBySOF().multiply(new BigDecimal(totalReloadQty));
     	 return sumCommissionAmountDeductedBySof; 
    }
    
    /**
	 * Get SumNetPaymentToTng (sumTotalChargeToCustomer - sumCommissionAmountDeductedBySof)
	 * @param sumTotalChargeToCustomer BigDecimal
	 * @param sumCommissionAmountDeductedBySof BigDecimal
	 * @return BigDecimal SumNetPaymentToTng
	 */
    public static BigDecimal getSumNetPaymentToTng(BigDecimal sumTotalChargeToCustomer, BigDecimal sumCommissionAmountDeductedBySof) {	
     	BigDecimal sumNetPaymentToTng = sumTotalChargeToCustomer.subtract(sumCommissionAmountDeductedBySof);
     	 return sumNetPaymentToTng; 
    }
       
    /**
	 * Get totalFees (ReportFee x totalReloadQty)
	 * @param totalReloadQty long
	 * @return BigDecimal totalFees 
	 */ 
    public static BigDecimal getTotalFee(long totalReloadQty) {	
     	BigDecimal totalFees= getReportFee().multiply(new BigDecimal(totalReloadQty));
     	 return totalFees; 
    } 
    
//    /**
//	 * Get getCurrentDayOfMonth
//	 * @param none
//	 * @return days of month in int
//	 */ 
//    public static int getCurrentDayOfMonth()
//    {
//    	Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int date = 1;
//
//    	return DateUtil.getDayOfMonth(year, month, date);
//    }
    
    /**
	 * Get get initial date value
	 * @param none
	 * @return date in 01/MM/yyyy
	 */
    public static Date getInitDateMin()
    {
	  	Calendar cal = Calendar.getInstance();
	  	Calendar calNow = Calendar.getInstance();
	    int date = 1;
	    cal.set(calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH), date);
	    return cal.getTime();
    }
	/*
	 * end calculation methods
   	*/
    
    /**
	 * To copy reload request list to report list
	 * @param listReloadRequest A list of reload request
	 * @return List Report list
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
	            listReport.add(report);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return listReport;   	
    }

   
    
    
    /*
	 * TNG Report
	 */
   	/**
     * get result for report dailyDetailsRequestReloadFrmCelcomReport/TG0001-Report
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List getDailyDetailsRequestReloadFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = new ArrayList<String>();
      	
      	Date dateMin = null;
    	Date dateMax = null;
    	
		if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
	    	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
	    	dateMax = DateUtil.add(dateMin, 5, 1);
		}
		else
		{
			dateMin = DateUtil.convertStringToDate(dateMinStr);
			dateMax = DateUtil.convertStringToDate(dateMaxStr);
		}  	
    	
    	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 


    	List listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
        listReport = copyReloadRequestToReport(listReloadRequest);
		Iterator it = listReport.iterator();

		//Summary Variables
//		BigDecimal totalFee= new BigDecimal("0.00");
//		int totalQty = 0;
//		BigDecimal totalAmountRequest = new BigDecimal("0.00");
//		BigDecimal totalChargeCust= new BigDecimal("0.00");
//		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
//		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");
		
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
//            	totalFee = totalFee.add(report.getFees());
//            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
//            	totalQty = totalQty + 1;
//            	totalChargeCust = totalChargeCust.add(report.getTotalChargeToCustomer());
//            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
//            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
          
        return listCompleteReport;

    }
    
    
    /**
     * get result for report dailyDetailedReloadFrmCelcomReport/TG0003
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyDetailedReloadFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = new ArrayList<String>();
      	
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
    	
    	Date dateMin = null;
    	Date dateMax = null;
    	
		if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
	    	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
	    	dateMax = DateUtil.add(dateMin, 5, 1);
		}
		else
		{
			dateMin = DateUtil.convertStringToDate(dateMinStr);
			dateMax = DateUtil.convertStringToDate(dateMaxStr);
		}  	

    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
        listReport = copyReloadRequestToReport(listReloadRequest);        
		Iterator it = listReport.iterator();
		
		//Summary Variables
//		BigDecimal totalFee= new BigDecimal("0.00");
//		int totalQty = 0;
//		BigDecimal totalAmountRequest = new BigDecimal("0.00");
//		BigDecimal totalChargeCust= new BigDecimal("0.00");
//		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
//		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");

        while(it.hasNext())
		{        
            try {

            	Report report = (Report)it.next();
            	//manually set value into report fields
            	report.setReloadDate(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	listCompleteReport.add(report);
            	
//            	//sum
//            	totalFee = totalFee.add(report.getFees());
//            	totalAmountRequest = totalAmountRequest.add(report.getReloadAmount());
//            	totalQty = totalQty + 1;
//            	totalChargeCust = totalChargeCust.add(report.getTotalChargeToCustomer());
//            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
//            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
            	

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        return listCompleteReport;
    }
    
    /**
     * get result for report dailyDetailsCancellationReloadReqFrmCelcomReport/TG0005 
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List getDailyDetailsCancellationReloadReqFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
    	//all reload request status
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = new ArrayList<String>();
    	     	 
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	
    	Date dateMin = null;
    	Date dateMax = null;
    	
		if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
	    	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
	    	dateMax = DateUtil.add(dateMin, 5, 1);
		}
		else
		{
			dateMin = DateUtil.convertStringToDate(dateMinStr);
			dateMax = DateUtil.convertStringToDate(dateMaxStr);
		}  	
		
		List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);   
        listReport = copyReloadRequestToReport(listReloadRequest);
		Iterator it = listReport.iterator();

		//Summary Variables
//		BigDecimal totalFee= new BigDecimal("0.00");
//		int totalCancellation = 0;
//		BigDecimal totalAmountCancelled = new BigDecimal("0.00");
//		BigDecimal totalChargeCust = new BigDecimal("0.00");
//		BigDecimal totalCommSofDeduct = new BigDecimal("0.00");
//		BigDecimal totalRefundCust = new BigDecimal("0.00");
		
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
            	report.setDateRefundedCustomer(report.getModifiedDate());
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
//            	totalChargeCust = totalChargeCust.add(report.getTotalChargeToCustomer());
//            	totalCancellation = totalCancellation + 1;
//            	totalAmountCancelled = totalAmountCancelled.add(report.getReloadAmount());
//            	totalFee = totalFee.add(report.getFees());
//            	totalRefundCust = totalRefundCust.add(report.getAmountRefundedToCustomer());
//            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
            	

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        return listCompleteReport;
    }
    

    /**
     * TO DO
     * get result for report dailySettlementReloadReqFrmCelcomReport/TG0007 
     * @param none
     * @return List A report list
     * @throws Exception 
     */
  public static List<Report> getDailySettlementReloadFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
	List<Report> listReport = new ArrayList<Report>();
    List<Report> listCompleteReport = new ArrayList<Report>();
  	List<String> listStatus = new ArrayList<String>();
  	
	Date dateMin = null;
	Date dateMax = null;
	
	if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
    	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
    	dateMax = DateUtil.add(dateMin, 5, 1);
	}
	else
	{
		dateMin = DateUtil.convertStringToDate(dateMinStr);
		dateMax = DateUtil.convertStringToDate(dateMaxStr);
	}  	
  		
  	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
	
  	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
    listReport = copyReloadRequestToReport(listReloadRequest);
	Iterator it = listReport.iterator();
	
      while(it.hasNext())
		{        
          try {
          	Report report = (Report)it.next();
          	// TO DO 
          	report.setTransactionDate(report.getRequestedTime());
          	report.setTotalReloadQty(listReport.size());
          	report.setGrossPaymentToTngRm(report.getReloadAmount());
          	report.setTotalCancellationRm(report.getReloadAmount());
          	report.setAmountCreditedToTngRm(report.getReloadAmount());
          	report.setDateCreditedToTngAccount(report.getRequestedTime());

          	listCompleteReport.add(report);
          } catch (Exception e) {
              e.printStackTrace();  
          }

      }       
      return listCompleteReport;
  }

  	//Tng Summary Reports
    /**
     * get result for report summaryRequestReloadFrmCelcomReport/TG0002
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryRequestReloadFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = new ArrayList<String>();
    	
    	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
     	
    	Date dateMin = null;
    	Date dateMax = null;
    	Date dateMaxSearch = null;
    	
    	if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
    		dateMin = getInitDateMin();
    		dateMax = DateUtil.add(dateMin, 2, 1);
    	}
    	else
    	{
    		dateMin = DateUtil.convertStringToDate(dateMinStr);
    		dateMax = DateUtil.convertStringToDate(dateMaxStr);
    	}  

    	while(dateMin.before(dateMax))
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus);
    		
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
        return listCompleteReport;
    }


    /**
     * get result for report summaryReloadFrmCelcomReport/TG0004
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryReloadReport(String dateMinStr, String dateMaxStr) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = new ArrayList<String>();
    	
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
    	
    	Date dateMin = null;
    	Date dateMax = null;
    	Date dateMaxSearch = null;
    	
    	if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
    		dateMin = getInitDateMin();
    		dateMax = DateUtil.add(dateMin, 2, 1);
    	}
    	else
    	{
    		dateMin = DateUtil.convertStringToDate(dateMinStr);
    		dateMax = DateUtil.convertStringToDate(dateMaxStr);
    	}  

    	while(dateMin.before(dateMax))
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus);
    		
    		if (listReloadRequest != null && listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      reportSummary.setReloadDate(reportSummary.getRequestedTime());
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
        return listCompleteReport;
    }
   
    
    /**
     * get result for report summaryCancellationReloadReqFrmCelcomReport/TG0006
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryCancellationReloadReqFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = new ArrayList();
    	
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());

    	Date dateMin = null;
    	Date dateMax = null;
    	Date dateMaxSearch = null;
    	
    	if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
    		dateMin = getInitDateMin();
    		dateMax = DateUtil.add(dateMin, 2, 1);
    	}
    	else
    	{
    		dateMin = DateUtil.convertStringToDate(dateMinStr);
    		dateMax = DateUtil.convertStringToDate(dateMaxStr);
    	}  

    	while(dateMin.before(dateMax))	   		
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		  		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus);
    		
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
        return listCompleteReport;
    }
  
    /** 
     * TO DO
     * get result for report summarySettlementReloadReqFrmCelcomReport/TG0008
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummarySettlementReloadFrmCelcomReport(String dateMinStr, String dateMaxStr) throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  List listStatus = new ArrayList();


	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

	Date dateMin = null;
	Date dateMax = null;
	Date dateMaxSearch = null;
	
	if("null".equals(dateMinStr) | "null".equals(dateMaxStr)){
		dateMin = getInitDateMin();
		dateMax = DateUtil.add(dateMin, 2, 1);
	}
	else
	{
		dateMin = DateUtil.convertStringToDate(dateMinStr);
		dateMax = DateUtil.convertStringToDate(dateMaxStr);
	}  

	while(dateMin.before(dateMax))
	{	   		   		
		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus);
		
		if (listReloadRequest != null && listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
    		Iterator it = listReport.iterator();
    		Report reportSummary = new Report();
            while(it.hasNext()) {        
                try {
                  reportSummary = (Report)it.next();
                   //todo
				  reportSummary.setTransactionDate(reportSummary.getRequestedTime());
		          reportSummary.setGrossPaymentToTngRm(reportSummary.getReloadAmount());
		          reportSummary.setTotalCancellationRm(reportSummary.getReloadAmount());
		          reportSummary.setAmountCreditedToTngRm(reportSummary.getReloadAmount());
		          reportSummary.setDateCreditedToTngAccount(reportSummary.getRequestedTime());
  		        } catch (Exception e) {
                    e.printStackTrace();  
                }
            }
            listCompleteReport.add(reportSummary);    		
		}  		
		dateMin = dateMaxSearch;
	}
    return listCompleteReport;

  }
    
    /*
	 * End of TNG Report
	 */
}
