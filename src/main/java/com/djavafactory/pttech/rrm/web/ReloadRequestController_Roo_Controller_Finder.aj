// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import java.lang.String;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect ReloadRequestController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=ByRequestedTimeBetween", "form" }, method = RequestMethod.GET)
    public String ReloadRequestController.findReloadRequestsByRequestedTimeBetweenForm(Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        return "reloadrequests/findReloadRequestsByRequestedTimeBetween";
    }
    
    @RequestMapping(params = "find=ByRequestedTimeBetween", method = RequestMethod.GET)
    public String ReloadRequestController.findReloadRequestsByRequestedTimeBetween(@RequestParam("minRequestedTime") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date minRequestedTime, @RequestParam("maxRequestedTime") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date maxRequestedTime, Model uiModel) {
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
    
}
