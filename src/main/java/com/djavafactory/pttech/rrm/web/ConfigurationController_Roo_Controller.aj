// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Configuration;
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

privileged aspect ConfigurationController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String ConfigurationController.create(@Valid Configuration configuration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("configuration", configuration);
            return "configurations/create";
        }
        uiModel.asMap().clear();
        configuration.persist();
        return "redirect:/configurations/" + encodeUrlPathSegment(configuration.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ConfigurationController.createForm(Model uiModel) {
        uiModel.addAttribute("configuration", new Configuration());
        return "configurations/create";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String ConfigurationController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("configurations", Configuration.findConfigurationEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Configuration.countConfigurations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("configurations", Configuration.findAllConfigurations());
        }
        return "configurations/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ConfigurationController.update(@Valid Configuration configuration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("configuration", configuration);
            return "configurations/update";
        }
        uiModel.asMap().clear();
        configuration.merge();
        return "redirect:/configurations/" + encodeUrlPathSegment(configuration.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ConfigurationController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("configuration", Configuration.findConfiguration(id));
        return "configurations/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ConfigurationController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Configuration.findConfiguration(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/configurations";
    }
    
    @RequestMapping(params = { "find=ByConfigKeyLike", "form" }, method = RequestMethod.GET)
    public String ConfigurationController.findConfigurationsByConfigKeyLikeForm(Model uiModel) {
        return "configurations/findConfigurationsByConfigKeyLike";
    }
    
    @RequestMapping(params = "find=ByConfigKeyLike", method = RequestMethod.GET)
    public String ConfigurationController.findConfigurationsByConfigKeyLike(@RequestParam("configKey") String configKey, Model uiModel) {
        uiModel.addAttribute("configurations", Configuration.findConfigurationsByConfigKeyLike(configKey).getResultList());
        return "configurations/list";
    }
    
    @ModelAttribute("configurations")
    public Collection<Configuration> ConfigurationController.populateConfigurations() {
        return Configuration.findAllConfigurations();
    }
    
    String ConfigurationController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
