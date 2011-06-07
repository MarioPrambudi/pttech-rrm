// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.ConfValidityPeriod;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ConfValidityPeriodController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String ConfValidityPeriodController.create(@Valid ConfValidityPeriod confValidityPeriod, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("confValidityPeriod", confValidityPeriod);
            addDateTimeFormatPatterns(uiModel);
            return "confvalidityperiods/create";
        }
        uiModel.asMap().clear();
        confValidityPeriod.persist();
        return "redirect:/confvalidityperiods/" + encodeUrlPathSegment(confValidityPeriod.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ConfValidityPeriodController.createForm(Model uiModel) {
        uiModel.addAttribute("confValidityPeriod", new ConfValidityPeriod());
        addDateTimeFormatPatterns(uiModel);
        return "confvalidityperiods/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ConfValidityPeriodController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("confvalidityperiod", ConfValidityPeriod.findConfValidityPeriod(id));
        uiModel.addAttribute("itemId", id);
        return "confvalidityperiods/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String ConfValidityPeriodController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("confvalidityperiods", ConfValidityPeriod.findConfValidityPeriodEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ConfValidityPeriod.countConfValidityPeriods() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("confvalidityperiods", ConfValidityPeriod.findAllConfValidityPeriods());
        }
        addDateTimeFormatPatterns(uiModel);
        return "confvalidityperiods/list";
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ConfValidityPeriodController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("confValidityPeriod", ConfValidityPeriod.findConfValidityPeriod(id));
        addDateTimeFormatPatterns(uiModel);
        return "confvalidityperiods/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ConfValidityPeriodController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ConfValidityPeriod.findConfValidityPeriod(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/confvalidityperiods";
    }
    
    @ModelAttribute("confvalidityperiods")
    public Collection<ConfValidityPeriod> ConfValidityPeriodController.populateConfValidityPeriods() {
        return ConfValidityPeriod.findAllConfValidityPeriods();
    }
    
    String ConfValidityPeriodController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
