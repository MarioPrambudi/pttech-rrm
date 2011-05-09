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

	/*
	 * end calculation methods
   	*/
    
    
   	/**
     * get result for report dailyDetailsRequestReloadFrmCelcomReport/TG0001-Report
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List getDailyDetailsRequestReloadFrmCelcomReport() throws Exception {
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
    	System.out.println("/////////////listReloadRequest.size()="+listReloadRequest.size());
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
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getTotalChargeToCustomer(), report.getCommissionAmountDeductedBySof()));
            	report.setAmountRefundedToCustomer(report.getReloadAmount());
            	//report.setDateRefundedCustomer(); TO BE CONTINUE
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
    
//    /**
    /**
     * get result for report dailySettlementReloadReqFrmCelcomReport/TG0007
     * @param none
     * @return List A report list
     * @throws Exception 
     */
//   * @param none
//   * @return List A report list
//   * @throws Exception 
//   */
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
          	//manually set value into report fields
          	
          	report.setTransactionDate(report.getRequestedTime());
          	//todo
//          	report.setTotalReloadQty();
//          	//todo
//          	report.setGrossPaymentToTngRm();
//          	//todo
//          	report.setTotalCancellationRm();
//          	//todo
//          	report.setAmountCreditedToTngRm();
//          	//todo
//          	report.setDateCreditedToTngAccount();
          	
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
    	
    	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 
    	
 		//daily from date and to date
    	Date dateMin, dateMax = null;   	 
		dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		dateMax = DateUtil.add(dateMin, 5, 1);
		
    	List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
    	
        listReport = copyReloadRequestToReport(listReloadRequest);
        
		Iterator it = listReport.iterator();
        while(it.hasNext()) {        
            try {
              Report reportSummary = (Report)it.next();
              
              // summary calculation
              reportSummary.setSofRequestedDatetime(reportSummary.getRequestedTime());
              reportSummary.setTotalReloadQty(reportSummary.getMfgNumber());
              reportSummary.setTotalAmountRequestRm(reportSummary.getReloadAmount());
              reportSummary.setTotalFees(getReportFee().multiply(new BigDecimal(reportSummary.getTotalReloadQty())));
              reportSummary.setTotalChargeToCustomer(reportSummary.getTotalAmountRequestRm()
              														.add(reportSummary.getTotalFees()) );
              reportSummary.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF()
            		  												.multiply(new BigDecimal(reportSummary.getTotalReloadQty())));
              reportSummary.setNetPaymentToTng(reportSummary.getTotalChargeToCustomer()
              														.subtract(reportSummary.getCommissionAmountDeductedBySof()) );
      		
              listCompleteReport.add(reportSummary);
            } catch (Exception e) {
                e.printStackTrace();  
            }
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
    	
 		//daily from date and to date
    	Date dateMin = null;
    	Date dateMax = null;   	 
		dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		dateMax = DateUtil.add(dateMin, 5, 1);

    	List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);

        listReport = copyReloadRequestToReport(listReloadRequest);
        
		Iterator it = listReport.iterator();
		

        while(it.hasNext()) {        
            try {

            	Report reportSummary = (Report)it.next();
            	
            	// summary calculation
                reportSummary.setReloadDate(reportSummary.getRequestedTime());
                reportSummary.setTotalReloadQty(reportSummary.getMfgNumber());
                reportSummary.setTotalReloadAmountRm(reportSummary.getReloadAmount());
                reportSummary.setTotalFees(getReportFee().multiply(new BigDecimal(reportSummary.getTotalReloadQty())));
                reportSummary.setTotalChargeToCustomer(reportSummary.getTotalReloadAmountRm()
                														.add(reportSummary.getTotalFees()) );
                reportSummary.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF()
              		  												.multiply(new BigDecimal(reportSummary.getTotalReloadQty())));
                reportSummary.setNetPaymentToTng(reportSummary.getTotalChargeToCustomer()
                														.subtract(reportSummary.getCommissionAmountDeductedBySof()) );
                
            	listCompleteReport.add(reportSummary);
            } catch (Exception e) {
                e.printStackTrace();  
            }

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

    	
 		//daily from date and to date
    	Date dateMin = null;
    	Date dateMax = null;   	 
		dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		dateMax = DateUtil.add(dateMin, 5, 1);
		
		List<ReloadRequest> listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);
        
        //call method to copy reload request list to report list
        listReport = copyReloadRequestToReport(listReloadRequest);
        
        
        
		Iterator it = listReport.iterator();

		//Summary Variables
		BigDecimal totalFee= new BigDecimal("0.00");
		int totalCancellation = 0;
		BigDecimal totalAmountCancelled = new BigDecimal("0.00");
		BigDecimal totalChargeCust = new BigDecimal("0.00");
		BigDecimal totalCommSofDeduct = new BigDecimal("0.00");
		BigDecimal totalRefundCust = new BigDecimal("0.00");
		
        while(it.hasNext()) {        
            try {

            	Report reportSummary = (Report)it.next();
            	
            	// summary calculation
            	// todo:different between datecancelrequest and reload request date
            	reportSummary.setDateCancelRequest(reportSummary.getRequestedTime());
                reportSummary.setReloadDate(reportSummary.getRequestedTime());
                reportSummary.setTotalCancellationQty(reportSummary.getMfgNumber());
                reportSummary.setTotalAmountCancelledRm(reportSummary.getReloadAmount());
                reportSummary.setTotalFees(getReportFee().multiply(new BigDecimal(reportSummary.getTotalCancellationQty())));
                reportSummary.setTotalRefundToCustomerRm(reportSummary.getTotalAmountCancelledRm()
                														.add(reportSummary.getTotalFees()) );
                reportSummary.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF()
              		  												.multiply(new BigDecimal(reportSummary.getTotalCancellationQty())));
               
            	listCompleteReport.add(reportSummary);
            	
            } catch (Exception e) {
                e.printStackTrace();  
            }

        }
        return listCompleteReport;
    }
  
    /**
     * get result for report summarySettlementReloadReqFrmCelcomReport/TG0008
     * @param none
     * @return List A report list
     * @throws Exception 
     */
  public static List<Report> getSummarySettlementReloadFrmCelcomReport() throws Exception {
	  List <Report> listReport = new ArrayList<Report>();
      List <Report> listCompleteReport = new ArrayList<Report>();
	  List listStatus = new ArrayList();
	  	
	Date dateMin = null;
  	Date dateMax = null;
  	dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
  	dateMax = DateUtil.add(dateMin, 5, 1);

  	listStatus.add(Constants.RELOAD_REQUEST_NEW.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_FAILED.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_EXPIRED.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_MANUALCANCEL.toLowerCase());
  	listStatus.add(Constants.RELOAD_REQUEST_SUCCESS.toLowerCase()); 

  	List listReloadRequest = ReloadRequest.findSummaryReloadRequestsByRequestedTimeBetweenAndStatus(dateMin, dateMax, listStatus);

      listReport = copyReloadRequestToReport(listReloadRequest);


		Iterator it = listReport.iterator();

      while(it.hasNext()) {        
          try {
Report reportSummary = (Report)it.next();
              
              // summary calculation
			  reportSummary.setReloadDate(reportSummary.getRequestedTime());
              reportSummary.setTotalReloadQty(reportSummary.getMfgNumber());
              
//              //todo
//              reportSummary.setTotalPaymentToTngRm();
//              //todo
//              reportSummary.setTotalCancellationRm();
//              //todo
//              reportSummary.setNetPaymentToTng();
//              //todo
//              reportSummary.setDateCreditedToTngAccount();
      		
              listCompleteReport.add(reportSummary);
          } catch (Exception e) {
              e.printStackTrace();  
          }

      }
        
      return listCompleteReport;

  }
}
