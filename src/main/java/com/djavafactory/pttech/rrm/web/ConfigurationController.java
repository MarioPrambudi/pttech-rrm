package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Configuration;
import com.google.gwt.place.shared.Prefix;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RooWebScaffold(path = "configurations", formBackingObject = Configuration.class)
@RequestMapping("/configurations")
@Controller
// todo when rendering configKey, get text from i18n
public class ConfigurationController {
    @RequestMapping(value = "/keys/{prefix}", method = RequestMethod.GET)
    public String keys(@PathVariable("prefix") String configKeyPrefix, Model uiModel) {
        uiModel.addAttribute("configurations", Configuration.findByConfigKeyPrefix(configKeyPrefix));
        uiModel.addAttribute("itemId", configKeyPrefix);
        return "configurations/keys";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        Configuration configurtion = Configuration.findConfiguration(id);
        uiModel.addAttribute("configuration", configurtion);
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("configKeyPrefix", configurtion.getConfigPrefix().getKey());
        return "configurations/show";
    }
}
