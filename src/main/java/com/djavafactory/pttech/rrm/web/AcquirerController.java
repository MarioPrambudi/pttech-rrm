package com.djavafactory.pttech.rrm.web;

<<<<<<< HEAD

=======
>>>>>>> upstream/master
import com.djavafactory.pttech.rrm.domain.Acquirer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RooWebScaffold(path = "acquirers", formBackingObject = Acquirer.class)
@RequestMapping("/acquirers")
@Controller
public class AcquirerController {

    public static Boolean LDELETED_STATUS = true;
    private Date createdDate; //to hold the createdTime

    @Autowired
    private MessageSource messageSource;

    /**
    * To show the list of acquirer with paginate
    * @param page The page number
    * @param size The size of the display list for a page
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Acquirer> acquirerList = Acquirer.findAcquirersByParam(null, null, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList();
            uiModel.addAttribute("acquirers", acquirerList);
            float nrOfPages = (float) acquirerList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("acquirers", Acquirer.findAcquirersByParam(null, null, -1, -1).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }

    /**
    * To search acquirers by parameters
    * @param name The acquirer name
    * @param registrationNo The Acquirer registration no
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(value = "/findAcquirersByParam", method = RequestMethod.POST)
    public String findAcquirersByParam(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "registrationNo", required = false) String registrationNo, Model uiModel) {
        uiModel.addAttribute("acquirers", Acquirer.findAcquirersByParam(name, registrationNo, -1, -1).getResultList());
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }
	
<<<<<<< HEAD
	

=======
>>>>>>> upstream/master
    /**
    * To delete acquirer by updated deletedStatus to LDELETED_STATUS value
    * @param id The acquirer id
    * @param page Integer
    * @param size Integer
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
<<<<<<< HEAD

=======
>>>>>>> upstream/master
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Acquirer acquirer;
		acquirer = Acquirer.findAcquirer(id);
		acquirer.setDeleted(LDELETED_STATUS);
		uiModel.asMap().clear();
		acquirer.merge();
        return "redirect:/acquirers";
    }
	
    /**
    * insert new acquirer with createdTime and createdBy
    * @param acquirer the acquirer object
    * @param bindingResult BindingResult
    * @param uiModel Model
    * @param httpServletRequest HttpServletRequest
    * @exception none
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
        // Temporary static
        acquirer.setCreatedBy("System");
        acquirer.setCreatedTime(getCurrentDate());
        acquirer.persist();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }
	
	
    /**
    * get the current date
    * @param none
    * @exception none
    * @return Date the current date
    */
	@Transient
	public Date getCurrentDate(){
	    return new Date();
	}
	
    /**
    * update new acquirer with modifiedTime and modifiedBy
    * @param acquirer the acquirer object
    * @param bindingResult BindingResult
    * @param uiModel Model
    * @param httpServletRequest HttpServletRequest
    * @exception none
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
<<<<<<< HEAD
        // Temporary static
=======
        
        // ModifiedBy DEMO
>>>>>>> upstream/master
        acquirer.setCreatedTime(createdDate);
        acquirer.setModifiedBy("System");
        acquirer.setModifiedTime(getCurrentDate());
        acquirer.merge();
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }

<<<<<<< HEAD
	

    /**
  	* display update form and save the createdTime into createdDate
    * @param id The Acquirer id
=======
    /**
  	* display update form and save the createdTime into createdDate
    * @param id The Terminal id
>>>>>>> upstream/master
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
  	*/
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Acquirer objAcquirer;
        objAcquirer = Acquirer.findAcquirer(id);
        createdDate = objAcquirer.getCreatedTime();
        uiModel.addAttribute("acquirer", objAcquirer);
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/update";
    }

<<<<<<< HEAD

=======
>>>>>>> upstream/master
    /**
    * display date/time formatting get from resource bundle
    * @exception none
    * @return String the page path to redirect
    */
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("acquirer_createdtime_date_format", messageSource.getMessage("display_date_format", null, LocaleContextHolder.getLocale()));
        uiModel.addAttribute("acquirer_modifiedtime_date_format", messageSource.getMessage("display_date_format", null, LocaleContextHolder.getLocale()));
    }
}
