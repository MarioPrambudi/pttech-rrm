// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ReloadRequestController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String ReloadRequestController.create(@Valid ReloadRequest reloadRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("reloadRequest", reloadRequest);
            addDateTimeFormatPatterns(uiModel);
            return "reloadrequests/create";
        }
        uiModel.asMap().clear();
        reloadRequest.persist();
        return "redirect:/reloadrequests/" + encodeUrlPathSegment(reloadRequest.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ReloadRequestController.createForm(Model uiModel) {
        uiModel.addAttribute("reloadRequest", new ReloadRequest());
        addDateTimeFormatPatterns(uiModel);
        return "reloadrequests/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ReloadRequestController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("reloadrequest", ReloadRequest.findReloadRequest(id));
        uiModel.addAttribute("itemId", id);
        return "reloadrequests/show";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ReloadRequestController.update(@Valid ReloadRequest reloadRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("reloadRequest", reloadRequest);
            addDateTimeFormatPatterns(uiModel);
            return "reloadrequests/update";
        }
        uiModel.asMap().clear();
        reloadRequest.merge();
        return "redirect:/reloadrequests/" + encodeUrlPathSegment(reloadRequest.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ReloadRequestController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("reloadRequest", ReloadRequest.findReloadRequest(id));
        addDateTimeFormatPatterns(uiModel);
        return "reloadrequests/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ReloadRequestController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ReloadRequest.findReloadRequest(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/reloadrequests";
    }
    
    @RequestMapping(params = { "find=ByRequestedTimeBetween", "form" }, method = RequestMethod.GET)
    public String ReloadRequestController.findReloadRequestsByRequestedTimeBetweenForm(Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        return "reloadrequests/findReloadRequestsByRequestedTimeBetween";
    }
    
    @RequestMapping(params = "find=ByRequestedTimeBetween", method = RequestMethod.GET)
    public String ReloadRequestController.findReloadRequestsByRequestedTimeBetween(@RequestParam("minRequestedTime") @DateTimeFormat(style = "S-") Date minRequestedTime, @RequestParam("maxRequestedTime") @DateTimeFormat(style = "S-") Date maxRequestedTime, Model uiModel) {
        uiModel.addAttribute("reloadrequests", ReloadRequest.findReloadRequestsByRequestedTimeBetween(minRequestedTime, maxRequestedTime).getResultList());
        addDateTimeFormatPatterns(uiModel);
        return "reloadrequests/list";
    }
    
    @RequestMapping(params = { "find=ByTransId", "form" }, method = RequestMethod.GET)
    public String ReloadRequestController.findReloadRequestsByTransIdForm(Model uiModel) {
        return "reloadrequests/findReloadRequestsByTransId";
    }
    
    @RequestMapping(params = "find=ByTransId", method = RequestMethod.GET)
    public String ReloadRequestController.findReloadRequestsByTransId(@RequestParam("transId") String transId, Model uiModel) {
        uiModel.addAttribute("reloadrequests", ReloadRequest.findReloadRequestsByTransId(transId).getResultList());
        return "reloadrequests/list";
    }
    
    @ModelAttribute("reloadrequests")
    public Collection<ReloadRequest> ReloadRequestController.populateReloadRequests() {
        return ReloadRequest.findAllReloadRequests();
    }
    
    void ReloadRequestController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("reloadRequest_minrequestedtime_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("reloadRequest_requestedtime_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("reloadRequest_maxrequestedtime_date_format", org.joda.time.format.DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    String ReloadRequestController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
