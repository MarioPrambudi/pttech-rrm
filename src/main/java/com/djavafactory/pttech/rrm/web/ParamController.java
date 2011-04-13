package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Param;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "params", formBackingObject = Param.class)
@RequestMapping("/params")
@Controller
public class ParamController {
}
