// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Firmware;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

privileged aspect FirmwareController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String FirmwareController.create(@Valid Firmware firmware, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("firmware", firmware);
            return "firmwares/create";
        }
        uiModel.asMap().clear();
        firmware.persist();
        return "redirect:/firmwares/" + encodeUrlPathSegment(firmware.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String FirmwareController.createForm(Model uiModel) {
        uiModel.addAttribute("firmware", new Firmware());
        List dependencies = new ArrayList();
        if (Acquirer.countAcquirers() == 0) {
            dependencies.add(new String[]{"acquirer", "acquirers"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "firmwares/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String FirmwareController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("firmware", Firmware.findFirmware(id));
        uiModel.addAttribute("itemId", id);
        return "firmwares/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String FirmwareController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("firmwares", Firmware.findFirmwareEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Firmware.countFirmwares() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("firmwares", Firmware.findAllFirmwares());
        }
        return "firmwares/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String FirmwareController.update(@Valid Firmware firmware, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("firmware", firmware);
            return "firmwares/update";
        }
        uiModel.asMap().clear();
        firmware.merge();
        return "redirect:/firmwares/" + encodeUrlPathSegment(firmware.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String FirmwareController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("firmware", Firmware.findFirmware(id));
        return "firmwares/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String FirmwareController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Firmware.findFirmware(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/firmwares";
    }
    
    @ModelAttribute("acquirers")
    public Collection<Acquirer> FirmwareController.populateAcquirers() {
        return Acquirer.findAllAcquirers();
    }
    
    @ModelAttribute("firmwares")
    public Collection<Firmware> FirmwareController.populateFirmwares() {
        return Firmware.findAllFirmwares();
    }
    
    String FirmwareController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
