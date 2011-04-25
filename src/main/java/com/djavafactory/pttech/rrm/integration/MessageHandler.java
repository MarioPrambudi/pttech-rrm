package com.djavafactory.pttech.rrm.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageHandler {

    private static final Log logger = LogFactory.getLog(MessageHandler.class);

	public String reloadRequestHandler(byte[] data) {
        String message = new String(data);
		logger.info("[reloadRequestHandler - Received Reload Request Message] >> " + message);

        return message;
	}

}
