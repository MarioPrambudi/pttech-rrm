package com.djavafactory.pttech.rrm.reports;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ConfValidityPeriod;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.EventTrail;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.Report;
import com.djavafactory.pttech.rrm.mongorepository.EventTrailRepository;
import com.djavafactory.pttech.rrm.mongorepository.ReportRepository;
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
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.document.mongodb.MongoOperations;

/**
 * Methods to generate reports.
 * User: rainpoh
 * Date: 5/5/11
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */

@Aspect
@Configurable
public class ReportGenerator {
	@Autowired
	ReportRepository reportRepository;

	
	private static final String FIELDREQUESTEDTIME= "requestedTime";
	private static final String FIELDMODIFIEDTIME = "modifiedDate";
	
	/*
	 * calculation method
	 */
    
    /**
	 * Get fee value from configuration
	 * @param none
	 * @return double - Fee 
	 */
    public static double getReportFee(Date requestedDate) {    	 
    	 String reportfee =  ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_FEE, requestedDate);
    	 double doubleFee = Double.parseDouble(reportfee); 
    	 return doubleFee;
    }

    /**
	 * Get SOF value from configuration
	 * @param none
	 * @return double - SOF
	 */
    public static double getReportSOF(Date requestedDate) {	      
    	 String reportSOF = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_SOF, requestedDate);
    	 double doubleSOF = Double.parseDouble(reportSOF); 
    	 return doubleSOF;    	 
    }
    
    /**
	 * Get TNG value from configuration
	 * @param none
	 * @return double - TNG
	 */
    public static double getReportTng(Date requestedDate) {	      
     	 String reportTng = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_TNG, requestedDate);
     	 double doubleTng= Double.parseDouble(reportTng); 
     	 return doubleTng; 
    }
    
    /**
	 * Get RS value from configuration
	 * @param none
	 * @return double - RS
	 */
    public static double getReportRS(Date requestedDate) {	      
    	 String reportRS = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_RS, requestedDate);
    	 double decimalRS= Double.parseDouble(reportRS); 
    	 return decimalRS; 
   }
   
    /**
	 * Get AT value from configuration
	 * @param none
	 * @return double - AT
	 */
    public static double getReportAT(Date requestedDate) {	      
    	 String reportAT = ConfValidityPeriod.getReportConfigValue(Constants.REPORT_CONFIG_AT, requestedDate);
    	 double decimalAT = Double.parseDouble(reportAT); 
    	 return decimalAT; 
   }   
    
    /**
	 * Get totalChargeToCustomer (reload amount + fee)
	 * @param reloadAmount double
	 * @return double - TotalChargeToCustomer
	 */ 
    public static double getTotalChargeToCustomer(double reloadAmount, double reportFee) {	
     	double sumForReloadAmountAndFee = reportFee + reloadAmount;   	 
     	 return sumForReloadAmountAndFee; 
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
	 * get list of all status
	 * @param none
	 * @return List<String> - list of status
	 */
	  public static String[] getListAllStatus(){
		  String[] listStatus = new String[6];
		  listStatus[0] = Constants.RELOAD_REQUEST_NEW;
		  listStatus[1] = Constants.RELOAD_REQUEST_FAILED;			
		  listStatus[2] = Constants.RELOAD_REQUEST_EXPIRED;			
		  listStatus[3] = Constants.RELOAD_REQUEST_MANUALCANCEL;
		  listStatus[4] = Constants.RELOAD_REQUEST_SUCCESS;
		  listStatus[5] = Constants.RELOAD_STATUS_PENDING;
		  return listStatus;		
	  }
	  
	/**
	 * get list of all status
	 * @param none
	 * @return List<String> - list of status
	 */
	  public static String[] getListAllStatusNotPending(){
		  String[] listStatus = new String[5];
		  listStatus[0] = Constants.RELOAD_REQUEST_NEW;
		  listStatus[1] = Constants.RELOAD_REQUEST_FAILED;			
		  listStatus[2] = Constants.RELOAD_REQUEST_EXPIRED;			
		  listStatus[3] = Constants.RELOAD_REQUEST_MANUALCANCEL;
		  listStatus[4] = Constants.RELOAD_REQUEST_SUCCESS;
		  return listStatus;		
	  }
		  
	/**
	 * get list of all cancellation status
	 * @param none
	 * @return List<String> - list cancellation status
	 */
	  public static String[] getListAllCancel(){
		  String[] listStatus = new String[3];
		  listStatus[0] = Constants.RELOAD_REQUEST_FAILED;
		  listStatus[1] = Constants.RELOAD_REQUEST_EXPIRED;
		  listStatus[2] = Constants.RELOAD_REQUEST_MANUALCANCEL;
		  return listStatus;
	  }
	  
    /**
	 * get list of all success status
	 * @param none
	 * @return List<String> - list of success status
	 */
	  public static String[] getListAllSuccess(){
		  String[] listStatus = {Constants.RELOAD_REQUEST_SUCCESS}; 
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
    public List<Report> getDailyDetailsRequestReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {   	      
      	String[] listStatus = getListAllStatus();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
    	syncReportRecord(dateMin, dateMax);
    	List<Report> listReport  = reportRepository.findByParam(dateMin, dateMax, listStatus, FIELDREQUESTEDTIME);
    	List<Report> listCompleteReport = new ArrayList<Report>();
        int i = 1;        
		for(Report report : listReport)
		{   
        	report.setSeqNo(i++);                      
        	listCompleteReport.add(report);        
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
    public List<Report> getDailyDetailedReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllSuccess();    	
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
    	syncReportRecord(dateMin, dateMax);
    	List<Report> listReport  = reportRepository.findByParam(dateMin, dateMax, listStatus, FIELDMODIFIEDTIME);
    	List<Report> listCompleteReport = new ArrayList<Report>(); 
        int i = 1; 
		for(Report report : listReport)
		{        
        	report.setSeqNo(i++);      
        	listCompleteReport.add(report);            	  	
 
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
    public List<Report> getDailyDetailsCancellationReloadReqFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
      	String[] listStatus = getListAllCancel();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
    	syncReportRecord(dateMin, dateMax);
    	List<Report> listReport  = reportRepository.findByParam(dateMin, dateMax, listStatus, FIELDMODIFIEDTIME);
    	List<Report> listCompleteReport = new ArrayList<Report>();		
        int i = 1; 
        for(Report report : listReport)
		{        
        	report.setSeqNo(i++);    
        	report.setStatus(getLongStatus(report.getStatus()));            
        	listCompleteReport.add(report);            	
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
  public List<Report> getSettlementReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
	  String[] listStatus = getListAllStatusNotPending();
	  List <Report> listCompleteReport = new ArrayList<Report>();	  
	  listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDMODIFIEDTIME);
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
    public List<Report> getSummaryRequestReloadFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllStatus();     
    	List<Report> listCompleteReport = new ArrayList<Report>();
    	listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDREQUESTEDTIME);
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
    public List<Report> getSummaryReloadReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllSuccess();
        List<Report> listCompleteReport = new ArrayList<Report>();
    	listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDMODIFIEDTIME);
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
    public List<Report> getSummaryCancellationReloadReqFrmCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllCancel();
        List<Report> listCompleteReport = new ArrayList<Report>();
        listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDMODIFIEDTIME);
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
    public List<Report> getCommissionReloadFrmCchsForCelcomReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
      String[] listStatus = getListAllStatusNotPending();
	  List <Report> listCompleteReport = new ArrayList<Report>();
	  listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDMODIFIEDTIME);
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
    public List<Report> getDailyTransactionDetailsReport(int first, int size) throws Exception {
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
    public List<Report> getDailyTransactionDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllStatus();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
     	syncReportRecord(dateMin, dateMax);
    	List<Report> listReport  = reportRepository.findByParam(dateMin, dateMax, listStatus, FIELDREQUESTEDTIME);
    	List<Report> listCompleteReport = new ArrayList<Report>();		
        int i = 1; 
        for(Report report : listReport)
		{              
        	report.setSeqNo(i++);              	            
        	listCompleteReport.add(report);   
        	report.setSeqNo(i++);          	
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
    public List<Report> getDailyTransactionFeeDetailsReport(int first, int size) throws Exception {
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
    public List<Report> getDailyTransactionFeeDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllStatus();
      	Date dateMin = getDateMin(dateMinStr);
    	Date dateMax = getDateMax(dateMin, dateMaxStr);	
     	syncReportRecord(dateMin, dateMax);
    	List<Report> listReport  = reportRepository.findByParam(dateMin, dateMax, listStatus, FIELDREQUESTEDTIME);
    	List<Report> listCompleteReport = new ArrayList<Report>();		
        int i = 1; 
        for(Report report : listReport)
		{              
        	report.setSeqNo(i++);              	            
        	listCompleteReport.add(report);   
        	report.setSeqNo(i++);          	
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
    public List<Report> getSummaryDailyTransactionDetailsReport(int first, int size) throws Exception {
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
    public List<Report> getSummaryDailyTransactionDetailsReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllStatusNotPending();     
    	List<Report> listCompleteReport = new ArrayList<Report>();
    	listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDREQUESTEDTIME);
  	    return listCompleteReport;	
    }
    
    /** 
     * TODO
     * get result for report summaryDailyTransactionDetailsReport/CE0007
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public List<Report> getSummaryDailyFeeReport(int first, int size) throws Exception {
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
    public List<Report> getSummaryDailyFeeByRangeDateReport(String dateMinStr, String dateMaxStr, int first, int size) throws Exception {
    	String[] listStatus = getListAllStatusNotPending();     
    	List<Report> listCompleteReport = new ArrayList<Report>();
    	listCompleteReport = getSummaryReportData(dateMinStr, dateMaxStr, listStatus, FIELDREQUESTEDTIME);
  	    return listCompleteReport;	
    }
    
    
    /** 
     * generate report according to schedule time
     * @param dateMin, dateMax
     * @return List A report list
     * @throws Exception
     */  
  //  @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailyReport() throws Exception
    {
    	Date startDate = getDailyStartDate();
    	Date endDate = getDailyEndDate(startDate);     	
    	generateReport(startDate, endDate);    	
    }
    
    /** 
     * generate daily report and save into MongoDB
     * @param dateMin, dateMax
     * @return List A report list
     * @throws ParseException
     */   
    public void generateReport(Date startDate, Date endDate) throws Exception{    	  	
    	if (isExistingReport(startDate, endDate)) 
    	{
    		syncReportRecord(startDate, endDate);
    	}
    	else
    	{
	    	List<ReloadRequest> listReloadRequest = ReloadRequest.findDailyReloadRequests(startDate, endDate).getResultList();
	        List<Report> listCompleteReport = new ArrayList<Report>();
	
			for(ReloadRequest reloadrequest : listReloadRequest)
			{   
	            try {  
	            	
	            	double reportRS = getReportRS(reloadrequest.getRequestedTime());
	            	double reportAT = getReportAT(reloadrequest.getRequestedTime());
	            	double reportFee = getReportFee(reloadrequest.getRequestedTime());
	            	double reportSOF = getReportSOF(reloadrequest.getRequestedTime());
	            	double reportTng = getReportTng(reloadrequest.getRequestedTime());	
	            	double commDeductedBySOF =  reportRS + reportSOF;              
	            	double totalChargeToCust = reportFee + reloadrequest.getReloadAmount().doubleValue();
	            	double grossPaymentToTng = reloadrequest.getReloadAmount().doubleValue() + reportFee;
	            	double tngFee = reportFee*reportAT + reportTng;
	            	double printisFee = reportFee*reportRS; 
	            	double cmmFee = reportFee*reportSOF;  	
	            	double celcomFee = 0.00;  	//TODO
	            	
	            	Report report = new Report();            	
	            	report.setRequestedTime(DateUtil.convertDateToDate(reloadrequest.getRequestedTime(), Constants.REPORT_MONGODB_DATE_PATTERN));
	            	report.setReloadAmount(reloadrequest.getReloadAmount().doubleValue());
	            	report.setRs(reportRS);
	            	report.setAt(reportAT);
	            	report.setSof(reportFee);
	            	report.setTng(reportSOF);
	            	report.setFees(reportFee);
	            	report.setTotalChargeToCustomer(totalChargeToCust);
	            	report.setMfgNumber(reloadrequest.getMfgNumber());
	            	report.setCommissionAmountDeductedBySof(commDeductedBySOF);
	            	report.setNetPaymentToTng(totalChargeToCust - commDeductedBySOF);
	            	report.setReloadDate(DateUtil.convertDateToDate(reloadrequest.getModifiedTime(), Constants.REPORT_MONGODB_DATE_PATTERN));
	            	report.setAmountRefundedToCustomer(reloadrequest.getReloadAmount().doubleValue());
	            	report.setDateRefundedCustomer(DateUtil.convertDateToDate(reloadrequest.getModifiedTime(), Constants.REPORT_MONGODB_DATE_PATTERN));
	            	report.setGrossPaymentToTngRm(grossPaymentToTng);
	            	report.setAcquirerTerminal(reloadrequest.getAcquirerTerminal());
	            	report.setCmmpTrxId(reloadrequest.getCmmpTrxId());
	            	//report.setAircashAccNo(); TODO
	            	report.setMobileNo(reloadrequest.getMobileNo());
	            	report.setModifiedDate(DateUtil.convertDateToDate(reloadrequest.getModifiedTime(), Constants.REPORT_MONGODB_DATE_PATTERN));
	            	report.setStatus(reloadrequest.getStatus());
	            	report.setTransId(reloadrequest.getTransId());
	            	report.setTngFee(tngFee);
	            	report.setCelcomMobileFee(celcomFee); //TODO
	            	report.setCmmFee(cmmFee);
	            	report.setPrintisFee(printisFee);
	            	report.setTotalFees(tngFee + printisFee + cmmFee + celcomFee);
	            	report.setAmountDueTng(reloadrequest.getReloadAmount().doubleValue() + tngFee);
	            	report.setAmountDuePrintis(reloadrequest.getReloadAmount().doubleValue() + printisFee);
	            	report.setAmountDueCmm(reloadrequest.getReloadAmount().doubleValue() + cmmFee);
	            	report.setAmountDueCelcomMobile(reloadrequest.getReloadAmount().doubleValue() + celcomFee);
	            	//report.setDateCreditedToTngAccount(); //TODO
	            	listCompleteReport.add(report);
	        
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
			
			if (listCompleteReport.size()>0)
			{
				reportRepository.saveReport(listCompleteReport);
			}
		
    	}
    }

    /** 
     * TODO
     * check existing report for the date
     * @param startDate Date
     * @param endDate Date
     * @return Boolean 
     */  
    public boolean isExistingReport(Date startDate, Date endDate){
    	Boolean lresult = false;
    	if(reportRepository.countByParam(startDate, endDate) > 0) lresult = true;
    	return lresult;
    }
    
 
    /**
     * Sync ReloadRequest with MongoDB
     * @param startDate Date
     * @param endDate Date
     * @return none
     * @throws Exception 
     */
    public void syncReportRecord(Date startDate, Date endDate) throws Exception {
    	Long rrSize = ReloadRequest.totalReloadRequestsByRequestedTimeBetweenAndStatus(startDate, endDate);
    	Long reportSize = reportRepository.countByParam(startDate, endDate);
    	if (!(rrSize == reportSize))
    		if (!(2 > 3))
    	{
    		while(isExistingReport(startDate, endDate))
    		{
    			reportRepository.delete(startDate, endDate);
    		}  	
    		generateReport(startDate, endDate);
    	}
    }
    
    //SUMMARY REPORT
    public List<Report> getSummaryReportData(String dateMinStr, String dateMaxStr, String[] listStatus, String dateField) throws Exception {
  	  List <Report> listCompleteReport = new ArrayList<Report>();
  	  Date dateMin = getSummaryDateMin(dateMinStr);
  	  Date dateMax = getSummaryDateMax(dateMin, dateMaxStr);	
  	  Date dateMaxSearch = null;
  	  int i = 1;
  	  while(dateMin.before(dateMax))
  	  {	   		   		  
  		dateMaxSearch = getDailyEndDate(dateMin);     
  		List <Report> listReport  = reportRepository.findByParam(dateMin, dateMaxSearch, listStatus, dateField);
  		
  		if (listReport != null && listReport.size()>0)
  		{
  			long totalQuantity = listReport.size();	
  			double totalReloadAmount = 0.00;			
  			double totalCommDeductedBySof = 0.00;		
  			double totalReportFee = 0.00;
  			double totalPaymentToTng = 0.00;
  			double commSofAmountComm = 0.00;
  			double sumTng = 0.00;
  			double sumCelcomMobile = 0.00;
  			double sumCmm = 0.00;
  			double sumTngFee = 0.00;
  			double sumCmmFee = 0.00;
  			double sumPrintisFee = 0.00;
  			double sumCelcomMobileFee = 0.00;
  			double sumAmtDueTng = 0.00;
  			double sumAmtDuePrintis = 0.00;
  			double sumAmtDueCmm = 0.00;
  			double sumAmtDueCelcomMobile = 0.00;
  			//String acquirerTerminal TODO
  			
  			for(Report report : listReport)  
  			{    	
  	            totalReloadAmount = totalReloadAmount + report.getReloadAmount();
  	            totalCommDeductedBySof = totalCommDeductedBySof + report.getCommissionAmountDeductedBySof();
  	            totalReportFee = totalReportFee + report.getFees();
  	            commSofAmountComm = commSofAmountComm + report.getSof();
  	            sumTng = sumTng + report.getTng();
  	            sumCelcomMobile = sumCelcomMobile + report.getRs();
  	            sumCmm = sumCmm + report.getAt();
  	            sumTngFee = sumTngFee + report.getTngFee();
    			sumCmmFee = sumCmmFee + report.getCmmFee();
    			sumPrintisFee = sumPrintisFee + report.getPrintisFee();
    			sumCelcomMobileFee = sumCelcomMobileFee + report.getCelcomMobileFee();
    			sumAmtDueTng = sumAmtDueTng + report.getAmountDueTng();
      			sumAmtDuePrintis = sumAmtDuePrintis + report.getAmountDuePrintis();
      			sumAmtDueCmm = sumAmtDueCmm + report.getAmountDueCmm();
      			sumAmtDueCelcomMobile = sumAmtDueCelcomMobile + report.getAmountDueCelcomMobile();	
  	        }
//  			
  			totalPaymentToTng = totalReloadAmount + totalReportFee - totalCommDeductedBySof;
  			Report summaryReport = new Report();
  			summaryReport.setSeqNo(i++);
  			summaryReport.setReloadDate(dateMaxSearch);			
  			summaryReport.setTotalReloadQty(totalQuantity);
  			summaryReport.setTotalPaymentToTngRm(totalPaymentToTng);
  			summaryReport.setTotalCancellationRm(totalReloadAmount); 
  			summaryReport.setSumNetPaymentToTng(totalPaymentToTng);
  			summaryReport.setDateCreditedToTngAccount(new Date()); //>>>>>>>>TODO
  			summaryReport.setRequestedTime(dateMaxSearch);
  			summaryReport.setTotalAmountRequestRm(totalReloadAmount);
  			summaryReport.setTotalFees(totalReportFee);
  			summaryReport.setSumTotalChargeToCustomer(totalReloadAmount + totalReportFee);
  			summaryReport.setSumCommissionAmountDeductedBySof(totalCommDeductedBySof);
 			summaryReport.setDateCancelRequest(dateMaxSearch); //>>>>>>>>>>TODO
 			summaryReport.setTotalCancellationQty(totalQuantity);
 			summaryReport.setTotalRefundToCustomerRm(totalReloadAmount);
  			//summaryReport.setAcquirerTerminal(); //>>>>>>>>>>>>TODO 			
  			summaryReport.setCommSofAmountComm(commSofAmountComm); 			
  			summaryReport.setSumTng(sumTng); 						
  			summaryReport.setSumCelcomMobile(sumCelcomMobile);
  			summaryReport.setSumCmm(sumCmm);
  			summaryReport.setTngFee(sumTngFee);
  			summaryReport.setCmmFee(sumCmmFee);
  			summaryReport.setCelcomMobileFee(sumCelcomMobileFee);
  			summaryReport.setPrintisFee(sumPrintisFee);
  			summaryReport.setSumTotalFee(sumTngFee + sumCmmFee + sumCelcomMobileFee + sumPrintisFee);
  			summaryReport.setGrossPaymentToTngRm(totalReportFee + totalReloadAmount);
  			summaryReport.setAmountDueTng(sumAmtDueTng);
  			summaryReport.setAmountDuePrintis(sumAmtDuePrintis);
  			summaryReport.setAmountDueCmm(sumAmtDueCmm);
  			summaryReport.setAmountDueCelcomMobile(sumAmtDueCelcomMobile);
  			listCompleteReport.add(summaryReport);			        
  		}  	
  		dateMaxSearch = DateUtil.add(dateMaxSearch, 5, 1);   
  		dateMin = DateUtil. getDateMinTime(dateMaxSearch);
  	  }	
  	  	return listCompleteReport;
    }

    public String getLongStatus(String status){
    	String longStatus = status;
    	if(Constants.RELOAD_REQUEST_FAILED.equals(status))
		{
    		 longStatus = Constants.REPORT_RELOAD_REQUEST_FAILED;
		}
    	if(Constants.RELOAD_REQUEST_EXPIRED.equals(status))
		{
    		 longStatus = Constants.REPORT_RELOAD_REQUEST_EXPIRED;
		}
    	if(Constants.RELOAD_REQUEST_MANUALCANCEL.equals(status))
		{
    		 longStatus = Constants.REPORT_RELOAD_REQUEST_CANCELLED;
		}
    	return longStatus;
    }
}


