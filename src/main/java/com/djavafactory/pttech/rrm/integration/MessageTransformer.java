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

    public String transformInvalidMessage(ReloadRequestMessage message) {
		return transformResponse(message, "99", "Invalid reload request message.");
	}

    public String transformTimeoutMessage(ReloadRequestMessage message) {
		return transformResponse(message, "98", "Message timeout.");
	}

    public String transformSuccessMessage(ReloadRequestMessage message) {
		return transformResponse(message, "00", "Success");
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
