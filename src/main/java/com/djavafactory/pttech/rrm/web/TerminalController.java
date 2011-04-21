package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Terminal;
import com.djavafactory.pttech.rrm.domain.TerminalType;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RooWebScaffold(path = "terminals", formBackingObject = Terminal.class)
@RequestMapping("/terminals")
@Controller
public class TerminalController {

    private Date createdDate; //to hold the createdTime

    /**
    * To show the list of terminal with paginate
    * @param page The page number
    * @param size The size of the display list for a page
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Terminal> terminalList = new Terminal().findTerminalsByParam(null, null, -1L, -1L, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList();
            uiModel.addAttribute("terminals", terminalList);
            float nrOfPages = (float) terminalList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("terminals", new Terminal().findTerminalsByParam(null, null, -1L, -1L, -1, -1).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

    /**
    * To search terminals by parameters
    * @param terminalId The terminal id
    * @param status The terminal status
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(value = "/findTerminalsByParam", method = RequestMethod.POST)
    public String findTerminalsByParam(@RequestParam(value = "terminalId", required = false) String terminalId, @RequestParam(value = "status", required = false) String status,
                                       @RequestParam(value = "terminalType", required = false) Long terminalType, @RequestParam(value = "acquirer", required = false) Long acquirer, Model uiModel) {
        uiModel.addAttribute("terminals", new Terminal().findTerminalsByParam(terminalId, status, terminalType, acquirer, -1, -1).getResultList());
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

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
        terminal.setStatus(Constants.TERMINAL_STATUS_DELETED);
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
        terminal.setCreatedTime(createdDate);
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

    /**
    * display update form and save the createdTime into createdDate
    * @param id The Terminal id
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Terminal objTerminal;
        objTerminal = Terminal.findTerminal(id);
        createdDate = objTerminal.getCreatedTime();
        uiModel.addAttribute("terminal", objTerminal);
        addDateTimeFormatPatterns(uiModel);
        return "terminals/update";
    }

    /**
    * display drop down selection for undeleted acquirers in create terminal form
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("acquirers")
    public Collection<Acquirer> populateAcquirers() {
        return new Acquirer().findAcquirersByParam(null, null, -1, -1).getResultList();
    }

    /**
    * display drop down selection for all acquirers in update terminal form
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("allacquirers")
    public Collection<Acquirer> populateAllAcquirers() {
        return new Acquirer().findAllAcquirers();
    }

    /**
    * display drop down selection for undeleted terminal types in create terminal form and search terminal by terminal type
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("terminaltypes")
    public java.util.Collection<TerminalType> populateTerminalTypes() {
        return new TerminalType().findTerminalTypesByParam(null, -1, -1).getResultList();
    }

    /**
    * display drop down selection for ALL terminal types in update terminal form
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("allterminaltypes")
    public java.util.Collection<TerminalType> populateAllTerminalTypes() {
        return new TerminalType().findAllTerminalTypes();
    }

}
