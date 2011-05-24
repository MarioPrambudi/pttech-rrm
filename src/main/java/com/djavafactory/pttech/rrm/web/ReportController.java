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
import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.Report;
import com.djavafactory.pttech.rrm.reports.ReportGenerator;

import org.apache.regexp.RE;
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
	@RequestMapping(value ="/TG0001-Report/{format}", method = {RequestMethod.POST, RequestMethod.GET})
	public String dailyDetailsRequestReloadFfmCelcomReport(ModelMap modelMap,
														   Model uiModel,
														   @PathVariable("format") String format,
														   @RequestParam(value = "page", required = false) Integer page, 
														   @RequestParam(value = "size", required = false) Integer size,
														   @RequestParam(value = "dateMin", required = false)String dateMin,
														   @RequestParam(value = "dateMax", required = false)String dateMax										  
														  ) throws Exception {
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			  if (page != null || size != null) {
				    int sizeNo = size == null ? 10 : size.intValue();
		    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
		    		float nrOfPages = (float)totalReport / sizeNo;
		    		uiModel.addAttribute("reports", ReportGenerator.getDailyDetailsRequestReloadFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
		            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
			  } else {				    
		            uiModel.addAttribute("reports", ReportGenerator.getDailyDetailsRequestReloadFrmCelcomReport(dateMin, dateMax, -1, -1));
		      }
			  return "dailyDetailsRequestReloadFrmCelcomList";            
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailyDetailsRequestReloadFrmCelcomReport(dateMin, dateMax, -1, -1);
	        listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyDetailsRequestReloadFrmCelcomReport";
		}
		
	}
	
	@RequestMapping(value = "/TG0002-Report/{format}", method = RequestMethod.GET)
	public String summaryRequestReloadFrmCelcomReport(ModelMap modelMap,
													  Model uiModel,
													  @PathVariable("format") String format,
													  @RequestParam(value = "page", required = false) Integer page, 
													  @RequestParam(value = "size", required = false) Integer size,
													  @RequestParam(value = "dateMin", required = false)String dateMin,
													  @RequestParam(value = "dateMax", required = false)String dateMax										  										   
													  ) throws Exception {
		
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			  if (page != null || size != null) {
				    int sizeNo = size == null ? 10 : size.intValue();
		    		long totalReport = ReportGenerator.getTotalSummaryReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
		    		float nrOfPages = (float)totalReport / sizeNo;
		    		uiModel.addAttribute("reports", ReportGenerator.getSummaryRequestReloadFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
		            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);
			        
			  } else {
		            uiModel.addAttribute("reports", ReportGenerator.getSummaryRequestReloadFrmCelcomReport(dateMin, dateMax, -1, -1));
		      }			
			  return "summaryRequestReloadFrmCelcomList";
		} else {	
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getSummaryRequestReloadFrmCelcomReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
	        JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport ,false);
	        modelMap.put("reportData", jrDataSource);
	        modelMap.put("format", format);
	        return "summaryRequestReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0003-Report/{format}", method = RequestMethod.GET)
	public String dailyDetailedReloadFrmCelcomReport(ModelMap modelMap,
													   Model uiModel,
													   @PathVariable("format") String format,
													   @RequestParam(value = "page", required = false) Integer page, 
													   @RequestParam(value = "size", required = false) Integer size,
													   @RequestParam(value = "dateMin", required = false)String dateMin,
													   @RequestParam(value = "dateMax", required = false)String dateMax											   
													  ) throws Exception {
		
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";	
		if(format.equalsIgnoreCase("html")) {
			  if (page != null || size != null) {
				    int sizeNo = size == null ? 10 : size.intValue();
		    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllSuccess());
		    		float nrOfPages = (float)totalReport / sizeNo;
		    		uiModel.addAttribute("reports", ReportGenerator.getDailyDetailedReloadFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
		            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
			  } else {
		            uiModel.addAttribute("reports", ReportGenerator.getDailyDetailedReloadFrmCelcomReport(dateMin, dateMax, -1, -1));
		      }	
	        return "dailyDetailedReloadFrmCelcomList";
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailyDetailedReloadFrmCelcomReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyDetailedReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0004-Report/{format}", method = RequestMethod.GET)
	public String summaryReloadFrmCelcomReport(ModelMap modelMap, 
											   Model uiModel,
											   @PathVariable("format") String format,
											   @RequestParam(value = "page", required = false) Integer page, 
											   @RequestParam(value = "size", required = false) Integer size,
											   @RequestParam(value = "dateMin", required = false)String dateMin,
											   @RequestParam(value = "dateMax", required = false)String dateMax									   
											  ) throws Exception {
		
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			  if (page != null || size != null) {
				    int sizeNo = size == null ? 10 : size.intValue();
		    		long totalReport = ReportGenerator.getTotalSummaryReport(dateMin, dateMax, ReportGenerator.getListAllSuccess());
		    		float nrOfPages = (float)totalReport / sizeNo;
		    		uiModel.addAttribute("reports", ReportGenerator.getSummaryReloadReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
		            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);		        
			  } else {
		            uiModel.addAttribute("reports", ReportGenerator.getSummaryReloadReport(dateMin, dateMax, -1, -1));
		      }					
	        return "summaryReloadFrmCelcomList";
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getSummaryReloadReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0005-Report/{format}", method = RequestMethod.GET)
	public String dailyDetailsCancellationReloadReqFrmCelcomReport(ModelMap modelMap,
																   Model uiModel,
																   @PathVariable("format") String format,
																   @RequestParam(value = "page", required = false) Integer page, 
																   @RequestParam(value = "size", required = false) Integer size,
																   @RequestParam(value = "dateMin", required = false)String dateMin,
																   @RequestParam(value = "dateMax", required = false)String dateMax											   
																  ) throws Exception {
		
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			 if (page != null || size != null) {
				    int sizeNo = size == null ? 10 : size.intValue();
		    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllCancel());
		    		float nrOfPages = (float)totalReport / sizeNo;
		    		uiModel.addAttribute("reports", ReportGenerator.getDailyDetailsCancellationReloadReqFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
		            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
			  } else {
		            uiModel.addAttribute("reports", ReportGenerator.getDailyDetailsCancellationReloadReqFrmCelcomReport(dateMin, dateMax, -1, -1));
		      }
			 return "dailyDetailsCancellationReloadReqFrmCelcomList";
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailyDetailsCancellationReloadReqFrmCelcomReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyDetailsCancellationReloadReqFrmCelcomReport";
		}
	}
	
	@RequestMapping(value = "/TG0006-Report/{format}", method = RequestMethod.GET)
	public String summaryCancellationReloadFrmCelcomReport(ModelMap modelMap,
														   Model uiModel,
														   @PathVariable("format") String format,
														   @RequestParam(value = "page", required = false) Integer page, 													
														   @RequestParam(value = "size", required = false) Integer size,											
														   @RequestParam(value = "dateMin", required = false)String dateMin,													
														   @RequestParam(value = "dateMax", required = false)String dateMax										
														   ) throws Exception {
		
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReportGenerator.getTotalSummaryReport(dateMin, dateMax, ReportGenerator.getListAllCancel());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getSummaryCancellationReloadReqFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);		        
		  } else {
	            uiModel.addAttribute("reports", ReportGenerator.getSummaryCancellationReloadReqFrmCelcomReport(dateMin, dateMax, -1, -1));
	      }		
	      return "summaryCancellationReloadFrmCelcomList";
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getSummaryCancellationReloadReqFrmCelcomReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport ,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryCancellationReloadFrmCelcomReport";
		}
	}
	
	@RequestMapping(value ="/TG0007-Report/{format}", method = RequestMethod.GET)
	public String dailySettlementReloadFrmCelcomReport(ModelMap modelMap,
													   Model uiModel,
													   @PathVariable("format") String format,
													   @RequestParam(value = "page", required = false) Integer page, 
													   @RequestParam(value = "size", required = false) Integer size,
													   @RequestParam(value = "dateMin", required = false)String dateMin,
													   @RequestParam(value = "dateMax", required = false)String dateMax												   
													  ) throws Exception {
		
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getDailySettlementReloadFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);		        
			} else {
	            uiModel.addAttribute("reports", ReportGenerator.getDailySettlementReloadFrmCelcomReport(dateMin, dateMax, -1, -1));
			}				
	        return "dailySettlementReloadFrmCelcomList";
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailySettlementReloadFrmCelcomReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailySettlementReloadFrmCelcomReport";
		}
	}
	
	
	@RequestMapping(value = "/TG0008-Report/{format}", method = RequestMethod.GET)
	public String monthlySettlementReloadFrmCelcomReport(ModelMap modelMap,
														 Model uiModel,
														 @PathVariable("format") String format,
														 @RequestParam(value = "page", required = false) Integer page, 
														 @RequestParam(value = "size", required = false) Integer size,
														 @RequestParam(value = "dateMin", required = false)String dateMin,
														 @RequestParam(value = "dateMax", required = false)String dateMax											   
														  ) throws Exception {
			
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
			if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReportGenerator.getTotalSummaryReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getSummarySettlementReloadFrmCelcomReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);		        
			} else {
	            uiModel.addAttribute("reports", ReportGenerator.getSummarySettlementReloadFrmCelcomReport(dateMin, dateMax, -1, -1));
			}		
	        return "monthlySettlementReloadFrmCelcomList";
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getSummarySettlementReloadFrmCelcomReport(dateMin, dateMax, -1, -1);
			listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
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
										 @PathVariable("format") String format,
										 @RequestParam(value = "page", required = false) Integer page, 
										 @RequestParam(value = "size", required = false) Integer size,
										 @RequestParam(value = "dateMin", required = false)String dateMin,
										 @RequestParam(value = "dateMax", required = false)String dateMax) throws Exception {

		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
	        if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReloadRequest.totalReloadRequests(ReportGenerator.getListAllStatus());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getDailyTransactionDetailsReport((page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
		  } else {				    
	            uiModel.addAttribute("reports", ReportGenerator.getDailyTransactionDetailsReport(-1, -1));
	      }
		  return "dailyTrxDetailsReport"; 
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailyTransactionDetailsReport(-1, -1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxDetailsReport";
		}

	}
	
	@RequestMapping(value = "/CE0002-Report/{format}", method = RequestMethod.GET)
	public String dailyTrxDetailsByRangeDateReport(ModelMap modelMap,
													 Model uiModel,
													 @PathVariable("format") String format,
													 @RequestParam(value = "page", required = false) Integer page, 
													 @RequestParam(value = "size", required = false) Integer size,
													 @RequestParam(value = "dateMin", required = false)String dateMin,
													 @RequestParam(value = "dateMax", required = false)String dateMax	
													 ) throws Exception {

		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
	        if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getDailyTransactionDetailsReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
		  } else {				    
	            uiModel.addAttribute("reports", ReportGenerator.getDailyTransactionDetailsReport(dateMin, dateMax, -1, -1));
	      }
		  return "dailyTrxDetailsByRangeDateList"; 
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailyTransactionDetailsReport(dateMin, dateMax, -1, -1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxDetailsByRangeDateReport";
		}       
	}
	
	@RequestMapping(value = "/CE0003-Report/{format}", method = RequestMethod.GET)
	public String dailyTrxFeeDetailsReport(ModelMap modelMap, Model uiModel,
											 @PathVariable("format") String format,
											 @RequestParam(value = "page", required = false) Integer page, 
											 @RequestParam(value = "size", required = false) Integer size,
											 @RequestParam(value = "dateMin", required = false)String dateMin,
											 @RequestParam(value = "dateMax", required = false)String dateMax) throws Exception {

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
													 @RequestParam(value = "page", required = false) Integer page, 
													 @RequestParam(value = "size", required = false) Integer size,
													 @RequestParam(value = "dateMin", required = false)String dateMin,
													 @RequestParam(value = "dateMax", required = false)String dateMax) throws Exception {

		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
	        if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getDailyTransactionFeeDetailsReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
		  } else {				    
	            uiModel.addAttribute("reports", ReportGenerator.getDailyTransactionFeeDetailsReport(dateMin, dateMax, -1, -1));
	      }
		  return "dailyTrxFeeDetailsByRangeDateList"; 
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getDailyTransactionFeeDetailsReport(dateMin, dateMax, -1, -1);
	        listReport.remove(listReport.size()-1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "dailyTrxFeeDetailsByRangeDateReport";
		}
	}
	
	@RequestMapping(value = "/CE0005-Report/{format}", method = RequestMethod.GET)
	public String summaryDailyTrxReport(ModelMap modelMap, Model uiModel,
										 @PathVariable("format") String format,
										 @RequestParam(value = "page", required = false) Integer page, 
										 @RequestParam(value = "size", required = false) Integer size,
										 @RequestParam(value = "dateMin", required = false)String dateMin,
										 @RequestParam(value = "dateMax", required = false)String dateMax) throws Exception {

		List<Report> reportList = ReportGenerator.getSummaryDailyTransactionDetailsReport(-1,-1);
		
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
													 @RequestParam(value = "page", required = false) Integer page, 
													 @RequestParam(value = "size", required = false) Integer size,
													 @RequestParam(value = "dateMin", required = false)String dateMin,
													 @RequestParam(value = "dateMax", required = false)String dateMax) throws Exception {
	
		if (dateMin==null) dateMin="null";
		if (dateMax==null) dateMax="null";
		if(format.equalsIgnoreCase("html")) {
	        if (page != null || size != null) {
			    int sizeNo = size == null ? 10 : size.intValue();
	    		long totalReport = ReportGenerator.getTotalReport(dateMin, dateMax, ReportGenerator.getListAllStatus());
	    		float nrOfPages = (float)totalReport / sizeNo;
	    		uiModel.addAttribute("reports", ReportGenerator.getSummaryDailyTransactionDetailsReport(dateMin, dateMax, (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo));		            
	            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	            uiModel.addAttribute("params", "&dateMin=" + dateMin + "&dateMax=" + dateMax);			        
		  } else {				    
	            uiModel.addAttribute("reports", ReportGenerator.getSummaryDailyTransactionDetailsReport(dateMin, dateMax, -1, -1));
	      }
		  return "summaryDailyTrxByRangeDateList"; 
		} else {
			List<Report> listReport = new ArrayList<Report>();
			listReport = ReportGenerator.getSummaryDailyTransactionDetailsReport(dateMin, dateMax, -1, -1);
			JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(listReport,false);
			modelMap.put("reportData", jrDataSource);
			modelMap.put("format", format);
			return "summaryDailyTrxByRangeDateReport";
		}
	}
}
