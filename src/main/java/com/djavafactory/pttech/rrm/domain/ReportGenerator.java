package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.Constants;
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
	 * @param reloadAmount 
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
	 * @param totalChargeToCust
	 * @return BigDecimal TotalChargeToCustomer
	 */ 
    public static BigDecimal getNetPaymentToTnG(BigDecimal totalChargeToCust, BigDecimal commAmountSof) {	
     	BigDecimal netPayment = totalChargeToCust.subtract(commAmountSof);
     	return netPayment; 
    }

    
    /**
	 * Get sumTotalChargeToCustomer (total reload amount + total fee)
	 * @param totalreloadAmount 
	 * @param totalFee
	 * @return BigDecimal sumTotalChargeToCustomer
	 */ 
    public static BigDecimal getSumTotalChargeToCustomer(BigDecimal totalReloadAmount, BigDecimal totalFee) {	
     	BigDecimal sumTotalChargeToCustomer = totalReloadAmount.add(totalFee);
     	 return sumTotalChargeToCustomer; 
    }
    
    
    public static BigDecimal getSumCommissionAmountDeductedBySof(long totalReloadQty) {	
     	BigDecimal sumCommissionAmountDeductedBySof = getCommAmountDeductedBySOF().multiply(new BigDecimal(totalReloadQty));
     	 return sumCommissionAmountDeductedBySof; 
    }
    
    public static BigDecimal getSumNetPaymentToTng(BigDecimal sumTotalChargeToCustomer, BigDecimal sumCommissionAmountDeductedBySof) {	
     	BigDecimal sumNetPaymentToTng = sumTotalChargeToCustomer.subtract(sumCommissionAmountDeductedBySof);
     	 return sumNetPaymentToTng; 
    }
    
    public static BigDecimal getTotalFee(long totalReloadQty) {	
     	BigDecimal totalFees= getReportFee().multiply(new BigDecimal(totalReloadQty));
     	 return totalFees; 
    }
    
    
    public static int getCurrentDayOfMonth()
    {
    	Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = 1;

    	return DateUtil.getDayOfMonth(year, month, date);
    }
    
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
     * get result for report dailyDetailsRequestReloadFrmCelcomReport/TG0001-Report
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyDetailsRequestReloadFrmCelcomReport() throws Exception {
    	Date dateMin = null;
    	Date dateMax = null;
    	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
    	dateMax = DateUtil.add(dateMin, 5, 1);
    	
    	//all reload request status
    	List listStatus = new ArrayList();
    	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

    	//get reloadrequest list
    	List listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
    	List <Report> listReport = new ArrayList<Report>();

        //call method to copy reload request list to report list
        listReport = copyReloadRequestToReport(listReloadRequest);

        //declare report list to hold complete report data
        List <Report> listCompleteReport = new ArrayList<Report>();

		Iterator it = listReport.iterator();

		//Summary Variables
		BigDecimal totalFee= new BigDecimal("0.00");
		int totalQty = 0;
		BigDecimal totalAmountRequest = new BigDecimal("0.00");
		BigDecimal totalChargeCust= new BigDecimal("0.00");
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
            	totalQty = totalQty + 1;
            	totalChargeCust = totalChargeCust.add(report.getTotalChargeToCustomer());
            	totalCommSofDeduct = totalCommSofDeduct.add(report.getCommissionAmountDeductedBySof());
            	totalNetPaymentTng = totalNetPaymentTng.add(report.getNetPaymentToTng());
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
    public static List<Report> getDailyDetailedReloadFrmCelcomReport() throws Exception {

    	//all reload request status
    	List listStatus = new ArrayList();
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
    	
 		//daily from date and to date
    	Date dateMin = null;
    	Date dateMax = null;   	 
		dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		dateMax = DateUtil.add(dateMin, 5, 1);

    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);

        List <Report> listReport = new ArrayList<Report>();
        
        //call method to copy reload request list to report list
        listReport = copyReloadRequestToReport(listReloadRequest);
        
        //declare report list to hold complete report data
        List <Report> listCompleteReport = new ArrayList<Report>();
        
		Iterator it = listReport.iterator();
		
		//Summary Variables
		BigDecimal totalFee= new BigDecimal("0.00");
		int totalQty = 0;
		BigDecimal totalAmountRequest = new BigDecimal("0.00");
		BigDecimal totalChargeCust= new BigDecimal("0.00");
		BigDecimal totalCommSofDeduct= new BigDecimal("0.00");
		BigDecimal totalNetPaymentTng= new BigDecimal("0.00");

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
    public static List getDailyDetailsCancellationReloadReqFrmCelcomReport() throws Exception {
    	//all reload request status
    	List listStatus = new ArrayList();
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());

    	
 		//daily from date and to date
    	Date dateMin = null;
    	Date dateMax = null;   	 
		dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		dateMax = DateUtil.add(dateMin, 5, 1);
		
		List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
        List <Report> listReport = new ArrayList<Report>();
        
        //call method to copy reload request list to report list
        listReport = copyReloadRequestToReport(listReloadRequest);
        
        //declare report list to hold complete report data
        List <Report> listCompleteReport = new ArrayList<Report>();
        
		Iterator it = listReport.iterator();

		//Summary Variables
		BigDecimal totalFee= new BigDecimal("0.00");
		int totalCancellation = 0;
		BigDecimal totalAmountCancelled = new BigDecimal("0.00");
		BigDecimal totalChargeCust = new BigDecimal("0.00");
		BigDecimal totalCommSofDeduct = new BigDecimal("0.00");
		BigDecimal totalRefundCust = new BigDecimal("0.00");
		
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
  public static List<Report> getDailySettlementReloadFrmCelcomReport() throws Exception {
  	Date dateMin = null;
  	Date dateMax = null;
  	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
  	dateMax = DateUtil.add(dateMin, 5, 1);
  	
  	//all reload request status
  	List listStatus = new ArrayList();
  	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

  	//get reloadrequest list
  	List listReloadRequest = ReloadRequest.findReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);

  	List <Report> listReport = new ArrayList<Report>();

      //call method to copy reload request list to report list
      listReport = copyReloadRequestToReport(listReloadRequest);

      //declare report list to hold complete report data
      List <Report> listCompleteReport = new ArrayList<Report>();

		Iterator it = listReport.iterator();
//
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


    /**
     * get result for report summaryRequestReloadFrmCelcomReport/TG0002
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryRequestReloadFrmCelcomReport() throws Exception {
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
    	int daysOfMonth = getCurrentDayOfMonth();
    	dateMin = getInitDateMin();
    	
    	for (int i=1; i<=daysOfMonth; i++)
    	{	   		
    		dateMax = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
    		
    		if (listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      reportSummary.setSofRequestedDatetime(reportSummary.getRequestedTime());
                      reportSummary.setTotalReloadQty(reportSummary.getMfgNumber()); //to be change
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
    		dateMin = dateMax;
    	}
        return listCompleteReport;
    }


    /**
     * get result for report summaryReloadFrmCelcomReport/TG0004
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryReloadReport() throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = new ArrayList<String>();
    	
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
    	
    	Date dateMin = null;
    	Date dateMax = null;   
    	int daysOfMonth = getCurrentDayOfMonth();
    	dateMin = getInitDateMin();

    	for (int i=1; i<=daysOfMonth; i++)
    	{	   		
    		dateMax = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
    		
    		if (listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      reportSummary.setReloadDate(reportSummary.getRequestedTime());
	                  reportSummary.setTotalReloadQty(reportSummary.getMfgNumber()); //to be change
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
    		dateMin = dateMax;
    	}
        return listCompleteReport;
    }
   
    
    /**
     * get result for report summaryCancellationReloadReqFrmCelcomReport/TG0006
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryCancellationReloadReqFrmCelcomReport() throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	List<String> listStatus = new ArrayList();
    	
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());

    	Date dateMin = null;
    	Date dateMax = null;   
    	int daysOfMonth = getCurrentDayOfMonth();
    	dateMin = getInitDateMin();
    	
    	for (int i=1; i<=daysOfMonth; i++)
    	{	   		
    		dateMax = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
    		
    		if (listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      // summary calculation
	            	  // todo:different between datecancelrequest and reload request date
	            	  reportSummary.setDateCancelRequest(reportSummary.getModifiedDate());
	                  reportSummary.setReloadDate(reportSummary.getRequestedTime());
	                  reportSummary.setTotalCancellationQty(reportSummary.getMfgNumber()); //to be change
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
    		dateMin = dateMax;
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
    public static List<Report> getSummarySettlementReloadFrmCelcomReport() throws Exception {
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
	int daysOfMonth = getCurrentDayOfMonth();
	dateMin = getInitDateMin();
	
	for (int i=1; i<=daysOfMonth; i++)
	{	   		
		dateMax = DateUtil.add(dateMin, 5, 1);   		
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
		
		if (listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
    		Iterator it = listReport.iterator();
    		Report reportSummary = new Report();
            while(it.hasNext()) {        
                try {
                  reportSummary = (Report)it.next();
                   //todo
				  reportSummary.setTransactionDate(reportSummary.getRequestedTime());
		          reportSummary.setTotalReloadQty(reportSummary.getMfgNumber());
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
		dateMin = dateMax;
	}
    return listCompleteReport;
  }
    
    
    
// <<<<<<<<<<<<<<<<<<<<<<<<< CELCOM REPORT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    /** 
     * TO DO
     * get result for report dailyTransactionDetailsReport/CE0001
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionDetailsReport() throws Exception {
    	return getDailyTransactionDetailsReport(null, null);
    }
    
    /** 
     * TO DO
     * get result for report dailyTransactionDetailsByRangeOfDatesReport/CE0002
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionDetailsReport(Date dateMin, Date dateMax) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
        
    	List listStatus = new ArrayList();
    	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

    	List listReloadRequest = ReloadRequest.findReloadRequestsByParam2(dateMin, dateMax, listStatus);
    	listReport = copyReloadRequestToReport(listReloadRequest);

		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
				
            	report.setSofRequestedDatetime(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	
            	listCompleteReport.add(report);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		          
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
    	return getDailyTransactionFeeDetailsReport(null, null);
    }
    
    /** 
     * TO DO
     * get result for report dailyTransactionFeeDetailsByRangeOfDatesReport/CE0004
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getDailyTransactionFeeDetailsReport(Date dateMin, Date dateMax) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
        
    	List listStatus = new ArrayList();
    	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

    	List listReloadRequest = ReloadRequest.findReloadRequestsByParam2(dateMin, dateMax, listStatus);
    	listReport = copyReloadRequestToReport(listReloadRequest);

		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction fee details report
				
            	report.setSofRequestedDatetime(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	
            	listCompleteReport.add(report);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		          
        return listCompleteReport;
    }
    
    /** 
     * TODO
     * get result for report summaryDailyTransactionDetailsReport/CE0005
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> summaryDailyTransactionDetailsReport() throws Exception {
    	return getSummaryDailyTransactionDetailsReport(null, null);
    }
    
    /** 
     * TODO
     * get result for report ummaryDailyTransactionDetailsReportByRangeOfDatesReport/CE0006
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryDailyTransactionDetailsReport(Date dateMin, Date dateMax) throws Exception {
    	List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
        
    	List listStatus = new ArrayList();
    	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

    	List listReloadRequest = ReloadRequest.findReloadRequestsByParam2(dateMin, dateMax, listStatus);
    	listReport = copyReloadRequestToReport(listReloadRequest);

		for (Report report : listReport) {
			try {
				// TODO :set attribute for summary daily transaction details report
				
            	report.setSofRequestedDatetime(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	
            	listCompleteReport.add(report);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		          
        return listCompleteReport;
    }
}
