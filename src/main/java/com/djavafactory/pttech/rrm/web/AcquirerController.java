package com.djavafactory.pttech.rrm.web;


import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RooWebScaffold(path = "acquirers", formBackingObject = Acquirer.class)
@RequestMapping("/acquirers")
@Controller
public class AcquirerController extends BaseController {

    private Date createdDate; //to hold the createdTime   todo from blake: will have trouble in a multithreaded environment
    /**
    * To show the list of acquirer with paginate
    * @param page The page number
    * @param size The size of the display list for a page
    * @param uiModel Model
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
    * @return String the page path to redirect
    */
    @RequestMapping(value = "/findAcquirersByParam", method = RequestMethod.POST)
    public String findAcquirersByParam(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "registrationNo", required = false) String registrationNo, Model uiModel) {
        uiModel.addAttribute("acquirers", Acquirer.findAcquirersByParam(name, registrationNo, -1, -1).getResultList());
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }
	
    /**
    * To delete acquirer by updated deletedStatus to LDELETED_STATUS value
    * @param id The acquirer id
    * @param page Integer
    * @param size Integer
    * @param uiModel Model
    * @return String the page path to redirect
    */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Acquirer acquirer;
		acquirer = Acquirer.findAcquirer(id);
		// Delete all terminal that belong to this acquirer
		Set terminalSet= new HashSet();
		terminalSet = acquirer.getTerminals();
		Iterator it = terminalSet.iterator();

		while(it.hasNext())
		{
			Terminal terminal;
			terminal = (Terminal)it.next();
			terminal.setStatus(Constants.TERMINAL_STATUS_DELETED);
			terminal.merge();

		}
		acquirer.setDeleted(true);
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
        acquirer.setCreatedBy("System");  // Temporary static
        acquirer.setCreatedTime(new Date());
        
        try
        {
        	acquirer.persist();
        }
        catch (DataAccessException e)
        {
        	//1. prompt user msg, enable or disable
        	//2. disable - redirect a new form
        	//3. enable - findacquirerbyregistrationno and reload in update form
        	
        }
        
        return "redirect:/acquirers/" + encodeUrlPathSegment(acquirer.getId().toString(), httpServletRequest);
    }


    /**
    * update new acquirer with modifiedTime and modifiedBy
    * @param acquirer the acquirer object
    * @param bindingResult BindingResult
    * @param uiModel Model
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
    * @param id The acquirer id
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
    * @param uiModel
    * @return String the page path to redirect
    */
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("acquirer_createdtime_date_format", getResourceText("display_date_format"));
        uiModel.addAttribute("acquirer_modifiedtime_date_format", getResourceText("display_date_format"));
    }
}
