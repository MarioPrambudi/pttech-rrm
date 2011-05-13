package com.djavafactory.pttech.rrm.service;


import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.ftp.session.DefaultFtpsSessionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BatchReportServiceManagerImpl implements BatchReportServiceManager {
    private static final Log logger = LogFactory.getLog(BatchReportServiceManagerImpl.class);

    private DefaultFtpsSessionFactory ftpsSessionFactory;
    private MessageChannel ftpsChannel;

    public BatchReportServiceManagerImpl() {
    }

    public BatchReportServiceManagerImpl(DefaultFtpsSessionFactory ftpsSessionFactory, MessageChannel sftpChannel) {
        this.ftpsSessionFactory = ftpsSessionFactory;
        this.ftpsChannel = sftpChannel;
    }

    public void sendCelcomBatchReport(String batchString) {
        List<Configuration> configurationList = Configuration.findByConfigKeyPrefix(Configuration.ConfigPrefix.CELCOM.getKey());

        for (Configuration configuration : configurationList) {
            if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.ip")) {
                ftpsSessionFactory.setHost(configuration.getConfigValue());
            } else if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.port")) {
                ftpsSessionFactory.setPort(Integer.valueOf(configuration.getConfigValue()));
            } else if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.username")) {
                ftpsSessionFactory.setUsername(configuration.getConfigValue());
            } else if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.password")) {
                ftpsSessionFactory.setPassword(configuration.getConfigValue());
            }
        }
        Message<String> message = MessageBuilder.withPayload(batchString).build();
        ftpsChannel.send(message);
    }

    public void getCelcomBatchReportDetail() {
        sendCelcomBatchReport("report string");  //TODO get Celcom Batch report detail at configured time and send to Celcom via FTPS
    }

    public void getTngBatchReportDetail() {
        //TODO get Celcom Batch report detail at configured time and send to TNG via WS
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void generateCelcomBatchReport() {
        //TODO generate celcom batch reports
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void generateTngBatchReport() {
        //TODO generate celcom batch reports
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void updateTimeoutReloadReq() {
        Configuration configuration = Configuration.findConfigurationByConfigKey(Constants.CONFIG_TNG_TIMEOUT);
        if (configuration != null && !"".equals(configuration.getConfigValue())) {
            Calendar timeOutDate = Calendar.getInstance();
            timeOutDate.setTimeInMillis(timeOutDate.getTimeInMillis() - Long.valueOf(configuration.getConfigValue()));

            List<ReloadRequest> reloadRequests = ReloadRequest.findReloadRequestsByParam(Constants.RELOAD_STATUS_NEW, null,
                    null, timeOutDate.getTime(), -1, -1, null).getResultList();
            for (ReloadRequest request : reloadRequests) {
                request.setStatus(Constants.RELOAD_STATUS_FAILED);
                request.setModifiedTime(new Date());
                request.merge();
            }
        }
    }
}
