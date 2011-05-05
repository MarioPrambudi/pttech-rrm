package com.djavafactory.pttech.rrm.integration;


import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Concrete class to log the queue messages into database.
 *
 * @author Carine Leong
 */
public class MessagePersistence {
    private static final Log logger = LogFactory.getLog(MessagePersistence.class);

    /**
     * Method to log the queue messages into database
     *
     * @param requestList List of object. First item in the list contains ReloadRequest object whereas second item in the list contains either ReloadRequestMessage or ReloadResponseMessage.
     * @return ReloadRequestMessage or ReloadResponseMessage object
     */
    public Object mergeReloadRequest(List<Object> requestList) {
        if (requestList != null && requestList.size() == 2) {
            logger.info("[mergeReloadRequest - logging message] >> " + requestList.get(0));
            ((ReloadRequest) requestList.get(0)).merge();
            return requestList.get(1);
        }
        return null;
    }
}
