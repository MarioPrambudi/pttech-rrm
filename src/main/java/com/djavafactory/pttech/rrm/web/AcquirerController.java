package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "acquirers", formBackingObject = Acquirer.class)
@RequestMapping("/acquirers")
@Controller
public class AcquirerController {
}
