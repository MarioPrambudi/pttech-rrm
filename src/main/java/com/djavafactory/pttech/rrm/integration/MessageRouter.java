package com.djavafactory.pttech.rrm.integration;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


public class MessageRouter {
    private static final Log logger = LogFactory.getLog(MessageRouter.class);

    public String routeReloadRequest(ReloadRequestMessage message) {
        if(StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.newReloadRequest)) {
            return (message.getEncryptedMsg() == null || StringUtils.equalsIgnoreCase(message.getEncryptedMsg(), "")) ? "newReloadReqPersistChannel" : "tngKeyInboundChannel";
        } else if(StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.failedReloadRequest)) {
            return "failedReloadReqPersistChannel";
        } else if(StringUtils.equalsIgnoreCase(message.getMsgType(), Constants.expiredReloadRequest)) {
            return "expiredReloadReqPersistChannel";
        } else {
            return "successReloadReqPersistChannel";
        }
    }

    public String routeReloadResponse(ReloadResponseMessage message) {
        List<ReloadRequest> reloadRecord = new ReloadRequest().findReloadRequestsByTransId(message.getTransId()).getResultList();
        if(reloadRecord != null && !reloadRecord.isEmpty()) {
            ReloadRequest reloadRequest = reloadRecord.get(0);
            if(StringUtils.equalsIgnoreCase(reloadRequest.getStatus(), Constants.newRequestStatus)) {
                return "tngResponsePersistChannel";
            } else if(StringUtils.equalsIgnoreCase(reloadRequest.getStatus(), Constants.pendingRequestStatus)) {
                return null; //TODO for response returned by RTM for manual cancellation response
            }
        }
        return null;
    }
}
