package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Configuration;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "configurations", formBackingObject = Configuration.class)
@RequestMapping("/configurations")
@Controller
public class ConfigurationController {
}
