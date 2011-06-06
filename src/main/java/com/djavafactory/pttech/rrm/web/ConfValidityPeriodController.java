package com.djavafactory.pttech.rrm.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.djavafactory.pttech.rrm.domain.ConfValidityPeriod;
import com.djavafactory.pttech.rrm.util.DateUtil;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "confvalidityperiods", formBackingObject = ConfValidityPeriod.class)
@RequestMapping("/confvalidityperiods")
@Controller
public class ConfValidityPeriodController extends BaseController {
	
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid ConfValidityPeriod confValidityPeriod, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) throws ParseException {
        String redirectPage = null;
		if (bindingResult.hasErrors()) {
            uiModel.addAttribute("confValidityPeriod", confValidityPeriod);
            addDateTimeFormatPatterns(uiModel);
            return "confvalidityperiods/update";
        }
        uiModel.asMap().clear();	
        
        if(confValidityPeriod.getConfigValue().equals(confValidityPeriod.getConfigValueHidden()))
        {
        	 // no redirect to show page confvalidityperiods/id
        	redirectPage = "redirect:/confvalidityperiods/" + confValidityPeriod.getId();
        }
        else
        {
        	ConfValidityPeriod newConfKey;       	
        	try{
        		//update old configkey
            	confValidityPeriod.setEndDate(DateUtil.getDateMaxTime(confValidityPeriod.getStartDateNew()));
            	confValidityPeriod.merge();          	
            	//save new confkey
        		newConfKey = new ConfValidityPeriod();
        		newConfKey.setConfigKey(confValidityPeriod.getConfigKey());
        		newConfKey.setConfigValue(confValidityPeriod.getConfigValue());
        		newConfKey.setStartDate(DateUtil.getDateMinTime(confValidityPeriod.getStartDateNew())); 
        		newConfKey.persist();
        		redirectPage = "redirect:/confvalidityperiods/" + encodeUrlPathSegment(confValidityPeriod.getId().toString(), httpServletRequest);
        	}catch (Exception e){
        		e.printStackTrace();
        	}
        }     
        return redirectPage;
	}
	
	 void addDateTimeFormatPatterns(Model uiModel) {
	        uiModel.addAttribute("confValidityPeriod_startdate_date_format", getResourceText("date_time_display_format"));
	        uiModel.addAttribute("confValidityPeriod_enddate_date_format", getResourceText("date_time_display_format"));
	        uiModel.addAttribute("confValidityPeriod_startdatenew_date_format", getResourceText("date_display_format"));
	    }
}
