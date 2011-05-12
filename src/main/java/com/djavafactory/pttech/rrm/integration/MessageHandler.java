package com.djavafactory.pttech.rrm.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Spring Integration Service Activator class.
 *
 * @author Carine Leong
 */
public class MessageHandler {

    private static final Log logger = LogFactory.getLog(MessageHandler.class);

    /**
     * Method to process the message in the queue.rrm.reload.req and queue.rrm.reload.status queue from byte array to String.
     *
     * @param data Message in byte array
     * @return
     */
    public String reloadRequestHandler(byte[] data) {
        String message = new String(data);
        logger.info("[reloadRequestHandler - Received Reload Request Message] >> " + message);

        return message;
    }
}
