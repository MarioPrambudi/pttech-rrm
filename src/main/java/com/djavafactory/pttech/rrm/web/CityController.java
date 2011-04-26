package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.City;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "citys", formBackingObject = City.class)
@RequestMapping("/citys")
@Controller
public class CityController {

    @RequestMapping(value = "/getCitiesByState/{id}", method = RequestMethod.GET)
    public @ResponseBody String getCitiesByState(@PathVariable("id") Long id) {
        return City.findCitiesByState(id);
    }
}
