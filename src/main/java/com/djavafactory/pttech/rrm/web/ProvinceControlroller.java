package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Province;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "provinces", formBackingObject = Province.class)
@RequestMapping("/provinces")
@Controller
public class ProvinceControlroller {
}
