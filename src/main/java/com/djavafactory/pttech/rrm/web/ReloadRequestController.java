package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "reloadrequests", formBackingObject = ReloadRequest.class)
@RequestMapping("/reloadrequests")
@Controller
public class ReloadRequestController {
}
