package com.djavafactory.pttech.rrm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.Report;
import com.djavafactory.pttech.rrm.reports.ReportGenerator;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * This class is generated by <b>jasperoo setup</b>.
 * Request mapping methods are added by <b>jasperoo add</b> or <b>jasperoo all</b>.
 * <p/>
 * <b>ANYTHING ADDED AFTER THE CLOSING BRACE WILL BE DELETED BY <u>jasperoo add</u>!</b>
 */
@RequestMapping("/reports")
@Controller
public class ReportController extends BaseController {

    public static final String REPORT_TYPE_CELCOM = "celcom";
    public static final String REPORT_TYPE_TNG = "tng";

    @RequestMapping(method = RequestMethod.GET)
    public String getReportList(@RequestParam(value = "type") String type, Model uiModel) {
        List<Configuration> configList = Configuration.findByConfigKeyPrefix(type);
        if (configList != null && !configList.isEmpty()) {
            for (int i = 0; i < configList.size(); i++) {
                Configuration config = configList.get(i);
                if (config.getConfigKey().lastIndexOf(".") > -1) {
                    config.setConfigKey(config.getConfigKey().substring(config.getConfigKey().lastIndexOf(".") + 1));
                }
            }
        }
        uiModel.addAttribute("reports", configList);
        return "reports/reportList";
    }

/*
* The template for the "List" Request mapping methods is:
*
*	@RequestMapping(value ="/**ENTITY_NAME_LOWER**List/{format}", method = RequestMethod.GET)
*	public String report**ENTITY_NAME**List(ModelMap modelMap, @PathVariable("format") String format) {
*		JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(**ENTITY_NAME**.findAll**REPORT_TITLE**(),false);
*		modelMap.put("reportData", jrDataSource);
*		modelMap.put("format", format);
*		return "**ENTITY_NAME_LOWER**ReportList";
*	}
*/

    @RequestMapping(value = "/reloadrequestList/{format}", method = RequestMethod.GET)
    public String reportReloadRequestList(ModelMap modelMap, @PathVariable("format") String format) {
        JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(com.djavafactory.pttech.rrm.domain.ReloadRequest.findAllReloadRequests(), false);
        modelMap.put("reportData", jrDataSource);
        modelMap.put("format", format);
        return "reloadrequestReportList";
    }

