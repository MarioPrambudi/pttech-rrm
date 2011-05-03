package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.City;
import com.djavafactory.pttech.rrm.domain.Terminal;
import com.djavafactory.pttech.rrm.domain.TerminalType;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RooWebScaffold(path = "terminals", formBackingObject = Terminal.class)
@RequestMapping("/terminals")
@Controller
public class TerminalController extends BaseController {

    /**
     * To show the list of terminal with paginate
     *
     * @param page    The page number
     * @param size    The size of the display list for a page
     * @param uiModel Model
     * @return String the page path to redirect
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Terminal> terminalList = Terminal.findTerminalsByParam(null, null, -1L, -1L, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo, "terminal.terminalId").getResultList();
            uiModel.addAttribute("terminals", regenerateList(terminalList));
            float nrOfPages = (float) terminalList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("terminals", regenerateList(Terminal.findTerminalsByParam(null, null, -1L, -1L, -1, -1, "terminal.terminalId").getResultList()));
        }
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

    /**
     * To search terminals by parameters
     *
     * @param terminalId The terminal id
     * @param status     The terminal status
     * @param uiModel    Model
     * @return String the page path to redirect
     */
    @RequestMapping(value = "/findTerminalsByParam", method = RequestMethod.POST)
    public String findTerminalsByParam(@RequestParam(value = "terminalId", required = false) String terminalId, @RequestParam(value = "status", required = false) String status,
                                       @RequestParam(value = "terminalType", required = false) Long terminalType, @RequestParam(value = "acquirer", required = false) Long acquirer, Model uiModel) {
        acquirer = (acquirer == null) ? -1L : acquirer;
        terminalType = (terminalType == null) ? -1L : terminalType;
        uiModel.addAttribute("terminals", regenerateList(Terminal.findTerminalsByParam(terminalId, status, terminalType, acquirer, -1, -1, "terminal.terminalId").getResultList()));
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

    /**
     * To delete Terminal by updated deletedStatus to "D"
     *
     * @param id      The Terminal id
     * @param page    Integer
     * @param size    Integer
     * @param uiModel Model
     * @return String the page path to redirect
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Terminal terminal;
        terminal = Terminal.findTerminal(id);
        uiModel.asMap().clear();
        terminal.setStatus(Constants.TERMINAL_STATUS_DELETED);
        terminal.merge();
        return "redirect:/terminals";
    }

    /**
     * insert new terminal with createdTime and createdBy
     *
     * @param terminal           the terminal object
     * @param bindingResult      BindingResult
     * @param uiModel            Model
     * @param httpServletRequest HttpServletRequest
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
     *
     * @param terminal           the terminal object
     * @param bindingResult      BindingResult
     * @param uiModel            Model
     * @param httpServletRequest HttpServletRequest
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
        terminal.setModifiedBy("System");
        terminal.setModifiedTime(new Date());
        terminal.merge();
        return "redirect:/terminals/" + encodeUrlPathSegment(terminal.getId().toString(), httpServletRequest);
    }

    /**
     * update new terminal with new status
     *
     * @param uiModel Model
     * @return String the page path to redirect
     */
    @RequestMapping(value = "/setstatus", method = RequestMethod.POST)
    public String updateStatus(@RequestParam(value = "terminalIds") String terminalIds, @RequestParam(value = "code") String code, Model uiModel) {
        for (String id : terminalIds.split(",")) {
            Terminal terminal = Terminal.findTerminal(Long.valueOf(id));
            terminal.setStatus(code);
            terminal.setModifiedBy("System");
            terminal.setModifiedTime(new Date());
            terminal.merge();
        }
        uiModel.asMap().clear();
        return "redirect:/terminals";
    }

    /**
     * display update form
     *
     * @param id      The Terminal id
     * @param uiModel Model
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
     *
     * @return String the page path to redirect
     */
    @ModelAttribute("acquirers")
    public Collection<Acquirer> populateAcquirers() {
        return Acquirer.findAcquirersByParam(null, null, false, "acquirer.name", -1, -1).getResultList();
    }

    /**
     * display drop down selection for all acquirers in update terminal form
     *
     * @return String the page path to redirect
     */
    @ModelAttribute("allacquirers")
    public Collection<Acquirer> populateAllAcquirers() {
        return Acquirer.findAcquirersByParam(null, null, true, "acquirer.name", -1, -1).getResultList();
    }

    /**
     * display drop down selection for undeleted terminal types in create terminal form and search terminal by terminal type
     *
     * @return String the page path to redirect
     */
    @ModelAttribute("terminaltypes")
    public java.util.Collection<TerminalType> populateTerminalTypes() {
        return TerminalType.findTerminalTypesByParam(null, false, "terminalType.name", -1, -1).getResultList();
    }

    /**
     * display drop down selection for ALL terminal types in update terminal form
     *
     * @return String the page path to redirect
     */
    @ModelAttribute("allterminaltypes")
    public java.util.Collection<TerminalType> populateAllTerminalTypes() {
        return TerminalType.findTerminalTypesByParam(null, true, "terminalType.name", -1, -1).getResultList();
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
        map.put("id", Constants.TERMINAL_STATUS_BLOCK);
        map.put("value", getResourceText("terminal_status_code_" + Constants.TERMINAL_STATUS_BLOCK));
        list.add(map);
        map = null;

        map = new HashMap<String, String>();
        map.put("id", Constants.TERMINAL_STATUS_INACTIVE);
        map.put("value", getResourceText("terminal_status_code_" + Constants.TERMINAL_STATUS_INACTIVE));
        list.add(map);
        map = null;

        return list;
    }

    /**
     * display date/time formatting get from resource bundle
     *
     * @return String the page path to redirect
     */
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("terminal_createdtime_date_format", getResourceText("display_date_format"));
        uiModel.addAttribute("terminal_modifiedtime_date_format", getResourceText("display_date_format"));
    }

    /**
     * Get the terminal info and format the status
     *
     * @param id      The Terminal id
     * @param uiModel Model
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

    /**
     * display drop down selection for all cities in create/update acquirer form
     *
     * @return String the page path to redirect
     */
    @ModelAttribute("citys")
    public java.util.Collection<City> populateCitys() {
        return City.findAllCitys();
    }

    private List<Terminal> regenerateList(List<Terminal> terminalList) {
        for (int i = 0; i < terminalList.size(); i++) {
            Terminal terminal = terminalList.get(i);
            terminal.setStatus(getResourceText("terminal_status_code_" + terminal.getStatus()));
        }
        return terminalList;
    }


}
