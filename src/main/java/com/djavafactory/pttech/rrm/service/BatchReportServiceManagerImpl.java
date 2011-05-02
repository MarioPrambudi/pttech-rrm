package com.djavafactory.pttech.rrm.service;


import com.djavafactory.pttech.rrm.domain.Configuration;
import org.apache.commons.lang.StringUtils;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.support.MessageBuilder;

import java.util.List;

public class BatchReportServiceManagerImpl implements BatchReportServiceManager {

    private DefaultSftpSessionFactory sftpSessionFactory;
    private MessageChannel sftpChannel;

    public BatchReportServiceManagerImpl(DefaultSftpSessionFactory sftpSessionFactory, MessageChannel sftpChannel) {
        this.sftpSessionFactory = sftpSessionFactory;
        this.sftpChannel = sftpChannel;
    }

    @Override
    public void sendCelcomBatchReport(String batchString) {
        List<Configuration> configurationList = Configuration.findByConfigKeyPrefix(Configuration.ConfigPrefix.CELCOM.getKey());

        for (Configuration configuration : configurationList) {
            if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.ip")) {
                sftpSessionFactory.setHost(configuration.getConfigValue());
            } else if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.port")) {
                sftpSessionFactory.setPort(Integer.valueOf(configuration.getConfigValue()));
            } else if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.username")) {
                sftpSessionFactory.setUser(configuration.getConfigValue());
            } else if (StringUtils.equalsIgnoreCase(configuration.getConfigKey(), "CEL.password")) {
                sftpSessionFactory.setPassword(configuration.getConfigValue());
            }
        }
        Message<String> message = MessageBuilder.withPayload(batchString).build();
        sftpChannel.send(message);
    }
}
