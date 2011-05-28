package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Spring Integration Router class to route the messages to appropriate channel.
 *
 * @author Carine Leong
 */
public class MessageRouter {
    private static final Log logger = LogFactory.getLog(MessageRouter.class);
    private static final String rmiReq = "rmi";

    /**
     * Method to route the ReloadRequestMessage to the appropriate channel based on message type.
     *
     * @param message ReloadRequestMessage object
     * @return Channel in string
     */
    public String routeReloadRequest(ReloadRequestMessage message) {
        return (StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.RELOAD_REQUEST_NEW) && (message.getEncryptedMsg() != null && !StringUtils.equalsIgnoreCase(message.getEncryptedMsg(), "")))
                ? "tngKeyInboundChannel"
                : ((StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.RELOAD_REQUEST_NEW) && (message.getEncryptedMsg() == null || StringUtils.equalsIgnoreCase(message.getEncryptedMsg(), "")))
                ? "newReloadReqPersistChannel" : "rtmReloadReqPersistChannel");
        //TODO add manual cancellation router in phase 2
    }

    /**
     * Method to route the Error Message to the appropriate channel based on message type.
     *
     * @param message object
     * @return Channel in string
     */
    public String routeErrorRequest(ReloadRequestMessage message) {
        return (message.getMsgType() != null) ? ((StringUtils.equalsIgnoreCase(rmiReq, message.getMsgType())) ? "rmiErrorChannel" : "rtmErrorChannel") : null;
    }
}
