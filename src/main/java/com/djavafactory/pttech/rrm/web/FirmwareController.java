package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Firmware;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@RooWebScaffold(path = "firmwares", formBackingObject = Firmware.class)

@RequestMapping("/firmwares")
@Controller
public class FirmwareController {
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
    /**
     * insert new firmware with createdTime and createdBy
     * @param firmware          Firmware
     * @param bindingResult      BindingResult
     * @param uiModel            Model
     * @param httpServletRequest HttpServletRequest
     * @return String the page path to redirect
     */
	@RequestMapping(value="savefirmware",  method = RequestMethod.POST)
    public String createFirmware(@Valid Firmware firmware, BindingResult result, Model model,
    					 	@RequestParam("firmwareFile") MultipartFile firmwareFile, HttpServletRequest request) {    

        if (result.hasErrors()) {
            model.addAttribute("firmware", firmware);
            return "firmwares/create";
        }
        firmware.setCreatedBy("System");  // TODO Temporary static
        firmware.setCreatedTime(new Date());
        firmware.persist();     
        return "redirect:/firmwares/" + encodeUrlPathSegment(firmware.getId().toString(), request);
    }
	
	/**
     * update new firmware with modifiedTime, modifiedBy and active to false
     * @param firmware           Firmware
     * @param bindingResult      BindingResult
     * @param uiModel            Model
     * @param httpServletRequest HttpServletRequest
     * @return String the page path to redirect
     */
	@RequestMapping(value="updatefirmware", method = RequestMethod.POST)
    public String updateFirmware(@Valid Firmware firmware, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("firmware", firmware);
			addDateTimeFormatPatterns(uiModel);
           return "firmwares/update";
        }
		uiModel.asMap().clear();
		// backup old firmware
		firmware.setActive(false);
		firmware.setModifiedBy("System");  // Temporary Static  
		firmware.setModifiedTime(new Date());
	    firmware.merge();  
	    saveUpdateFirmware(firmware);
	     
        return "redirect:/firmwares/" + encodeUrlPathSegment(firmware.getId().toString(), httpServletRequest);
    }
	
	/**
     * To save updated firmware 
     * @param firmware Firmware
     * @return Long firmware id
     */
	public Long saveUpdateFirmware(Firmware firmware){		
	    firmware.setCreatedBy("System");  // TODO Temporary static
        firmware.setCreatedTime(new Date());
        firmware.setActive(true);
        firmware.setId(null);
        firmware.persist();     
        return firmware.getId();
	}

	
	 /**
     * To show the list of firmware with paginate
     * @param page    The page number
     * @param size    The size of the display list for a page
     * @param uiModel Model
     * @return String the page path to redirect
     */ 
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("firmwares", Firmware.findFirmwaresByParam(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList());
            float nrOfPages = (float) Firmware.countFirmwaresByParam() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("firmwares", Firmware.findFirmwaresByParam(-1, -1).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "firmwares/list";
    }
}
