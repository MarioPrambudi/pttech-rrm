package com.djavafactory.pttech.rrm.util;


import org.springframework.integration.Message;
import org.springframework.integration.file.FileNameGenerator;

import java.text.SimpleDateFormat;
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
        return new StringBuilder().append(new SimpleDateFormat("yyyyMMdd").format(new Date())).append("_").append(message.getPayload().toString().substring(0, 10)).append(".").append(fileExtension).toString();
    }
}
