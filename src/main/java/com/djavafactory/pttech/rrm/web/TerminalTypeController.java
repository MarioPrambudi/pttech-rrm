package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.TerminalType;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "terminaltypes", formBackingObject = TerminalType.class)
@RequestMapping("/terminaltypes")
@Controller
public class TerminalTypeController {

 /**
   * To delete terminalType by updated deletedStatus to "d"
   * @param id The terminalType id
   * @param page Integer
   * @param size Integer
   * @param uiModel Model
   * @exception none 
   * @return String the page path to redirect
   */ 
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        TerminalType terminaltype;
        terminaltype = TerminalType.findTerminalType(id);
		terminaltype.setDeletedStatus("d");
        uiModel.asMap().clear();
        return "redirect:/terminaltypes";
    }
	
}
