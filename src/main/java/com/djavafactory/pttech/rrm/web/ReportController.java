package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/reports")
@Controller
public class ReportController {
    @RequestMapping(method = RequestMethod.GET)
    public String getReportList(@RequestParam(value = "type") String type, Model uiModel) {
        List<Configuration> configList = null;
        if(StringUtils.endsWithIgnoreCase(type, Constants.REPORT_TYPE_CELCOM)) {
            configList = Configuration.findConfigurationsByConfigKeyLike("report.celcom").getResultList(); //todo Use Enum or ?
        } else if(StringUtils.endsWithIgnoreCase(type, Constants.REPORT_TYPE_TNG)) {
            configList = Configuration.findConfigurationsByConfigKeyLike("report.tng").getResultList(); //todo Use Enum or ?
        }
        if(configList != null && !configList.isEmpty()) {
            for(int i = 0; i < configList.size(); i++) {
                Configuration config = configList.get(i);
                if(config.getConfigKey().lastIndexOf(".") > -1) {
                    config.setConfigKey(config.getConfigKey().substring(config.getConfigKey().lastIndexOf(".")+1));
                }
            }
        }
        uiModel.addAttribute("reports", configList);
        return "reports/reportList";
    }
}
