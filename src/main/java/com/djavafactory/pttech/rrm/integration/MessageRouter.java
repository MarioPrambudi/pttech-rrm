package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Spring Integration Router class to route the messages to appropriate channel.
 *
 * @author Carine Leong
 */
public class MessageRouter {
    private static final Log logger = LogFactory.getLog(MessageRouter.class);

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
     * Method to route the ReloadResponseMessage to the appropriate channel based on message type.
     *
     * @param message ReloadResponseMessage object
     * @return Channel in string
     */
    public String routeReloadResponse(ReloadResponseMessage message) {
        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getTransId()).getResultList();
        if (reloadRecord != null && !reloadRecord.isEmpty()) {
            ReloadRequest reloadRequest = reloadRecord.get(0);
            if (StringUtils.equalsIgnoreCase(reloadRequest.getStatus(), Constants.RELOAD_STATUS_NEW)) {
                return "tngResponsePersistChannel";
            } else if (StringUtils.equalsIgnoreCase(reloadRequest.getStatus(), Constants.RELOAD_STATUS_PENDING)) {
                return null; //TODO for response returned by RTM for manual cancellation response
            }
        }
        return null;
    }
}
