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
import java.util.*;

@RooWebScaffold(path = "terminals", formBackingObject = Terminal.class)
@RequestMapping("/terminals")
@Controller
public class TerminalController extends BaseController {


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

            List<Terminal> terminalList = Terminal.findTerminalsByParam(null, null, -1L, -1L, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList();
            uiModel.addAttribute("terminals", regenerateList(terminalList));
            float nrOfPages = (float) terminalList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("terminals", regenerateList(Terminal.findTerminalsByParam(null, null, -1L, -1L, -1, -1).getResultList()));
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
        uiModel.addAttribute("terminals", regenerateList(Terminal.findTerminalsByParam(terminalId, status, terminalType, acquirer, -1, -1).getResultList()));
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

 /**
   * To delete Terminal by updated deletedStatus to "D"
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
   * @param terminal the terminal object
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
        terminal.setCreatedBy("System"); // Temporary static
        terminal.setCreatedTime(new Date());
        terminal.persist();
        return "redirect:/terminals/" + encodeUrlPathSegment(terminal.getId().toString(), httpServletRequest);
    }


  /**
   * update new terminal with modifiedTime and modifiedBy 
   * @param terminal the terminal object
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
        // Temporary Static
        terminal.setModifiedBy("System");
        terminal.setModifiedTime(new Date());
        terminal.merge();
        return "redirect:/terminals/" + encodeUrlPathSegment(terminal.getId().toString(), httpServletRequest);
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
        uiModel.addAttribute("terminal", Terminal.findTerminal(id));
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
        return Acquirer.findAcquirersByParam(null, null, -1, -1).getResultList();
    }

    /**
    * display drop down selection for all acquirers in update terminal form
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("allacquirers")
    public Collection<Acquirer> populateAllAcquirers() {
        return Acquirer.findAllAcquirers();
    }

    /**
    * display drop down selection for undeleted terminal types in create terminal form and search terminal by terminal type
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("terminaltypes")
    public java.util.Collection<TerminalType> populateTerminalTypes() {
        return TerminalType.findTerminalTypesByParam(null, -1, -1).getResultList();
    }

    /**
    * display drop down selection for ALL terminal types in update terminal form
    * @exception none
    * @return String the page path to redirect
    */
    @ModelAttribute("allterminaltypes")
    public java.util.Collection<TerminalType> populateAllTerminalTypes() {
        return TerminalType.findAllTerminalTypes();
    }

    @ModelAttribute("statuscode")
    public java.util.Collection<Map> populateStatusCodes() {
        List<Map> list = new ArrayList<Map>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", Constants.TERMINAL_STATUS_ACTIVE);
        map.put("value", getResourceText("terminal_status_code_" + Constants.TERMINAL_STATUS_ACTIVE));
        list.add(map);
        map = null;

        map = new HashMap<String, String>();
        map.put("id", Constants.TERMINAL_STATUS_INACTIVE);
        map.put("value", getResourceText("terminal_status_code_" + Constants.TERMINAL_STATUS_INACTIVE));
        list.add(map);
        map = null;

        map = new HashMap<String, String>();
        map.put("id", Constants.TERMINAL_STATUS_BLOCK);
        map.put("value", getResourceText("terminal_status_code_" + Constants.TERMINAL_STATUS_BLOCK));
        list.add(map);
        map = null;

        return list;
    }

    /**
    * display date/time formatting get from resource bundle
    * @exception none
    * @return String the page path to redirect
    */
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("terminal_createdtime_date_format", getResourceText("display_date_format"));
        uiModel.addAttribute("terminal_modifiedtime_date_format", getResourceText("display_date_format"));
    }

    /**
    * Get the terminal info and format the status
    * @param id The Terminal id
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        Terminal terminal = Terminal.findTerminal(id);
        terminal.setStatus(getResourceText("terminal_status_code_" + terminal.getStatus()));
        uiModel.addAttribute("terminal", terminal);
        uiModel.addAttribute("itemId", id);
        return "terminals/show";
    }

    private List<Terminal> regenerateList(List<Terminal> terminalList) {
        for(int i = 0; i < terminalList.size(); i++) {
            Terminal terminal = terminalList.get(i);
            terminal.setStatus(getResourceText("terminal_status_code_" + terminal.getStatus()));
            terminalList.set(i, terminal);
        }
        return terminalList;
    }
}
