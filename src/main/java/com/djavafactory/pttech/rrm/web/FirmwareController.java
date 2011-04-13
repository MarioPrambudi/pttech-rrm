package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Firmware;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "firmwares", formBackingObject = Firmware.class)
@RequestMapping("/firmwares")
@Controller
public class FirmwareController {
}
