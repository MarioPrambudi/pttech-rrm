package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * Spring Integration Transformer class.
 *
 * @author Carine Leong
 */
public class MessageTransformer {
    private static final Log logger = LogFactory.getLog(MessageTransformer.class);

    /**
     * Method to transform the message in JSON string format to ReloadRequestMessage
     *
     * @param content Message in JSON string format
     * @return ReloadRequestMessage object
     */
    public ReloadRequestMessage transformReloadRequest(String content) {
        return new ReloadRequestMessage().fromJsonToMessage(content);
    }

    /**
     * Method to transform the message in JSON string format to ReloadResponseMessage
     *
     * @param content Message in JSON string format
     * @return ReloadResponseMessage object
     */
    public ReloadResponseMessage transformReloadResponse(String content) {
        return new ReloadResponseMessage().fromJsonToMessage(content);
    }

    /**
     * Method to transform the ReloadRequestMessage to a response string in JSON format.
     *
     * @param message ReloadRequestMessage object
     * @return Response JSON string
     */
    public String transformInvalidMessage(ReloadRequestMessage message) {
        return transformResponse(message, "99", "Invalid reload request message.");   //TODO
    }

    /**
     * Method to transform the ReloadRequestMessage to a response string in JSON format.
     *
     * @param message ReloadRequestMessage object
     * @return Response JSON string
     */
    public String transformTimeoutMessage(ReloadRequestMessage message) {
        return transformResponse(message, "98", "Message timeout.");      //TODO
    }

    /**
     * Method to transform the ReloadRequestMessage to a response string in JSON format.
     *
     * @param message ReloadRequestMessage object
     * @return Response JSON string
     */
    public String transformSuccessMessage(ReloadRequestMessage message) {
        return transformResponse(message, "00", "Success");         //TODO
    }

    /**
     * Method to transform the ReloadRequestMessage to a response string in JSON format.
     *
     * @param message ReloadRequestMessage object
     * @return Response JSON string
     */
    public String transformInvalidStatusMessage(ReloadRequestMessage message) {
        return transformResponse(message, "97", "Unable to proceed the request due to the record is not found or the record is not in a correct status.");         //TODO
    }

    /**
     * Method to transform the ReloadRequestMessage or ReloadResponseMessage to JSON format.
     *
     * @param message Object message object
     * @return JSON string
     */
    public String transformMessageToJson(Object message) {
        if (message != null && message instanceof ReloadRequestMessage) {
            ((ReloadRequestMessage) message).setRequestTime(new Date());
            logger.info("[transformRtmReloadRequest - New json string] >> " + ((ReloadRequestMessage) message).toJsonString());
            return ((ReloadRequestMessage) message).toJsonString();
        } else if (message != null && message instanceof ReloadResponseMessage) {
            ((ReloadResponseMessage) message).setResponseTime(new Date());
            logger.info("[transformRtmReloadRequest - New json string] >> " + ((ReloadResponseMessage) message).toJsonString());
            return ((ReloadResponseMessage) message).toJsonString();
        }
        return null;
    }

    private String transformResponse(ReloadRequestMessage message, String statusCode, String statusMsg) {
        ReloadResponseMessage reloadResponseMessage = new ReloadResponseMessage();
        reloadResponseMessage.setTransId(message.getTransId());
        reloadResponseMessage.setResponseTime(new Date());
        reloadResponseMessage.setStatusCode(statusCode);
        reloadResponseMessage.setStatusMsg(statusMsg);

        logger.info("[transformResponse - New ReloadResponseMessage json string] >> " + reloadResponseMessage);

        return reloadResponseMessage.toJsonString();
    }
}
