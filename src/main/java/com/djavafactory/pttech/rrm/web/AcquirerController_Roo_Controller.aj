// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Firmware;
import com.djavafactory.pttech.rrm.domain.Province;
import com.djavafactory.pttech.rrm.domain.Terminal;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect AcquirerController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String AcquirerController.create(@Valid Acquirer acquirer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("acquirer", acquirer);
            addDateTimeFormatPatterns(uiModel);
            return "acquirers/create";
        }
        uiModel.asMap().clear();
        acquirer.persist();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String AcquirerController.createForm(Model uiModel) {
        uiModel.addAttribute("acquirer", new Acquirer());
        addDateTimeFormatPatterns(uiModel);
        List dependencies = new ArrayList();
        if (Province.countProvinces() == 0) {
            dependencies.add(new String[]{"province", "provinces"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "acquirers/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String AcquirerController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("acquirer", Acquirer.findAcquirer(id));
        uiModel.addAttribute("itemId", id);
        return "acquirers/show";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String AcquirerController.update(@Valid Acquirer acquirer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("acquirer", acquirer);
            addDateTimeFormatPatterns(uiModel);
            return "acquirers/update";
        }
        uiModel.asMap().clear();
        acquirer.merge();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String AcquirerController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("acquirer", Acquirer.findAcquirer(id));
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String AcquirerController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Acquirer.findAcquirer(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/acquirers";
    }
    
    @ModelAttribute("acquirers")
    public Collection<Acquirer> AcquirerController.populateAcquirers() {
        return Acquirer.findAllAcquirers();
    }
    
    @ModelAttribute("firmwares")
    public java.util.Collection<Firmware> AcquirerController.populateFirmwares() {
        return Firmware.findAllFirmwares();
    }
    
    @ModelAttribute("provinces")
    public java.util.Collection<Province> AcquirerController.populateProvinces() {
        return Province.findAllProvinces();
    }
    
    @ModelAttribute("terminals")
    public java.util.Collection<Terminal> AcquirerController.populateTerminals() {
        return Terminal.findAllTerminals();
    }
    
    void AcquirerController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("acquirer_createdtime_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("acquirer_modifiedtime_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    String AcquirerController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
