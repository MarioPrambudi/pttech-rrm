package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.exception.RrmStatusCode;
import flexjson.JSONDeserializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;

import java.util.HashMap;

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

    /**
     * Method to process the error message in errorChannel.
     *
     * @param e Exception
     * @return
     */
    public ReloadRequestMessage reloadRequestErrorHandler(Exception e) {
        ReloadRequestMessage requestMessage = null;
        if (e instanceof MessagingException) {
            Message<?> failedMessage = ((MessagingException) e).getFailedMessage();
            if (failedMessage != null) {
                if (failedMessage.getPayload() instanceof ReloadRequestMessage) {
                    requestMessage = (ReloadRequestMessage) failedMessage.getPayload();
                } else if (failedMessage.getPayload() instanceof String) {
                    requestMessage = new ReloadRequestMessage();
                    try {
                        HashMap hashMap = new JSONDeserializer<HashMap>().deserialize(String.valueOf(failedMessage.getPayload()));
                        requestMessage.setTransId(String.valueOf(hashMap.get("transId")));
                    } catch (ClassCastException ex) {
                        logger.info("[transformStringToReloadRequest] >> Invalid message [" + failedMessage.getPayload() + "]. Ignore!!");
                    }
                }
                requestMessage.setMsgType(String.valueOf(failedMessage.getHeaders().get("req")));
                requestMessage.setStatusCode(RrmStatusCode.STS_GENERALRRMERROR.getCode());
                requestMessage.setStatusMsg(e.getMessage());
            }
        }
        return requestMessage;
    }
}
