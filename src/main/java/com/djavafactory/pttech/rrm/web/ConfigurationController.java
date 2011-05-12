package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.conf.DynamicScheduler;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.service.BatchReportServiceManager;
import com.djavafactory.pttech.rrm.service.JmxReloadContextManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BatchReportServiceManager batchReportServiceManager;

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

    @RequestMapping(value = "/keys/{prefix}/{id}", params = "form", method = RequestMethod.GET)
    public String updateFormWithKey(@PathVariable("prefix") String configKeyPrefix, @PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("configuration", Configuration.findConfiguration(id));
        return "configurations/update";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Configuration configuration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("configuration", configuration);
            return "configurations/create";
        }
        uiModel.asMap().clear();
        configuration.persist();
        resetScheduler(configuration);
        return "redirect:/configurations/" + encodeUrlPathSegment(configuration.getId().toString(), httpServletRequest);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Configuration configuration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("configuration", configuration);
            return "configurations/update";
        }
        uiModel.asMap().clear();
        configuration.merge();
        resetScheduler(configuration);
        return "redirect:/configurations/" + encodeUrlPathSegment(configuration.getId().toString(), httpServletRequest);
    }

    @Autowired
    private JmxReloadContextManager jmxReloadContextManager;

    private void resetScheduler(Configuration configuration) {
        if (StringUtils.equals(Constants.CONFIG_CEL_BATCH_SCHEDULE, configuration.getConfigKey())) {
            DynamicScheduler celcomScheduler = jmxReloadContextManager.getContext().getBean("celcomScheduler", DynamicScheduler.class);
            celcomScheduler.resetScheduler(configuration.getConfigValue());
        } else if (StringUtils.equals(Constants.CONFIG_TNG_BATCH_SCHEDULE, configuration.getConfigKey())) {
            DynamicScheduler tngScheduler = jmxReloadContextManager.getContext().getBean("tngScheduler", DynamicScheduler.class);
            tngScheduler.resetScheduler(configuration.getConfigValue());
        } else if (StringUtils.equals(Constants.CONFIG_CEL_LOCATION, configuration.getConfigKey()) ||
                StringUtils.equals(Constants.CONFIG_TNG_BATCHWS, configuration.getConfigKey())) {
            jmxReloadContextManager.reloadContext();
        }
    }
}
