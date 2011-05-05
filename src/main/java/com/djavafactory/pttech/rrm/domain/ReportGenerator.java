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
     * get result for report dailyDetailsRequestReloadFrmCelcomReport/TG0001-Report
     * @param none
     * @return List A report list
     * @throws Exception 
     */
    public static List getDailyDetailsRequestReloadFrmCelcomReport() throws Exception
    {
		//get reloadrequest list
    	List listReloadRequest = ReloadRequest.findDailyReloadRequestsByRequestedTimeBetween(Constants.RELOAD_REQUEST_ALL);

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
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getCommissionAmountDeductedBySof()));
            	listCompleteReport.add(report);

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
    public static List getDailyDetailedReloadFrmCelcomReport() throws Exception
    {
    	List listReloadRequest = ReloadRequest.findDailyReloadRequestsByRequestedTimeBetween(Constants.RELOAD_STATUS_SUCCESS);
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
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getCommissionAmountDeductedBySof()));
            	listCompleteReport.add(report);

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
    public static List getDailyDetailsCancellationReloadReqFrmCelcomReport() throws Exception
    {
    	List listReloadRequest = ReloadRequest.findDailyReloadRequestsByRequestedTimeBetween(Constants.RELOAD_REQUEST_ALLFAIL);
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
            	report.setFees(getReportFee());
            	report.setTotalChargeToCustomer(getTotalChargeToCustomer(report.getReloadAmount()));
            	report.setCommissionAmountDeductedBySof(getCommAmountDeductedBySOF());
            	report.setNetPaymentToTng(getNetPaymentToTnG(report.getCommissionAmountDeductedBySof()));
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
	 * Get totalChargeToCustomer (reload amount + fee)
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
	 * Get commission amount deducted by SOF (fee x celcomm)
	 * @param none
	 * @return BigDecimal commAmount
	 */ 
    public static BigDecimal getCommAmountDeductedBySOF()
    {	           	     	 
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
    public static BigDecimal getNetPaymentToTnG(BigDecimal totalChargeToCust)
    {	
     	BigDecimal commAmount = getCommAmountDeductedBySOF();
     	BigDecimal netPayment = totalChargeToCust.subtract(commAmount);
     	 
     	 return netPayment; 
    }
    

}
