package com.djavafactory.pttech.rrm.web;

import java.util.Date;

import com.djavafactory.pttech.rrm.domain.Terminal;

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

@RooWebScaffold(path = "terminals", formBackingObject = Terminal.class)
@RequestMapping("/terminals")
@Controller
public class TerminalController {

	public static String TERMINAL_STATUS_DELETED = "d";
 /**
   * To delete Terminal by updated deletedStatus to "d"
   * @param id The Terminal id
   * @param page Integer
   * @param size Integer
   * @param uiModel Model
   * @exception none 
   * @return String the page path to redirect
   */ 
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Terminal terminal;
        terminal = Terminal.findTerminal(id);
        terminal.setStatus(TERMINAL_STATUS_DELETED);
        uiModel.asMap().clear();
        return "redirect:/terminals";
    }
	
  /**
   * insert new terminal with createdTime and createdBy 
   * @param acquirer the terminal object
   * @param bindingResult BindingResult
   * @param uiModel Model
   * @param httpServletRequest HttpServletRequest 
   * @exception none 
   * @return String the page path to redirect
   */
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Terminal terminal, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("terminal", terminal);
            addDateTimeFormatPatterns(uiModel);
            return "terminals/create";
        }
        uiModel.asMap().clear();
        // Temporary static
        terminal.setCreatedBy("System");
        terminal.setCreatedTime(getCurrentDate());
        terminal.persist();
        return "redirect:/terminals/" + encodeUrlPathSegment(terminal.getId().toString(), httpServletRequest);
    }


  /**
   * update new terminal with modifiedTime and modifiedBy 
   * @param acquirer the terminal object
   * @param bindingResult BindingResult
   * @param uiModel Model
   * @param httpServletRequest HttpServletRequest 
   * @exception none 
   * @return String the page path to redirect
   */
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Terminal terminal, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("terminal", terminal);
            addDateTimeFormatPatterns(uiModel);
            return "terminals/update";
        }
        uiModel.asMap().clear();
        // ModifiedBy DEMO
        terminal.setModifiedBy("System");
        terminal.setModifiedTime(getCurrentDate());
        terminal.merge();
        return "redirect:/terminals/" + encodeUrlPathSegment(terminal.getId().toString(), httpServletRequest);
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
}