	/*
	 * TnG Report
	 */
	@RequestMapping(value ="/TG0001-Report/{format}", method = RequestMethod.GET)
	public String dailyDetailsRequestReloadFfmCelcomReport(ModelMap modelMap,
														   Model uiModel,
														   @PathVariable("format") String format,
														   @RequestParam(value = "dateMin", required = false)String dateMin,
														   @RequestParam(value = "dateMax", required = false)String dateMax											   
														  ) throws Exception {

		List<Report> reportList = ReportGenerator.getDailyDetailsRequestReloadFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
	        
	        return "dailyDetailsRequestReloadFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);

			return "dailyDetailsRequestReloadFrmCelcomReport";
		}
		
	}
	
	@RequestMapping(value = "/TG0002-Report/{format}", method = RequestMethod.GET)
	public String summaryRequestReloadFrmCelcomReport(ModelMap modelMap,
													  Model uiModel,
													  @PathVariable("format") String format,
													  @RequestParam(value = "dateMin", required = false)String dateMin,
													  @RequestParam(value = "dateMax", required = false)String dateMax											   
													  ) throws Exception {
		
		List<Report> reportList = ReportGenerator.getSummaryRequestReloadFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "summaryRequestReloadFrmCelcomList";
		} else {	
	        JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
	        modelMap.put("reportData", jrDataSource);
	        modelMap.put("format", format);
	        return "summaryRequestReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0003-Report/{format}", method = RequestMethod.GET)
	public String dailyDetailedReloadFrmCelcomReport(ModelMap modelMap,
													   Model uiModel,
													   @PathVariable("format") String format,
													   @RequestParam(value = "dateMin", required = false)String dateMin,
													   @RequestParam(value = "dateMax", required = false)String dateMax											   
													  ) throws Exception {
		
		List<Report> reportList = ReportGenerator.getDailyDetailedReloadFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailyDetailedReloadFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyDetailedReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0004-Report/{format}", method = RequestMethod.GET)
	public String summaryReloadFrmCelcomReport(ModelMap modelMap, 
											   Model uiModel,
											   @PathVariable("format") String format,
											   @RequestParam(value = "dateMin", required = false)String dateMin,
											   @RequestParam(value = "dateMax", required = false)String dateMax											   
											  ) throws Exception {
		
		List<Report> reportList = ReportGenerator.getSummaryReloadReport(dateMin, dateMax);

		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "summaryReloadFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0005-Report/{format}", method = RequestMethod.GET)
	public String dailyDetailsCancellationReloadReqFrmCelcomReport(ModelMap modelMap,
																   Model uiModel,
																   @PathVariable("format") String format,
																   @RequestParam(value = "dateMin", required = false)String dateMin,
																   @RequestParam(value = "dateMax", required = false)String dateMax											   
																  ) throws Exception {
		
		List<Report> reportList = ReportGenerator.getDailyDetailsCancellationReloadReqFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailyDetailsCancellationReloadReqFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyDetailsCancellationReloadReqFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0006-Report/{format}", method = RequestMethod.GET)
	public String summaryCancellationReloadFrmCelcomReport(ModelMap modelMap,
														   Model uiModel,
														   @PathVariable("format") String format,
														   @RequestParam(value = "dateMin", required = false)String dateMin,
														   @RequestParam(value = "dateMax", required = false)String dateMax										
														   ) throws Exception {
		
		List<Report> reportList = ReportGenerator.getSummaryCancellationReloadReqFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "summaryCancellationReloadFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryCancellationReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value ="/TG0007-Report/{format}", method = RequestMethod.GET)
	public String dailySettlementReloadFrmCelcomReport(ModelMap modelMap,
													   Model uiModel,
													   @PathVariable("format") String format,
													   @RequestParam(value = "dateMin", required = false)String dateMin,
													   @RequestParam(value = "dateMax", required = false)String dateMax											   
													  ) throws Exception {
		
		List<Report> reportList = ReportGenerator.getDailySettlementReloadFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailySettlementReloadFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailySettlementReloadFrmCelcomReport";
		}
	}
	
	
	@RequestMapping(value = "/TG0008-Report/{format}", method = RequestMethod.GET)
	public String monthlySettlementReloadFrmCelcomReport(ModelMap modelMap,
														 Model uiModel,
														 @PathVariable("format") String format,
														 @RequestParam(value = "dateMin", required = false)String dateMin,
														 @RequestParam(value = "dateMax", required = false)String dateMax											   
														  ) throws Exception {
			
		
		List<Report> reportList = ReportGenerator.getSummarySettlementReloadFrmCelcomReport(dateMin, dateMax);
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "monthlySettlementReloadFrmCelcomList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "monthlySettlementReloadFrmCelcomReport";
		}
	}
	
	/*
	 * Celcom Report
	 */
	@RequestMapping(value = "/CE0001-Report/{format}", method = RequestMethod.GET)
	public String dailyTrxDetailsReport(ModelMap modelMap,
										Model uiModel,
										@PathVariable("format") String format) throws Exception {

		List<Report> reportList = ReportGenerator.getDailyTransactionDetailsReport();
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailyTrxDetailsList";
		} else {
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxDetailsReport";
		}
	}
	
	@RequestMapping(value = "/CE0002-Report/{format}", method = RequestMethod.GET)
	public String dailyTrxDetailsByRangeDateReport(ModelMap modelMap, Model uiModel,
												   @PathVariable("format") String format,
												   @RequestParam(value = "startDate", required = false) String startDate,
												   @RequestParam(value = "endDate", required = false) String endDate) throws Exception {

		List<Report> reportList = ReportGenerator.getDailyTransactionDetailsReport();
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailyTrxDetailsByRangeDateList";
		} else {		
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxDetailsByRangeDateReport";
		}
	}
	
	@RequestMapping(value = "/CE0003-Report/{format}", method = RequestMethod.GET)
	public String dailyTrxFeeDetailsReport(ModelMap modelMap, Model uiModel,
										   @PathVariable("format") String format) throws Exception {

		List<Report> reportList = ReportGenerator.getDailyTransactionFeeDetailsReport();
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailyTrxFeeDetailsList";
		} else {		
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxFeeDetailsReport";
		}
	}
	
	@RequestMapping(value = "/CE0004-Report/{format}", method = RequestMethod.GET)
	public String dailyTrxFeeDetailsByRangeDateReport(ModelMap modelMap, Model uiModel,
			 										  @PathVariable("format") String format,
													   @RequestParam(value = "startDate", required = false) String startDate,
													   @RequestParam(value = "endDate", required = false) String endDate) throws Exception {

		List<Report> reportList = ReportGenerator.getDailyTransactionFeeDetailsReport();
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "dailyTrxFeeDetailsByRangeDateList";
		} else {	
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxFeeDetailsByRangeDateReport";
		}
	}
	
	@RequestMapping(value = "/CE0005-Report/{format}", method = RequestMethod.GET)
	public String summaryDailyTrxReport(ModelMap modelMap, Model uiModel,
										@PathVariable("format") String format) throws Exception {

		List<Report> reportList = ReportGenerator.summaryDailyTransactionDetailsReport();
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "summaryDailyTrxList";
		} else {	
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryDailyTrxReport";
		}
	}
	
	@RequestMapping(value = "/CE0006-Report/{format}", method = RequestMethod.GET)
	public String summaryDailyTrxByRangeDateReport(ModelMap modelMap, Model uiModel,
												   @PathVariable("format") String format,
												   @RequestParam(value = "startDate", required = false) String startDate,
												   @RequestParam(value = "endDate", required = false) String endDate) throws Exception {

		List<Report> reportList = ReportGenerator.summaryDailyTransactionDetailsReport();
		
		if(format.equalsIgnoreCase("html")) {
	        uiModel.addAttribute("reports", reportList);
			
	        return "summaryDailyTrxByRangeDateList";
		} else {	
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(reportList ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryDailyTrxByRangeDateReport";
		}
	}
	

//	@RequestMapping(value = "/findReportByParam", method = RequestMethod.POST)
//	public String findReloadRequestsByParam(
//			@RequestParam(value = "status", required = false) String status,
//			@RequestParam(value = "minRequestedTime", required = false) String dateMinStr,
//			@RequestParam(value = "maxRequestedTime", required = false) String dateMaxStr, Model uiModel) throws Exception {
//		Date dateMin = null;
//		Date dateMax = null;
//		SimpleDateFormat dateFormat = new SimpleDateFormat(getResourceText("date_display_format"));
//		try {
//			dateMin = dateMinStr == null || dateMinStr.isEmpty() ? null : dateFormat
//					.parse(dateMinStr);
//			dateMax = dateMaxStr == null || dateMaxStr.isEmpty() ? null : dateFormat
//					.parse(dateMaxStr);
//		} catch (ParseException x) {
//			x.printStackTrace();
//		}
//		
//		List<Report> reportList = ReportGenerator.getSummaryRequestReloadFrmCelcomReport();
//	    uiModel.addAttribute("reports", reportList);
//			
//	    return "summaryRequestReloadFrmCelcomList";
//	
//	}
//	
//	@ModelAttribute("reportType")
//	public Collection<Map<String, String>> populateStatusCodes() {
//		List<Map<String, String>> statusList = new ArrayList<Map<String, String>>();
//		Map<String, String> statusMap = new TreeMap<String, String>();
//		statusMap.put("id", Constants.RELOAD_STATUS_NEW);
//		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_NEW));
//		statusList.add(statusMap);
//
//		statusMap = new TreeMap<String, String>();
//		statusMap.put("id", Constants.RELOAD_STATUS_PENDING);
//		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_PENDING));
//		statusList.add(statusMap);
//
//		statusMap = new TreeMap<String, String>();
//		statusMap.put("id", Constants.RELOAD_STATUS_FAILED);
//		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_FAILED));
//		statusList.add(statusMap);
//
//		statusMap = new TreeMap<String, String>();
//		statusMap.put("id", Constants.RELOAD_STATUS_EXPIRED);
//		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_EXPIRED));
//		statusList.add(statusMap);
//
//		statusMap = new TreeMap<String, String>();
//		statusMap.put("id", Constants.RELOAD_STATUS_MANUALCANCEL);
//		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_MANUALCANCEL));
//		statusList.add(statusMap);
//
//		statusMap = new TreeMap<String, String>();
//		statusMap.put("id", Constants.RELOAD_STATUS_SUCCESS);
//		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_SUCCESS));
//		statusList.add(statusMap);
//		return statusList;
//	}
//
//	


}
