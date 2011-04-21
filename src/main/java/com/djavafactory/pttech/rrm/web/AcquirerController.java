package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RooWebScaffold(path = "acquirers", formBackingObject = Acquirer.class)
@RequestMapping("/acquirers")
@Controller
public class AcquirerController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();

            List<Acquirer> acquirerList = new Acquirer().findAcquirersByParam(null, null, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo).getResultList();
            uiModel.addAttribute("acquirers", acquirerList);
            float nrOfPages = (float) acquirerList.size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("acquirers", new Acquirer().findAcquirersByParam(null, null, -1, -1).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }

    @RequestMapping(value = "/findAcquirersByParam", method = RequestMethod.POST)
    public String findAcquirersByParam(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "registrationNo", required = false) String registrationNo, Model uiModel) {
        uiModel.addAttribute("acquirers", new Acquirer().findAcquirersByParam(name, registrationNo, -1, -1).getResultList());
        addDateTimeFormatPatterns(uiModel);
        return "acquirers/list";
    }
}
