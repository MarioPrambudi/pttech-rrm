package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.ReloadRequestMessage;
import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class MessagePersistence {
    private static final Log logger = LogFactory.getLog(MessagePersistence.class);

    public Object mergeReloadRequest(List<Object> requestList) {
        if(requestList != null && requestList.size() == 2) {
            logger.info("[mergeReloadRequest - logging message] >> " + requestList.get(0));
            ((ReloadRequest) requestList.get(0)).merge();
            return requestList.get(1);
        }
        return null;
    }
}
