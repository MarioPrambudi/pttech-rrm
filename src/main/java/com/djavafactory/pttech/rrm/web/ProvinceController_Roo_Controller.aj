// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.City;
import com.djavafactory.pttech.rrm.domain.Province;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
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

privileged aspect ProvinceController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String ProvinceController.create(@Valid Province province, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("province", province);
            return "provinces/create";
        }
        uiModel.asMap().clear();
        province.persist();
        return "redirect:/provinces/" + encodeUrlPathSegment(province.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ProvinceController.createForm(Model uiModel) {
        uiModel.addAttribute("province", new Province());
        return "provinces/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ProvinceController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("province", Province.findProvince(id));
        uiModel.addAttribute("itemId", id);
        return "provinces/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String ProvinceController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("provinces", Province.findProvinceEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Province.countProvinces() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("provinces", Province.findAllProvinces());
        }
        return "provinces/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ProvinceController.update(@Valid Province province, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("province", province);
            return "provinces/update";
        }
        uiModel.asMap().clear();
        province.merge();
        return "redirect:/provinces/" + encodeUrlPathSegment(province.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ProvinceController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("province", Province.findProvince(id));
        return "provinces/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ProvinceController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Province.findProvince(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/provinces";
    }
    
    @ModelAttribute("citys")
    public Collection<City> ProvinceController.populateCitys() {
        return City.findAllCitys();
    }
    
    @ModelAttribute("provinces")
    public java.util.Collection<Province> ProvinceController.populateProvinces() {
        return Province.findAllProvinces();
    }
    
    String ProvinceController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
