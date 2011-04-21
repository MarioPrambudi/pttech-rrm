package com.djavafactory.pttech.rrm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.djavafactory.pttech.rrm.domain.Acquirer;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "acquirers", formBackingObject = Acquirer.class)
@RequestMapping("/acquirers")
@Controller
public class AcquirerController {
	
	public static Boolean LDELETED_STATUS = true;
	
  /**
   * To delete Terminal by updated deletedStatus to LDELETED_STATUS value
   * @param id The Terminal id
   * @param page Integer
   * @param size Integer
   * @param uiModel Model
   * @exception none 
   * @return String the page path to redirect
   */ 
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Acquirer acquirer;
		acquirer = Acquirer.findAcquirer(id);
		acquirer.setDeleted(LDELETED_STATUS);
		uiModel.asMap().clear();
		acquirer.merge();
        return "redirect:/acquirers";
    }
	
  /**
   * insert new acquirer with createdTime and createdBy 
   * @param acquirer the acquirer object
   * @param bindingResult BindingResult
   * @param uiModel Model
   * @param httpServletRequest HttpServletRequest 
   * @exception none 
   * @return String the page path to redirect
   */
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Acquirer acquirer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("acquirer", acquirer);
            addDateTimeFormatPatterns(uiModel);            
            return "acquirers/create";
        }
        uiModel.asMap().clear();
        // Temporary static
        acquirer.setCreatedBy("System");
        acquirer.setCreatedTime(getCurrentDate());
        acquirer.persist();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }
	
	
  /**
   * get the current date
   * @param none
   * @exception none 
   * @return Date the current date
   */
	@Transient
	public Date getCurrentDate(){
	return new Date();
	}
	
  /**
   * update new acquirer with modifiedTime and modifiedBy 
   * @param acquirer the acquirer object
   * @param bindingResult BindingResult
   * @param uiModel Model
   * @param httpServletRequest HttpServletRequest 
   * @exception none 
   * @return String the page path to redirect
   */
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Acquirer acquirer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("acquirer", acquirer);
            addDateTimeFormatPatterns(uiModel);
            return "acquirers/update";
        }
        uiModel.asMap().clear();
        
        // ModifiedBy DEMO
        acquirer.setModifiedBy("System");
        acquirer.setModifiedTime(getCurrentDate());
        acquirer.merge();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }
}
