package com.djavafactory.pttech.rrm.reports;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ConfValidityPeriod;
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
import org.springframework.scheduling.annotation.Scheduled;

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
    public static BigDecimal getReportFee(Date requestedDate) {    	 
    	 String reportfee =  ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_FEE, requestedDate);
    	 BigDecimal decimalFee = new BigDecimal(reportfee); 
    	 return decimalFee;
    }

    /**
	 * Get SOF value from configuration
	 * @param none
	 * @return BigDecimal - SOF
	 */
    public static BigDecimal getReportSOF(Date requestedDate) {	      
    	 String reportSOF = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_SOF, requestedDate);
    	 BigDecimal decimalSOF = new BigDecimal(reportSOF); 
    	 return decimalSOF;    	 
    }
    
    /**
	 * Get TNG value from configuration
	 * @param none
	 * @return BigDecimal - TNG
	 */
    public static BigDecimal getReportTng(Date requestedDate) {	      
     	 String reportTng = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_TNG, requestedDate);
     	 BigDecimal decimalTng= new BigDecimal(reportTng); 
     	 return decimalTng; 
    }
    
    /**
	 * Get RS value from configuration
	 * @param none
	 * @return BigDecimal - RS
	 */
    public static BigDecimal getReportRS(Date requestedDate) {	      
    	 String reportRS = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_RS, requestedDate);
    	 BigDecimal decimalRS= new BigDecimal(reportRS); 
    	 return decimalRS; 
   }
   
    /**
	 * Get AT value from configuration
	 * @param none
	 * @return BigDecimal - AT
	 */
    public static BigDecimal getReportAT(Date requestedDate) {	      
    	 String reportAT = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_AT, requestedDate);
    	 BigDecimal decimalAT = new BigDecimal(reportAT); 
    	 return decimalAT; 
   }
    
    
    /**
	 * Get totalChargeToCustomer (reload amount + fee)
	 * @param reloadAmount BigDecimal
	 * @return BigDecimal - TotalChargeToCustomer
	 */ 
    public static BigDecimal getTotalChargeToCustomer(BigDecimal reloadAmount, BigDecimal reportFee) {	
     	BigDecimal sumForReloadAmountAndFee = reportFee.add(reloadAmount);   	 
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
	 * Get get initial date value
	 * @param none
	 * @return Date - 01/MM/yyyy
	 */
    public static Date getDailyStartDate()
    {
	  	Calendar cal = Calendar.getInstance();
	  	Calendar calNow = Calendar.getInstance();
	    cal.set(calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH), calNow.get(Calendar.DATE), 00, 00, 00);
	    return cal.getTime();
    }

    
    /**
	 * Get get initial date value
	 * @param none
	 * @return Date - 01/MM/yyyy
	 */
    public static Date getDailyEndDate(Date startDate)
    {
    	  Calendar calMin = Calendar.getInstance();
		  Calendar calMax = Calendar.getInstance();
		  calMin.setTime(startDate);
		  calMax.set(calMin.get(Calendar.YEAR), calMin.get(Calendar.MONTH), calMin.get(Calendar.DATE), 23, 59, 59);
		  return calMax.getTime();
    }
    
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
	 * Get get initial date value
	 * @param none
	 * @return Date - 01/MM/yyyy
	 */
    public static Date getInitDateMin()
    {
	  	Calendar cal = Calendar.getInstance();
	  	Calendar calNow = Calendar.getInstance();
	    int date = 1;
	    cal.set(calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH), date, 00, 00, 00);
	    return cal.getTime();
    }

    
	/**
	 * get the formated start date for finder
	 * @param dateMinStr String 
	 * @return Date - dateMin
	 */
	  public static Date getDateMin(String dateMinStr) throws ParseException{
		  Calendar cal = Calendar.getInstance();
		  Calendar calNow = Calendar.getInstance();
		  Date dateMin=null;
		  
		  if("null".equals(dateMinStr))
		  {
		    cal.set(calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH), calNow.get(Calendar.DATE), 00, 00, 00);
		    dateMin = cal.getTime();
		  }
		  else
		  {
			dateMin = DateUtil.convertStringToDate(dateMinStr + " 00:00:00");
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
		  Calendar calMin = Calendar.getInstance();
		  Calendar calMax = Calendar.getInstance();
		  calMin.setTime(dateMin);
		  Date dateMax = null;
		  if("null".equals(dateMaxStr))
		  {
			calMax.set(calMin.get(Calendar.YEAR), calMin.get(Calendar.MONTH), calMin.get(Calendar.DATE), 23, 59, 59);
			dateMax = calMax.getTime();
		  }
		  else
		  {
			dateMax = DateUtil.convertStringToDate(dateMaxStr + " 23:59:59");
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
			dateMin = DateUtil.convertStringToDate(dateMinStr + " 00:00:00");
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
		  Calendar calMax = Calendar.getInstance();
		  Calendar calMaxLong = Calendar.getInstance();
		  
		  if("null".equals(dateMaxStr))
		  {
			dateMax = DateUtil.add(dateMin, 2, 1);
			dateMax = DateUtil.add(dateMax, 5, -1);
			calMax.setTime(dateMax);
			calMaxLong.set(calMax.get(Calendar.YEAR), calMax.get(Calendar.MONTH), calMax.get(Calendar.DATE), 23, 59, 59);
			dateMax = calMaxLong.getTime();
		  }
		  else
		  {
			dateMax = DateUtil.convertStringToDate(dateMaxStr + " 23:59:59");
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
        int i = 1; 
        
		for(Report report : listReport)
		{   
            try {
            	//manually set value into report fields
            	report.setSeqNo(i++);          
            	report.setSofRequestedDatetime(report.getRequestedTime());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer()));
            	listCompleteReport.add(report);
        
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
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
        int i = 1; 
		for(Report report : listReport)
		{        
            try {
            	//manually set value into report fields
            	report.setSeqNo(i++);    
            	report.setReloadDateTime(report.getModifiedDate());
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommDeductedBySOF());
            	report.setNetPaymentToTng(report.getTotalChargeToCustomer().subtract(report.getCommissionAmountDeductedBySof()));
            	listCompleteReport.add(report);            	  	
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
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
        int i = 1; 
        for(Report report : listReport)
		{        
            try {

            	//manually set value into report fields
            	report.setSeqNo(i++);    
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
                 	      	           	
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
       
        return listCompleteReport;
    }
    

    /**
     * get result for report settlementReloadReqFrmCelcomReport/TG0007 
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
  public static List<Report> getSettlementReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  List<String> listStatus = getListAllStatusNotPending();
	  Date dateMin = getSummaryDateMin(dateMinStr);
	  Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);	
	  Date dateMaxSearch = null;	  
	  int i = 1; 
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
	            	 reportSummary.setSeqNo(i++);  
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
    	int i = 1; 
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
                      reportSummary.setSeqNo(i++);    
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
    	 int i = 1; 
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
                      reportSummary.setSeqNo(i++);  
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
    	Date dateMin = getSummaryDateMin(dateMinStr);
    	Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
    	Date dateMaxSearch = null; 
    	 int i = 1; 
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
                      // summary calculation
                      reportSummary.setSeqNo(i++);  
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
     * get result for report commissionReloadFrmCchsForCelcomReport/TG0008
     * @param dateMinStr String
     * @param dateMaxStr String
     * @param first int 
     * @param size int
     * @return List<Report> listReport
     * @throws Exception 
     */
    public static List<Report> getCommissionReloadFrmCchsForCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  List<String> listStatus = getListAllStatusNotPending();
	  Date dateMin = getSummaryDateMin(dateMinStr);
	  Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);		
	  Date dateMaxSearch = null;
	  int i = 1; 
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
	            	reportSummary.setSeqNo(i++);  
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
    	 int i = 1; 
		for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
//				report.setAircashAccNo(); 	blank
				report.setSeqNo(i++);  
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
    	 int i = 1; 
    	for (Report report : listReport) {
			try {
				// TODO :set attribute for daily transaction details report
				report.setSeqNo(i++);  
				report.setTngTrxId(report.getTransId());
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
   	  Date dateMin = getDateMin(dateMinStr);
   	  Date dateMax = getDateMax(dateMin, dateMaxStr);		
   	  Date dateMaxSearch = null;
   	 int i = 1; 
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
   	            	reportSummary.setSeqNo(i++);  
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
   	  Date dateMin = getDateMin(dateMinStr);
   	  Date dateMax = getDateMax(dateMin, dateMaxStr);		
   	  Date dateMaxSearch = null;
   	 int i = 1; 
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
   	            reportSummary.setSeqNo(i++);
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
    
    @Scheduled(cron = "0 0 0 * * ?")
    public static void generateDailyReport() throws ParseException{
    	Date startDate = getDailyStartDate();
    	Date endDate = getDailyEndDate(startDate);
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findDailyReloadRequests(startDate, endDate).getResultList();
        List<Report> listCompleteReport = new ArrayList<Report>();

		for(ReloadRequest reloadrequest : listReloadRequest)
		{   
            try {  
            	
            	BigDecimal reportRS = getReportRS(reloadrequest.getRequestedTime());
            	BigDecimal reportAT = getReportAT(reloadrequest.getRequestedTime());
            	BigDecimal reportFee = getReportFee(reloadrequest.getRequestedTime());
            	BigDecimal reportSOF = getReportSOF(reloadrequest.getRequestedTime());
            	BigDecimal reportTng = getReportTng(reloadrequest.getRequestedTime());	
            	BigDecimal commDeductedBySOF =  reportRS.add(reportSOF);              
            	BigDecimal grossPaymentToTng = reloadrequest.getReloadAmount().add(reportFee);
            	BigDecimal tngFee = reportFee .multiply(reportAT.add(reportTng));
            	BigDecimal printisFee = reportFee.multiply(reportRS); 
            	BigDecimal cmmFee = reportFee.multiply(reportSOF);  	
            	BigDecimal celcomFee = new BigDecimal(0.00);  	//TODO
            	
            	Report report = new Report();
            	report.setRequestedTime(reloadrequest.getRequestedTime());
            	report.setReloadAmount(reloadrequest.getReloadAmount());
            	report.setFees(reportFee);
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount(), reportFee));
            	report.setMfgNumber(reloadrequest.getMfgNumber());
            	report.setCommissionAmountDeductedBySof(commDeductedBySOF);
            	report.setNetPaymentToTng(report.getTotalChargeToCustomer().subtract(commDeductedBySOF));
            	report.setReloadDate(reloadrequest.getModifiedTime());
            	report.setAmountRefundedToCustomer(reloadrequest.getReloadAmount());
            	report.setDateRefundedCustomer(reloadrequest.getModifiedTime());
            	report.setGrossPaymentToTngRm(grossPaymentToTng);
            	report.setAcquirerTerminal(reloadrequest.getAcquirerTerminal());
            	report.setCmmpTrxId(reloadrequest.getCmmpTrxId());
            	//report.setAircashAccNo(); TODO
            	report.setMobileNo(reloadrequest.getMobileNo());
            	report.setModifiedDate(reloadrequest.getModifiedTime());
            	report.setStatus(reloadrequest.getStatus());
            	report.setTransId(reloadrequest.getTransId());
            	report.setTngFee(tngFee);
            	report.setCelcomMobileFee(celcomFee); //TODO
            	report.setCmmFee(cmmFee);
            	report.setPrintisFee(printisFee);
            	report.setTotalFees(tngFee.add(printisFee).add(cmmFee).add(celcomFee));
            	report.setAmountDueTng(reloadrequest.getReloadAmount().add(tngFee));
            	report.setAmountDuePrintis(reloadrequest.getReloadAmount().add(printisFee));
            	report.setAmountDueCmm(reloadrequest.getReloadAmount().add(cmmFee));
            	report.setAmountDueCelcomMobile(reloadrequest.getReloadAmount().add(celcomFee));
            	//report.setDate();
            	//report.setTime();
            	listCompleteReport.add(report);
        
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
		
		//INSERT INTO MONGO DB with SHORT DATE
    	
    }
    
    
}


