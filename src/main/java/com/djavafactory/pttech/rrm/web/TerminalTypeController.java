package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.TerminalType;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RooWebScaffold(path = "terminaltypes", formBackingObject = TerminalType.class)
@RequestMapping("/terminaltypes")
@Controller
public class TerminalTypeController {
	
	public static Boolean LDELETED_STATUS = true;

    /**
    * To show the list of terminal type with paginate
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

            List<TerminalType> terminalTypeList = new TerminalType().findTerminalTypesByParam(null, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList();
            uiModel.addAttribute("terminaltypes", terminalTypeList);
            float nrOfPages = (float) terminalTypeList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("terminaltypes", new TerminalType().findTerminalTypesByParam(null, -1, -1).getResultList());
        }
        return "terminaltypes/list";
    }

    /**
    * To search terminal types by parameters
    * @param name The search text
    * @param uiModel Model
    * @exception none
    * @return String the page path to redirect
    */
    @RequestMapping(value = "/findTerminalTypesByParam", method = RequestMethod.POST)
    public String findTerminalTypesByParam(@RequestParam(value = "searchText", required = false) String searchText, Model uiModel) {
        uiModel.addAttribute("terminaltypes", new TerminalType().findTerminalTypesByParam(searchText, -1, -1).getResultList());
        return "terminaltypes/list";
    }

 /**
   * To delete terminalType by updated deleted to LDELETED_STATUS value
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
		terminaltype.setDeleted(LDELETED_STATUS);
        uiModel.asMap().clear();
        terminaltype.merge();
        return "redirect:/terminaltypes";
    }
	
}
