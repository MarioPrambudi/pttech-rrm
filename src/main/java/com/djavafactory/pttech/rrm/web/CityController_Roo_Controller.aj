// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.City;
import com.djavafactory.pttech.rrm.domain.Province;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect CityController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String CityController.create(@Valid City city, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("city", city);
            return "citys/create";
        }
        uiModel.asMap().clear();
        city.persist();
        return "redirect:/citys/" + encodeUrlPathSegment(city.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String CityController.createForm(Model uiModel) {
        uiModel.addAttribute("city", new City());
        List dependencies = new ArrayList();
        if (Province.countProvinces() == 0) {
            dependencies.add(new String[]{"province", "provinces"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "citys/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String CityController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("city", City.findCity(id));
        uiModel.addAttribute("itemId", id);
        return "citys/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String CityController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("citys", City.findCityEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) City.countCitys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("citys", City.findAllCitys());
        }
        return "citys/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String CityController.update(@Valid City city, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("city", city);
            return "citys/update";
        }
        uiModel.asMap().clear();
        city.merge();
        return "redirect:/citys/" + encodeUrlPathSegment(city.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String CityController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("city", City.findCity(id));
        return "citys/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String CityController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        City.findCity(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/citys";
    }
    
    @ModelAttribute("citys")
    public Collection<City> CityController.populateCitys() {
        return City.findAllCitys();
    }
    
    @ModelAttribute("provinces")
    public Collection<Province> CityController.populateProvinces() {
        return Province.findAllProvinces();
    }
    
    String CityController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
