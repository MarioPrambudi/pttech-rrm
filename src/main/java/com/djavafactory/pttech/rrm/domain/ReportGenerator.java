package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.Constants;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rainpoh
 * Date: 5/5/11
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportGenerator {

    public static List getDetailsRequestReloadFrmCelcomReport()
    {
        System.out.println("------------------------ Report getlist method");
        String reportfee =  getReportFee();
        String reportCelComm = getReportCelComm();
        List listReloadRequest = ReloadRequest.findListReloadRequestsByRequestedTimeBetween(new Date(), new Date());
		Iterator it = listReloadRequest.iterator();
        List <Report>listReport = null;
        //Date curdate = new Date();

        while(it.hasNext())
		{
            System.out.println("------------------------ Report it.hasnext");
            Report report;

            try {

                report = new Report();
                System.out.println("------------------------ Report Report1" + report);
                ReloadRequest reloadrequest = (ReloadRequest)it.next();
                System.out.println("------------------------ Report reloadrequest" + reloadrequest);
                BeanUtils.copyProperties(reloadrequest, report);
                listReport.add(report);

               System.out.println("------------------------ Report Report2" + report);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


        }

        System.out.println("------------------------ Report listReport" + listReport);
        return listReport;
    }

    public static String getReportFee()
    {
            return Configuration.getReportConfigValue(Constants.REPORT_CONFIG_FEE);
    }

     public static String getReportCelComm()
    {
            return Configuration.getReportConfigValue(Constants.REPORT_CONFIG_CELCOMM);
    }
}
