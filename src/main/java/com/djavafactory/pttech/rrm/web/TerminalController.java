package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Terminal;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "terminals", formBackingObject = Terminal.class)
@RequestMapping("/terminals")
@Controller
public class TerminalController {
}
