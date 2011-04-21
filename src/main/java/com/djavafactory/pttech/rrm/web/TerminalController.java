package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Terminal;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RooWebScaffold(path = "terminals", formBackingObject = Terminal.class)
@RequestMapping("/terminals")
@Controller
public class TerminalController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Terminal> terminalList = new Terminal().findTerminalsByParam(null, null, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList();
            uiModel.addAttribute("terminals", terminalList);
            float nrOfPages = (float) terminalList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("terminals", new Terminal().findTerminalsByParam(null, null, -1, -1).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

    @RequestMapping(value = "/findTerminalsByParam", method = RequestMethod.POST)
    public String findTerminalsByParam(@RequestParam(value = "terminalId", required = false) String terminalId, @RequestParam(value = "status", required = false) String status, Model uiModel) {
        uiModel.addAttribute("terminals", new Terminal().findTerminalsByParam(terminalId, status, -1, -1).getResultList());
        addDateTimeFormatPatterns(uiModel);
        return "terminals/list";
    }

}
