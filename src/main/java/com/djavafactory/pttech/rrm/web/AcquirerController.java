package com.djavafactory.pttech.rrm.web;


import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.City;
import com.djavafactory.pttech.rrm.domain.Terminal;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RooWebScaffold(path = "acquirers", formBackingObject = Acquirer.class)
@RequestMapping("/acquirers")
@Controller
public class AcquirerController extends BaseController {

    /**
     * validate for unique registration number
     *
     * @param regNo The registraion number
     * @return -1 = unique, 0 = duplicated, acquirerId = duplicated and deleted
     */
    @RequestMapping(value = "/isDuplicate/{regNo}", method = RequestMethod.GET)
    public
    @ResponseBody
    String isDuplicate(@PathVariable("regNo") String regNo) {
        List listAcquirer = Acquirer.findAllAcquirers();
        Iterator it = listAcquirer.iterator();
        while (it.hasNext()) {
            Acquirer acquirer;
            acquirer = (Acquirer) it.next();
            if (acquirer.getRegistrationNo().equals(regNo)) {

                if (acquirer.getDeleted()) {
                    return acquirer.getId().toString();
                }
                return "0";

            }
        }
        return "-1";
    }


    /**
     * To show the list of acquirer with paginate
     *
     * @param page    The page number
     * @param size    The size of the display list for a page
     * @param uiModel Model
     * @return String the page path to redirect
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Acquirer> acquirerList = Acquirer.findAcquirersByParam(null, null, false, "acquirer.name", (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo).getResultList();
            uiModel.addAttribute("acquirers", acquirerList);
            float nrOfPages = (float) Acquirer.totalAcquirers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("acquirers", Acquirer.findAcquirersByParam(null, null, false, "acquirer.name", -1, -1).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }

    /**
     * To search acquirers by parameters
     *
     * @param name           The acquirer name
     * @param registrationNo The Acquirer registration no
     * @param uiModel        Model
     * @return String the page path to redirect
     */
    @RequestMapping(value = "/findAcquirersByParam", method = {RequestMethod.POST, RequestMethod.GET})
    public String findAcquirersByParam(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "registrationNo", required = false) String registrationNo,
                                       @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Acquirer> acquirerList = Acquirer.findAcquirersByParam(name, registrationNo, false, "acquirer.name", (page == null ? 0 : (page.intValue() - 1) * sizeNo), sizeNo).getResultList();
            uiModel.addAttribute("acquirers", acquirerList);

            float nrOfPages = (float) Acquirer.totalAcquirersByParam(name, registrationNo, false) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("params", "&name=" + name + "&registrationNo=" + registrationNo);
        } else {
            uiModel.addAttribute("acquirers", Acquirer.findAcquirersByParam(name, registrationNo, false, "acquirer.name", -1, -1).getResultList());
        }

        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }

    /**
     * To delete acquirer by updated deletedStatus to LDELETED_STATUS value
     *
     * @param id      The acquirer id
     * @param page    Integer
     * @param size    Integer
     * @param uiModel Model
     * @return String the page path to redirect
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Acquirer acquirer;
        acquirer = Acquirer.findAcquirer(id);

        // Delete all terminal that belong to this acquirer
        Set terminalSet = new HashSet();
        terminalSet = acquirer.getTerminals();
        Iterator it = terminalSet.iterator();

        while (it.hasNext()) {
            //call terminal method to delete terminal
            Terminal terminal = (Terminal) it.next();
            Terminal.deleteTerminalForAcquirer(terminal);
        }

        uiModel.asMap().clear();
        acquirer.setDeleted(true);
        acquirer.setModifiedBy("System");
        acquirer.setModifiedTime(new Date());
        acquirer.merge();
        return "redirect:/acquirers";
    }

    /**
     * insert new acquirer with createdTime and createdBy
     *
     * @param acquirer           the acquirer object
     * @param bindingResult      BindingResult
     * @param uiModel            Model
     * @param httpServletRequest HttpServletRequest
     * @return String the page path to redirect
     */
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Acquirer acquirer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("acquirer", acquirer);
            addDateTimeFormatPatterns(uiModel);
            return "acquirers/create";
        }

        uiModel.asMap().clear();
        acquirer.setCreatedBy("System");  // TODO Temporary static
        acquirer.setCreatedTime(new Date());
        acquirer.persist();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }


    /**
     * update new acquirer with modifiedTime and modifiedBy
     *
     * @param acquirer           the acquirer object
     * @param bindingResult      BindingResult
     * @param uiModel            Model
     * @param httpServletRequest HttpServletRequest
     * @return String the page path to redirect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Acquirer acquirer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("acquirer", acquirer);
            addDateTimeFormatPatterns(uiModel);
            return "acquirers/update";
        }

        uiModel.asMap().clear();
        acquirer.setModifiedBy("System");  // Temporary Static  
        acquirer.setModifiedTime(new Date());
        acquirer.merge();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }

    /**
     * display update form and save the createdTime into createdDate
     *
     * @param id      The acquirer id
     * @param uiModel Model
     * @return String the page path to redirect
     */
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("acquirer", Acquirer.findAcquirer(id));
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/update";
    }

    /**
     * display date/time formatting get from resource bundle
     *
     * @param uiModel
     * @return String the page path to redirect
     */
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("acquirer_createdtime_date_format", getResourceText("display_date_format"));
        uiModel.addAttribute("acquirer_modifiedtime_date_format", getResourceText("display_date_format"));
    }

    /**
     * display drop down selection for all cities in create/update acquirer form
     *
     * @return String the page path to redirect
     */
    @ModelAttribute("citys")
    public java.util.Collection<City> populateCitys() {
        return City.findAllCitys();
    }
}
