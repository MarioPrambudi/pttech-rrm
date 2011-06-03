package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import com.djavafactory.pttech.rrm.util.DateUtil;
import com.djavafactory.pttech.rrm.ws.KeyResponse;
import epg.webservice.ObjectFactory;
import epg.webservice.ReloadRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.MessagingException;

import javax.xml.bind.JAXBElement;
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
        try {
            return new ReloadRequestMessage().fromJsonToMessage(content);
        } catch (Exception e) {
            throw new MessagingException(e.getMessage() + " - " + content, e);
        }
    }

    /**
     * Method to transform the message in JSON string format to ReloadResponseMessage
     *
     * @param content Message in JSON string format
     * @return ReloadResponseMessage object
     */
    public ReloadResponseMessage transformReloadResponse(String content) {
        try {
            return new ReloadResponseMessage().fromJsonToMessage(content);
        } catch (Exception e) {
            throw new MessagingException(e.getMessage() + " - " + content, e);
        }
    }

    /**
     * Method to transform the ReloadRequestMessage to a response string in JSON format.
     *
     * @param message ReloadRequestMessage object
     * @return Response JSON string
     */
    public String transformMessageToResponse(Object message) {
        ReloadRequestMessage requestMessage = (ReloadRequestMessage) message;
        return transformResponse(requestMessage, requestMessage.getStatusCode(), requestMessage.getStatusMsg());
    }

    public KeyResponse transformMessageToTngResponse(Object message) {
        ReloadRequestMessage requestMessage = (ReloadRequestMessage) message;
        return transformKeyResponse(requestMessage.getStatusCode(), requestMessage.getStatusMsg(), requestMessage.getTransId());
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

    /**
     * Method to transform the ReloadRequestMessage to TnG WS ReloadRequest format.
     *
     * @param message Object message object
     * @return Object
     */
    public JAXBElement<ReloadRequest> transformMessageToReloadReq(Object message) {
        ReloadRequest reloadReq = new ObjectFactory().createReloadRequest();
        if (message != null && message instanceof ReloadRequestMessage) {
            reloadReq.setAmount(String.valueOf(((ReloadRequestMessage) message).getAmount()));
            reloadReq.setMfgNo(String.valueOf(((ReloadRequestMessage) message).getMfgNo()));
            reloadReq.setRequestDateTime(DateUtil.getDateTime("ddMMyyyyHHmmss", new Date()));
            reloadReq.setServiceProviderId(((ReloadRequestMessage) message).getSpId());
            reloadReq.setTransactionCode(((ReloadRequestMessage) message).getTransCode());
            reloadReq.setTransactionId(((ReloadRequestMessage) message).getTransId());
        }
        return new ObjectFactory().createReloadRequest(reloadReq);
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

    private KeyResponse transformKeyResponse(String statusCode, String statusMsg, String transId) {
        KeyResponse keyResponse = new com.djavafactory.pttech.rrm.ws.ObjectFactory().createTngKeyResponse();
        keyResponse.setStatusCode(statusCode);
        keyResponse.setStatusMsg(statusMsg);
        keyResponse.setTransactionId(transId);

        logger.info("[transformKeyResponse - New KeyResponse object] >> " + keyResponse);
        return keyResponse;
    }
}
