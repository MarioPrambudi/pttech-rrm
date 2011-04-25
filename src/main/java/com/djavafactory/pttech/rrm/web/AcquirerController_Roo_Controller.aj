// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Firmware;
import com.djavafactory.pttech.rrm.domain.Province;
import com.djavafactory.pttech.rrm.domain.Terminal;
import java.io.UnsupportedEncodingException;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect AcquirerController_Roo_Controller {
    
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
    
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    @RequestMapping(method = RequestMethod.GET)
    public String AcquirerController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("acquirers", Acquirer.findAcquirerEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Acquirer.countAcquirers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("acquirers", Acquirer.findAllAcquirers());
        }
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }
    
=======
>>>>>>> upstream/master
=======
>>>>>>> upstream/master
=======
>>>>>>> b8dda757f807362c586ef4a3035343fd2c2f1a06
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
