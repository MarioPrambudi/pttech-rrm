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
	 * Get SOF value from configuration
	 * @param none
	 * @return BigDecimal - SOF
	 */
    public static BigDecimal getReportSOF() {	      
    	 String reportSOF = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_SOF);
    	 BigDecimal decimalSOF = new BigDecimal(reportSOF); 
    	 return decimalSOF;    	 
    }
    
    /**
	 * Get TNG value from configuration
	 * @param none
	 * @return BigDecimal - TNG
	 */
    public static BigDecimal getReportTng() {	      
     	 String reportTng = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_TNG);
     	 BigDecimal decimalTng= new BigDecimal(reportTng); 
     	 return decimalTng; 
    }
    
    /**
	 * Get RS value from configuration
	 * @param none
	 * @return BigDecimal - RS
	 */
    public static BigDecimal getReportRS() {	      
    	 String reportRS = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_RS);
    	 BigDecimal decimalRS= new BigDecimal(reportRS); 
    	 return decimalRS; 
   }
   
    /**
	 * Get AT value from configuration
	 * @param none
	 * @return BigDecimal - AT
	 */
    public static BigDecimal getReportAT() {	      
    	 String reportAT = Configuration.getReportConfigValue(Constants.REPORT_CONFIG_AT);
    	 BigDecimal decimalAT = new BigDecimal(reportAT); 
    	 return decimalAT; 
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
	 * Get commission amount deducted by SOF (sof + rs)
	 * @param none
	 * @return BigDecimal - Commission Amount
	 */ 
    public static BigDecimal getCommDeductedBySOF() {	           	     	 
     	 BigDecimal commSOF = getReportRS().add(getReportSOF());     	 
     	 return commSOF; 
    }  
      
    /**
	 * Get net payment TnG
	 * @param totalChargeToCust BigDecimal
	 * @return BigDecimal - netPayment
	 */ 
    public static BigDecimal getNetPaymentToTnG(BigDecimal totalChargeToCust) {	
     	BigDecimal netPayment = totalChargeToCust.subtract(getCommDeductedBySOF());
     	return netPayment; 
    }
    
    /**
	 * Get SumCommissionAmountDeductedBySof (CommAmountDeductedBySOF x totalReloadQty)
	 * @param totalReloadQty long
	 * @return BigDecimal - SumCommissionAmountDeductedBySof
	 */ 
    public static BigDecimal getSumCommissionAmountDeductedBySof(long totalReloadQty) {	
     	BigDecimal sumCommissionAmountDeductedBySof = getCommDeductedBySOF().multiply(new BigDecimal(totalReloadQty));
     	 return sumCommissionAmountDeductedBySof; 
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
	 * Get getGrossAmount (reloadAmount + fee)
	 * @param reloadAmount BigDecimal 
	 * @return BigDecimal - grossAmount
	 */
    public static BigDecimal getGrossAmount(BigDecimal reloadAmount) {	
     	BigDecimal grossAmount = reloadAmount.add(getReportFee());
     	 return grossAmount; 
    }

    /**
	 * Get getTngFee (Fee x (TNG + AT))
	 * @param none 
	 * @return BigDecimal - tngFee
	 */
    public static BigDecimal getTngFee() {	
     	BigDecimal tngFee = getReportFee().multiply(getReportAT().add(getReportTng()));  	
     	return tngFee; 
    }
    
    /**
	 * Get getPrintisFee (Fee x RS)
	 * @param none 
	 * @return BigDecimal - printisFee
	 */
    public static BigDecimal getPrintisFee() {	
     	BigDecimal printisFee = getReportFee().multiply(getReportRS());  	
     	return printisFee; 
    }
    
    /**
	 * Get getPrintisFee (Fee x SOF)
	 * @param none 
	 * @return BigDecimal - printisFee
	 */
    public static BigDecimal getCmmFee() {	
     	BigDecimal cmmFee = getReportFee().multiply(getReportSOF());  	
     	return cmmFee; 
    }
    
    /** TODO celcom x fee
	 * Get getCelcomFee (Fee x RS)
	 * @param none 
	 * @return BigDecimal - celcomFee
	 */
    public static BigDecimal getCelcomFee() {	
     	BigDecimal celcomFee = new BigDecimal(0.00);  	
     	return celcomFee; 
    }
    
    /**
	 * Get getCelcomTotalFee (cmmFee + tngFee + printisFee + celcomFee)
	 * @param none 
	 * @return BigDecimal - celcomTotalFee
	 */  
    public static BigDecimal getCelcomTotalFee() {	
     	BigDecimal celcomTotalFee = getCmmFee().add(getTngFee()).add(getPrintisFee()).add(getCelcomFee());
     	return celcomTotalFee; 
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
	 * Get getAmountDueTng (reloadAmount + tngFee)
	 * @param reloadAmount BigDecimal 
	 * @return BigDecimal - amountDueTng
	 */
    public static BigDecimal getAmountDueTmg(BigDecimal reloadAmount) {	
     	BigDecimal amountDueTmg =  reloadAmount.add(getTngFee());  	
     	return amountDueTmg ; 
    }   
    
    /**
	 * Get getAmountDuePrintis (reloadAmount + printisFee)
	 * @param reloadAmount BigDecimal 
	 * @return BigDecimal - amountDuePrintis
	 */
    public static BigDecimal getAmountDuePrintis(BigDecimal reloadAmount) {	
     	BigDecimal amountDuePrintis =  reloadAmount.add(getPrintisFee());  	
     	return amountDuePrintis ; 
    }
    
    /**
	 * Get getAmountDueCelcomMobile (reloadAmount + celcomFee)
	 * @param reloadAmount BigDecimal 
	 * @return BigDecimal - amountDueCelcomMobile
	 */
    public static BigDecimal getAmountDueCelcomMobile(BigDecimal reloadAmount) {	
     	BigDecimal amountDueCelcomMobile =  reloadAmount.add(getCelcomFee());  	
     	return amountDueCelcomMobile ; 
    }
    
    /**
	 * Get getAmountDueCmm (reloadAmount + cmmFee)
	 * @param reloadAmount BigDecimal 
	 * @return BigDecimal - amountDueCmm
	 */
    public static BigDecimal getAmountDueCmm(BigDecimal reloadAmount) {	
     	BigDecimal amountDueCmm =  reloadAmount.add(getCmmFee());  	
     	return amountDueCmm ; 
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
	 * get total number of report based on requested date between and status
	 * @param String dateMinStr
	 * @param String dateMaxStr 
	 * @param listStatus List<String>  
	 * @return long - total number of report
	 */
	  public static long getTotalReportCelcom(String dateMinStr, String dateMaxStr, List<String> listStatus) throws ParseException
	  {
		  Date dateMin = getDateMin(dateMinStr);
		  Date dateMax = getDateMax(dateMin, dateMaxStr);	
		  long totalReport = ReloadRequest.totalReloadRequests(dateMin, dateMax, listStatus);
		  return totalReport;
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
            	report.setCommissionAmountDeductedBySof(getCommDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer()));
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
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMax, listStatus, first, size).getResultList();
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
            	report.setCommissionAmountDeductedBySof(getCommDeductedBySOF());
            	report.setNetPaymentToTng(report.getTotalChargeToCustomer().subtract(report.getCommissionAmountDeductedBySof()));
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
		List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMax, listStatus, first, size).getResultList();
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
            	report.setCommissionAmountDeductedBySof(getCommDeductedBySOF());
            	report.setNetPaymentToTng(report.getTotalChargeToCustomer().subtract(report.getCommissionAmountDeductedBySof()));
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

	  while(dateMin.before(dateMax))
	  {	   		   		
		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
		
		if (listReloadRequest != null && listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
			Iterator it = listReport.iterator();
			Report reportSummary = new Report();
	        while(it.hasNext()) {        
	            try {
	              reportSummary = (Report)it.next();
				  reportSummary.setReloadDate(reportSummary.getModifiedDate());
				  reportSummary.setGrossPaymentToTngRm(getTotalPaymentToTNG(reportSummary.getTotalReloadQty(), reportSummary.getReloadAmount()));		         		          
		          reportSummary.setTotalAmountCancelledRm(reportSummary.getReloadAmount());
				  reportSummary.setAmountCreditedToTngRm(reportSummary.getGrossPaymentToTngRm().subtract(reportSummary.getTotalCancellationRm()));
		          //reportSummary.setDateCreditedToTngAccount(); Blank //TODO
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
	   * get result for report dailyCommissionReloadFrmCchsForCelcomReport/TG0009
	   * @param dateMinStr String
	   * @param dateMaxStr String
	   * @param first int 
	   * @param size int
	   * @return List<Report> listReport
	   * @throws Exception 
	   */
	public static List<Report> getDailyCommissionReloadFrmCchsForCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
		List<Report> listReport = new ArrayList<Report>();
        List<Report> listCompleteReport = new ArrayList<Report>();
      	List<String> listStatus = getListAllCancel();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	 			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMax, listStatus, first, size).getResultList();
        listReport = copyReloadRequestToReport(listReloadRequest);
		Iterator it = listReport.iterator();

        while(it.hasNext())
		{        
            try {

            	Report report = (Report)it.next();
            	//manually set value into report fields
            	report.setReloadDateTime(report.getModifiedDate());
            	report.setFees(getReportFee());
            	report.setCommissionAmountDeductedBySof(getReportSOF());
            	report.setTotalReloadQty(1); //TODO            	
            	report.setTngFee(getReportTng()); 						
            	report.setCelcomMobileFee(getReportRS()); 		// TODO
            	report.setCmmFee(getReportAT()); 			
            	listCompleteReport.add(report);            	
      	      	           	
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return listCompleteReport;
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
    	List<String> listStatus = getListAllStatus();
     	
    	Date dateMin = getSummaryDateMin(dateMinStr);
    	Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
    	Date dateMaxSearch = null;
    	
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
                      reportSummary.setSumTotalChargeToCustomer(reportSummary.getTotalAmountRequestRm().add(reportSummary.getTotalFees()));
                      reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalReloadQty()));
                      reportSummary.setSumNetPaymentToTng(reportSummary.getSumTotalChargeToCustomer().subtract(reportSummary.getSumCommissionAmountDeductedBySof()));                  
                      listCompleteReport.add(reportSummary); 
                    } catch (Exception e) {
                        e.printStackTrace();  
                    }
                }
                   		
    		}  		
    		dateMin = dateMaxSearch;
    	}	
    	
    	return listCompleteReport;
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
    	Date dateMin = getSummaryDateMin(dateMinStr);
    	Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
    	Date dateMaxSearch = null;
		
    	while(dateMin.before(dateMax))
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
    		
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
	                  reportSummary.setSumTotalChargeToCustomer(reportSummary.getTotalReloadAmountRm().add(reportSummary.getTotalFees()));
	                  reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalReloadQty()));
	                  reportSummary.setSumNetPaymentToTng(reportSummary.getSumTotalChargeToCustomer().subtract(reportSummary.getSumCommissionAmountDeductedBySof()));                    
	                  listCompleteReport.add(reportSummary); 
	                } catch (Exception e) {
                        e.printStackTrace();  
                    }
                }
                 		
    		}  		
    		dateMin = dateMaxSearch;
    	}
 
    	return listCompleteReport;
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

    	while(dateMin.before(dateMax))	   		
    	{	   		
    		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   		  		
    		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
    		
    		if (listReloadRequest != null && listReloadRequest.size()>0)
    		{
    			listReport = copyReloadRequestToReport(listReloadRequest);    		
        		Iterator it = listReport.iterator();
        		Report reportSummary = new Report();
                while(it.hasNext()) {        
                    try {
                      reportSummary = (Report)it.next();
                      // summary calculation
	            	  reportSummary.setDateCancelRequest(reportSummary.getModifiedDate());//TODO
	                  reportSummary.setTotalCancellationQty(reportSummary.getTotalReloadQty());
	                  reportSummary.setTotalAmountCancelledRm(reportSummary.getReloadAmount());
	                  reportSummary.setTotalFees(getTotalFee(reportSummary.getTotalCancellationQty()));
	                  reportSummary.setTotalRefundToCustomerRm(reportSummary.getTotalAmountCancelledRm());
	                  reportSummary.setSumCommissionAmountDeductedBySof(getSumCommissionAmountDeductedBySof(reportSummary.getTotalCancellationQty()));                   
	                  listCompleteReport.add(reportSummary);    		
	                } catch (Exception e) {
                        e.printStackTrace();  
                    }
                }
               
    		}  		
    		dateMin = dateMaxSearch;
    	}
    
    	return listCompleteReport;
   	 
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
	  
	  while(dateMin.before(dateMax))
	  {	   		   		
		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
		
		if (listReloadRequest != null && listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
			
			for(Report reportSummary : listReport)
			{        
	            try {
				  reportSummary.setReloadDate(reportSummary.getModifiedDate());
				  reportSummary.setTotalPaymentToTngRm(getTotalPaymentToTNG(reportSummary.getTotalReloadQty(), reportSummary.getReloadAmount()));		         		          
				  reportSummary.setTotalCancellationRm(reportSummary.getReloadAmount()); 
				  reportSummary.setNetPaymentToTng(reportSummary.getTotalPaymentToTngRm().subtract(reportSummary.getTotalCancellationRm()));		          
		          //reportSummary.setDateCreditedToTngAccount(); Blank //TODO
		          listCompleteReport.add(reportSummary); 
		        } catch (Exception e) {
	                e.printStackTrace();  
	            }
	        }         		
		}  		
		dateMin = dateMaxSearch;
	  }

	  	return listCompleteReport; 	 
  }
    
    /** 
     * get result for report monthlyCommissionReloadFrmCchsForCelcomReport/TG0010
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getMonthlyCommissionReloadFrmCchsForCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  List<String> listStatus = getListAllStatusNotPending();
	  List<Report> listReportPage = new ArrayList<Report>();
	  Date dateMin = getSummaryDateMin(dateMinStr);
	  Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
	  Date dateMaxSearch = null;

	  while(dateMin.before(dateMax))
	  {	   		   		
		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByModifiedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
		
		if (listReloadRequest != null && listReloadRequest.size()>0)
		{
			listReport = copyReloadRequestToReport(listReloadRequest);    		
			
			for(Report reportSummary : listReport)
			{        
	            try {
	              reportSummary.setCommissionAmountDeductedBySof(getReportSOF().multiply(new BigDecimal (reportSummary.getTotalReloadQty())));
				  reportSummary.setReloadDateTime(reportSummary.getModifiedDate());
				  reportSummary.setFees(getTotalFee(reportSummary.getTotalReloadQty()));
				  reportSummary.setTngFee(getReportTng().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 						
 	              reportSummary.setCelcomMobileFee(getReportRS().multiply(new BigDecimal (reportSummary.getTotalReloadQty())));
 	              reportSummary.setCmmFee(getReportAT().multiply(new BigDecimal (reportSummary.getTotalReloadQty())));
		          listCompleteReport.add(reportSummary); 
		        } catch (Exception e) {
	                e.printStackTrace();  
	            }
	        }         		
		}  		
		dateMin = dateMaxSearch;
	  }	
	  	return listCompleteReport;
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
    	List<Report> listReportResult = new ArrayList<Report>();
    	listReportResult = getDailyTransactionDetailsReport("null", "null", first, size);
    	return listReportResult;
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
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByParamCelcom(dateMin, dateMax, listStatus, first, size).getResultList();
    	listReport = copyReloadRequestToReport(listReloadRequest);

		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
//				report.setAircashAccNo(); 	blank
				report.setTngMfgNo(report.getMfgNumber()); 		// TODO
				report.setTngTrxId(report.getTransId());
            	report.setFees(getReportFee());
            	report.setDate(report.getRequestedTime());
            	report.setTime(report.getRequestedTime());
            	report.setGrossAmount(getGrossAmount(report.getReloadAmount())); 		
            	report.setTngFee(getTngFee()); 				
            	report.setPrintisFee(getPrintisFee()); 			
            	report.setCelcomMobileFee(getCelcomFee()); 		// RM 0.00
            	report.setCmmFee(getCmmFee()); 				
            	report.setTotalGrossAmount(getCelcomTotalFee()); 				
            	report.setAmountDueTng(getAmountDueTmg(report.getReloadAmount()));	
            	report.setAmountDuePrintis(getAmountDuePrintis(report.getReloadAmount())); 		
            	report.setAmountDueCelcomMobile(getAmountDueCelcomMobile(report.getReloadAmount())); 
            	report.setAmountDueCmm(getAmountDueCmm(report.getReloadAmount())); 			      	
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
    public static List<Report> getDailyTransactionFeeDetailsReport(int first, int size) throws Exception {
    	List<Report> listReportResult = new ArrayList<Report>();
    	listReportResult = getDailyTransactionFeeDetailsReport("null", "null", first, size);
    	return listReportResult;
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
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findReloadRequestsByParamCelcom(dateMin, dateMax, listStatus, first, size).getResultList();
    	listReport = copyReloadRequestToReport(listReloadRequest);
		
    	for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
				report.setTngMfgNo(report.getMfgNumber()); 		// TODO
				report.setDate(report.getRequestedTime());
            	report.setTime(report.getRequestedTime());
				report.setFees(getReportFee());           
            	report.setTngFee(getTngFee()); 	
            	report.setGrossAmount(getGrossAmount(report.getReloadAmount())); 
            	report.setPrintisFee(getPrintisFee()); 			
            	report.setCelcomMobileFee(getCelcomFee()); 		// RM 0.00 TODO
            	report.setCmmFee(getCmmFee()); 			
            	report.setTotalGrossAmount(getCelcomTotalFee()); 				
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
    public static List<Report> getSummaryDailyTransactionDetailsReport(int first, int size) throws Exception {
      	List<Report> listReportResult = new ArrayList<Report>();
    	listReportResult = getSummaryDailyTransactionDetailsReport("null", "null", first, size);
    	return listReportResult;
    }
    
    /** 
     * TODO
     * get result for report summaryDailyTransactionDetailsReportByRangeOfDatesReport/CE0006
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryDailyTransactionDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
      List <Report> listReport = new ArrayList<Report>();
   	  List <Report> listCompleteReport = new ArrayList<Report>();
   	  List<String> listStatus = getListAllStatusNotPending();
   	  List<Report> listReportPage = new ArrayList<Report>();
   	  Date dateMin = getDateMin(dateMinStr);
   	  Date dateMax = getDateMax(dateMin, dateMaxStr);		
   	  Date dateMaxSearch = null;
   	  
   	  while(dateMin.before(dateMax))
   	  {	   		   		
   		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
   		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
   		
   		if (listReloadRequest != null && listReloadRequest.size()>0)
   		{
   			listReport = copyReloadRequestToReport(listReloadRequest);    		
   			
   			for(Report reportSummary : listReport)
   			{        
   	            try {
   	          // TODO :set attribute for daily transaction details report
   	            	reportSummary.setDate(reportSummary.getRequestedTime());
   	            	reportSummary.setFees(getTotalFee(reportSummary.getTotalReloadQty()));
   	            	reportSummary.setGrossAmount(reportSummary.getReloadAmount().add(reportSummary.getFees()));
   	            	reportSummary.setTngFee(getTngFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 				
   	            	reportSummary.setPrintisFee(getPrintisFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 		
   	            	reportSummary.setCelcomMobileFee(getCelcomFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 				
   	            	reportSummary.setCmmFee(getCmmFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 		
   	            	reportSummary.setTotalGrossAmount(reportSummary.getTngFee().add(reportSummary.getPrintisFee().add(reportSummary.getCmmFee()))); 	//sum up all fee		
   	            	reportSummary.setAmountDueTng(reportSummary.getReloadAmount().add(reportSummary.getTngFee()));	
   	            	reportSummary.setAmountDuePrintis(reportSummary.getReloadAmount().add(reportSummary.getPrintisFee())); 		
   	            	reportSummary.setAmountDueCelcomMobile(reportSummary.getReloadAmount().add(reportSummary.getCelcomMobileFee())); 	
   	            	reportSummary.setAmountDueCmm(reportSummary.getReloadAmount().add(reportSummary.getCmmFee())); 			                 
   	            	listCompleteReport.add(reportSummary); 
   		        } catch (Exception e) {
   	                e.printStackTrace();  
   	            }
   	        }         		
   		}  		
   		dateMin = dateMaxSearch;
   	  }
	
   	  	return listCompleteReport;
    }
    
    /** 
     * TODO
     * get result for report summaryDailyTransactionDetailsReport/CE0007
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryDailyFeeReport(int first, int size) throws Exception {
      	List<Report> listReportResult = new ArrayList<Report>();
    	listReportResult = getSummaryDailyFeeByRangeDateReport("null", "null", first, size);
    	return listReportResult;
    }
    
    /** 
     * TODO
     * get result for report summaryDailyTransactionDetailsReportByRangeOfDatesReport/CE0008
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception 
     */
    public static List<Report> getSummaryDailyFeeByRangeDateReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
      List <Report> listReport = new ArrayList<Report>();
   	  List <Report> listCompleteReport = new ArrayList<Report>();
   	  List<String> listStatus = getListAllStatusNotPending();
   	  List<Report> listReportPage = new ArrayList<Report>();
   	  Date dateMin = getDateMin(dateMinStr);
   	  Date dateMax = getDateMax(dateMin, dateMaxStr);		
   	  Date dateMaxSearch = null;
   	  
   	  while(dateMin.before(dateMax))
   	  {	   		   		
   		dateMaxSearch = DateUtil.add(dateMin, 5, 1);   			
   		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMaxSearch, listStatus, -1, 0);
   		
   		if (listReloadRequest != null && listReloadRequest.size()>0)
   		{
   			listReport = copyReloadRequestToReport(listReloadRequest);    		
   			
   			for(Report reportSummary : listReport)
   			{        
   	            try {
   	          // TODO :set attribute for daily transaction details report
   	            reportSummary.setFees(getTotalFee(reportSummary.getTotalReloadQty()));  	            	
            	reportSummary.setTngFee(getTngFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 				
            	reportSummary.setPrintisFee(getPrintisFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 		
            	reportSummary.setCelcomMobileFee(getCelcomFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 		
            	reportSummary.setCmmFee(getCmmFee().multiply(new BigDecimal (reportSummary.getTotalReloadQty()))); 		
            	reportSummary.setTotalGrossAmount(reportSummary.getTngFee().add(reportSummary.getPrintisFee().add(reportSummary.getCmmFee()))); 	//sum up all fee		                  
   	            listCompleteReport.add(reportSummary); 
   		        } catch (Exception e) {
   	                e.printStackTrace();  
   	            }
   	        }         		
   		}  		
   		dateMin = dateMaxSearch;
   	  }
   	  	return listCompleteReport;
    }
}
