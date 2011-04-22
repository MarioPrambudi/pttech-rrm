// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Province;
import com.djavafactory.pttech.rrm.domain.Terminal;
import com.djavafactory.pttech.rrm.domain.TerminalType;
import java.io.UnsupportedEncodingException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect TerminalController_Roo_Controller {
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String TerminalController.createForm(Model uiModel) {
        uiModel.addAttribute("terminal", new Terminal());
        addDateTimeFormatPatterns(uiModel);
        List dependencies = new ArrayList();
        if (Province.countProvinces() == 0) {
            dependencies.add(new String[]{"province", "provinces"});
        }
        if (Acquirer.countAcquirers() == 0) {
            dependencies.add(new String[]{"acquirer", "acquirers"});
        }
        if (TerminalType.countTerminalTypes() == 0) {
            dependencies.add(new String[]{"terminaltype", "terminaltypes"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "terminals/create";
    }
    
<<<<<<< HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String TerminalController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("terminal", Terminal.findTerminal(id));
        uiModel.addAttribute("itemId", id);
        return "terminals/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String TerminalController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("terminals", Terminal.findTerminalEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Terminal.countTerminals() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("terminals", Terminal.findAllTerminals());
        }
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }
    
    @ModelAttribute("acquirers")
    public Collection<Acquirer> TerminalController.populateAcquirers() {
        return Acquirer.findAllAcquirers();
=======
    @ModelAttribute("provinces")
    public Collection<Province> TerminalController.populateProvinces() {
        return Province.findAllProvinces();
>>>>>>> upstream/master
    }
    
    @ModelAttribute("terminals")
    public java.util.Collection<Terminal> TerminalController.populateTerminals() {
        return Terminal.findAllTerminals();
    }
    
<<<<<<< HEAD
    @ModelAttribute("terminaltypes")
    public java.util.Collection<TerminalType> TerminalController.populateTerminalTypes() {
        return TerminalType.findAllTerminalTypes();
    }
    
    void TerminalController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("terminal_createdtime_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("terminal_modifiedtime_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
=======
>>>>>>> upstream/master
    String TerminalController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
