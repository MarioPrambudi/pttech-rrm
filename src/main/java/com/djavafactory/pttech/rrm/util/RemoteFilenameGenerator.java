package com.djavafactory.pttech.rrm.util;


import org.springframework.integration.Message;
import org.springframework.integration.file.FileNameGenerator;

import java.util.Date;

public class RemoteFilenameGenerator implements FileNameGenerator {
    private String fileExtension;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String generateFileName(Message<?> message) {
        return new StringBuilder().append(DateUtil.convertDateToString(new Date(), "yyyyMMdd")).append("_").append(message.getPayload().toString().substring(0, 10)).append(".").append(fileExtension).toString();
    }
}
