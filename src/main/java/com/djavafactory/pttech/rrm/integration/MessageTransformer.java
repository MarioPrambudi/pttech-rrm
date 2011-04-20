package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class MessageTransformer {
    private static final Log logger = LogFactory.getLog(MessageTransformer.class);

    public ReloadRequestMessage transformReloadRequest(String content) {
        return new ReloadRequestMessage().fromJsonToMessage(content);
    }

    public ReloadResponseMessage transformReloadResponse(String content) {
        return new ReloadResponseMessage().fromJsonToMessage(content);
    }

    public String transformRejectedResponse(ReloadRequestMessage message) {
        ReloadResponseMessage reloadResponseMessage = new ReloadResponseMessage();
        reloadResponseMessage.setTransId(message.getTransId());
        reloadResponseMessage.setResponseTime(new Date());
        //TODO customize status code and message
        reloadResponseMessage.setStatusCode("99");
        reloadResponseMessage.setStatusMsg("Invalid reload request message.");

        logger.info("[transformRejectedResponse - New ReloadResponseMessage json string] >> " + reloadResponseMessage);

		return reloadResponseMessage.toJsonString();
	}

    public String transformMessageToJson(Object message) {
        if(message != null && message instanceof ReloadRequestMessage) {
            ((ReloadRequestMessage)message).setRequestTime(new Date());
            logger.info("[transformRtmReloadRequest - New json string] >> " + ((ReloadRequestMessage)message).toJsonString());
            return ((ReloadRequestMessage)message).toJsonString();
        } else if (message != null && message instanceof ReloadResponseMessage) {
            ((ReloadResponseMessage)message).setResponseTime(new Date());
            logger.info("[transformRtmReloadRequest - New json string] >> " + ((ReloadResponseMessage)message).toJsonString());
            return ((ReloadResponseMessage)message).toJsonString();
        }
        return null;
	}
}
